package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import sample.util.*;

public class PointTest{

  @Test
  public void copy_test(){
    Point p1 = new Point(1,2);
	Point p2 = new Point(p1);
	
	assertEquals(p1.equals(p2), true);
	assertFalse(p1 == p2);
  }
  
  @Test
  public void equals_test(){
    Point p1 = new Point(1,2);
	Point p2 = new Point(1,2);
	Point p3 = new Point(2,2);
	
	assertEquals(p1.equals(p2), true);
	assertEquals(p1.equals(p3), false);
  }
  
  @Test
  public void toString_test(){
	Point p1 = new Point(1,2);
	
	assertEquals(p1.toString().equals("X: 1 Y: 2"), true);
  }
  
  @Test
  public void getters_setters_test(){
	  Point p1 = new Point(1, 2);
	  p1.setX(3);
	  p1.setY(4);
	  
	  assertEquals(p1.getX() == 3, true);
	  assertEquals(p1.getY() == 4, true);
	  assertEquals(p1.equals(new Point(3, 4)), true);
  }
  
}