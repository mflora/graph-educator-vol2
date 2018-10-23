package hu.elte.mflora.sample.util;

import javafx.scene.paint.Color;
import hu.elte.mflora.sample.persistence.graph.Edge;
import hu.elte.mflora.sample.persistence.graph.Vertex;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mina on 2017. 11. 15..
 */
public class MyState {

    private Map<Vertex, Color> colorVertex;
    private Map<Edge, Color> colorEdge;
    private Matrix<String> oneRow;
    //stukikép ide

    /**/
    public MyState(MyState other) {
        if (other.getColor() != null) {

            colorVertex = new HashMap<>();
            for (Vertex v : other.colorVertex.keySet()) {
                colorVertex.put(v, other.colorVertex.get(v));
            }
        }else{
            this.colorVertex = null;
        }

        if(other.getColorEdge() != null) {
            colorEdge = new HashMap<>();
            for (Edge e : other.colorEdge.keySet()) {
                colorEdge.put(e, other.colorEdge.get(e));
            }
        }else{
            this.colorEdge = null;
        }

        //TODO ezt ki kell szedni, ha jó a táblázat
        if(other.getOneRow() != null) {
            //oneRow = new Matrix<String>(other.getOneRow());
        }
    }
    /**/

    public MyState(){
        colorVertex = new HashMap<>();
        colorEdge = new HashMap<>();
        oneRow = new Matrix<>(1, 2);
    }

    public MyState(Map<Vertex, Color> color, Map<Edge, Color> eColor, Matrix<String> oneRow){
        this.colorVertex = color;
        this.colorEdge = eColor;
        this.oneRow = oneRow;
    }

    public Map<Vertex, Color> getColor() {
        return colorVertex;
    }

    public Map<Edge, Color> getColorEdge(){
        return colorEdge;
    }

    public Matrix<String> getOneRow() {
        return oneRow;
    }

    public void setColor(Map<Vertex, Color> color) {
        this.colorVertex = color;
    }

    public void setColorEdge(Map<Edge, Color> colorEdge){
        this.colorEdge = colorEdge;
    }

    public void setOneRow(Matrix<String> oneRow) {
        this.oneRow = oneRow;
    }

    public void setOneRow(String a, String b){
        oneRow.setElementAt(0, 0, a);
        oneRow.setElementAt(0, 1, b);
    }

    @Override
    public String toString(){
        return colorVertex + " " + colorEdge + " " + oneRow + "\n";
        //return colorVertex + " " + colorEdge;
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

        if(!(o instanceof MyState)){
            return false;
        }

        MyState ms = (MyState)o;

        if(this.getColor().equals(((MyState) o).getColor())){
            if(this.getColorEdge().equals(((MyState) o).getColorEdge())){
                if(this.getOneRow().equals(((MyState) o).getOneRow())){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }

    }
}
