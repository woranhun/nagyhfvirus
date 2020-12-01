package simulator;

import javafx.scene.canvas.Canvas;
import simulatorComponents.Dot;
import simulatorComponents.dotTypes;

import java.util.ArrayList;


/**
 * SimulationTemplate osztály.
 * Szimuláció elindításához kapcsolatos adatokat tárol.
 * Megvalósítja a java.io.Serializable és Cloneable interfészeket
 */
public class SimulationTemplate implements java.io.Serializable, Cloneable {
    /**
     * Dot-okat tároló ArrayList.
     */
    private ArrayList<Dot> dots;
    /**
     * Átfertőzési esély
     */
    private double infChance;
    /**
     * Halálozási esély
     */
    private double mortChance;
    /**
     * Gyógyulási esély
     */
    private double healChance;
    /**
     * Dot sebessége
     */
    private double speedOfDot;

    /**
     * SimulationTemplate konstruktura
     * Beállítja az alapértelmezett értékeket és létrehozza az objektumot.
     */

    public SimulationTemplate() {
        dots = new ArrayList<>();
        infChance = 0;
        mortChance = 0;
        healChance = 0;
        speedOfDot = 0;
    }

    /**
     * Simulation template copy konstruktora
     *
     * @param st a másolandó SimulationTemplate
     */
    public SimulationTemplate(SimulationTemplate st) {
        this.dots = new ArrayList<>();
        for (Dot d : st.dots) {
            try {
                dots.add((Dot) d.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        this.infChance = st.infChance;
        this.mortChance = st.mortChance;
        this.healChance = st.healChance;
        this.speedOfDot = st.speedOfDot;
    }

    /**
     * clone függvény felüldefiniálása.
     * Célja, hogy a SimulationTemplate másolását megvalósítsa.
     *
     * @return Visszadja az objektum másolatát(deep copy)
     * @throws CloneNotSupportedException kivételt dobhat (de nem fog, mert a dot és a SimulationTemplate is klónozható)
     */

    @Override
    public Object clone() throws CloneNotSupportedException {
        SimulationTemplate cloned = (SimulationTemplate) super.clone();
        cloned.dots = new ArrayList<>();
        for (Dot d : this.dots) {
            cloned.dots.add((Dot) d.clone());
        }
        return cloned;
    }

    /**
     * Fertőzési esély getter függvénye
     *
     * @return A fertőzési esély
     */

    public double getInfChance() {
        return infChance;
    }


    /**
     * Fertőzési esély setter függvénye
     * Beállítja a kapott fertőzési esélyt a jövőben létrejövő Dot-okra.
     *
     * @param inf A kapott fertőzési esély
     */
    public void setInfection(double inf) {
        infChance = inf;
    }

    /**
     * Halálozási esély getter függvénye
     *
     * @return A kapott halálozási esély
     */

    public double getMortChance() {
        return mortChance;
    }

    /**
     * Halálozási esély setter függvénye
     * Beállítja a kapott halálozási esélyt a jövőben létrejövő Dot-okra.
     *
     * @param mort A kapott halálozási esély
     */

    public void setMortality(double mort) {
        mortChance = mort;
    }

    /**
     * Gyógyulási esély getter függvénye
     *
     * @return A gyógyulási esély
     */

    public double getHealChance() {
        return healChance;
    }

    /**
     * Gyógyulási esély setter függvénye
     * Beállítja a kapott gyógyulási esélyt a jövőben létrejövő Dot-okra.
     *
     * @param heal A kapott gyógyulási esély
     */

    public void setHealChance(double heal) {
        healChance = heal;
    }


    /**
     * A sebesség getter függvénye
     *
     * @return A sebessége a Dot-nak
     */

    public double getSpeedOfDot() {
        return speedOfDot;
    }

    /**
     * Sebesség esély setter függvénye
     * Beállítja a kapott sebességet a jövőben létrejövő Dot-okra.
     *
     * @param speed A kapott sebesség
     */

    public void setSpeed(double speed) {
        speedOfDot = speed;
    }

    /**
     * Dot lista getter függvénye
     *
     * @return a dotokat tartalmazó Array list
     */

    public ArrayList<Dot> getDots() {
        return dots;
    }

    /**
     * Hozzáad egy Dot-ot a Dotokat tartalmazó listához
     *
     * @param d A kapott Dot
     */

    public void addDot(Dot d) {
        dots.add(d);
    }

    /**
     * Létrehoz a megadott paraméterek alapján egy Dot-ot és hozzáadja a Dot listához
     *
     * @param type A létrehozandó Dot típusa
     * @param x    A létrehozandó Dot x kordinátája
     * @param y    A létrehozandó Dot y kordinátája
     * @param r    A létrehozandó Dot sugara
     */

    public void createDot(dotTypes type, double x, double y, double r) {
        addDot(new Dot(x, y, r, speedOfDot, type, infChance, mortChance, healChance));
    }

    //TODO ez kell?

//    /**
//     * Kitörli a Canvason kívűlre eső Dotokat
//     * @param img A kapott canvas
//     */

//    public void deleteDotsFromOutOfCanvas(Canvas img) {
//        ArrayList<Dot> removeList = new ArrayList<>();
//        for (Dot d : dots) {
//            if (d.getLocation().isOutOfCanvas(img, d.getRadius())) {
//                removeList.add(d);
//            }
//        }
//        dots.removeAll(removeList);
//    }

    /**
     * Frissíti a Canvas tartalmát
     *
     * @param c A kapott canvas
     */
    public void refresh(Canvas c) {
        c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
        for (Dot d : dots) {
            d.init(c);
        }
    }

}
