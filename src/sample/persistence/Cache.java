package sample.persistence;

import sample.model.algorithm.IAlgorithm;
import sample.persistence.graph.Vertex;
import sample.util.MyState;

import java.util.*;

/**
 * Created by Mina on 2017. 09. 04..
 */
public class Cache extends Observable{
    public static List<MyState>  states;
    private MyState currentState;
    private int stateCounter;
    private IAlgorithm algo;

    Observer AlgoChangedHandler = new Observer(){
        @Override
        public void update(Observable o, Object arg){
            if (!(arg instanceof List)){
                return;
            }


        }
    };

    public Cache(IAlgorithm algorithm, Vertex v){
        states = new ArrayList<>();
        stateCounter = 0;
        this.algo = algorithm;
        loadAlgorithm(v);
    }

    private void loadAlgorithm(Vertex v){
        System.out.println("LOAD ALGO LEFUT");
        algo.first(v);
        states.add(new MyState(algo.updateColor()));
        System.out.println("States first utan:_" + states);
        int i=1;
        while(!algo.end()){
            System.out.println("" + i + states);
            algo.next();
            states.add(new MyState(algo.updateColor()));
            ++i;
        }
        System.out.println("FINAL STATES: " + states);
        System.out.println("**********************************LOAD VEGE aktuális state" + stateCounter);
    }

    public Cache(List<MyState> states, IAlgorithm algorithm){
        this.states = states;
        stateCounter = 0;
        if (!states.isEmpty()) {
            this.currentState = states.get(stateCounter);
            this.algo = algorithm;
        }else{
            throw new IllegalArgumentException("Empty states!");
        }

    }

    public List<MyState> getMyStates(){
        return states;
    }

    public void nextState(){
        stateCounter++;
    }

    public void prevState(){
        stateCounter--;
    }

    public MyState getCurrentState(){
        return states.get(stateCounter);
    }

    public MyState getNextState(){
        return states.get(stateCounter+1);
    }

   public void setToDefault(){
        stateCounter = 0;
   }

    public void addState(MyState state){
        states.add(state);
    }

    public boolean hasNextState(){
        return stateCounter < states.size()-1;
    }

    public boolean hasPrevState(){
        return stateCounter > 0;
    }


}
