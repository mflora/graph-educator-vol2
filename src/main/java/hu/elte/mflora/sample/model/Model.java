package hu.elte.mflora.sample.model;

import hu.elte.mflora.sample.model.algorithm.*;
import hu.elte.mflora.sample.persistence.Cache;
import hu.elte.mflora.sample.persistence.graph.*;
import hu.elte.mflora.sample.util.Matrix;
import hu.elte.mflora.sample.util.Point;
import hu.elte.mflora.sample.view.*;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.StrictMath.sqrt;

/**
 * Created by Mina on 2017. 11. 09..
 */
public class Model extends Observable{
    private IBaseGraph graph;
    private Cache cache;
    private Map<Vertex, Color> vertexColors;
    private Map<Edge, Color> edgeColors;
    Matrix<String> table;
    private boolean isDirected;
    private boolean isWeighted;
    private boolean isListed;
    private IAlgorithm algorithm;
    private int delayTime;
    private boolean isPaused;

    /** /
    private Observer updateColorHandler = new Observer(){
        @Override
        public void actionPerformed(Object sender, Object EventArgs){
            throw new NotImplementedException();
        }
    };
    /**/

    //public Observable<List<Drawable>> updateGraph;


    public Observable graphChangedEvent;


    Observer cacheChangedHandler = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            System.out.println("Graph changed...");

            if (!(arg instanceof List)){
                return;
            }

