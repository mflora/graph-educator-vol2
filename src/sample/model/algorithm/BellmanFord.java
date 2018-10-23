package sample.model.algorithm;

import javafx.scene.paint.Color;
import sample.persistence.Cache;
import sample.persistence.graph.IBaseGraph;
import sample.persistence.graph.Edge;
import sample.persistence.graph.Vertex;
import sample.persistence.graph.WeightedEdge;
import sample.util.Matrix;
import sample.util.MyState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by Mina on 2017. 11. 14..
 */
public class BellmanFord implements IAlgorithm {

    private IBaseGraph graph;
    private Map<Vertex, Integer> distance;
    private Map<Vertex, Vertex> parent;
    private int counter;
    private Map<Vertex, Color> color;
    private Map<Edge, Color> eColor;
    private Matrix<String> table;
    private List<String> keys;
    private Queue<Vertex> q;

    public BellmanFord(IBaseGraph g) {
        graph = g;
        counter = 1;
    }

    @Override
    public void first(Vertex start) {

        distance = new HashMap<>(graph.vertices().size());
        parent = new HashMap<>(graph.vertices().size());
        color = new HashMap<>();
        keys = new LinkedList<>();
        eColor = new HashMap<>();
        q = new LinkedList<>();

        List<String> rows = new LinkedList<>();
        rows.add("Vertex");
        rows.add("Parent");
        rows.add("Distance");

        for (Vertex v : graph.vertices()) {
            distance.put(v, Integer.MAX_VALUE);
            parent.put(v, null);
            keys.add(v.getLabel());
            color.put(v, Color.WHITE);
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

        for (Edge e : graph.edges()) {
            eColor.put(e, Color.BLACK);
        }

        distance.put(start, 0);
        int index = keys.indexOf(start.getLabel());
        table.setElementAt(2, index + 1, "0");
        color.put(start, Color.GRAY);
        q.add(start);

    }

    @Override
    public void next() {

        Map<Vertex, Integer> bufferDistance = new HashMap<>();
        Map<Vertex, Vertex> bufferParent = new HashMap<>();
        Map<Edge, Color> bufferColor = new HashMap<>();
        Queue<Vertex> tmpQ = new LinkedList<>();

        for (Edge e : graph.edges()) {
            Vertex u = e.getIn();
            if (q.contains(e.getOut())) {
                if (distance.get(u) > distance.get(e.getOut()) + graph.getEdgeWeight(e)) {
                    if (parent.get(u) != null) {
                        eColor.put(graph.getEdge(parent.get(u), u), Color.BLACK);
                    }

                    //bufferDistance.put(e.getIn(),(distance.get(e.getOut()) + graph.getEdgeWeight(e)));
                    distance.put(u, (distance.get(e.getOut()) + graph.getEdgeWeight(e)));
                    //bufferParent.put(e.getIn(), e.getOut());
                    parent.put(u, e.getOut());
                    //bufferColor.put(e, Color.BLUE);
                    eColor.put(e, Color.BLUE);
                    tmpQ.add(u);
                }
            }

        }

        q = tmpQ;
       /*
        for (Edge e : bufferColor.keySet()) {
            //if(distance.get(e) == null){
            distance.put(e.getIn(), bufferDistance.get(e.getIn()));
            //}else{
            //    distance.get(e.getIn()) = bufferDistance.get(e.getIn());
            //}
            parent.put(e.getIn(), bufferParent.get(e.getIn()));
            eColor.put(e, Color.BLUE);
        }
        */

        bufferColor.clear();


        counter++;

    }

    @Override
    public Matrix<String> current() {

        return table;

    }

    @Override
    public boolean end() {
        System.out.println("****************************************" + (graph.vertices().size()));
        return !(counter < graph.vertices().size());
    }

    @Override
    public MyState updateColor() {
        return (new MyState(null, eColor, table));
    }


}