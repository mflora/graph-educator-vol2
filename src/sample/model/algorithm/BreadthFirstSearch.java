package sample.model.algorithm;

import javafx.scene.paint.Color;
import sample.persistence.Cache;
import sample.persistence.graph.Edge;
import sample.persistence.graph.Vertex;
import sample.persistence.graph.IBaseGraph;
import sample.util.Matrix;
import sample.util.MyState;

import java.util.*;

/**
 * Created by Mina on 2017. 11. 14..
 */
public class BreadthFirstSearch implements IAlgorithm{
    private IBaseGraph graph;
    private Map<Vertex, Color> color;
    private Map<Edge, Color> eColor;
    private Map<Vertex, Vertex> parent;
    private Map<Vertex, Integer> depth;
    private Queue<Vertex> queue;
    private Matrix<String> table;
    private LinkedList<String> keys;

    public BreadthFirstSearch(IBaseGraph g){
        graph = g;
        eColor = null;
    }

    @Override
    public void first(Vertex start){

        System.out.println("INIT");

        color = new HashMap<>(graph.vertices().size());
        parent = new HashMap<>(graph.vertices().size());
        depth = new HashMap<>(graph.vertices().size());
        queue = new LinkedList<>();
        keys = new LinkedList<>();

        java.util.List<String> rows = new LinkedList<>();
        rows.add("Vertex");
        rows.add("Parent");
        rows.add("Depth");

        for(Vertex v : graph.vertices()){
            color.put(v, Color.WHITE);
            depth.put(v, Integer.MAX_VALUE);
            parent.put(v, null);
            keys.add(v.getLabel());
        }

        table = new Matrix<String>(rows.size(), keys.size()+1);

        table.setElementAt(0, 0, rows.get(0));
        table.setElementAt(1, 0, rows.get(1));
        table.setElementAt(2, 0, rows.get(2));

        int i = 0;
        for(Vertex v : graph.vertices()){
            table.setElementAt(0, i + 1, v.getLabel());
            table.setElementAt(1, i + 1, (parent.get(v) == null) ? "None" : parent.get(v).getLabel());
            table.setElementAt(2, i + 1, depth.get(v).toString());
            i++;
        }

        color.put(start, Color.GRAY);
        depth.put(start, 0);
        int index = keys.indexOf(start.getLabel());
        table.setElementAt(2, index + 1, "0");

        queue.add(start);

        System.out.println("INIT DONE");
        System.out.println("COLORS: " + color.toString());

    }

    @Override
    public void next(){
        System.out.println("NEXT");
        Vertex v = queue.poll();
        color.put(v, Color.MAGENTA);
        for(Edge e : graph.edgesOf(v)){
            Vertex u = e.getIn();
            if(color.get(u).equals(Color.WHITE)){
                color.put(u, Color.GRAY);
                depth.put(u, depth.get(v)+1);
                parent.put(u, v);
                queue.add(u);
                for(int i = 0; i < keys.size(); ++i){
                    if(u.getLabel().equals(keys.get(i))){
                        table.setElementAt(1, i +1, v.getLabel());
                        table.setElementAt(2, i +1 , depth.get(u).toString());
                    }
                }

            }
        }

        current();

    }

    @Override public Matrix<String> current(){
        System.out.println(table.toString());
        return table;
    }

    @Override
    public boolean end(){
        return queue.peek() == null;
    }

    @Override
    public MyState updateColor(){
        return (new MyState(color, eColor, table));
    }
}
