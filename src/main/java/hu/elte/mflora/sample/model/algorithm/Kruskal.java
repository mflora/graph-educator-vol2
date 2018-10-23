package hu.elte.mflora.sample.model.algorithm;

import javafx.scene.paint.Color;
import hu.elte.mflora.sample.persistence.Cache;
import hu.elte.mflora.sample.persistence.graph.*;
import hu.elte.mflora.sample.util.*;
import hu.elte.mflora.sample.util.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mina on 2017. 11. 14..
 */
public class Kruskal implements IAlgorithm {

    private IBaseGraph graph;
    private Map<Vertex, Integer> halmaz;
    private Map<Edge, Color> colorEdge;
    private Map<Vertex, Color> colorVertex;
    private Matrix<String> table;

    public Kruskal(IBaseGraph g){
        graph = g;
    }

    @Override
    public void first(Vertex start){

        halmaz = new HashMap<>(graph.vertices().size());
        colorEdge = new HashMap<>(graph.vertices().size());
        colorVertex = null;

        java.util.List<String> keys = new LinkedList<>();
        java.util.List<String> row = new LinkedList<>();
        row.add("Parent");
        row.add("Distance");

        int i = 0;
        for(Vertex v : graph.vertices()){
            halmaz.put(v, i);
            keys.add(v.getLabel());
            ++i;
        }

        for(Edge e : graph.edges()){
            colorEdge.put(e, Color.BLACK);
        }

    }

    @Override
    public void next(){
        Edge e = getWhiteEdgeWithMinimalWeight();
        if(endsInSameSet(e)){
            colorEdge.put(e, Color.RED);
        }else{
            colorEdge.put(e, Color.BLUE);
            unify(e);
        }
    }

    @Override
    public Matrix<String> current(){
        return table;
    }

    @Override
    public boolean end(){
        return colorEdge.containsValue(Color.WHITE);
    }

    @Override
    public MyState updateColor(){
        throw new NotImplementedException();
    }

    private void unify(Edge e){

        Integer h1 = halmaz.get(e.getIn());
        Integer h2 = halmaz.get(e.getOut());


        for(Vertex v : graph.vertices()){
            if(halmaz.get(v).equals(h1)){
                halmaz.put(v, h2);
            }
        }
    }

    private boolean endsInSameSet(Edge e){
        return halmaz.get(e.getIn()).equals(halmaz.get(e.getOut()));
    }

    private Edge getWhiteEdgeWithMinimalWeight(){
        return new BaseEdge(new BaseVertex("asd", new Point(0,0)), new BaseVertex("asdasd", new Point(0,0)));
        //return graph.edgeSet().stream().filter(e -> color.get(e).equals(Color.WHITE)).min((a, b) -> (a.getWeight() - b.getWeight())).get();
    }


}
