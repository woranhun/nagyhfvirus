package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import simulator.SimulationPlayer;
import simulator.Steppable;

import java.io.Serializable;
import java.util.Random;

/**
 * Dotokat reprezentáló osztály.
 * Megvalósítja a Drawable, Serializable, Steppable és Clonable interfészeket.
 */
public class Dot implements Serializable, Steppable, Cloneable {
    /**
     * A pötty közepének poziciója
     */
    private Point location = null;
    /**
     * A pötty sugara
     */
    private double radius;
    /**
     * A pötty sebesség vektorának végpontja (Mintha a Vector az (0,0)-ból mutatna velocity-ba)
     */
    private Point velocity;
    /**
     * A pötty típusa
     */
    private dotTypes type;
    /**
     * A pötty átfertőzésének esélye
     */
    private double infChance;
    /**
     * A pötty halálozási esélye
     */
    private double mortChance;
    /**
     * A pötty gyógyulási esélye
     */
    private double healChance;
    /**
     * A pötty tömege
     */
    private double mass;
    /**
     * Fertőzés óta eltelt tickek száma
     */
    private int sinceInfection = 0;
    /**
     * A halál pillanatátül eltelt tickek száma
     */
    private int sinceDead = 0;

    /**
     * A pötty konstruktora
     *
     * @param x A pötty x kordinátája
     * @param y A pötty y kordinátája
     * @param r A pötty sugara
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
     * A pötty konstruktora
     *
     * @param x     A pötty x kordinátája
     * @param y     A pötty y kordinátája
     * @param r     A pötty sugara
     * @param speed A pötty sebessége
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
     * A pötty konstruktora
     *
     * @param x          A pötty x kordinátája
     * @param y          A pötty y kordinátája
     * @param r          A pötty sugara
     * @param speed      A pötty sebessége
     * @param type       A pötty típusa
     * @param infChance  Másik pöttyöt ilyen esélyel fertőz, ha a Dot fertőző
     * @param mortChance A pötty halálozási esélye, ha már megfertőzödött és a vírus lappangási ideje lejárt
     * @param healChance A pötty gyógyulási esélye, ha már megfertőzödött és a vírus lappangási ideje lejárt
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
     * A pötty által felüldefiniált clone metódus.
     *
     * @return A pötty Object
     * @throws CloneNotSupportedException kivételt dobhat(De nem fog, mert a Dot szerializálható)
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
     * Sebesség vektor inicializálása a kapott sebesség alapján.
     * A függvény a lehetséges irányt véletlenszerűen generálja.
     *
     * @param speed A kapott sebesség
     */
    public void initVelocity(double speed) {
        Random r = new Random();
        Point dir = new Point(r.nextInt(3) - 1, ((double) r.nextInt(3) - 1));

        //1D mozgás esetén használd ezt:
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
     * Gyógyulási esély settere
     *
     * @param heal A kapott gyógyulási esély
     */
    public void setHealChance(double heal) {
        this.healChance = heal;
    }

    /**
     * Átfertőzései esély settere
     *
     * @param inf A kapott átfertőzési esély
     */
    public void setInfChance(double inf) {
        this.infChance = inf;
    }

    /**
     * Halálozási esély settere
     *
     * @param mort A halálozási gyógyulási esély
     */
    public void setMortChance(double mort) {
        this.mortChance = mort;
    }

    /**
     * A pötty típusának gettere
     *
     * @return A pötty típusa
     */
    public dotTypes getType() {
        return this.type;
    }

    /**
     * A pötty sugarának gettere
     *
     * @return A pötty sugara
     */
    public double getRadius() {
        return radius;
    }


    /**
     * A pötty ütközött-e a kapott steppable-el?
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
     * A pötty ütközött-e a kapott steppable-el?
     *
     * @param dot A kapott pötty
     * @return Igen vagy Nem, a két pötty közepei között lévő távolság és a sugarak összege alapján.
     */
    @Override
    public boolean isCollidedWith(Dot dot) {
        if (this == dot) return false;
        return this.location.calcDistance(dot.location) <= dot.radius + this.radius;
    }

    /**
     * Megvizsgálja, hogy a pötty a canvason kívül van-e
     *
     * @param c A kapott Canvas
     * @return Igen vagy Nem
     */
    @Override
    public boolean isOutOfWindow(Canvas c) {
        return this.location.isOutOfCanvas(c, radius);
    }

    /**
     * A pötty másik pöttyel való ütközése során hívódik.
     * Ez kezeli a statikus (pl.: két pötty fedné egymást) és dinamikus ütközést (rugalmas ütközés)
     *
     * @param d A másik pötty
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
     * Fertőzés bekövetkezése
     * Feladata, hogy ennek a pöttynek a megfelelő adatait beállítsa,
     * a SimulationPlayer felé jelzi, hogy új fertőzés történt
     *
     * @param d A fertőzést okozó pötty
     */
    private void infectedBy(Dot d) {
        this.type = dotTypes.Infectious;
        this.infChance = d.infChance;
        this.healChance = d.healChance;
        this.mortChance = d.mortChance;
        SimulationPlayer.addInfectedDot();
    }

    /**
     * Lépés függvény
     * Feladatai: Lappangási idő vizsgálata,
     * Halálozás után eltelt idő vizsgálata,
     * Pálya szélével történő ütközés,
     * pötty léptetése
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
     * A pötty meggyógyul
     * Feladatai: A pötty típusának megváltoztatása, SimulationPlayer felé jelzi, hogy egy pötty meggyógyult
     */
    private void heal() {
        this.type = dotTypes.Healthy;
        SimulationPlayer.addHealedDot();
    }

    /**
     * A pötty meghal
     * Feladatai: A pötty típusának megváltoztatása, SimulationPlayer felé jelzi, hogy egy pötty meghalt
     */
    private void die() {
        this.type = dotTypes.Dead;
        this.velocity = new Point(0, 0);
        SimulationPlayer.addDeadDot();
    }

    /**
     * A pötty hozzáadása a kör végén eltávolítandók listájához
     */
    protected void remove() {
        SimulationPlayer.removeSteppable(this);
    }


    /**
     * Visszahúzza a pöttyöt a Canvas területére, ha kiment belőle
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
     * A pötty a Canvas szélén visszapattan
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
     * A pöttyöt inicializálja: Ha a Canvason kívül van visszahúzza őt, majd kirajzolja
     *
     * @param c A kapott Canvas
     */
    @Override
    public void init(Canvas c) {
        if (this.isOutOfWindow(c)) this.moveBack(c);
        draw(c);
    }

    /**
     * A pötty újrarajzolása
     *
     * @param c A kapott Canvas
     */
    @Override
    public void refresh(Canvas c) {
        this.draw(c);
    }


    /**
     * A pöttyöt kirajzoló függvény.
     * Kirajzolja a pöttyöt, majd rárajzolja a sebesség vektorát
     *
     * @param c A kapott Canvas
     */
    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> {
            //Draw Dot itself
            switch (type) {
                case Neutral -> c.getGraphicsContext2D().setFill(Color.GRAY);
                case Healthy -> c.getGraphicsContext2D().setFill(Color.GREEN);
                case Infectious -> c.getGraphicsContext2D().setFill(Color.RED);
                case Dead -> c.getGraphicsContext2D().setFill(Color.BLACK);
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
     * Két pötty közepe fölött megrajzolja a vectort. Debug célokra használtam, de nem töröltem.
     *
     * @param dot A másik Dot
     * @param c   A kapott Canvas
     */
    private void drawCenters(Dot dot, Canvas c) {
        Platform.runLater(() -> {
            c.getGraphicsContext2D().setStroke(Color.BLUE);
            c.getGraphicsContext2D().strokeLine(this.location.x, this.location.y, dot.location.x, dot.location.y);
        });
    }
}