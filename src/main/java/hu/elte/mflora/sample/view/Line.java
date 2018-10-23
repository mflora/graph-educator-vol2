package hu.elte.mflora.sample.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Mina on 2017. 11. 09..
 */
public class Line implements Drawable {
    private PointView a;
    private PointView b;
    private double lineWidth;
    private Color color;

    public Line(PointView a, PointView b, Color color) {
        this.a = a;
        this.b = b;
        this.color = color;
    }

    public Line(Line other){
        this.lineWidth=other.getLineWidth();
        this.a = new PointView(other.getA());
        this.b = new PointView(other.getB());
        this.color = other.getColor();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public PointView getA() {
        return a;
    }

    public void setA(PointView a) {
        this.a = a;
    }

    public PointView getB() {
        return b;
    }

    public void setB(PointView b) {
        this.b = b;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    @Override
    public void Draw(GraphicsContext gc){
        gc.setStroke(color);
        gc.setLineWidth(lineWidth);
        gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
        //gc.strokeLine(0, 0, 100, 100);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Start: " + a.toString() + " End: " + b.toString() + " Line Width: " + lineWidth;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }

        if(!(o instanceof Line)){
            return false;
        }

        Line l = (Line)o;

        return((this.getA().equals(l.getA()) && this.getB().equals(l.getB())) || this.getA().equals(l.getB()) && this.getB().equals(l.getA()));
    }
}
