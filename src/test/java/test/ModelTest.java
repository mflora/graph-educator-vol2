package src.main.test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.*;

import hu.elte.mflora.sample.model.Model;
import hu.elte.mflora.sample.model.algorithm.*;
import hu.elte.mflora.sample.util.*;
import hu.elte.mflora.sample.persistence.graph.*;
import org.junit.Ignore;

public class ModelTest{
	
	//TODO: DepthFirstSearchre megírni az exception dobást rendesen a modellben.
	/*
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest1() {
        BaseMatrixGraph b1 = new BaseMatrixGraph(true, false);
        BaseVertex v1 = new BaseVertex("a", new Point(1, 2));
        b1.addVertex(v1);
    }*/
	@Ignore
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest1() {
		//direction, weight, list
        Model m = new Model(false, true, true);
		IAlgorithm algo = new Dijkstra(m.getGraph());
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest2() {
		//direction, weight, list
        Model m = new Model(true, false, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new Dijkstra(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest3() {
		//direction, weight, list
        Model m = new Model(false, false, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new Dijkstra(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest4() {
		//direction, weight, list
        Model m = new Model(true, false, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new BellmanFord(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest5() {
		//direction, weight, list
        Model m = new Model(false, false, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new BellmanFord(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest6() {
		//direction, weight, list
        Model m = new Model(false, true, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new BellmanFord(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest7() {
		//direction, weight, list
        Model m = new Model(true, true, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new BreadthFirstSearch(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest8() {
		//direction, weight, list
        Model m = new Model(false, true, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new BreadthFirstSearch(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest9() {
		//direction, weight, list
        Model m = new Model(true, true, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new Kruskal(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest10() {
		//direction, weight, list
        Model m = new Model(true, false, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new Kruskal(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest11() {
		//direction, weight, list
        Model m = new Model(false, false, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new Kruskal(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
		@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest12() {
		//direction, weight, list
        Model m = new Model(true, true, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new Prim(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest13() {
		//direction, weight, list
        Model m = new Model(true, false, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new Prim(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest14() {
		//direction, weight, list
        Model m = new Model(false, false, true);
		Vertex v = new BaseVertex("a", new Point(1,2));
		IAlgorithm algo = new Prim(m.getGraph());
		m.loadAlgorithm(algo, v);
		
    }
	
	/*
    @Test(expected=java.io.FileNotFoundException.class)
    public void wrongParamaterTest15() {
        Model m = new Model(true, true, true);
		m.saveToFile("nincsIlyenFile.mina");
		
    }
	
	//Ez csak Exceptiont dob. Ezt hogy kell tesztelni? 
	@Test(expected=java.io.FileNotFoundException.class)
    public void wrongParamaterTest16() {
        Model m = new Model(true, true, true);
		m.loadFromFile("nincsIlyenFile.mina");
		
    }
    */
}