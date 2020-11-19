package simulator;

import javafx.scene.canvas.Canvas;
import simulatorComponents.SimulationMap;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SimulatorPlayer extends TimerTask{
    SimulationMap s;
    Timer timer;
    boolean paused;
    ArrayList<Steppable> steppables = new ArrayList<Steppable>();
    Canvas canvas;


    public SimulatorPlayer(SimulationTemplate sim,Canvas c) {
        canvas=c;
        add(new SimulationMap(c));
        for(Steppable st : sim.getDots()){
            add(st);
            st.init(c);
        }
        timer=new Timer();
        timer.schedule(this,0,34);
        paused=true;
    }
    void add(Steppable st){
        steppables.add(st);
    }
    @Override
    public void run() {
        if(!paused){
            for(Steppable s : steppables){
                s.step(canvas);
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
}
