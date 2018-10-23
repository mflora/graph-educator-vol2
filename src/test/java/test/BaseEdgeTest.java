package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import hu.elte.mflora.sample.util.*;
import hu.elte.mflora.sample.persistence.graph.*;

public class BaseEdgeTest{

  @Test
  public void copy_test(){
    BaseEdge b1 = new BaseEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(3,4)));
	BaseEdge b2 = new BaseEdge(b1);
	
	assertEquals(b1.equals(b2), true);
	assertFalse(b1 == b2);
	assertFalse(b1.getIn() == b2.getIn());
	assertFalse(b1.getOut() == b2.getOut());
  }
  
  @Test
  public void equals_test(){
    BaseEdge b1 = new BaseEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(3,4)));
	BaseEdge b2 = new BaseEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(3,4)));
	BaseEdge b3 = new BaseEdge(new BaseVertex("c", new Point(1,2)), new BaseVertex("d", new Point(3,4)));
	BaseEdge b4 = new BaseEdge(new BaseVertex("a", new Point(3,4)), new BaseVertex("b", new Point(5,6)));
	BaseEdge b5 = new BaseEdge(new BaseVertex("b", new Point(3,4)), new BaseVertex("a", new Point(1,2)));
	BaseEdge b6 = new BaseEdge(new BaseVertex("c", new Point(3,4)), new BaseVertex("d", new Point(5,6)));
	
	assertEquals(b1.equals(b2), true);
	assertEquals(b1.equals(b3), false);
	assertEquals(b1.equals(b4), true);
	assertEquals(b1.equals(b5), false);
	assertEquals(b1.equals(b6), false);
  }
  
  @Test
  public void toString_test(){
    BaseEdge b1 = new BaseEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(3,4)));
	
	assertEquals(b1.toString().equals("a->b"), true);
  }
  
  @Test
  public void getters_test(){
	  
    BaseEdge b1 = new BaseEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(3,4)));	
	/*b1.setOut(new BaseVertex("c", new Point(5,6)));
	b1.setIn(new BaseVertex("d", new Point(8,9)));
	*/
    
	assertEquals(b1.getOut().equals(new BaseVertex("a", new Point(1,2))), true);
	assertEquals(b1.getIn().equals(new BaseVertex("b", new Point(3,4))), true);
	
  }
  
  @Test(expected=java.lang.IllegalArgumentException.class)
  public void wrongParamaterTest1() {
        BaseEdge b1 = new BaseEdge(null, new BaseVertex("a", new Point(1,2)));
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