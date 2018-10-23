package sample.persistence.graph;

import sample.util.Point;

/**
 * Created by Mina on 2017. 09. 19..
 */
public interface Vertex {

   String getLabel();
   Point getPosition();
   void setLabel(String label);
   void setPosition(Point position);
   int getPosX();
   int getPosY();

}
