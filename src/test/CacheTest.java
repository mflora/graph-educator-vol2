package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.*;

import sample.model.Model;
import sample.model.algorithm.Dijkstra;
import sample.model.algorithm.IAlgorithm;
import sample.persistence.Cache;
import sample.util.*;
import sample.persistence.graph.*;

public class CacheTest{
    @Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest1() {
        List<MyState> states = new LinkedList<>();
        IBaseGraph graph = new BaseListGraph();
        IAlgorithm algo = new Dijkstra(graph);
        Cache c = new Cache(states, algo);
    }
}