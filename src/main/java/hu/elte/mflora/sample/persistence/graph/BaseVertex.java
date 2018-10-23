package hu.elte.mflora.sample.persistence.graph;

import hu.elte.mflora.sample.util.Point;

/**
 * Created by Mina on 2017. 09. 19..
 */
public class BaseVertex implements Vertex {

    private String label;
    private Point position;

    public BaseVertex(String label, Point position){

        if(label == null){
            throw new IllegalArgumentException("Label cannot be null!");
        }

        if(position == null){
            throw new IllegalArgumentException("Position cannot be null!");
        }

        this.label = label;
        this.position = new Point(position);
    }

    public BaseVertex(BaseVertex other){
        this.label = other.getLabel();
        this.position = new Point(other.getPosition());
    }

    @Override
    public String getLabel(){
        return label;
    }

    @Override
    public void setLabel(String label){
        this.label = label;
    }

    @Override
    public Point getPosition(){
        return new Point(position);
    }

    @Override
    public int getPosX(){
        return position.getX();
    }

    @Override
    public int getPosY(){
        return position.getY();
    }

    @Override
    public void setPosition(Point position){
        this.position = new Point(position);
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }

        if(!(o instanceof BaseVertex)){
            return false;
        }

        BaseVertex v = (BaseVertex)o;

        return this.label.equals(v.label); // && this.position.equals(v.position);
    }

    @Override
    public String toString(){
        return label;
    }

    @Override
    public int hashCode(){
        return 0;
    }

}
