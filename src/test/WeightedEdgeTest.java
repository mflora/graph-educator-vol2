package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import sample.util.*;
import sample.persistence.graph.*;

public class WeightedEdgeTest{
	@Test
	public void copy_test(){
		WeightedEdge w1 = new WeightedEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(3,4)), 8);
		WeightedEdge w2 = new WeightedEdge(w1);
	
		assertEquals(w1.equals(w2), true);
		assertFalse(w1 == w2);
		assertFalse(w1.getIn() == w2.getIn());
		assertFalse(w1.getOut() == w2.getOut());
  }
  
	@Test
	public void equals_test(){
		WeightedEdge w1 = new WeightedEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(3,4)), 8);
		WeightedEdge w2 = new WeightedEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(3,4)), 8);
		WeightedEdge w3 = new WeightedEdge(new BaseVertex("c", new Point(1,2)), new BaseVertex("d", new Point(3,4)), 8);
		WeightedEdge w4 = new WeightedEdge(new BaseVertex("a", new Point(3,4)), new BaseVertex("b", new Point(5,6)), 8);
		WeightedEdge w5 = new WeightedEdge(new BaseVertex("b", new Point(3,4)), new BaseVertex("a", new Point(1,2)), 8);
		WeightedEdge w6 = new WeightedEdge(new BaseVertex("b", new Point(3,4)), new BaseVertex("a", new Point(1,2)), 9);
		WeightedEdge w7 = new WeightedEdge(new BaseVertex("c", new Point(4,5)), new BaseVertex("d", new Point(6,7)), 9);
	
		assertEquals(w1.equals(w2), true);
		assertEquals(w1.equals(w3), false);
		assertEquals(w1.equals(w4), true);
		assertEquals(w1.equals(w5), false);
		assertEquals(w1.equals(w6), false);
		assertEquals(w1.equals(w7), false);
  }
  
  @Test
  public void toString_test(){
	  WeightedEdge w1 = new WeightedEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(3,4)), 8);
		
		assertEquals(w1.toString().equals("a->b Weight: 8"), true);
  }
  @Test
  public void getters_setters_test(){
	  WeightedEdge w1 = new WeightedEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(3,4)), 8);
		w1.setWeight(10);
		
		assertEquals(w1.getWeight() == 10, true);
  }
 }