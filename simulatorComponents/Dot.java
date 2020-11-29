package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import simulator.Drawable;
import simulator.SimulatorPlayer;
import simulator.Steppable;

import java.io.Serializable;
import java.util.Random;


public class Dot implements Drawable, Serializable, Steppable, Cloneable {
    Point location = null;
    double radius;
    double speed;
    Point direction;
    dotTypes type;

    public Dot(double x, double y, double r) {
        location = new Point(x, y);
        radius = r;
        this.speed = 1.0;
        type = dotTypes.Neutral;
    }

    public Dot(double x, double y, double r, double speed) {
        location = new Point(x, y);
        radius = r;
        this.speed = speed;
        type = dotTypes.Neutral;
    }

    Dot(Point p, double r) {
        location = p;
        radius = r;
        type = dotTypes.Neutral;
    }

    Dot(Point p, double r, double s) {
        location = p;
        radius = r;
        this.speed = s;
        type = dotTypes.Neutral;
    }

    public Dot(double x, double y, double r, double speedOfDot, dotTypes type) {
        location = new Point(x,y);
        radius = r;
        speed= speedOfDot;
        this.type = type;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Dot cloned = (Dot) super.clone();
        cloned.location = new Point(this.location);
        cloned.radius = radius;
        cloned.speed = speed;
        cloned.type = type;
        if (this.direction == null) {
            cloned.direction = null;
        } else {
            cloned.direction = new Point(this.direction);
        }
        return cloned;
    }

    boolean isCollided(Dot d) {
        return location.calcDistance(d.location) < radius + d.radius;
    }


    public void calcDirection() {
        Random r = new Random();
        direction = new Point(r.nextInt(3) - 1, ((double) r.nextInt(3) - 1));
    }

    @Override
    public boolean isCollidedWith(Steppable st) {
        if (this == st) return false;
        return st.isCollidedWith(this);
    }

    @Override
    public boolean isCollidedWith(Dot dot) {
        if (this == dot) return false;
        return this.location.calcDistance(dot.location) < dot.radius + this.radius;
    }

    @Override
    public void hitBy(NeutralDot dot) {

    }

    @Override
    public void hitBy(DeadDot dot) {

    }

    @Override
    public void hitBy(HealthyDot dot) {

    }

    @Override
    public void hitBy(InfectiousDot dot) {

    }

    @Override
    public void step(Canvas c) {

    }

    @Override
    public void init(Canvas c) {
        draw(c);
    }

    protected void remove() {
        SimulatorPlayer.removeSteppable(this);
    }

    protected void bounceBack() {
        //ToDo rugalmas ütközés
        if (this.direction == null) calcDirection();
        direction = new Point(-1 * direction.x, direction.y * -1);
        location.add(direction);
    }

    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> {
            switch (type) {
                case Neutral -> c.getGraphicsContext2D().setFill(Color.GRAY);
                case Healthy -> c.getGraphicsContext2D().setFill(Color.GREEN);
                case Infectious -> c.getGraphicsContext2D().setFill(Color.RED);
                case Dead -> c.getGraphicsContext2D().setFill(Color.BLACK);
            }
            c.getGraphicsContext2D().fillOval(location.x, location.y, radius, radius);
        });
    }
}
