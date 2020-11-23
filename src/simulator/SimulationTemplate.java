package simulator;

import javafx.scene.canvas.Canvas;
import simulatorComponents.*;

import java.util.ArrayList;

public class SimulationTemplate implements java.io.Serializable {
    ArrayList<Dot> dots = new ArrayList<>();
    double infChance;
    double mortChance;
    double healChance;
    double speedOfDot;

    public SimulationTemplate() {

    }
    public SimulationTemplate(SimulationTemplate st){
        this.dots=st.dots;
        this.infChance=st.infChance;
        this.mortChance=st.mortChance;
        this.healChance=st.healChance;
        this.speedOfDot=st.speedOfDot;
    }

    public double getInfChance() {
        return infChance;
    }

    public double getMortChance() {
        return mortChance;
    }

    public double getHealChance() {
        return healChance;
    }

    public void setHealChance(double heal) {
        healChance = heal;
    }

    public double getSpeedOfDot() {
        return speedOfDot;
    }

    public ArrayList<Dot> getDots() {
        return dots;
    }

    void addDot(Dot d) {
        dots.add(d);
    }

    public void createDot(dotTypes type, double x, double y, double r) {
        switch (type) {
            case Dead -> {
                addDot(new DeadDot(x, y, r, speedOfDot));
            }
            case Healthy -> {
                addDot(new HealthyDot(x, y, r, speedOfDot));
            }
            case Neutral -> {
                addDot(new Dot(x, y, r, speedOfDot));
            }
            case Infectious -> {
                addDot(new InfectiousDot(x, y, r, speedOfDot,infChance,mortChance,healChance));
            }
            case None -> {

            }
        }
    }

    public void refresh(Canvas c) {
        c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
        for (Dot d : dots) {
            d.draw(c);
        }
    }

    public void setInfection(double inf) {
        infChance = inf;
    }

    public void setMortality(double mort) {
        mortChance = mort;
    }

    public void setSpeed(double speed) {
        speedOfDot = speed;
    }

    public void deleteDotsFromOutOfWindow(Canvas img) {
        ArrayList<Dot> removeList= new ArrayList<Dot>();
        for(Dot d : dots){
            if(d.getLocation().isOutOfCanvas(img)){
                removeList.add(d);
            }
        }
        dots.removeAll(removeList);
    }
}