            for (Object d : (List)arg){
                if(d instanceof Map){
                    HashMap<Vertex, Color> tmpColors = (HashMap<Vertex, Color>)d;
                    setVertexColors(tmpColors);
                }
                if(d instanceof Matrix){
                    Matrix<String> tmpMatrix = (Matrix<String>)d;
                    setTable(tmpMatrix);
                }


            }
        }
    };


    public Model(boolean isDirected, boolean isWeighted, boolean isListed){
        graphChangedEvent = new Observable();
        //TODO: modell konstrukrort írni a 2-3. statehet is!!!!!!!!!!
        //cache = new Cache(new Kruskal(graph));
        //cache.addObserver(cacheChangedHandler);
        graph = isListed ? (new BaseListGraph(isDirected, isWeighted)) : (new BaseMatrixGraph(isDirected, isWeighted));
        this.isDirected = isDirected;
        this.isWeighted = isWeighted;
        this.isListed = isListed;
        vertexColors = new HashMap<>();
        edgeColors = new HashMap<>();
        table = new Matrix<>();
        this.algorithm = null;
        this.delayTime = 30000;
        isPaused = false;
        System.out.println("Gráf létrehozva: Directed? " + isDirected + " Weighted? " + isWeighted + " Listed? " + isListed);
    }

    public Model(boolean isDirected, boolean isWeighted, boolean isListed, IAlgorithm algorithm){
        graphChangedEvent = new Observable();
        //cache = new Cache(new Kruskal(graph));
        //cache.addObserver(cacheChangedHandler);
        graph = isListed ? (new BaseListGraph(isDirected, isWeighted)) : (new BaseMatrixGraph(isDirected, isWeighted));
        this.isDirected = isDirected;
        this.isWeighted = isWeighted;
        this.isListed = isListed;
        vertexColors = new HashMap<>();
        edgeColors = new HashMap<>();
        table = new Matrix<>();
        this.algorithm = algorithm;
        delayTime = 30000;
        isPaused = false;
        System.out.println("Gráf létrehozva: Directed? " + isDirected + " Weighted? " + isWeighted + " Listed? " + isListed);
    }

    public void setAlgorithm(IAlgorithm algo){
        this.algorithm = algo;
    }

    /** /
    public Model(boolean isDirected, boolean isWeighted, boolean isListed, IAlgorithm algorithm){
        graphChangedEvent = new Observable();
        cache = new Cache();
        graph = isListed ? (new BaseListGraph(isDirected)) : (new BaseMatrixGraph(isDirected));
        this.isDirected = isDirected;
        this.isWeighted = isWeighted;
        this.isListed = isListed;
        this.algorithm = algorithm;
    }
    /**/

    /** /
    public void newGraph(boolean isDirected, boolean isWeighted, boolean isListed){


    }
    /**/


    public void addNewVertex(String label, Point position){

        Vertex v = new BaseVertex(label, position);
        graph.addVertex(v);
        vertexColors.put(v, Color.MAGENTA);

        updateScreen();
    }

    public void addNewVertex(String label, Point position, int weight ){

        graph.addVertex(new WeightedVertex(label, position, weight));

        updateScreen();
    }

    public void addNewEdge(String outLabel, String inLabel){

        graph.addEdge(graph.getVertexByLabel(outLabel), graph.getVertexByLabel(inLabel));
        System.out.println("VERTEX OUT: " + graph.getVertexByLabel(outLabel).getPosX() + ", " + graph.getVertexByLabel(outLabel).getPosY());
        System.out.println("VERTEX IN: " + graph.getVertexByLabel(inLabel).getPosX() + ", " + graph.getVertexByLabel(inLabel).getPosY());

        updateScreen();
    }

    public void addNewEdge(String outLabel, String inLabel, int weight){

        graph.addEdge(graph.getVertexByLabel(outLabel), graph.getVertexByLabel(inLabel), weight);

        updateScreen();
    }

    public IBaseGraph getGraph(){
        return graph;
    }

    public void loadFromFile(String filename) throws Exception{
        System.out.println(filename);
        graph = FileIO.loadFromFile(filename);
        isDirected = graph.getDirection();
        Edge ed = null;
        for(Edge e : graph.edges()){
            ed = e;
            break;
        }
        isWeighted = ed instanceof WeightedEdge;

        updateScreen();
    }

    public void saveToFile(String filename) throws FileNotFoundException {
        FileIO.saveToFile(filename, graph);
    }


    public void loadAlgorithm(IAlgorithm algo, Vertex v){

            if(algo instanceof DepthFirstSearch){
                System.out.println("Depth");
                if(true){
                    System.out.println("Futtatom a Depth");
                    this.setAlgorithm(algo);
                    cache = new Cache(algo, v);
                    //cache.addObserver(cacheChangedHandler);
                    //run(algo, v);
                }else{
                    throw new IllegalArgumentException("Bad graph format! (Directed & Weigted needed!)");
                }

            }

            if(algo instanceof Dijkstra){
                System.out.println("dIJKSTRA");
                if(isDirected && isWeighted){
                    System.out.println("Futtatom a Dikjstra");
                    this.setAlgorithm(algo);
                    cache = new Cache(algo, v);
                    //cache.addObserver(cacheChangedHandler);
                    //run(algo, v);
                }else{
                    throw new IllegalArgumentException("Bad graph format! (Directed & Weigted needed!)");
                }
            }

            if(algo instanceof BellmanFord){
                if(isDirected && isWeighted){
                    System.out.println("Futtatom a BellmanFord");
                    this.setAlgorithm(algo);
                    cache = new Cache(algo, v);
                    //cache.addObserver(cacheChangedHandler);
                    //run(algo, v);
                    runAlgorithm();
                }else{
                    throw new IllegalArgumentException("Bad graph format! (Directed & Weigted needed!)");
                }
            }

            if(algo instanceof BreadthFirstSearch){
                if( !isWeighted){
                    System.out.println("Futtatom a BreadthFirstSearch");
                    this.setAlgorithm(algo);
                    cache = new Cache(algo, v);
                    //cache.addObserver(cacheChangedHandler);
                    //run(algo, v);
                    runAlgorithm();
                }else{
                    throw new IllegalArgumentException("Bad graph format! (Unweigted needed!)");
                }
            }

            if(algo instanceof Kruskal){
                System.out.println("Futtatom a Kruskal");
                if(!isDirected && isWeighted){
                    this.setAlgorithm(algo);
                    cache = new Cache(algo, v);
                    //cache.addObserver(cacheChangedHandler);
                    //run(algo, v);
                }else{
                    throw new IllegalArgumentException("Bad graph format! (Undirected & Weigted needed!)");
                }
            }

            if(algo instanceof Prim){
                System.out.println("Futtatom a Prim");
                if(!isDirected && isWeighted){
                    cache = new Cache(algo, v);
                    //cache.addObserver(cacheChangedHandler);
                    //this.setAlgorithm(algo);
                    this.algorithm = algo;
                    runAlgorithm();
                    //run(algo, v);
                }else{
                    throw new IllegalArgumentException("Bad graph format! (Undirected & Weigted needed!)");
                }
            }
        }

        public void updateIfTrue(String s){
            System.out.println("ITT HÍVÓDIK MEG AZ UPDATEIFTRUE");
            if(isItCorrect(s)){
                cache.nextState();
                runAlgorithm();
            }
        }

        public IAlgorithm getAlgorithm(){
            return algorithm;
        }

        public boolean isItCorrect(String guess){

            System.out.println("GUESS: " + guess);

            if (algorithm instanceof BreadthFirstSearch) {

                if("".equals(guess)){
                    if(cache.hasNextState()){
                        for(Vertex v : cache.getCurrentState().getColor().keySet()){
                            if(!cache.getCurrentState().getColor().get(v).equals(cache.getNextState().getColor().get(v))){
                                return false;
                            }
                        }
                        return true;
                    }else{
                        System.out.println("ÜRES STRING");
                        return false;
                    }
                }
                String[] guesses = guess.split(" ");
                LinkedList<Vertex> finishedVertices = new LinkedList<>();

                if(guesses.length > graph.vertices().size()){
                    return false;
                }

                for(String s : guesses){
                    String[] oneGuessPair = s.split("=");
                    if(graph.getVertexByLabel(oneGuessPair[0]) != null){
                        switch(oneGuessPair[1]){
                            case "pink":
                                if(!cache.getNextState().getColor().get(graph.getVertexByLabel(oneGuessPair[0])).equals(Color.MAGENTA)){
                                    System.out.println("NEM PINK");
                                    return false;
                                }
                                finishedVertices.add(graph.getVertexByLabel(oneGuessPair[0]));
                                break;
                            case "white":
                                if(!cache.getNextState().getColor().get(graph.getVertexByLabel(oneGuessPair[0])).equals(Color.WHITE)){
                                    System.out.println("NEM WHITE");
                                return false;
                                }
                                finishedVertices.add(graph.getVertexByLabel(oneGuessPair[0]));
                                break;
                            case "gray":
                                if(!cache.getNextState().getColor().get(graph.getVertexByLabel(oneGuessPair[0])).equals(Color.GRAY)){
                                    System.out.println("NEM GRAY");
                                    return false;
                                }
                                finishedVertices.add(graph.getVertexByLabel(oneGuessPair[0]));
                                break;
                            default:
                                System.out.println("DEFAULT FALSE");
                                return false;

                        }
                    }else{
                        System.out.println("NINCS ILYEN VERTEX");
                        return false;
                    }
                }

                for(Vertex v: vertexColors.keySet()){
                    if(!finishedVertices.contains(v)){
                        System.out.println("FINISHED VERTICES AINT CONTAIN V");
                        if(!cache.getCurrentState().getColor().get(v).equals(cache.getNextState().getColor().get(v))){
                            System.out.println("ELBASZTAD A COLORT");
                            return false;
                        }
                    }
                }
            }else{

                if(null == guess){
                    return false;
                }

                if("".equals(guess)) {
                    if (cache.hasNextState()) {
                        for (Edge e : cache.getCurrentState().getColorEdge().keySet()) {
                            if (!cache.getCurrentState().getColorEdge().get(e).equals(cache.getNextState().getColorEdge().get(e))) {
                                return false;
                            }
                        }
                        return true;
                    } else {
                        return false;
                    }
                }

                String[] guesses = guess.split(" ");
                System.out.println("guessnum: " + guesses.length);
                LinkedList<Edge> finishedEdges = new LinkedList<>();

                if(guesses.length > graph.vertices().size()){
                    return false;
                }

                for(String s : guesses){
                    System.out.println("GUEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEESS: " + s);

                    //try {
                        String[] tmpSplit = s.split("->");
                        if(tmpSplit.length<2){
                            return false;
                        }
                        String outVertex = tmpSplit[0];
                        String[] tmpSplit2 = tmpSplit[1].split("=");
                        String inVertex = tmpSplit2[0];
                        String color = tmpSplit2[1];

                        if(graph.getEdgeByLabels(outVertex, inVertex) != null){

                            //if (!(algorithm instanceof Prim)) {

                                if (isDirected) {
                                    switch(color){
                                        case "pink":
                                            if(!cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(outVertex, inVertex)).equals(Color.CORAL)){
                                                return false;
                                            }
                                            finishedEdges.add(graph.getEdgeByLabels(outVertex, inVertex));
                                            break;
                                        case "blue":
                                            if(!cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(outVertex, inVertex)).equals(Color.BLUE)){
                                                return false;
                                            }
                                            finishedEdges.add(graph.getEdgeByLabels(outVertex, inVertex));
                                            break;
                                        case "black":
                                            if(!cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(outVertex, inVertex)).equals(Color.BLACK)){
                                                return false;
                                            }
                                            finishedEdges.add(graph.getEdgeByLabels(outVertex, inVertex));
                                            break;
                                        default:
                                            return false;
                                    }
                                } else {
                                    System.out.println("CURR STATE: " + cache.getCurrentState() + " NEXT: " + cache.getNextState());
                                    Map<Edge, Color> changes = new HashMap<>();
                                    for(Edge e : cache.getNextState().getColorEdge().keySet()){
                                        if(cache.getCurrentState().getColorEdge().keySet().isEmpty()){
                                            changes.put(e, cache.getNextState().getColorEdge().get(e));
                                        }else {
                                            if (!cache.getCurrentState().getColorEdge().containsKey(e)) {
                                                changes.put(e, cache.getNextState().getColorEdge().get(e));
                                            }else {
                                                if (!cache.getCurrentState().getColorEdge().get(e).equals(cache.getNextState().getColorEdge().get(e))) {
                                                    changes.put(e, cache.getNextState().getColorEdge().get(e));
                                                }
                                            }
                                        }
                                    }
                                    Map<Edge, Color> guessesMap = new HashMap<>();
                                    switch(color){
                                        case "pink":
                                            /*if(cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(outVertex, inVertex)) != null){
                                                System.out.println("PIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIINK");
                                                if(cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(outVertex, inVertex)).equals(Color.CORAL)){
                                                    finishedEdges.add(graph.getEdgeByLabels(outVertex, inVertex));
                                                    finishedEdges.add(graph.getEdgeByLabels(inVertex, outVertex));
                                                }else{


                                                    return false;
                                                }

                                            }*/
                                            guessesMap.put(graph.getEdgeByLabels(outVertex, inVertex), Color.CORAL);
                                            guessesMap.put(graph.getEdgeByLabels(inVertex, outVertex), Color.CORAL);

                                            break;

//                                            if(cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(inVertex, outVertex)) != null){
//                                                System.out.println("2222222222222222222222222222222222222222222222222222222222");
//                                                finishedEdges.add(graph.getEdgeByLabels(inVertex, outVertex));
//                                                finishedEdges.add(graph.getEdgeByLabels(outVertex, inVertex));
//                                                if(!cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(inVertex, outVertex)).equals(Color.CORAL)){
//                                                    return false;
//                                                }
//                                            }
                                        case "blue":
                                            if(cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(outVertex, inVertex)) != null){
                                                finishedEdges.add(graph.getEdgeByLabels(outVertex, inVertex));
                                                finishedEdges.add(graph.getEdgeByLabels(inVertex, outVertex));
                                                if(!cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(outVertex, inVertex)).equals(Color.BLUE)){
                                                    return false;
                                                }
                                            }

                                            break;

//                                            if(cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(inVertex, outVertex)) != null){
//                                                finishedEdges.add(graph.getEdgeByLabels(inVertex, outVertex));
//                                                finishedEdges.add(graph.getEdgeByLabels(outVertex, inVertex));
//                                                if(!cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(inVertex, outVertex)).equals(Color.BLUE)){
//                                                    return false;
//                                                }
//                                            }
                                        case "black":
                                            if(cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(outVertex, inVertex)) != null){
                                                finishedEdges.add(graph.getEdgeByLabels(outVertex, inVertex));
                                                finishedEdges.add(graph.getEdgeByLabels(inVertex, outVertex));
                                                if(!cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(outVertex, inVertex)).equals(Color.BLACK)){
                                                    return false;
                                                }
                                            }

                                            break;

