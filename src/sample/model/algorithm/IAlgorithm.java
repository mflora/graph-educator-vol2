package sample.model.algorithm;

import javafx.scene.paint.Color;
import sample.persistence.Cache;
import sample.persistence.graph.Vertex;
import sample.util.Matrix;
import sample.util.MyState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mina on 2017. 11. 14..
 */
public interface IAlgorithm {

    public void first(Vertex v);
    public void next();
    public Matrix<String> current();
    public boolean end();
    public MyState updateColor();

    //Az a tábla csinálós valami, mint órán, ide?
}
