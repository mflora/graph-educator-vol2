package hu.elte.mflora.sample.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Mina on 2017. 11. 09..
 */
public class Text implements Drawable {

    private String label;
    private PointView pos;

    public Text(String label, PointView pos){
        this.label = label;
        this.pos = pos;
    }

    public Text(Text other){
        this.label = other.getLabel();
        this.pos = new PointView(other.getPos());
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PointView getPos() {
        return pos;
    }

    public void setPos(PointView pos) {
        this.pos = pos;
    }

    @Override
    public void Draw(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        gc.strokeText(label, pos.getX(), pos.getY(), 20);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }

        if(!(o instanceof Text)){
            return false;
        }

        Text t = (Text)o;

        return (t.getLabel().equals(this.getLabel()) && t.getPos().equals(this.getPos()));
    }
}