//                                            if(cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(inVertex, outVertex)) != null){
//                                                finishedEdges.add(graph.getEdgeByLabels(inVertex, outVertex));
//                                                finishedEdges.add(graph.getEdgeByLabels(outVertex, inVertex));
//                                                if(!cache.getNextState().getColorEdge().get(graph.getEdgeByLabels(inVertex, outVertex)).equals(Color.BLACK)){
//                                                    return false;
//                                                }
//                                            }
                                        default:
                                            System.out.println("DEFAULT");
                                            return false;
                                    }

                                    System.out.println("CHANGES: " + changes.keySet().size() + " GUESSES: " + guessesMap.keySet().size());

                                    if(changes.keySet().size() != guessesMap.keySet().size()){
                                        System.out.println("NEM ELEGENDO TIPP");
                                        return false;
                                    }

                                    for(Edge e : changes.keySet()){
                                        //if(!guessesMap.containsKey(e)){
                                            //System.out.println("KIMARADT A KEY");
                                            //return false;
                                        //}else{
                                            if(!guessesMap.get(e).equals(changes.get(e))){
                                                System.out.println("WRONG GUESS");
                                                return false;
                                            }
                                        //}
                                    }

                                    return true;

                                }

//                            }else{
//                                System.out.println("WTH");
//                                //cache.nextState();
//                            }
                        }else{
                            System.out.println("NINCS EDGE");
                            return false;
                        }
