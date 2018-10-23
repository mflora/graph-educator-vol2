package sample.persistence.graph;

/**
 * Created by Mina on 2017. 09. 19..
 */
public class BaseEdge implements Edge {

    private final Vertex out;
    private final Vertex in;

    public BaseEdge(Vertex out, Vertex in) {
        if(out == null || in == null){
            throw new IllegalArgumentException("Vertices cannot be null!");
        }
        this.out = out;
        this.in = in;
    }

    public BaseEdge(BaseEdge other){
        this.out = new BaseVertex((BaseVertex) other.getOut());
        this.in = new BaseVertex((BaseVertex) other.getIn());
    }

    @Override
    public Vertex getOut() {
        return out;
    }

    @Override
    public Vertex getIn() {
        return in;
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }

        if(!(o instanceof BaseEdge)){
            return false;
        }

        Edge v = (Edge)o;

        return (this.getIn().equals(v.getIn()) && this.getOut().equals(v.getOut()));
    }

    @Override
    public String toString(){
        return out.toString() + "->" + in.toString();
    }

    @Override
    public int hashCode(){
        return 0;
    }
}
