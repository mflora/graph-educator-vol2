package hu.elte.mflora.sample.persistence.graph;

import java.util.*;

/**
 * Created by Mina on 2017. 09. 20..
 */
public class BaseListGraph implements IBaseGraph {

    private Map<Vertex, List<Vertex>> graph;
    private List<Edge> edges;
    private boolean directed;
    private boolean weighted;

    public BaseListGraph(boolean direction, boolean weight){
        this.graph = new HashMap<>();
        this.edges = new LinkedList<>();
        this.directed = direction;
        this.weighted = weight;
    }

    public BaseListGraph(){
        this.graph = new HashMap<>();
        this.edges = new LinkedList<>();
        this.directed = false;
        this.weighted = false;
    }

    public BaseListGraph(BaseListGraph other){
        this.directed = other.getDirection();

        Map<Vertex, List<Vertex>> m = new HashMap();
        for(Vertex v : other.getGraph().keySet()){
            List<Vertex> tmpList = new LinkedList<>();
            for(Vertex u : other.getGraph().get(v)){
                tmpList.add(new BaseVertex((BaseVertex)u));
            }
            m.put(new BaseVertex((BaseVertex)v), tmpList);
        }
        this.graph = m;

        List<Edge> myEdges = new LinkedList<>();
        for(Edge e : other.edges()){
            if(e instanceof WeightedEdge){
                myEdges.add(new WeightedEdge((WeightedEdge)e));
            }else{
                myEdges.add(new BaseEdge((BaseEdge)e));
            }
        }
        this.edges = myEdges;


    }


    @Override
    public void addEdge(Vertex source, Vertex target){
        if(source == null || target == null){
            throw new IllegalArgumentException("Vertices can't be null");
        }

        if(source.equals(target)){
            throw new IllegalArgumentException("Source vertex cannot be equal to target vertex!");
        }

        /** /
        if(directed){
            if(graph.containsKey(source) && graph.get(source).contains(target)){
                throw new IllegalArgumentException("Edge already exist");
            }
        }else{
            if(graph.containsKey(source) && graph.get(source).contains(target) && graph.containsKey(target) && graph.get(target).contains(source)){
                throw new IllegalArgumentException(("Edge already exist"));
            }
        }
        /**/

        if(!graph.containsKey(source)){
            List<Vertex> tmpList = new LinkedList<>();
            tmpList.add(target);
            graph.put(source, tmpList);
            edges.add(new BaseEdge(source, target));
        }else{
            if(!graph.get(source).contains(target)){
                graph.get(source).add(target);
                edges.add(new BaseEdge(source, target));
            }
        }

        if(!directed){
            if(!graph.containsKey(target)){
                List<Vertex> tmpList = new LinkedList<>();
                tmpList.add(source);
                graph.put(target, tmpList);
                edges.add(new BaseEdge(target, source));
            }else{
                if(!graph.get(target).contains(source)){
                    graph.get(target).add(source);
                    edges.add(new BaseEdge(target, source));
                }
            }
        }
    }

    @Override
    public void addEdge(Vertex source, Vertex target, Integer weight){
        if(source == null || target == null){
            throw new IllegalArgumentException("Vertices can't be null");
        }

        if(source.equals(target)){
            throw new IllegalArgumentException("Source vertex cannot be equal to target vertex!");
        }

        /** /
        if(directed){
            if(graph.containsKey(source) && graph.get(source).contains(target)){
                throw new IllegalArgumentException("Edge already exist");
            }
        }else{
            if(graph.containsKey(source) && graph.get(source).contains(target) && graph.containsKey(target) && graph.get(target).contains(source)){
                throw new IllegalArgumentException(("Edge already exist"));
            }
        }
        /**/

        if(!graph.containsKey(source)){
            List<Vertex> tmpList = new LinkedList<>();
            tmpList.add(target);
            graph.put(source, tmpList);
            edges.add(new WeightedEdge(source, target, weight));
        }else{
            if(!graph.get(source).contains(target)){
                graph.get(source).add(target);
                edges.add(new WeightedEdge(source, target, weight));
            }
        }

        if(!directed){
            if(!graph.containsKey(target)){
                List<Vertex> tmpList = new LinkedList<>();
                tmpList.add(source);
                graph.put(target, tmpList);
                edges.add(new WeightedEdge(target, source, weight));
            }else{
                if(!graph.get(target).contains(source)){
                    graph.get(target).add(source);
                    edges.add(new WeightedEdge(target, source, weight));
                }
            }
        }
    }

    @Override
    public boolean isWeighted(){
        LinkedList<Edge> tmpList = new LinkedList<>();
        for(Edge e : edges()){
           tmpList.add(e);
           break;
    }

        return tmpList.get(0) instanceof WeightedEdge;
    }

