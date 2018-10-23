package sample.persistence.graph;

/**
 * Created by Mina on 2017. 09. 19..
 */
public interface Edge {
    Vertex getOut();
    Vertex getIn();
}