//                    }catch (Exception ex){
//                        System.out.println("EXCEPTION");
//                        return false;
//                    }

                }

                System.out.println();

                for(Edge e : edgeColors.keySet()){
                    if(!finishedEdges.contains(e)){
                        System.out.println("MEGEGYEZIK");
                            if (!cache.getCurrentState().getColorEdge().get(e).equals(cache.getNextState().getColorEdge().get(e))) {
                                System.out.println("NEMEGYEZIKATOMBBE");
                                return false;
                            }

                    }
                }
            }


            return true;
        }

    public Cache getCache() {
        return cache;
    }

    public void updateScreen(){
        System.out.println(graphChangedEvent.countObservers());

        List<Vertex> vertices = graph.vertices();

        List<Circle> circles = new LinkedList<>();
        List<Drawable> labels = new LinkedList<>();

        int n = vertices.size();

            for (Vertex v : vertices) {
                if(vertexColors != null ) {
                    if(vertexColors.get(v) != null) {
                        circles.add(new Circle(new PointView(v.getPosX(), v.getPosY()), 20, vertexColors.get(v)));
                    }else{
                        circles.add(new Circle(new PointView(v.getPosX(), v.getPosY()), 20, Color.MAGENTA));
                    }
                }else {
                    circles.add(new Circle(new PointView(v.getPosX(), v.getPosY()), 20, Color.MAGENTA));
                }

                Text text = new Text(v.getLabel(), new PointView(v.getPosX(), v.getPosY()));
                //TODO: change text color

                labels.add(text);
            }

        /** /
        for(int i = 0; i <  n; ++i){
            double alpha = (double)i/n * 2* Math.PI;
            double vx = 100.0 + 80 * Math.sin(alpha);
            double vy = 100.0 + 80 * Math.cos(alpha);

            circles.add(new Circle(new PointView(vx, vy), 20, Color.MAGENTA));
            labels.add(new Text(vertices.get(i).getLabel(), new PointView(vx, vy)));
        }
        /**/

        List<Drawable> data = new LinkedList<>();
        List<Drawable> weights = new LinkedList<>();
        List<Edge> tmpList = new LinkedList<>();

        for(Edge e : graph.edges()){

            //System.out.println("EDGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEES: " + graph.edges());

            //int in = -1;
            Vertex inVertex = null;

            for(Vertex v : vertices){
                //++in;
                if (v.equals(e.getIn())){
                    inVertex = v;
                    break;
                }
            }

            //int out = -1;
            Vertex outVertex = null;
            for(Vertex v : vertices){
                //++out;
                if (v.equals(e.getOut())){
                    outVertex = v;
                    break;
                }
            }
            if(isWeighted){
                PointView StartPoint = new PointView(outVertex.getPosX(), outVertex.getPosY());
                PointView EndPoint = new PointView(inVertex.getPosX(), inVertex.getPosY());
                PointView EndStartVector = new PointView(StartPoint.getX() - EndPoint.getX(), StartPoint.getY() - EndPoint.getY());
                PointView NormalizedEndStartVector = new PointView(EndStartVector.getX() / Length(EndStartVector), EndStartVector.getY() / Length(EndStartVector));
                double tmpLength = sqrt(((EndPoint.getX()-StartPoint.getX())* (EndPoint.getX()-StartPoint.getX())) + ((EndPoint.getY()-StartPoint.getY())* (EndPoint.getY()-StartPoint.getY())))/2;
                int WishedLength = (int)tmpLength;
                int tmpX = (int)(StartPoint.getX() + -1 * NormalizedEndStartVector.getX() *  WishedLength);
                int tmpY = (int)(StartPoint.getY() + -1 * NormalizedEndStartVector.getY() * WishedLength);
                PointView RealStartPoint = new PointView(tmpX, tmpY);
                tmpX = (int)(EndPoint.getX() + NormalizedEndStartVector.getX() *  WishedLength);
                tmpY = (int)(EndPoint.getY() + NormalizedEndStartVector.getY() * WishedLength);
                PointView RealEndPoint = new PointView(tmpX, tmpY);
                WeightedEdge we = (WeightedEdge)e;
                if(!isDirected) {
                    System.out.println("WEIGHT: " + we.getWeight().toString());
                    //data.add(new Text(we.getWeight().toString(), new PointView(RealStartPoint.getX(), RealStartPoint.getY())));
                    weights.add(new Text(we.getWeight().toString(), new PointView(RealStartPoint.getX(), RealStartPoint.getY())));
                }else{
                    boolean alreadyHasThisEdge = false;
                    for(Edge ee : tmpList){
                        WeightedEdge wee = (WeightedEdge)ee;
                        if(we.equals(wee)){
                            alreadyHasThisEdge = true;
                            break;
                        }
                    }
                    if(!alreadyHasThisEdge){
                        System.out.println("WEIGHT: " + we.getWeight().toString());
                        //data.add(new Text(we.getWeight().toString(), new PointView(RealStartPoint.getX(), RealStartPoint.getY())));
                        weights.add(new Text(we.getWeight().toString(), new PointView(RealStartPoint.getX(), RealStartPoint.getY())));
                    }

                }
            }
            if(!isDirected) {
                if(edgeColors != null) {
                    if(edgeColors.get(e) != null) {
                        data.add(new Line(new PointView(outVertex.getPosX(), outVertex.getPosY()), new PointView(inVertex.getPosX(), inVertex.getPosY()), edgeColors.get(e)));
                    }else{
                        data.add(new Line(new PointView(outVertex.getPosX(), outVertex.getPosY()), new PointView(inVertex.getPosX(), inVertex.getPosY()), Color.BLACK));
                    }
                }else {
                    data.add(new Line(new PointView(outVertex.getPosX(), outVertex.getPosY()), new PointView(inVertex.getPosX(), inVertex.getPosY()), Color.BLACK));
                }
            }else{
                if(!graph.containsEdge(graph.getEdge(e.getIn(), e.getOut()))) {
                    PointView StartPoint = new PointView(inVertex.getPosX(), inVertex.getPosY());
                    PointView EndPoint = new PointView(outVertex.getPosX(), outVertex.getPosY());
                    PointView EndStartVector = new PointView(StartPoint.getX() - EndPoint.getX(), StartPoint.getY() - EndPoint.getY());
                    PointView NormalizedEndStartVector = new PointView(EndStartVector.getX() / Length(EndStartVector), EndStartVector.getY() / Length(EndStartVector));
                    int WishedLength = 20;
                    int tmpX = (int) (StartPoint.getX() + -1 * NormalizedEndStartVector.getX() * WishedLength);
                    int tmpY = (int) (StartPoint.getY() + -1 * NormalizedEndStartVector.getY() * WishedLength);
                    PointView RealStartPoint = new PointView(tmpX, tmpY);
                    tmpX = (int) (EndPoint.getX() + NormalizedEndStartVector.getX() * WishedLength);
                    tmpY = (int) (EndPoint.getY() + NormalizedEndStartVector.getY() * WishedLength);
                    PointView RealEndPoint = new PointView(tmpX, tmpY);

                    if (edgeColors.get(e) != null) {
                        data.add(new Arrow(RealStartPoint.getX(), RealStartPoint.getY(), RealEndPoint.getX(), RealEndPoint.getY(), edgeColors.get(e)));
                    } else {
                        data.add(new Arrow(RealStartPoint.getX(), RealStartPoint.getY(), RealEndPoint.getX(), RealEndPoint.getY(), Color.BLACK));
                    }
                }else{

                    PointView StartPoint = new PointView(inVertex.getPosX(), inVertex.getPosY());
                    System.out.println("START: " + StartPoint);
                    PointView EndPoint = new PointView(outVertex.getPosX(), outVertex.getPosY());
                    System.out.println("END: " + EndPoint);
                    PointView EndStartVector = new PointView(StartPoint.getX() - EndPoint.getX(), StartPoint.getY() - EndPoint.getY());
                    System.out.println("ENDSTARTVECTOR: " + EndStartVector);
                    PointView EndstartNormalVector = new PointView(EndStartVector.getY(), -1 * EndStartVector.getX());
                    System.out.println("ENDSTARTNORMAL: " + EndstartNormalVector);
                    //PointView EndstartNormalVector = new PointView(StartPoint.getY() - EndPoint.getY(), EndPoint.getX() - StartPoint.getX());
                    PointView NormalizedEndStartVector = new PointView(EndStartVector.getX() / Length(EndStartVector), EndStartVector.getY() / Length(EndStartVector));
                    System.out.println("NORMALIZED ENDSTART: " + NormalizedEndStartVector);
                    PointView NormalizedNormalVecttor = new PointView(EndstartNormalVector.getX() /Length(EndstartNormalVector), EndstartNormalVector.getY() / Length(EndstartNormalVector));
                    System.out.println("NORMALIZED NORMAL: " + NormalizedNormalVecttor);
                    int WishedLength = 20;
                    int normalLenth = 10;
                    //int tmpX = (int) (StartPoint.getX() + -1 * NormalizedEndStartVector.getX() * WishedLength);
                    int tmpX = (int) (StartPoint.getX() + NormalizedNormalVecttor.getX() * normalLenth);
                    System.out.println("TMPX START: " + tmpX);
                    tmpX = tmpX  + (int)( -1 * NormalizedEndStartVector.getX() * WishedLength);
                    System.out.println("TMPX KISEEBRE VÉVE: " + tmpX);
                    //int tmpY = (int) (StartPoint.getY() + -1 * NormalizedEndStartVector.getY() * WishedLength);
                    int tmpY = (int) ((StartPoint.getY() + NormalizedNormalVecttor.getY() * normalLenth));
                    System.out.println("TMPY START: " + tmpY);
                    tmpY = tmpY + (int)(+ -1 * NormalizedEndStartVector.getY() * WishedLength);
                    System.out.println("TMPY kisebbre: " + tmpY);
                    PointView RealStartPoint = new PointView(tmpX, tmpY);
                    tmpX = (int) ((EndPoint.getX()  +  NormalizedNormalVecttor.getX() * normalLenth));
                    System.out.println("TMPX END: " + tmpX);
                    tmpX = tmpX + (int) (NormalizedEndStartVector.getX() * WishedLength);
                    System.out.println("ENDX kisebbre: " + tmpX);
                    tmpY = (int) ((EndPoint.getY() +  NormalizedNormalVecttor.getY() * normalLenth));
                    System.out.println("TMPX END: " + tmpY);
                    tmpY = tmpY + (int) (+ NormalizedEndStartVector.getY() * WishedLength);
                    System.out.println("TMPY KISEBBRE END: " + tmpY);
                    PointView RealEndPoint = new PointView(tmpX, tmpY);
                    if(isWeighted){
                        WeightedEdge we = (WeightedEdge)e;
                        ((LinkedList<Drawable>) weights).removeLast();
                        double textNewPoint = sqrt(((EndPoint.getX()-StartPoint.getX())* (EndPoint.getX()-StartPoint.getX())) + ((EndPoint.getY()-StartPoint.getY())* (EndPoint.getY()-StartPoint.getY())))/2 ;
                        int WishedLengthText = (int)textNewPoint;
                        int tmpXText = (int) (StartPoint.getX() + NormalizedNormalVecttor.getX() * normalLenth + -1 * NormalizedEndStartVector.getX() * WishedLengthText);
                        //int tmpY = (int) (StartPoint.getY() + -1 * NormalizedEndStartVector.getY() * WishedLength);
                        int tmpYText = (int) (StartPoint.getY() + NormalizedNormalVecttor.getY() * normalLenth + -1 * NormalizedEndStartVector.getY() * WishedLengthText);
                        PointView RealStartPointText = new PointView(tmpXText, tmpYText);
                        weights.add(new Text(we.getWeight().toString(), new PointView(RealStartPointText.getX(), RealStartPointText.getY())));
                    }

                    if (edgeColors.get(e) != null) {
                        data.add(new Arrow(RealStartPoint.getX(), RealStartPoint.getY(), RealEndPoint.getX(), RealEndPoint.getY(), edgeColors.get(e)));
                    } else {
                        data.add(new Arrow(RealStartPoint.getX(), RealStartPoint.getY(), RealEndPoint.getX(), RealEndPoint.getY(), Color.BLACK));
                    }

                }
            }
            tmpList.add(e);
        }

        for(Circle c : circles){
            data.add(c);
        }

        for(Drawable d : labels){
            data.add(d);
        }

        for(Drawable d : weights){
            data.add(d);
        }

        /** /
         data.add(new Line(new PointView(50.0, 50.0), new PointView(100.0, 100.0)));
         data.add(new Circle(new PointView(100.0, 100.0), 20.0, Color.MAGENTA));
         data.add(new Circle(new PointView(50, 50.0), 20.0, Color.BLUE));
         /**/

        this.setChanged();
        this.notifyObservers(data);
        //graphChangedEvent.notifyObservers(data);

        System.out.println("EDGE COLORS: " + edgeColors);
    }

    public void updateLearnTable(){

        //TODO: valahogyan updatelni a változtatásokat a táblában

    }


    public List<String> listOfLabels(){

        List<String> labels = new LinkedList<>();
        //Ez itt a nem jo, verteices ures
        List<Vertex> vertices = graph.vertices();

        for(Vertex v : vertices){
            labels.add(v.getLabel());
        }

        return labels;

    }

    public boolean isDirected(){
        return isDirected;
    }

    public boolean isWeighted(){
        return isWeighted;
    }

    /**/
    public void setPauseTime(int time){
        //1000 miliseconsd = 1 sec ~default 30k akkor
        this.delayTime = time;
    }

    public void runAlgorithm(){
        //while(cache.hasNextState()){
            //while(isPaused) {}
                vertexColors = cache.getCurrentState().getColor();
                edgeColors = cache.getCurrentState().getColorEdge();
                //table = cache.getCurrentState().getOneRow();
        System.out.println("MYSTATE: " + cache.getCurrentState() );
        System.out.println("ALL STATES: " + cache.getMyStates());
                updateScreen();
                /** /
                try {
                    Thread.sleep(delayTime);
                } catch (InterruptedException ex) {
                }
                //TODO: táblázat is!!
                cache.nextState();
                /**/

        //}
    }

    public void stepForward(){
        if(cache.hasNextState()) {
            cache.nextState();
            runAlgorithm();
        }
    }

    public void stepBackward(){
        if(cache.hasPrevState()) {
            cache.prevState();
            runAlgorithm();
        }
    }

    public void setPause(){
        isPaused = true;
    }

    public void setPlay(){
        isPaused = false;
    }
    public void setStop(){
        cache.setToDefault();
        vertexColors = cache.getCurrentState().getColor();
        edgeColors = cache.getCurrentState().getColorEdge();
        table = cache.getCurrentState().getOneRow();
        updateScreen();
        isPaused = true;
    }

    public void setVertexColors(Map<Vertex, Color> colors){
        vertexColors = colors;
        updateScreen();
    }

    public void setTable(Matrix<String> table){
        this.table = table;
        updateScreen();
    }

    public Matrix<String> getTable(){
        return table;
    }

    public double Length(PointView p){
        return sqrt(p.getX() * p.getX() + p.getY() * p.getY());
    }
    /**/

    /** /
     public void updateColorHandler(){
     throw new NotImplementedException();
     }
     /**/
}
