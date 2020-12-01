package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import simulator.SimulationPlayer;
import simulator.Steppable;

import java.io.Serializable;
import java.util.Random;

/**
 * Dotokat reprezentalo osztaly.
 * Megvalositja a Drawable, Serializable, Steppable es Clonable interfeszeket.
 */
public class Dot implements Serializable, Steppable, Cloneable {
    /**
     * A potty kozepenek pozicioja
     */
    private Point location = null;
    /**
     * A potty sugara
     */
    private double radius;
    /**
     * A potty sebesseg vektoranak vegpontja (Mintha a Vector az (0,0)-bol mutatna velocity-ba)
     */
    private Point velocity;
    /**
     * A potty tipusa
     */
    private dotTypes type;
    /**
     * A potty atfertozesenek eselye
     */
    private double infChance;
    /**
     * A potty halalozasi eselye
     */
    private double mortChance;
    /**
     * A potty gyogyulasi eselye
     */
    private double healChance;
    /**
     * A potty tomege
     */
    private double mass;
    /**
     * Fertozes ota eltelt tickek szama
     */
    private int sinceInfection = 0;
    /**
     * A halal pillanatatul eltelt tickek szama
     */
    private int sinceDead = 0;

    /**
     * A potty konstruktora
     *
     * @param x A potty x kordinataja
     * @param y A potty y kordinataja
     * @param r A potty sugara
     */
    public Dot(double x, double y, double r) {
        location = new Point(x, y);
        radius = r;
        initVelocity(0);
        type = dotTypes.Neutral;
        infChance = 0.0;
        mortChance = 0.0;
        healChance = 0.0;
        this.velocity = new Point(0, 0);
        mass = r * 0.1;
    }

    /**
     * A potty konstruktora
     *
     * @param x     A potty x kordinataja
     * @param y     A potty y kordinataja
     * @param r     A potty sugara
     * @param speed A potty sebessege
     */
    public Dot(double x, double y, double r, double speed) {
        location = new Point(x, y);
        radius = r;
        initVelocity(speed);
        type = dotTypes.Neutral;
        infChance = 0.0;
        mortChance = 0.0;
        healChance = 0.0;
        mass = r * 0.1;
    }

    /**
     * A potty konstruktora
     *
     * @param x          A potty x kordinataja
     * @param y          A potty y kordinataja
     * @param r          A potty sugara
     * @param speed      A potty sebessege
     * @param type       A potty tipusa
     * @param infChance  Masik pottyot ilyen eselyel fertoz, ha a Dot fertozo
     * @param mortChance A potty halalozasi eselye, ha mar megfertozodott es a virus lappangasi ideje lejart
     * @param healChance A potty gyogyulasi eselye, ha mar megfertozodott es a virus lappangasi ideje lejart
     */

    public Dot(double x, double y, double r, double speed, dotTypes type, double infChance, double mortChance, double healChance) {
        location = new Point(x, y);
        radius = r;
        initVelocity(speed);
        this.type = type;
        this.infChance = infChance;
        this.mortChance = mortChance;
        this.healChance = healChance;
        mass = r * 0.1;
    }

    /**
     * A potty altal feluldefinialt clone metodus.
     *
     * @return A potty Object
     * @throws CloneNotSupportedException kivetelt dobhat(De nem fog, mert a Dot szerializalhato)
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Dot cloned = (Dot) super.clone();
        cloned.location = new Point(this.location);
        cloned.radius = radius;
        cloned.velocity = velocity;
        cloned.type = type;
        cloned.infChance = infChance;
        cloned.mortChance = mortChance;
        cloned.healChance = healChance;
        cloned.velocity = new Point(this.velocity);
        cloned.mass = mass;
        cloned.sinceInfection = sinceInfection;
        cloned.sinceDead = sinceDead;
        return cloned;
    }

    /**
     * Sebesseg vektor inicializalasa a kapott sebesseg alapjan.
     * A fuggveny a lehetseges iranyt veletlenszeruen generalja.
     *
     * @param speed A kapott sebesseg
     */
    public void initVelocity(double speed) {
        Random r = new Random();
        Point dir = new Point(r.nextInt(3) - 1, ((double) r.nextInt(3) - 1));

        //1D mozgas eseten hasznald ezt:
        //Point dir = new Point(r.nextInt(2) == 0 ? -1 : 1, 0);

        velocity = new Point(dir.x * speed, dir.y * speed);
    }