    @Override
    public void addVertex(Vertex v){
       if(v == null){
           throw new IllegalArgumentException("Vertice cannot be null!");
       }
       if(v.getPosX()<20 || v.getPosY()<20){
           throw new IllegalArgumentException("Vertex min position is (20,20)");
       }

       for(Vertex vx: graph.keySet()){
           if((v.getPosX()-vx.getPosX()) * (v.getPosX()-vx.getPosX()) + (v.getPosY()-vx.getPosY()) * (v.getPosY()-vx.getPosY()) < 4900){
               throw new IllegalArgumentException("Vertices can't be closer than 70 pixel!");
           }
       }

       graph.put(v, new LinkedList());
    }

    @Override
    public boolean containsEdge(Edge e){
        try {
            if(!graph.containsKey(e.getOut())){
                return false;
            }else{
                if(!graph.get(e.getOut()).contains(e.getIn())){
                    return false;
                }
            }
        } catch (NullPointerException ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean containsEdge(Vertex source, Vertex target){
        try {
            if(!graph.containsKey(source)){
                return false;
            }else{
                if(!graph.get(source).contains(target)){
                    return false;
                }
            }
        } catch (NullPointerException e) {
           return false;
        }

        return true;
    }

    @Override
    public boolean containsVertex(Vertex v){
        return graph.containsKey(v);
    }

    @Override
    public List<Edge> edges(){
        return new LinkedList<Edge>(edges);
    }

    @Override
    public List<Edge> edgesOf(Vertex v){
        List<Edge> ret = new LinkedList<>();

        for(Edge e : edges){
            if(v.equals(e.getOut())){
                ret.add(e);
            }
        }

        return ret;
    }


    // Ez valószinu nem kell, mert nincs duplaél meg hurokél
    @Override
    public Set<Edge> getAllEdges(Vertex source, Vertex target){
        return null;
    }

    @Override
    public boolean getDirection(){
        return directed;
    }

    @Override
    public Edge getEdge(Vertex source, Vertex target){
        for(Edge e : edges){
            if(e.getOut().equals(source) && e.getIn().equals(target)){
                return e;
            }
        }

        return null;
    }

    @Override
    public Vertex getEdgeSource(Edge e){
        return e.getOut();
    }

    @Override
    public Vertex getEdgeTarget(Edge e){
        return e.getIn();
    }

    public Edge getEdgeByLabels(String out, String in){
        for(Edge e : edges){
            if(e.getOut().getLabel().equals(out) && e.getIn().getLabel().equals(in)){
                return e;
            }
        }

        return null;
    }

    @Override
    public Integer getEdgeWeight(Edge e){
        if(e instanceof WeightedEdge){
            return ((WeightedEdge) e).getWeight();
        }else{
            return null;
        }
    }


    @Override
    public Vertex getVertexByLabel(String label){
        Vertex ret = null;

        Set<Vertex> ks = graph.keySet();

        for(Vertex v : ks){
            if(v.getLabel().equals(label)){
                ret = v;
                return ret;
            }
        }

        return ret;
}



    @Override
    public void removeEdge(Edge e){
        if(edges.contains(e)) {
            edges.remove(e);
        }else{
            throw new NoSuchElementException("There isn't edge betveen " + e.getOut().getLabel()  + " and " + e.getIn().getLabel());
        }
    }

    @Override
    public void removeVertex(Vertex v){
        if(graph.containsKey(v)){
            graph.remove(v);
            for(Edge e : edges){
                if(v.equals(e.getOut()) || v.equals(e.getIn())){
                    edges.remove(e);
                }
            }
        }else{
            throw new NoSuchElementException("Vertex doesn't exist");
        }
    }

    public String verticeString(){
        String ret = "";

        for(Vertex v : vertices()){
            ret = ret + v.getLabel() + " ";
        }

        return ret;
    }

    @Override
    public List<Vertex> vertices(){
        List<Vertex> ret = new LinkedList<>();
        for(Vertex v : graph.keySet()){
            ret.add(v);
        }
        return ret;
    }

    public Map<Vertex, List<Vertex>> getGraph(){
        return graph;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Directed: " + directed  + " Edges: " + edges.toString() + "Graph: " + graph.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }

        if(!(o instanceof BaseListGraph)){
            return false;
        }

        BaseListGraph blg = (BaseListGraph)o;

        if(this.getDirection() == blg.getDirection() && this.edges().equals(blg.edges()) && this.getGraph().equals(blg.getGraph())){
            return true;
        }else{
            return false;
        }
    }
}
