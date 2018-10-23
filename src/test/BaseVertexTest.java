package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import sample.util.*;
import sample.persistence.graph.*;
import sample.util.Point;

public class BaseVertexTest{

  @Test
  public void copy_test(){
    BaseVertex b1 = new BaseVertex("a", new Point(1,2));
	BaseVertex b2 = new BaseVertex(b1);
	
	assertEquals(b1.equals(b2), true);
	assertFalse(b1 == b2);
	assertFalse(b1.getPosition() == b2.getPosition());
  }
  
  @Test
  public void equals_test(){
    BaseVertex b1 = new BaseVertex("a", new Point(1,2));
	BaseVertex b2 = new BaseVertex("a", new Point(1,2));
	BaseVertex b3 = new BaseVertex("b", new Point(1,2));
	BaseVertex b4 = new BaseVertex("a", new Point(2,2));
	
	assertEquals(b1.equals(b2), true);
	assertEquals(b1.equals(b3), false);
	assertEquals(b1.equals(b4), true);
  }
  
  @Test
  public void label_tests(){
	  BaseVertex b1 = new BaseVertex("a", new Point(1,2));
	  b1.setLabel("c");
	  
	  assertEquals(b1.getLabel().equals("c"), true);
	  assertEquals(b1.toString().equals("c"), true);
  }
  
  @Test
  public void position_test(){
	BaseVertex b1 = new BaseVertex("a", new Point(1,2));
	b1.setPosition(new Point(3,4));
	
	assertEquals(b1.getPosX() == 3, true);
	assertEquals(b1.getPosY() == 4, true);
	assertEquals(b1.getPosition().equals(new Point(3,4)), true);

  }

  @Test(expected=java.lang.IllegalArgumentException.class)
  public void wrongParamaterTest1() {
        BaseEdge b1 = new BaseEdge(null,  new BaseVertex("a", new Point(1,2)));
  }
  
  @Test(expected=java.lang.IllegalArgumentException.class)
  public void wrongParamaterTest2() {
        BaseEdge b1 = new BaseEdge(new BaseVertex("a", new Point(1,2)), null);
  }
  
  @Test(expected=java.lang.IllegalArgumentException.class)
  public void wrongParamaterTest3() {
        BaseEdge b1 = new BaseEdge(null, null);
  }
}
