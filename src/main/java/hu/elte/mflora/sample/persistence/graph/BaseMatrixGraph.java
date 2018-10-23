package hu.elte.mflora.sample.persistence.graph;

import hu.elte.mflora.sample.util.Matrix;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Mina on 2017. 09. 20..
 */
public class BaseMatrixGraph implements IBaseGraph {

    private Matrix<Integer> mtx;
    private List<Vertex> vertices;
    private boolean directed;
    private boolean weighted;


    public BaseMatrixGraph(boolean direction, boolean weighted){
        this.mtx = new Matrix<>();
        this.vertices = new LinkedList<>();
        this.directed = direction;
        this.weighted = weighted;
    }

    public BaseMatrixGraph(BaseMatrixGraph other){
        this.directed = other.getDirection();
        this.weighted = other.getWeighted();
        this.mtx = new Matrix<>(other.getGraph());
        List<Vertex> tmpVertices = new LinkedList<>();

        for(Vertex v : other.vertices){
            tmpVertices.add(new BaseVertex((BaseVertex)v));
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

    public boolean getWeighted(){
        return weighted;
    }


        @Override
    public void addEdge(Vertex source, Vertex target){
        if(source == null || target == null){
            throw new IllegalArgumentException("Vertices can't be null");
        }

        if(source.equals(target)){
            throw new IllegalArgumentException("Source vertex cannot be equal to target vertex!");
        }

        if(!vertices.contains(source)){
            vertices.add(source);
            mtx.addNewColumn();
            mtx.addNewRow();
        }

        if(!vertices.contains(target)){
            vertices.add(source);
            mtx.addNewColumn();
            mtx.addNewRow();
        }

        int sourceIndex = vertices.indexOf(source);
        int targetIndex = vertices.indexOf(target);

        mtx.setElementAt(sourceIndex, targetIndex, 1);
    }
    public void addEdge(Vertex source, Vertex target, Integer weight){
        if(source == null || target == null){
            throw new IllegalArgumentException("Vertices can't be null");
        }

        if(source.equals(target)){
            throw new IllegalArgumentException("Source vertex cannot be equal to target vertex!");
        }

        if(!vertices.contains(source)){
            vertices.add(source);
            mtx.addNewColumn();
            mtx.addNewRow();
        }

        if(!vertices.contains(target)){
            vertices.add(source);
            mtx.addNewColumn();
            mtx.addNewRow();
        }

        int sourceIndex = vertices.indexOf(source);
        int targetIndex = vertices.indexOf(target);

        mtx.setElementAt(sourceIndex, targetIndex, weight);
    }

    @Override
    public void addVertex(Vertex v){
        if(v == null){
            throw new IllegalArgumentException("Vertice cannot be null!");
        }

        if(v.getPosX()<20 || v.getPosY()<20){
            throw new IllegalArgumentException("Vertex min position is (20,20)");
        }

        for(Vertex vx: vertices){
            if((v.getPosX()-vx.getPosX()) * (v.getPosX()-vx.getPosX()) + (v.getPosY()-vx.getPosY()) * (v.getPosY()-vx.getPosY()) < 4900){
                throw new IllegalArgumentException("Vertices can't be closer than 70 pixel!");
            }
        }

       vertices.add(v);
        mtx.addNewColumn();
        mtx.addNewRow();
    }

    @Override
    public boolean containsEdge(Edge e){
        return (mtx.getElementAt(vertices.indexOf(e.getOut()), vertices.indexOf(e.getIn())) != null );
    }

    @Override
    public boolean containsEdge(Vertex source, Vertex target){
        return (mtx.getElementAt(vertices.indexOf(source), vertices.indexOf(target)) != null );
    }

    @Override
    public boolean containsVertex(Vertex v){
        return vertices.contains(v);
    }

    @Override
    public List<Edge> edges(){
        List<Edge> ret = new LinkedList<>();

        for(int i = 0; i < mtx.getWidth(); ++i){
            for(int j = 0; j < mtx.getHeight(); ++j){
                if(mtx.getElementAt(i, j) != null){
                    if(weighted){
                        ret.add(new WeightedEdge(vertices.get(i), vertices.get(j), mtx.getElementAt(i,j)));
                    }else{
                        ret.add(new BaseEdge(vertices.get(i), vertices.get(j)));
                    }
                }
            }
        }

        return ret;
    }

    @Override
    public List<Edge> edgesOf(Vertex v){
        int index = vertices.indexOf(v);
        List<Edge> ret = new LinkedList<>();

        for(int i = 0; i < mtx.getWidth(); ++i){
            if(mtx.getElementAt(index, i) != null){
                if(weighted){
                    ret.add(new WeightedEdge(vertices.get(index), vertices.get(i), mtx.getElementAt(index, i)));
                }else{
                    ret.add(new BaseEdge(vertices.get(index), vertices.get(i)));
                }
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
        return weighted ? (new WeightedEdge(source, target, mtx.getElementAt(vertices.indexOf(source), vertices.indexOf(target)))) : (new BaseEdge(source, target));
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
        int outInd = vertices.indexOf(out);
        int inInd = vertices.indexOf(in);
        Edge ret = null;

        if(mtx.getElementAt(outInd, inInd) != null){
            if(weighted){
                ret = (new WeightedEdge(vertices.get(outInd), vertices.get(inInd), mtx.getElementAt(outInd, inInd)));
            }else{
                ret = (new BaseEdge(vertices.get(outInd), vertices.get(inInd)));
            }
        }

        return ret;

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

        for(Vertex v : vertices){
            if(v.getLabel().equals(label)){
                ret = v;
                return ret;
            }
        }

        return ret;
    }


    @Override
    public void removeEdge(Edge e){
        mtx.setElementAt(vertices.indexOf(e.getOut()), vertices.indexOf(e.getIn()), null);
    }

    @Override
    public void removeVertex(Vertex v){
        mtx.removeColumn(vertices.indexOf(v));
        mtx.removeRow(vertices.indexOf(v));
        vertices.remove(v);
    }

    @Override
    public List<Vertex> vertices(){
        return vertices;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Weighted: " + weighted + " Directed: " + directed + " Vertexek: " + vertices.toString() + "\nThe Matrix:\n" + mtx.toString();
    }

    public Matrix<Integer> getGraph(){
        return mtx;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
        return false;
    }

        if(!(o instanceof BaseMatrixGraph)){
            return false;
        }

        BaseMatrixGraph mx = (BaseMatrixGraph) o;

        if(this.getDirection() == mx.getDirection() && this.mtx.equals(mx.getGraph()) && this.vertices().equals(mx.vertices())){
            return true;
        }else{
            return false;
        }
    }
}
