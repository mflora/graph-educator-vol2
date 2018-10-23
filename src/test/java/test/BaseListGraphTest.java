package src.main.test;

import hu.elte.mflora.sample.persistence.graph.*;
import hu.elte.mflora.sample.util.Point;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BaseListGraphTest{
	@Test
	public void copy_test(){		
		BaseListGraph b1 = new BaseListGraph(true, false);
		BaseVertex v1 = new BaseVertex("a", new Point(30, 40));
		BaseVertex v2 = new BaseVertex("b", new Point(100, 12011));
		b1.addVertex(v1);
		b1.addVertex(v2);
		b1.addEdge(v1, v2);
		BaseListGraph b2 = new BaseListGraph(b1);
		/*
		BaseListGraph b3 = new BaseListGraph();
		BaseListGraph b
        */
		assertEquals(b1.equals(b2), true);
		assertFalse(b1 == b2);
		assertTrue(b1.getVertexByLabel("a").equals(b2.getVertexByLabel("a")));
		assertTrue(b1.getVertexByLabel("b").equals(b2.getVertexByLabel("b")));
        
        List<Edge> el1 = b1.edges();
        List<Edge> el2 = b2.edges();
        assertTrue(el1.equals(el2));
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
  
    @Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest1() {
        BaseListGraph b1 = new BaseListGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(1, 2));
        b1.addVertex(v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest2() {
        BaseListGraph b1 = new BaseListGraph(true, false);
        BaseVertex v1 = null;
        b1.addVertex(v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest3() {
        BaseListGraph b1 = new BaseListGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(1, 21));
        b1.addVertex(v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest4() {
        BaseListGraph b1 = new BaseListGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 2));
        b1.addVertex(v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest5() {
        BaseListGraph b1 = new BaseListGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        b1.addVertex(v1);
        BaseVertex v2 = new BaseVertex("a", new Point(30, 30));
        b1.addVertex(v2);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest6() {
        BaseListGraph b1 = new BaseListGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
		b1.addEdge(null, v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest7() {
        BaseListGraph b1 = new BaseListGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, null);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest8() {
        BaseListGraph b1 = new BaseListGraph(true, false);
        b1.addEdge(null, null);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest9() {
        BaseListGraph b1 = new BaseListGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest10() {
        BaseListGraph b1 = new BaseListGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        BaseVertex v2 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, v2);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest11() {
        BaseListGraph b1 = new BaseListGraph(true, true);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
		b1.addEdge(null, v1, 10);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest12() {
        BaseListGraph b1 = new BaseListGraph(true, true);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, null, 10);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest13() {
        BaseListGraph b1 = new BaseListGraph(true, true);
        b1.addEdge(null, null, 10);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest14() {
        BaseListGraph b1 = new BaseListGraph(true, true);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, v1, 10);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest15() {
        BaseListGraph b1 = new BaseListGraph(true, true);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        BaseVertex v2 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, v2, 10);
    }
	
	@Test(expected=java.util.NoSuchElementException.class)
    public void wrongParamaterTest16() {
        BaseListGraph b1 = new BaseListGraph(true, false);
		BaseEdge be = new BaseEdge(new BaseVertex("a", new Point(1,2)), new BaseVertex("b", new Point(4,5)));
		b1.removeEdge(be);
    }
	
	@Test(expected=java.util.NoSuchElementException.class)
    public void wrongParamaterTest17() {
        BaseListGraph b1 = new BaseListGraph(true, false);
		BaseVertex bv = new BaseVertex("a", new Point(1,2));
		b1.removeVertex(bv);
    }
 }
 