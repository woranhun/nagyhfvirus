package simulator;

import javafx.scene.canvas.Canvas;
import simulatorComponents.Dot;
import simulatorComponents.SimulationMap;
import simulatorComponents.dotTypes;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SimulationPlayer extends TimerTask {
    static ArrayList<Steppable> stepables;
    static ArrayList<Steppable> removeInTheEnd;
    static ArrayList<Steppable> addInTheEnd;
    static int infectedCnt;
    static int deadCnt;
    static int healedCnt;
    static int neutralCnt;
    //One tick is x ms
    static int oneTickInMs;
    SimulationStatisticsStore sss;
    Timer timer;
    boolean paused;
    Canvas canvas;
    int currTick;
    int millisecondsElapsed;
    int minPeriod;

    public SimulationPlayer(SimulationTemplate sim, Canvas canvas, SimulationStatisticsStore sss) {
        deadCnt = 0;
        healedCnt = 0;
        neutralCnt = 0;
        infectedCnt = 0;
        currTick = 0;
        millisecondsElapsed = 0;
        minPeriod = 10;
        oneTickInMs=32;

        this.sss = sss;
        this.canvas = canvas;
        stepables = new ArrayList<>();
        stepables.add(new SimulationMap(canvas));
        for (Dot st : sim.getDots()) {
            stepables.add(st);
            st.init(canvas);

            if (st.getType() == dotTypes.Infectious) {
                infectedCnt++;
            } else if (st.getType() == dotTypes.Healthy) {
                healedCnt++;
            } else if (st.getType() == dotTypes.Dead) {
                deadCnt++;
            } else {
                neutralCnt++;
            }
        }
        timer = new Timer(true);
        timer.schedule(this, 0, minPeriod);
        paused = true;
        removeInTheEnd = new ArrayList<>();
        addInTheEnd = new ArrayList<>();
    }

    public static int getIncubationPeriod() {
        //ToDo parameterize this
        //In tick
        return 500 ;
    }

    public static int getRemoveTime() {
        //ToDo parameterize this
        return 150;
    }

    static public void removeSteppable(Steppable st) {
        removeInTheEnd.add(st);
    }

    static public void addSteppable(Steppable s) {
        addInTheEnd.add(s);
    }

    public static ArrayList<Steppable> getRemove() {
        return removeInTheEnd;
    }

    public static void addInfectedDot() {
        infectedCnt++;
        neutralCnt--;
    }

    public static void addHealedDot() {
        infectedCnt--;
        healedCnt++;
    }

    public static void addDeadDot() {
        infectedCnt--;
        deadCnt++;
    }

    public void moveDotsFromOutOfWindow(Canvas img) {
        for (Steppable s : stepables) {
            if (s.isOutOfWindow(canvas))
                s.moveBack(img);
        }
    }

    public void refresh(Canvas c) {
        c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
        for (Steppable s : stepables) {
            s.refresh(c);
        }
    }

    @Override
    public void run() {
        if (!paused) {
            if(millisecondsElapsed%oneTickInMs==0) {
                forwardOneStep();
            }else{
                System.gc();
            }
            millisecondsElapsed+=minPeriod;
        }
    }

    public void playAndPause() {
        paused = !paused;
    }

    public void forwardOneStep() {
        for (Steppable s : stepables) {
            s.step(canvas);
        }
        //Collision detection
        for (int i = 0; i < stepables.size(); ++i) {
            Steppable s1 = stepables.get(i);
            for (int j = i + 1; j < stepables.size(); ++j) {
                Steppable s2 = stepables.get(j);
                if (s2.isCollidedWith(s1)) {
                    s2.hitBy((Dot) s1);
                }
            }
        }
        if (!removeInTheEnd.isEmpty()) {
            for (Steppable s : removeInTheEnd) {
                stepables.remove(s);
            }
            removeInTheEnd.clear();
        }
        if (!addInTheEnd.isEmpty()) {
            stepables.addAll(addInTheEnd);
            addInTheEnd.clear();
        }
        for (Steppable s : stepables) {
            s.refresh(canvas);
        }
        currTickIncrease();
    }

    private void currTickIncrease() {
        sendData();
        currTick++;
    }

    public void exit() {
        timer.cancel();
        stepables.clear();
        removeInTheEnd.clear();
        addInTheEnd.clear();
        sss.clearAll();
    }

    public void speedUp() {
        if(oneTickInMs>1){
            oneTickInMs/=2;
        }
    }

    public void speedDown() {
        if(oneTickInMs<32){
            oneTickInMs*=2;
        }
    }
    private void sendData(){
        if (currTick%10==0) {
            sss.addDeathsChange(currTick, deadCnt);
            sss.addHealChange(currTick, healedCnt);
            sss.addInfectionChange(currTick, infectedCnt);
            sss.addPopulationChange(currTick, neutralCnt);
        }
    }
}
