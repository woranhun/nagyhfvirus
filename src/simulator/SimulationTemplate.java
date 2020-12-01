package simulator;

import javafx.scene.canvas.Canvas;
import simulatorComponents.Dot;
import simulatorComponents.dotTypes;

import java.util.ArrayList;


/**
 * SimulationTemplate osztaly.
 * Szimulacio elinditasahoz kapcsolatos adatokat tarol.
 * Megvalositja a java.io.Serializable es Cloneable interfeszeket
 */
public class SimulationTemplate implements java.io.Serializable, Cloneable {
    /**
     * Dot-okat tarolo ArrayList.
     */
    private ArrayList<Dot> dots;
    /**
     * atfertozesi esely
     */
    private double infChance;
    /**
     * Halalozasi esely
     */
    private double mortChance;
    /**
     * Gyogyulasi esely
     */
    private double healChance;
    /**
     * Dot sebessege
     */
    private double speedOfDot;

    /**
     * SimulationTemplate konstruktura
     * Beallitja az alapertelmezett ertekeket es letrehozza az objektumot.
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
     * @param st a masolando SimulationTemplate
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
     * clone fuggveny feluldefinialasa.
     * Celja, hogy a SimulationTemplate masolasat megvalositsa.
     *
     * @return Visszadja az objektum masolatat(deep copy)
     * @throws CloneNotSupportedException kivetelt dobhat (de nem fog, mert a dot es a SimulationTemplate is klonozhato)
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
     * Fertozesi esely getter fuggvenye
     *
     * @return A fertozesi esely
     */

    public double getInfChance() {
        return infChance;
    }


    /**
     * Fertozesi esely setter fuggvenye
     * Beallitja a kapott fertozesi eselyt a jovoben letrejovo Dot-okra.
     *
     * @param inf A kapott fertozesi esely
     */
    public void setInfection(double inf) {
        infChance = inf;
    }

    /**
     * Halalozasi esely getter fuggvenye
     *
     * @return A kapott halalozasi esely
     */

    public double getMortChance() {
        return mortChance;
    }

    /**
     * Halalozasi esely setter fuggvenye
     * Beallitja a kapott halalozasi eselyt a jovoben letrejovo Dot-okra.
     *
     * @param mort A kapott halalozasi esely
     */

    public void setMortality(double mort) {
        mortChance = mort;
    }

    /**
     * Gyogyulasi esely getter fuggvenye
     *
     * @return A gyogyulasi esely
     */

    public double getHealChance() {
        return healChance;
    }

    /**
     * Gyogyulasi esely setter fuggvenye
     * Beallitja a kapott gyogyulasi eselyt a jovoben letrejovo Dot-okra.
     *
     * @param heal A kapott gyogyulasi esely
     */

    public void setHealChance(double heal) {
        healChance = heal;
    }


    /**
     * A sebesseg getter fuggvenye
     *
     * @return A sebessege a Dot-nak
     */

    public double getSpeedOfDot() {
        return speedOfDot;
    }

    /**
     * Sebesseg esely setter fuggvenye
     * Beallitja a kapott sebesseget a jovoben letrejovo Dot-okra.
     *
     * @param speed A kapott sebesseg
     */

    public void setSpeed(double speed) {
        speedOfDot = speed;
    }

    /**
     * Dot lista getter fuggvenye
     *
     * @return a dotokat tartalmazo Array list
     */

    public ArrayList<Dot> getDots() {
        return dots;
    }

    /**
     * Hozzaad egy Dot-ot a Dotokat tartalmazo listahoz
     *
     * @param d A kapott Dot
     */

    public void addDot(Dot d) {
        dots.add(d);
    }

    /**
     * Letrehoz a megadott parameterek alapjan egy Dot-ot es hozzaadja a Dot listahoz
     *
     * @param type A letrehozando Dot tipusa
     * @param x    A letrehozando Dot x kordinataja
     * @param y    A letrehozando Dot y kordinataja
     * @param r    A letrehozando Dot sugara
     */

    public void createDot(dotTypes type, double x, double y, double r) {
        addDot(new Dot(x, y, r, speedOfDot, type, infChance, mortChance, healChance));
    }

    /**
     * Frissiti a Canvas tartalmat
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
