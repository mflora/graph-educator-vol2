package sample.util;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mina on 2017. 09. 21..
 */
public class Matrix<T> {
    private List<List<T>> mtx;
    private int width;
    private int height;

    public Matrix(){
        this.mtx = new LinkedList<>();
        this.width = 0;
        this.height = 0;
    }

    /**/
    public Matrix(Matrix<T> other){
        this.width = other.getWidth();
        this.height = other.getHeight();
        this.mtx = new LinkedList<>();

        for(int i = 0; i < this.getWidth(); ++i){
            mtx.add(new LinkedList<>());
            for(int j = 0; j < this.getHeight();++j){
                mtx.get(i).add(other.mtx.get(i).get(j));
                /*if(other.getElementAt(i,j) != null) {
                    this.setElementAt(i, j, other.getElementAt(i, j));
                }else{
                    this.setElementAt(i, j, null);
                }*/


            }
        }
    }
    /**/

    public Matrix(int width, int height){

        if(width < 0 || height < 0){
            throw new IllegalArgumentException("Width or height cannot be less than 0.");
        }

        this.width = width;
        this.height = height;
        this.mtx = new LinkedList<>();

        for(int i = 0; i < width; ++i){
            List<T> tmp = new LinkedList<>();
            for(int j = 0; j < height; ++j){
                tmp.add(null);
            }
            mtx.add(tmp);
        }

    }

    public T getElementAt(int column, int row){

        if(column > width || row > height || column < 0 || row < 0){
            throw new IllegalArgumentException("Column cannot be more than width and row cannot be more than height and column and row cannot be less than 0");
        }

        return mtx.get(column).get(row);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setElementAt(int column, int row, T value){

        if(column > width || row > height || column < 0 || row < 0){
            throw new IllegalArgumentException("Column cannot be more than width and row cannot be more than height and column and row cannot be less than 0");
        }

        mtx.get(column).set(row, value);
    }

    public void addNewRow(){

        for(int i = 0; i < width; ++i){
            mtx.get(i).add(null);
        }

        height++;
    }

    public void addNewColumn(){

        List<T> tmp = new LinkedList<>();
        for(int i = 0; i < height; ++i){
            tmp.add(null);
        }

        mtx.add(tmp);
        width++;
    }

    public void removeRow(int index){

        for(int i = 0; i < width; ++i) {
            mtx.get(i).remove(index);
        }

        height--;
    }

    public void removeColumn(int index){

        mtx.remove(index);

        width --;
    }

    public JTable toTableView(){

        Object[] columnNames = new Object[getHeight()];
        for(int i = 0; i < getWidth(); ++i){
            columnNames[i] = getElementAt(i, 0);
        }

        Object[][] rowData = new Object[getWidth()][getHeight()];

        for(int i = 0 ; i < getWidth(); ++i){
            for(int j = 1; j < getHeight();++j){
                rowData[i][j] = getElementAt(i, j);
            }
        }

        JTable ret = new JTable(rowData, columnNames);



        return ret;
    }

    @Override
    public String toString(){
        String ret = "";

        for(int i = 0; i < width; ++i){
            for(int j = 0 ; j < height; ++j){
                ret += getElementAt(i,j) + "\t\t\t";
            }
            ret += "\n";
        }

        return ret;

    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null){
            return false;
        }

        if(!(obj instanceof Matrix)){
            return false;
        }

        Matrix mx = (Matrix)obj;

        for(int i = 0; i < width; ++i){
            for(int j = 0 ; j < height; ++j){
                if(!this.getElementAt(i,j).equals(mx.getElementAt(i,j))){
                    return false;
                }
            }
        }

        return true;

    }
}
