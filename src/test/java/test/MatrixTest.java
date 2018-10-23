package src.main.test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.*;

import hu.elte.mflora.sample.model.Model;
import hu.elte.mflora.sample.model.algorithm.Dijkstra;
import hu.elte.mflora.sample.model.algorithm.IAlgorithm;
import hu.elte.mflora.sample.util.*;
import hu.elte.mflora.sample.persistence.graph.*;

public class MatrixTest{
	
	@Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest1() {
		Matrix<String> mtx = new Matrix<>(-1, -1);
	}

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void wrongParamaterTest2() {
		Matrix<String> mtx = new Matrix<>(5, 5);
		mtx.getElementAt(6, 1);
	}

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void wrongParamaterTest3() {
		Matrix<String> mtx = new Matrix<>(5, 5);
		mtx.getElementAt(1, 6);
	}

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void wrongParamaterTest4() {
		Matrix<String> mtx = new Matrix<>(5, 5);
		mtx.getElementAt(-1, 2);
	}

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void wrongParamaterTest5() {
		Matrix<String> mtx = new Matrix<>(5, 5);
		mtx.getElementAt(2, -1);
	}

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void wrongParamaterTest6() {
		Matrix<String> mtx = new Matrix<>(5, 5);
		mtx.setElementAt(6, 1, "a");
	}

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void wrongParamaterTest7() {
		Matrix<String> mtx = new Matrix<>(5, 5);
		mtx.setElementAt(1, 6, "a");
	}

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void wrongParamaterTest8() {
		Matrix<String> mtx = new Matrix<>(5, 5);
		mtx.setElementAt(-1, 2, "a");
	}

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void wrongParamaterTest9() {
		Matrix<String> mtx = new Matrix<>(5, 5);
		mtx.setElementAt(2, -1, "a");
	}
}