    /**
     * Location getterje
     *
     * @return A hely
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Location setterje
     *
     * @param location A kapott hely
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /**
     * Gyogyulasi esely settere
     *
     * @param heal A kapott gyogyulasi esely
     */
    public void setHealChance(double heal) {
        this.healChance = heal;
    }

    /**
     * atfertozesei esely settere
     *
     * @param inf A kapott atfertozesi esely
     */
    public void setInfChance(double inf) {
        this.infChance = inf;
    }

    /**
     * Halalozasi esely settere
     *
     * @param mort A halalozasi gyogyulasi esely
     */
    public void setMortChance(double mort) {
        this.mortChance = mort;
    }

    /**
     * A potty tipusanak gettere
     *
     * @return A potty tipusa
     */
    public dotTypes getType() {
        return this.type;
    }

    /**
     * A potty sugaranak gettere
     *
     * @return A potty sugara
     */
    public double getRadius() {
        return radius;
    }


    /**
     * A potty utkozott-e a kapott steppable-el?
     *
     * @param st A kapott steppable
     * @return Igen vagy Nem
     */

    @Override
    public boolean isCollidedWith(Steppable st) {
        if (this == st) return false;
        return st.isCollidedWith(this);
    }

    /**
     * A potty utkozott-e a kapott steppable-el?
     *
     * @param dot A kapott potty
     * @return Igen vagy Nem, a ket potty kozepei kozott levo tavolsag es a sugarak osszege alapjan.
     */
    @Override
    public boolean isCollidedWith(Dot dot) {
        if (this == dot) return false;
        return this.location.calcDistance(dot.location) <= dot.radius + this.radius;
    }

    /**
     * Megvizsgalja, hogy a potty a canvason kivul van-e
     *
     * @param c A kapott Canvas
     * @return Igen vagy Nem
     */
    @Override
    public boolean isOutOfWindow(Canvas c) {
        return this.location.isOutOfCanvas(c, radius);
    }

    /**
     * A potty masik pottyel valo utkozese soran hivodik.
     * Ez kezeli a statikus (pl.: ket potty fedne egymast) es dinamikus utkozest (rugalmas utkozes)
     *
     * @param d A masik potty
     */

    @Override
    public void hitBy(Dot d) {
        //Static collision, e.g. Overlap
        //First calculate the distances between the centers.
        double dstBetweenCenters = this.location.calcDistance(d.location);
        //They're the same, if and only if the current time is tick 0. So we can remove one of the dots.
        if (dstBetweenCenters == 0) {
            this.remove();
            return;
        }
        //Calculate the Overlap correction
        double correctionOverlap = 0.5 * (this.radius + d.radius - dstBetweenCenters);

        //Calculate normalized direction of the locations
        Point dif = new Point(this.location);
        dif.subtract(d.location);
        dif.divide(dstBetweenCenters);

        //Calculate direction multiplied with the correction Overlap
        Point dir = new Point(dif);
        dif.multiply(correctionOverlap);

        //Move this to the right and down
        this.location.add(dif);

        //Move the other to the left and up
        d.location.subtract(dif);

        //Dynamic collision

        //Calculate velocity Difference
        Point velocityDifference = new Point(this.velocity);
        velocityDifference.subtract(d.velocity);

        //Calculate momentum
        double p = 2 * dir.dotProduct(velocityDifference) / (this.mass + d.mass);

        //Calculate new velocity of this
        Point temp = new Point(dir);
        temp.multiply(p * d.mass);
        this.velocity.subtract(temp);

        //Calculate new velocity of other
        Point temp2 = new Point(dir);
        temp2.multiply(p * this.mass);
        d.velocity.add(temp2);

        //type based events after collision
        //Infect
        if (this.type == dotTypes.Infectious) {
            if (d.type == dotTypes.Neutral) {
                Random random = new Random();
                double randomVal = random.nextDouble();
                if (randomVal < infChance) {
                    d.infectedBy(this);
                }

            }

        } else if (d.type == dotTypes.Infectious) {
            if (this.type == dotTypes.Neutral) {
                Random random = new Random();
                double randomVal = random.nextDouble();
                if (randomVal < infChance) {
                    this.infectedBy(d);
                }
            }

        }
        //Anchor dead dot
        if (this.type == dotTypes.Dead && (this.velocity.x != 0 || this.velocity.y != 0)) {
            this.velocity = new Point(0, 0);
        }
        if (d.type == dotTypes.Dead && (d.velocity.x != 0 || d.velocity.y != 0)) {
            d.velocity = new Point(0, 0);
        }
    }

