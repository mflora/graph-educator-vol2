package sample.view;

import static java.lang.StrictMath.sqrt;

/**
 * Created by Mina on 2017. 11. 09..
 */
public class PointView {

    private double x;
    private double y;

    public PointView(double x, double y){

        this.x = x;
        this.y = y;

    }

    public PointView(PointView other){
        this.x = other.getX();
        this.y = other.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "(X: " + x + ", Y: " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }

        if(!(o instanceof PointView)){
            return false;
        }

        PointView pv = (PointView)o;

        return (pv.getX()== this.getX() && pv.getY() == this.getY());
    }
}