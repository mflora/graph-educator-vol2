package src.main.test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.*;

import hu.elte.mflora.sample.model.Model;
import hu.elte.mflora.sample.model.algorithm.Dijkstra;
import hu.elte.mflora.sample.model.algorithm.IAlgorithm;
import hu.elte.mflora.sample.persistence.Cache;
import hu.elte.mflora.sample.util.*;
import hu.elte.mflora.sample.persistence.graph.*;

public class CacheTest{
    @Test(expected=java.lang.IllegalArgumentException.class)
    public void wrongParamaterTest1() {
        List<MyState> states = new LinkedList<>();
        IBaseGraph graph = new BaseListGraph();
        IAlgorithm algo = new Dijkstra(graph);
        Cache c = new Cache(states, algo);
    }
}