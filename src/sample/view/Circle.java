package sample.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Mina on 2017. 11. 09..
 */
public class Circle implements Drawable{

    private PointView o;
    private double r;
    private Color color;

    public Circle(PointView o, double r, Color color) {
        this.o = o;
        this.r = r;
        this.color = color;

    }

    public Circle(Circle other){
        this.r = other.getR();
        this.o = new PointView(other.getO());
        this.color = other.getColor();
    }

    public PointView getO() {
        return o;
    }

    public void setO(PointView o) {
        this.o = o;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void Draw(GraphicsContext gc){

        gc.setFill(color);
        gc.fillOval(o.getX() - r, o.getY() - r, 2*r, 2*r);

    }

    @Override
    public String toString() {
        return "Origo: " + o.toString() + " Radius: " + r + " Color: " + color.toString();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }

        if(!(o instanceof Circle)){
            return false;
        }

        Circle c = (Circle) o;

        return(this.getR() == c.getR() && this.getO().equals(c.getO()) && this.getColor().equals(c.getColor()));
    }
}
