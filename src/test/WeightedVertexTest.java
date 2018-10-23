package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import sample.util.*;
import sample.persistence.graph.*;

public class WeightedVertexTest{
	@Test
	public void copy_test(){
		WeightedVertex w1 = new WeightedVertex("a", new Point(1,2), 8);
		WeightedVertex w2 = new WeightedVertex(w1);
	
		assertEquals(w1.equals(w2), true);
		assertFalse(w1 == w2);
		assertFalse(w1.getPosition() == w2.getPosition());
  }
  
	@Test
	public void equals_test(){
		WeightedVertex w1 = new WeightedVertex("a", new Point(1,2), 8);
		WeightedVertex w2 = new WeightedVertex("a", new Point(1,2), 8);
		WeightedVertex w3 = new WeightedVertex("b", new Point(1,2), 8);
		WeightedVertex w4 = new WeightedVertex("a", new Point(2,2), 8);
		WeightedVertex w5 = new WeightedVertex("b", new Point(1,2), 9);
		WeightedVertex w6 = new WeightedVertex("a", new Point(2,2), 9);
	
		assertEquals(w1.equals(w2), true);
		assertEquals(w1.equals(w3), false);
		assertEquals(w1.equals(w4), true);
		assertEquals(w1.equals(w5), false);
		assertEquals(w1.equals(w6), true);
  }
  
  @Test
  public void toString_test(){
	  WeightedVertex w1 = new WeightedVertex("a", new Point(1,2), 8);
	  
	  assertEquals(w1.toString().equals("a Weight: 8"), true);
  }
  
  @Test
  public void getter_setter_test(){
	  WeightedVertex w1 = new WeightedVertex("a", new Point(1,2), 8);
	  w1.setWeight(10);
	  assertEquals(w1.equals( new WeightedVertex("a", new Point(1,2), 10)), true);
  }

}