package sample.persistence.graph;

import java.util.List;
import java.util.Set;

/**
 * Created by Mina on 2017. 09. 21..
 */
public interface IBaseGraph {
    void addEdge(Vertex source, Vertex target);
    void addEdge(Vertex source, Vertex target, Integer weight);
    void addVertex(Vertex V);
    boolean containsEdge(Edge e);
    boolean containsEdge(Vertex source, Vertex target);
    boolean containsVertex(Vertex v);
    boolean isWeighted();
    List<Edge> edges();
    List<Edge> edgesOf(Vertex v);
    // Ez valószinu nem kell, mert nincs duplaél meg hurokél
    Set<Edge> getAllEdges(Vertex source, Vertex target);
    boolean getDirection();
    Edge getEdge(Vertex source, Vertex target);
    Vertex getEdgeSource(Edge e);
    Vertex getEdgeTarget(Edge e);
    Integer getEdgeWeight(Edge e);
    Vertex getVertexByLabel(String label);
    //removeAll cuccok kellenek?
    void removeEdge(Edge e);
    void removeVertex(Vertex v);
    List<Vertex> vertices();

}
