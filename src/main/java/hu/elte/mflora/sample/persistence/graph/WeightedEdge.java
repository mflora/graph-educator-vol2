package hu.elte.mflora.sample.persistence.graph;

/**
 * Created by Mina on 2017. 09. 19..
 */
public class WeightedEdge extends BaseEdge {

    private Integer weight;
    //private final Vertex out;
    //private final Vertex in;

    public WeightedEdge(Vertex out, Vertex in, Integer weight) {
        super(out, in);
        this.weight = weight;
    }

    public WeightedEdge(WeightedEdge other){
        super(new BaseEdge(other.getOut(), other.getIn()));
        this.weight = other.getWeight();

    }

    @Override
    public String toString() {
        return super.toString() + " Weight: " + weight;
    }

    @Override
    public boolean equals(Object o) {

        if (super.equals(o)) {
            WeightedEdge we = (WeightedEdge)o;

            if(we.getWeight() == this.getWeight()){
                return true;
            }else{
                return false;
            }

        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
