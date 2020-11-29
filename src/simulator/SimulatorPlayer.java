package simulator;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;
import simulatorComponents.Dot;
import simulatorComponents.Point;
import simulatorComponents.SimulationMap;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SimulatorPlayer extends TimerTask{
    Timer timer;
    boolean paused;
    static ArrayList<Steppable> steppables = new ArrayList<Steppable>();
    static ArrayList<Steppable> removeInTheEnd;
    static ArrayList<Steppable> addInTheEnd;
    static ArrayList<Pair<Dot,Dot>> collidePair = new ArrayList<>();
    Canvas canvas;

    public SimulatorPlayer(SimulationTemplate sim,Canvas canvas) {
        this.canvas=canvas;
        steppables.add(new SimulationMap(canvas));
        for(Steppable st : sim.getDots()){
            steppables.add(st);
            st.init(canvas);
        }
        timer=new Timer(true);
        timer.schedule(this,0,34);
        paused=true;
        removeInTheEnd = new ArrayList<>();
        addInTheEnd = new ArrayList<>();
    }

    public static void addCollidePair(Dot d1, Dot d2) {
        collidePair.add(new Pair<>(d1,d2));
    }

    public void moveDotsFromOutOfWindow(Canvas img) {
        for(Steppable s : steppables){
            if(s.isOutOfWindow(canvas))
                s.moveBack(img);
        }
    }
    public void refresh(Canvas c) {
        c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
        for (Steppable s : steppables) {
            s.refresh(c);
        }
    }

    static public void removeSteppable(Steppable st) {
        removeInTheEnd.add(st);
    }
    static public void addSteppable(Steppable s){
        addInTheEnd.add(s);
    }

    static public ArrayList<Dot> getCollideWith(Dot d){
        ArrayList<Dot> collidedWith = new ArrayList<>();
        for (Steppable s: steppables){
            if(s!=d){
                if(d.isCollidedWith(s))
                    collidedWith.add((Dot) s);
            }
        }
        return collidedWith;
    }

    public static ArrayList<Steppable> getRemove() {
        return  removeInTheEnd;
    }

    @Override
    public void run() {
        if(!paused){
            forwardOneStep();
        }
    }

    public void playAndPause() {
        paused=!paused;
    }
    public void forwardOneStep() {
        for(Steppable s : steppables){
            s.step(canvas);
        }
        for(int i =0; i< steppables.size();++i){
            Steppable s1 = steppables.get(i);
            for(int j =i+1; j < steppables.size();++j){
                Steppable s2 = steppables.get(j);
                if(s2.isCollidedWith(s1)){
                    s2.hitBy((Dot) s1);
                }
            }
        }
        for(Pair<Dot,Dot> p:collidePair){
            p.getKey().hitBy(p.getValue());
        }
        collidePair.clear();
        if(!removeInTheEnd.isEmpty()){
            for(Steppable s : removeInTheEnd){
                steppables.remove(s);
            }
            removeInTheEnd.clear();
        }
        if(!addInTheEnd.isEmpty()){
            steppables.addAll(addInTheEnd);
            addInTheEnd.clear();
        }
        for(Steppable s :steppables){
            s.refresh(canvas);
        }
    }

    public void exit() {
        timer.cancel();
        steppables.clear();
    }
}
