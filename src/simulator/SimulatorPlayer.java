package simulator;

import javafx.scene.canvas.Canvas;
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

    static public void removeSteppable(Steppable st) {
        removeInTheEnd.add(st);
    }
    static public void addSteppable(Steppable s){
        addInTheEnd.add(s);
    }

    static public ArrayList<Dot> getCollideWith(Dot d){
        ArrayList<Dot> collidedWith = new ArrayList<>();
        for (Steppable s: steppables){
            if(d.isCollidedWith(s)) collidedWith.add((Dot) s);
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
    }

    public void exit() {
        timer.cancel();
    }
}
