package sample.model.algorithm;

import sample.persistence.Cache;
import sample.persistence.graph.Edge;
import sample.persistence.graph.Vertex;
import sample.persistence.graph.IBaseGraph;
import sample.util.Matrix;
import sample.util.MyState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Mina on 2017. 11. 14..
 */
public class DepthFirstSearch implements IAlgorithm {

    private IBaseGraph graph;
    private Map<Vertex, Integer> depth;
    private Map<Vertex, Integer> end;
    private Map<Vertex, Color> color;
    private Map<Vertex, Vertex> parent;
    private Stack<Vertex> stack;
    private Matrix<String> table;
    private int depthNum;
    private int endNum;

    private Predicate<Vertex> filter;


    public DepthFirstSearch(IBaseGraph g){
        graph = g;

        filter = new Predicate<Vertex>() {
            @Override
            public boolean test(Vertex vertex) {
                return color.get(vertex).equals(Color.WHITE);
            }
        };
    }

    @Override
    public void first(Vertex start){

        depth = new HashMap<>(graph.vertices().size());
        end = new HashMap<>(graph.vertices().size());
        color = new HashMap<>(graph.vertices().size());
        parent = new HashMap<>(graph.vertices().size());
        stack = new Stack<>();

        java.util.List<String> keys = new LinkedList<>();
        java.util.List<String> rows = new LinkedList<>();
        rows.add("Vertex");
        rows.add("Parent");
        rows.add("Depth");

        for(Vertex v : graph.vertices()){
            color.put(v, Color.WHITE);
            parent.put(v, null);
            depth.put(v, 0);
            end.put(v,0);
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

        depthNum = 0;
        endNum = 0;

        stack.push(start);
        color.put(start, Color.GRAY);
        depth.put(start, 1);
        depthNum++;

        //next();

    }

    @Override
    public void next(){

        Vertex v = stack.pop();
        //color.put(v, Color.BLACK);
        color.put(v, Color.MAGENTA);
        end.put(v, endNum);
        endNum++;

        for(Edge e : graph.edgesOf(v)){
            Vertex u = e.getIn();
            if(color.get(u).equals(Color.WHITE)){
                color.put(u, Color.GRAY);
                depth.put(u, depthNum);
                depthNum++;
                parent.put(u, v);
                stack.push(u);
            }
        }


    }

    @Override
    public Matrix<String> current(){
        return table;
    }

    @Override
    public boolean end(){
        return stack.empty();
    }

    @Override
    public MyState updateColor(){
        throw new NotImplementedException();
    }
}
