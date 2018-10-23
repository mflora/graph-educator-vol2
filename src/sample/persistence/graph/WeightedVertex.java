package sample.persistence.graph;

import sample.util.Point;

/**
 * Created by Mina on 2017. 09. 19..
 */
public class WeightedVertex extends BaseVertex{
    //private String label;
    private int weight;

    public WeightedVertex(String label, Point position, int weight){
        super(label, position);
        this.weight = weight;
    }

    public WeightedVertex(WeightedVertex other){
        super(new BaseVertex(other.getLabel(), other.getPosition()));
        this.weight = other.getWeight();
    }


    public Integer getWeight(){
        return weight;
    }


    public void setWeight(Integer weight){
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + " Weight: " + weight;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

}
