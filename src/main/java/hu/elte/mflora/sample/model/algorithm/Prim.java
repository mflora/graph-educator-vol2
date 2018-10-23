package hu.elte.mflora.sample.model.algorithm;

import javafx.scene.paint.Color;
import hu.elte.mflora.sample.persistence.Cache;
import hu.elte.mflora.sample.persistence.graph.BaseEdge;
import hu.elte.mflora.sample.persistence.graph.Edge;
import hu.elte.mflora.sample.persistence.graph.Vertex;
import hu.elte.mflora.sample.persistence.graph.IBaseGraph;
import hu.elte.mflora.sample.util.Matrix;
import hu.elte.mflora.sample.util.MyState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by Mina on 2017. 11. 14..
 */
public class Prim implements IAlgorithm{

    private IBaseGraph graph;
    private Map<Vertex, Vertex> parent;
    private Map<Vertex, Color> colorVertex;
    private Map<Edge, Color> colorEdge;
    private Map<Vertex, Integer> distance;
    private Set<Vertex> minQ;
    private Matrix<String> table;
    private Vertex startCsucs;

    public Prim(IBaseGraph g){
        graph = g;
    }

    @Override
    public void first(Vertex start){

        parent = new HashMap<>(graph.vertices().size());
        colorVertex = new HashMap<>(graph.vertices().size());
        colorEdge = new HashMap<>(graph.edges().size());
        distance = new HashMap<>(graph.vertices().size());
        minQ = new HashSet<>();

        java.util.List<String> keys = new LinkedList<>();
        java.util.List<String> rows = new LinkedList<>();
        rows.add("Vertex");
        rows.add("Parent");
        rows.add("Distance");

        for(Vertex v : graph.vertices()){
            parent.put(v, null);
            colorVertex.put(v, Color.WHITE);
            distance.put(v, Integer.MAX_VALUE);
            minQ.add(v);
            keys.add(v.getLabel());
        }

        table = new Matrix<String>(rows.size(), keys.size() + 1);

        table.setElementAt(0, 0, rows.get(0));
        table.setElementAt(1, 0, rows.get(1));
        table.setElementAt(2, 0, rows.get(2));

        int i = 0;
        for (Vertex v : graph.vertices()) {
            table.setElementAt(0, i + 1, v.getLabel());
            table.setElementAt(1, i + 1, (parent.get(v) == null) ? "None" : parent.get(v).getLabel());
            table.setElementAt(2, i + 1, distance.get(v).toString());
            i++;
        }

        /** /
        for(Edge e : graph.edges()){
            colorEdge.put(e, Color.BLACK);
        }
        /**/

        distance.put(start, 0);
        colorVertex.put(start, Color.MAGENTA);
        startCsucs=start;


    }

    @Override
    public void next(){
        Vertex u = kiveszMin();

        for(Edge e : graph.edgesOf(u)){
            Vertex v = e.getIn();
            if(minQ.contains(v) && graph.getEdgeWeight(graph.getEdge(u, v)) < distance.get(v)) {
                distance.put(v,graph.getEdgeWeight(graph.getEdge(u, v)));
                parent.put(v, u);
                colorVertex.put(v, Color.GRAY);
            }
        }
            colorVertex.put(u, Color.MAGENTA);
        if (!startCsucs.equals(u)) {
            colorEdge.put(graph.getEdge(u, parent.get(u)), Color.CORAL);
            colorEdge.put(graph.getEdge(parent.get(u), u), Color.CORAL);
            //System.out.println("BLUE EDGE: " + graph.getEdge(parent.get(u), u));
        }


    }

    @Override
    public Matrix<String> current(){
        return table;
    }

    @Override
    public boolean end(){
        return minQ.isEmpty();
    }

    @Override
    public MyState updateColor(){
        return (new MyState(colorVertex, colorEdge, table));
    }

    private Vertex kiveszMin(){
        //System.out.println("MINQ = " + minQ);

        Vertex minVertex = minQ.stream().min((a, b) -> (distance.get(a) - distance.get(b))).get();

        //System.out.println("minVertex " + minVertex);

        minQ.remove(minVertex);

        return minVertex;

    }
}