    /**
     * Fertozes bekovetkezese
     * Feladata, hogy ennek a pottynek a megfelelo adatait beallitsa,
     * a SimulationPlayer fele jelzi, hogy uj fertozes tortent
     *
     * @param d A fertozest okozo potty
     */
    private void infectedBy(Dot d) {
        this.type = dotTypes.Infectious;
        this.infChance = d.infChance;
        this.healChance = d.healChance;
        this.mortChance = d.mortChance;
        SimulationPlayer.addInfectedDot();
    }

    /**
     * Lepes fuggveny
     * Feladatai: Lappangasi ido vizsgalata,
     * Halalozas utan eltelt ido vizsgalata,
     * Palya szelevel torteno utkozes,
     * potty leptetese
     *
     * @param c A kapott Canvas
     */
    @Override
    public void step(Canvas c) {
        if (this.type == dotTypes.Infectious) {
            sinceInfection++;
            if (sinceInfection >= SimulationPlayer.getIncubationPeriod()) {
                Random random = new Random();
                if (random.nextDouble() < mortChance) {
                    this.die();
                } else {
                    if (random.nextDouble() < healChance) {
                        this.heal();
                    }
                }
            }
        } else if (this.type == dotTypes.Dead) {
            sinceDead++;
            if (sinceDead >= SimulationPlayer.getRemoveTime()) {
                this.remove();
                return;
            }
        }
        //Bounce with map border
        if (this.location.isOutOfCanvas(c, radius)) {
            this.bounceBack(c);
            this.location.add(velocity);
        }
        if (this.type == dotTypes.Dead && (this.velocity.x != 0 || this.velocity.y != 0)) velocity = new Point(0, 0);
        this.location.add(velocity);
    }

    /**
     * A potty meggyogyul
     * Feladatai: A potty tipusanak megvaltoztatasa, SimulationPlayer fele jelzi, hogy egy potty meggyogyult
     */
    private void heal() {
        this.type = dotTypes.Healthy;
        SimulationPlayer.addHealedDot();
    }

    /**
     * A potty meghal
     * Feladatai: A potty tipusanak megvaltoztatasa, SimulationPlayer fele jelzi, hogy egy potty meghalt
     */
    private void die() {
        this.type = dotTypes.Dead;
        this.velocity = new Point(0, 0);
        SimulationPlayer.addDeadDot();
    }

    /**
     * A potty hozzaadasa a kor vegen eltavolitandok listajahoz
     */
    protected void remove() {
        SimulationPlayer.removeSteppable(this);
    }


