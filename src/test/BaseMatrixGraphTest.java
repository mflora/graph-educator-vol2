package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.*;

import sample.util.*;
import sample.persistence.graph.*;

public class BaseMatrixGraphTest{

	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest1() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(1, 2));
        b1.addVertex(v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest2() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = null;
        b1.addVertex(v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest3() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(1, 21));
        b1.addVertex(v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest4() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 2));
        b1.addVertex(v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest5() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        b1.addVertex(v1);
        BaseVertex v2 = new BaseVertex("a", new Point(30, 30));
        b1.addVertex(v2);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest6() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
		b1.addEdge(null, v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest7() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, null);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest8() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        b1.addEdge(null, null);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest9() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, v1);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest10() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        BaseVertex v2 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, v2);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest11() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
		b1.addEdge(null, v1, 10);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest12() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, null, 10);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest13() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        b1.addEdge(null, null, 10);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest14() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, v1, 10);
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest15() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(21, 21));
        BaseVertex v2 = new BaseVertex("a", new Point(21, 21));
        b1.addEdge(v1, v2, 10);
    }
}