package simulator;

import javafx.scene.canvas.Canvas;
import simulatorComponents.*;

import java.util.ArrayList;

public class SimulationTemplate implements java.io.Serializable,Cloneable {
    ArrayList<Dot> dots = new ArrayList<>();
    double infChance;
    double mortChance;
    double healChance;
    double speedOfDot;

    public SimulationTemplate() {

    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        SimulationTemplate cloned = (SimulationTemplate) super.clone();
        cloned.dots=new ArrayList<>();
        for(Dot d : this.dots){
            cloned.dots.add((Dot) d.clone());
        }
        return cloned;
    }
    public SimulationTemplate(SimulationTemplate st){
        this.dots = new ArrayList<>();
        for(Dot d : st.dots){
            try{
                dots.add((Dot) d.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
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
        for(Dot d : dots){
            d.setHealChance(heal);
        }
    }
    public void setInfection(double inf) {
        infChance = inf;
        for(Dot d : dots){
            d.setInfChance(inf);
        }
    }

    public void setMortality(double mort) {
        mortChance = mort;
        for(Dot d : dots){
            d.setMortChance(mort);
        }
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
        addDot(new Dot(x, y, r, speedOfDot, type,infChance,mortChance,healChance));
    }

    public void refresh(Canvas c) {
        c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
        for (Dot d : dots) {
            d.init(c);
        }
    }


    public void setSpeed(double speed) {
        speedOfDot = speed;
    }

    public void deleteDotsFromOutOfWindow(Canvas img) {
        ArrayList<Dot> removeList= new ArrayList<Dot>();
        for(Dot d : dots){
            if(d.getLocation().isOutOfCanvas(img,d.getRadius())){
                removeList.add(d);
            }
        }
        dots.removeAll(removeList);
    }

}