    /**
     * Visszahuzza a pottyot a Canvas teruletere, ha kiment belole
     *
     * @param c A kapott Canvas
     */
    @Override
    public void moveBack(Canvas c) {
        if (this.location.isOutOfCanvasBottom(c, radius)) {
            double dyFromBottom = this.location.calcDistance(new Point(this.location.x, c.getHeight())) + 2 * radius;
            this.location.subtract(new Point(0, dyFromBottom));
        }
        if (this.location.isOutOfCanvasTop(c, radius)) {
            double dyFromTop = this.location.calcDistance(new Point(this.location.x, 0)) + 2 * radius;
            this.location.add(new Point(0, dyFromTop));
        }
        if (this.location.isOutOfCanvasRight(c, radius)) {
            double dxFromRight = this.location.calcDistance(new Point(c.getWidth(), this.location.y)) + 2 * radius;
            this.location.subtract(new Point(dxFromRight * (-1), 0));
        }
        if (this.location.isOutOfCanvasLeft(c, radius)) {
            double dxFromLeft = this.location.calcDistance(new Point(0, this.location.y)) + 2 * radius;
            this.location.add(new Point(dxFromLeft, 0));
        }
    }

    /**
     * A potty a Canvas szelen visszapattan
     *
     * @param c A kapott Canvas
     */
    protected void bounceBack(Canvas c) {
        if (location.isOutOfCanvasLeft(c, radius) || location.isOutOfCanvasRight(c, radius)) {
            velocity = new Point(-velocity.x, velocity.y);
        }
        if (location.isOutOfCanvasTop(c, radius) || location.isOutOfCanvasBottom(c, radius)) {
            velocity = new Point(velocity.x, -velocity.y);
        }
    }


    /**
     * A pottyot inicializalja: Ha a Canvason kivul van visszahuzza ot, majd kirajzolja
     *
     * @param c A kapott Canvas
     */
    @Override
    public void init(Canvas c) {
        if (this.isOutOfWindow(c)) this.moveBack(c);
        draw(c);
    }

    /**
     * A potty ujrarajzolasa
     *
     * @param c A kapott Canvas
     */
    @Override
    public void refresh(Canvas c) {
        this.draw(c);
    }


    /**
     * A pottyot kirajzolo fuggveny.
     * Kirajzolja a pottyot, majd rarajzolja a sebesseg vektorat
     *
     * @param c A kapott Canvas
     */
    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> {
            //Draw Dot itself
            switch (type) {
                case Neutral:{
                    c.getGraphicsContext2D().setFill(Color.GRAY);
                    break;
                }
                case Healthy:{
                    c.getGraphicsContext2D().setFill(Color.GREEN);
                    break;
                }
                case Infectious :{
                    c.getGraphicsContext2D().setFill(Color.RED);
                    break;
                }
                case Dead :{
                    c.getGraphicsContext2D().setFill(Color.BLACK);
                    break;
                }
            }
            c.getGraphicsContext2D().fillOval(location.x - radius, location.y - radius, radius * 2, radius * 2);

            //Draw Velocity
            Point dir = new Point((this.velocity.x < 0 ? -1 : (this.velocity.x > 0 ? 1 : 0)) * radius, (this.velocity.y < 0 ? -1 : (this.velocity.y > 0 ? 1 : 0)) * radius);
            dir.add(this.location);
            c.getGraphicsContext2D().setStroke(Color.CYAN);
            if (this.location.calcDisplacement(dir) > 0) {
                c.getGraphicsContext2D().strokeLine(dir.x, dir.y, this.location.x, this.location.y);
            } else {
                c.getGraphicsContext2D().strokeLine(this.location.x, this.location.y, dir.x, dir.y);
            }
        });
    }

    /**
     * Ket potty kozepe folott megrajzolja a vectort. Debug celokra hasznaltam, de nem toroltem.
     *
     * @param dot A masik Dot
     * @param c   A kapott Canvas
     */
    private void drawCenters(Dot dot, Canvas c) {
        Platform.runLater(() -> {
            c.getGraphicsContext2D().setStroke(Color.BLUE);
            c.getGraphicsContext2D().strokeLine(this.location.x, this.location.y, dot.location.x, dot.location.y);
        });
    }
}