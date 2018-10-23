package sample.persistence.graph;

import com.sun.media.sound.InvalidFormatException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sample.util.Point;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Mina on 2016. 11. 01..
 */
public final class FileIO {

    /**/

    public static IBaseGraph loadFromFile(String filename) throws Exception {

        File file = new File(filename);
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document doc = dBuilder.parse(file);
        IBaseGraph graph;

        if(doc.getElementsByTagName("header").getLength() <= 0){
            throw new InvalidFormatException("Invalid file format. Header needed.");
        }

        if(doc.getElementsByTagName("direction").getLength() <= 0){
            throw new InvalidFormatException("Invalid file format. Direction tag needed.");
        }

        boolean direction;

        if (doc.getElementsByTagName("direction").item(0).getTextContent().equals("directed")) {
            direction = true;
        } else {
            direction = false;
        }

        boolean weighted = false;
        NodeList nList = doc.getElementsByTagName("edge");

        for (int i = 0; i < nList.getLength() && !weighted ; ++i) {
            Node nNode = nList.item(i);
            Element eElement = (Element) nNode;
            NodeList nl = eElement.getElementsByTagName("weight");
            if (nl.getLength() > 0) {
               weighted = true;
            }
        }

        graph = new BaseListGraph(direction, weighted);

        nList = doc.getElementsByTagName("vertex");

        for (int i = 0; i < nList.getLength(); ++i) {
            Node nNode = nList.item(i);
            Element eElement = (Element) nNode;
            if(eElement.getElementsByTagName("label").getLength()<= 0){
                throw new InvalidFormatException("Invalid file format. Missing label tag");
            }
            //System.out.println("POSX: " + eElement.hasAttribute("posX") + " POSX: " + eElement.hasAttribute("posY"));
            if(eElement.getElementsByTagName("posX").getLength() <= 0 || eElement.getElementsByTagName("posY").getLength() <= 0){
                throw new InvalidFormatException("Invalid file format. Missing X or Y Coordinate.");
            }

            /*if(eElement.getElementsByTagName("posY").getLength() <= 0){
                throw new InvalidFormatException("Invalid file format. Coordinate Y needed.");
            }*/

            if (!eElement.hasAttribute("weight")) {
                graph.addVertex(new BaseVertex((eElement.getElementsByTagName("label").item(0).getTextContent()), new Point(Integer.parseInt(eElement.getElementsByTagName("posX").item(0).getTextContent()), Integer.parseInt(eElement.getElementsByTagName("posY").item(0).getTextContent()))));
            } else {
                graph.addVertex(new WeightedVertex((eElement.getElementsByTagName("label").item(0).getTextContent()), new Point(Integer.parseInt(eElement.getElementsByTagName("posX").item(0).getTextContent()), Integer.parseInt(eElement.getElementsByTagName("posY").item(0).getTextContent())) ,Integer.parseInt(((eElement.getElementsByTagName("weight").item(0).getTextContent())))));
            }

        }

        nList = doc.getElementsByTagName("edge");

        for (int i = 0; i < nList.getLength(); ++i) {
            Node nNode = nList.item(i);
            Element eElement = (Element) nNode;
            NodeList nl = eElement.getElementsByTagName("weight");
            if (nl.getLength() <= 0) {
                if(!weighted) {
                    graph.addEdge(graph.getVertexByLabel(eElement.getElementsByTagName("start").item(0).getTextContent()), graph.getVertexByLabel(eElement.getElementsByTagName("end").item(0).getTextContent()));
                }else{
                    graph.addEdge(graph.getVertexByLabel(eElement.getElementsByTagName("start").item(0).getTextContent()), graph.getVertexByLabel(eElement.getElementsByTagName("end").item(0).getTextContent()), 1);
                }
            } else {
                graph.addEdge(graph.getVertexByLabel(eElement.getElementsByTagName("start").item(0).getTextContent()), graph.getVertexByLabel(eElement.getElementsByTagName("end").item(0).getTextContent()), Integer.parseInt(eElement.getElementsByTagName("weight").item(0).getTextContent()));
            }
        }

        return graph;
    }

    public static void saveToFile(String filename, IBaseGraph graph) throws FileNotFoundException {

        PrintWriter pw = new PrintWriter(new File(filename));

        pw.println("<?xml version=\"1.0\"?>");
        pw.println("<graph>");
        pw.println("\t<header>");
        if (graph.getDirection()) {
            pw.println("\t\t<direction>directed</direction>");
        } else {
            pw.println("\t\t<direction>undirected</direction>");
        }
        /** /
         if(graph.edgeSet().toArray()[0] instanceof WeightedEdge) {
         pw.println("\t\t<direction>directed</direction>");
         }else{
         pw.println("\t\t<direction>undirected</direction>");
         }
         /**/

        /**/
        pw.println("\t</header>");
        pw.println("\t<data>");

        for (Vertex v : graph.vertices()) {
            pw.println("\t\t<vertex>");
            pw.println("\t\t\t<label>" + v.getLabel() + "</label>");

            pw.println("\t\t\t<position>");
            pw.println("\t\t\t\t<posX>" + v.getPosX() + "</posX>");
            pw.println("\t\t\t\t<posY>" + v.getPosY() + "</posY>");
            pw.println("\t\t\t</position>");

            if (v instanceof WeightedVertex) {
                WeightedVertex wv = (WeightedVertex) v;
                pw.println("\t\t\t<weight>" + wv.getWeight() + "</weight>");
            }

            pw.println("\t\t</vertex>");

        }

        for (Edge e : graph.edges()) {
            pw.println("\t\t<edge>");
            if (e instanceof WeightedEdge) {
                WeightedEdge we = (WeightedEdge) e;
                pw.println("\t\t\t<weight>" + we.getWeight() + "</weight>");
            }
            pw.println("\t\t\t<start>" + e.getOut().getLabel() + "</start>");
            pw.println("\t\t\t<end>" + e.getIn().getLabel() + "</end>");
            pw.println("\t\t</edge>");
        }

        pw.println("\t</data>");
        pw.println("</graph>");


        pw.close();

    }

    /**/
}

