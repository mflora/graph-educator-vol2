package hu.elte.mflora.sample.model.algorithm;

import javafx.scene.paint.Color;
import hu.elte.mflora.sample.persistence.Cache;
import hu.elte.mflora.sample.persistence.graph.Vertex;
import hu.elte.mflora.sample.util.Matrix;
import hu.elte.mflora.sample.util.MyState;

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
