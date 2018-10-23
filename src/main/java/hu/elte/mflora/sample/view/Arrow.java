package hu.elte.mflora.sample.view;

//import com.sun.prism.paint.Color;
//import com.sun.prism.paint.Color;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Mina on 2017. 11. 09..
 */
public class Arrow extends Group implements Drawable {

    Line line;
    Line arrow1;
    Line arrow2;
    private Color color;

    //public Arrow() {
    //this(new javafx.scene.shape.Line(), new javafx.scene.shape.Line(), new javafx.scene.shape.Line());
    //}

    private static final double arrowLength = 20;
    private static final double arrowWidth = 7;

    public Arrow(double eX, double eY, double sX, double sY, Color color) {
        //super(line, arrow1, arrow2);
        this.color = color;
        line = new Line();
        arrow1 = new Line();
        arrow2 = new Line();

//        InvalidationListener updater = o -> {
        double ex = eX;
        double ey = eY;
        double sx = sX;
        double sy = sY;

        line.setEndX(ex);
        line.setEndY(ey);
        line.setStartX(sx);
        line.setStartY(sy);
        arrow1.setEndX(ex);
        arrow1.setEndY(ey);
        arrow2.setEndX(ex);
        arrow2.setEndY(ey);

        if (ex == sx && ey == sy) {
            // arrow parts of length 0
            arrow1.setStartX(ex);
            arrow1.setStartY(ey);
            arrow2.setStartX(ex);
            arrow2.setStartY(ey);
        } else {
            double factor = arrowLength / Math.hypot(sx - ex, sy - ey);
            double factorO = arrowWidth / Math.hypot(sx - ex, sy - ey);

            // part in direction of main line
            double dx = (sx - ex) * factor;
            double dy = (sy - ey) * factor;

            // part ortogonal to main line
            double ox = (sx - ex) * factorO;
            double oy = (sy - ey) * factorO;

            arrow1.setStartX(ex + dx - oy);
            arrow1.setStartY(ey + dy + ox);
            arrow2.setStartX(ex + dx + oy);
            arrow2.setStartY(ey + dy - ox);
        }
//        };

        // add updater to properties
        //      startXProperty().addListener(updater);
        //    startYProperty().addListener(updater);
        //  endXProperty().addListener(updater);
        //endYProperty().addListener(updater);
        //updater.invalidated(null);
    }

    public Arrow(Arrow a){
        throw new NotImplementedException();
    }

    // start/end properties

    public final void setStartX(double value) {
        line.setStartX(value);
    }

    public final double getStartX() {
        return line.getStartX();
    }

    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    public final void setStartY(double value) {
        line.setStartY(value);
    }

    public final double getStartY() {
        return line.getStartY();
    }

    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    public final void setEndX(double value) {
        line.setEndX(value);
    }

    public final double getEndX() {
        return line.getEndX();
    }

    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    public final void setEndY(double value) {
        line.setEndY(value);
    }

    public final double getEndY() {
        return line.getEndY();
    }


    @Override
    public void Draw(GraphicsContext gc) {
        gc.setStroke(color);
        System.out.println("LINE START x: " + line.getStartX() + " START y: " + line.getStartY() + " END x: " + line.getEndX() + " END y: " + line.getEndY());
        gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        //gc.setStroke(Color.BLUE);
        gc.strokeLine(arrow1.getStartX(), arrow1.getStartY(), arrow1.getEndX(), arrow1.getEndY());
        //gc.setStroke(Color.MAGENTA);
        gc.strokeLine(arrow2.getStartX(), arrow2.getStartY(), arrow2.getEndX(), arrow2.getEndY());
        //gc.setStroke(Color.GREEN);
    }

    @Override
    public String toString() {
        return "End of the arrow: (" + getEndX() + ", " + getEndY() + ") ; Start of the arrow: (" + getStartX() + ", " + getStartY() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Arrow)) {
            return false;
        }

        Arrow a = (Arrow) o;

        return (a.getStartY() == this.getStartY() && a.getStartY() == this.getStartY() && a.getEndX() == this.getEndX() && a.getEndY() == this.getEndY());

    }

    @Override
    public int hashCode() {
        return 0;
    }
}