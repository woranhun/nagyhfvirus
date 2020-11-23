package simulator;

import javafx.scene.canvas.Canvas;
import simulatorComponents.SimulationMap;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SimulatorPlayer extends TimerTask{
    Timer timer;
    boolean paused;
    ArrayList<Steppable> steppables = new ArrayList<Steppable>();
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

    public static void removeSteppable(Steppable st) {
        removeInTheEnd.add(st);
    }
    static public void addSteppable(Steppable s){
        addInTheEnd.add(s);
    }

    @Override
    public void run() {
        if(!paused){
            for(Steppable s : steppables){
                s.step(canvas);
                for(Steppable st : steppables){
                    if(st!=s){
                        s.isCollidedWith(st);
                    }
                }
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
    }

    public void playAndPause() {
        paused=!paused;
    }
    public void forwardOneStep() {
        for(Steppable s : steppables){
            s.step(canvas);
        }
    }

    public void exit() {
        timer.cancel();
    }
}
