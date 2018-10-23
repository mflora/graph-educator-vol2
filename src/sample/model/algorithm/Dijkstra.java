package sample.model.algorithm;

import javafx.scene.paint.Color;
import sample.persistence.Cache;
import sample.persistence.graph.*;
import sample.util.Matrix;
import sample.util.MyState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by Mina on 2017. 11. 14..
 */
public class Dijkstra implements IAlgorithm {

    private IBaseGraph graph;
    private Map<Vertex, Integer> distance;
    private Map<Vertex, Color> color;
    private Map<Edge, Color> edgeColor;
    private Map<Vertex, Vertex> parent;
    private Matrix<String> table;
    private Predicate<Vertex> filter;
    private Comparator<Vertex> compare;

    public Dijkstra(IBaseGraph g){

        filter = new Predicate<Vertex>() {
            @Override
            public boolean test(Vertex vertex) {
                return !color.get(vertex).equals(Color.BLACK);
            }
        };

        compare = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex v1, Vertex v2) {
                return distance.get(v1)-distance.get(v2);
            }
        };
    }


    @Override
    public void first(Vertex start){

        System.out.println("NULL A GRAFOM! " + (graph == null));

        distance = new HashMap<>(graph.vertices().size());
        color = new HashMap<>(graph.vertices().size());
        parent = new HashMap<>(graph.vertices().size());

        java.util.List<String> keys = new LinkedList<>();
        java.util.List<String> row = new LinkedList<>();
        row.add("Vertex");
        row.add("Parent");
        row.add("Distance");


        for(Vertex v : graph.vertices()){
            distance.put(v, Integer.MAX_VALUE);
            color.put(v, Color.WHITE);
            parent.put(v, null);
            keys.add(v.getLabel());
        }

        table = new Matrix<String>(keys.size()+1, row.size());
        table.setElementAt(0, 0, "Vertex");
        table.setElementAt(0, 1, "Parent");
        table.setElementAt(0, 2, "Distance");
        for(int i = 1; i < keys.size();++i){
            table.setElementAt(0, i, keys.get(i));
        }
        for(int i = 1; i < keys.size();++i){
            table.setElementAt(1, i, "-");
        }
        for(int i = 1; i < keys.size();++i){
            table.setElementAt(1, i, "°°");
        }

        for(int i = 0; i < keys.size(); ++i){
            if(start.getLabel().equals(keys.get(i))){
                table.setElementAt(2, i, "0");
                break;
            }
        }

        distance.put(start, 0);
        color.put(start, Color.GRAY);

        next();
    }

    @Override
    public Matrix<String> current(){
        return table;
    }

    @Override
    public void next(){

        Vertex v = graph.vertices().stream().filter(filter).min(compare).get();

        color.put(v, Color.BLACK);

        for(Edge e : graph.edgesOf(v)){
            Vertex n = e.getIn();
            //TODO: valószinuleg ez a new wdgezős cucc nem lesz jó
            if(distance.get(n) > distance.get(v) + graph.getEdgeWeight(new BaseEdge(v, n))){
                distance.put(n, distance.get(v) + graph.getEdgeWeight(new BaseEdge(v, n)));
                color.put(n, Color.GRAY);
                parent.put(n,v);
                int i = 0;
                for(Vertex x: distance.keySet()){
                    if(n.getLabel().equals(x.getLabel())){
                        table.setElementAt(2, i, distance.get(v) + graph.getEdgeWeight(new BaseEdge(v, n)).toString());
                        table.setElementAt(1, i, parent.get(x).getLabel());
                    }
                    ++i;
                }
            }
        }
    }

    @Override
    public boolean end(){
        return graph.vertices().stream().anyMatch(v -> color.get(v).equals(Color.GRAY));
    }

    @Override
    public MyState updateColor(){
        throw new NotImplementedException();
    }
}
