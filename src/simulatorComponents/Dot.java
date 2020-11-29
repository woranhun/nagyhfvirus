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
    Point velocity;
    dotTypes type;
    double infChance;
    double mortChance;
    double healChance;
    double mass;


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

    public Dot(double x, double y, double r, double speedOfDot, dotTypes type, double infChance, double mortChance, double healChance) {
        location = new Point(x, y);
        radius = r;
        initVelocity(speedOfDot);
        this.type = type;
        this.infChance = infChance;
        this.mortChance = mortChance;
        this.healChance = healChance;
        mass = r * 0.1;
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
        //cloned.radius = 10;
        cloned.velocity = velocity;
        cloned.type = type;
        cloned.infChance = infChance;
        cloned.mortChance = mortChance;
        cloned.healChance = healChance;
        cloned.velocity = new Point(this.velocity);
        cloned.mass = mass;
        return cloned;
    }

    boolean isCollided(Dot d) {
        return location.calcDistance(d.location) < radius + d.radius;
    }


    public void initVelocity(double speed) {
        Random r = new Random();
        Point dir = new Point(r.nextInt(3) - 1, ((double) r.nextInt(3) - 1));
        //Point dir = new Point(r.nextInt(2) == 0 ? -1 : 1, 0);
        velocity = new Point(dir.x * speed, dir.y * speed);
    }

    @Override
    public boolean isCollidedWith(Steppable st) {
        if (this == st) return false;
        return st.isCollidedWith(this);
    }

    @Override
    public boolean isCollidedWith(Dot dot) {
        if (this == dot) return false;
        return this.location.calcDistance(dot.location) <= dot.radius + this.radius;
    }

    @Override
    public void hitBy(Dot d) {

        //Static collison, e.g. Overlap
        double dstBetweenCenters = this.location.calcDistance(d.location);
        double correctionOverlap = 0.5 * (this.radius + d.radius - dstBetweenCenters);

        Point dif = new Point(this.location);
        dif.subtract(d.location);
        dif.divide(dstBetweenCenters);
        Point dir = new Point(dif);
        dif.multiply(correctionOverlap);

        this.location.add(dif);

        d.location.subtract(dif);

        //Dynamic collision

        //Calculate Direction Vector

        Point k = new Point(this.velocity);
        k.subtract(d.velocity);

        double p = 2 * dir.dotProduct(k) / (this.mass + d.mass);

        Point temp = new Point(dir);
        temp.multiply(p * d.mass);
        this.velocity.subtract(temp);

        Point temp2 = new Point(dir);
        temp2.multiply(p * this.mass);
        d.velocity.add(temp2);
        //TODO infection comes here
    }

    @Override
    public void step(Canvas c) {
        this.location.add(velocity);
        if (this.location.isOutOfCanvas(c, radius)) {
            this.bounceBack(c);
        }
        if (SimulatorPlayer.getRemove().contains(this)) {
            return;
        }
    }


    @Override
    public boolean isOutOfWindow(Canvas c) {
        return this.location.isOutOfCanvas(c, radius);
    }

    @Override
    public void moveBack(Canvas c) {
        double dx = this.location.calcDistance(new Point(c.getWidth(), this.location.y)) + radius * 2;
        double dy = this.location.calcDistance(new Point(this.location.x, c.getHeight())) + radius * 2;
        if (this.location.isOutOfCanvasBottom(c, radius))
            this.location.add(new Point(0, dy * (-1)));
        if (this.location.isOutOfCanvasRight(c, radius))
            this.location.add(new Point(dx * (-1), 0));
    }

    @Override
    public void init(Canvas c) {
        draw(c);
    }

    @Override
    public void refresh(Canvas c) {
        this.draw(c);
    }

    protected void remove() {
        SimulatorPlayer.removeSteppable(this);
    }

    protected void bounceBack(Canvas c) {
        //ToDo rugalmas ütközés
        if(location.isOutOfCanvasLeft(c, radius)|| location.isOutOfCanvasRight(c,radius)){
            velocity = new Point(-velocity.x, velocity.y);
        }
        if(location.isOutOfCanvasTop(c,radius)|| location.isOutOfCanvasBottom(c,radius)){
            velocity = new Point(velocity.x, -velocity.y);
        }
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
            c.getGraphicsContext2D().fillOval(location.x - radius, location.y - radius, radius * 2, radius * 2);

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

    private void drawCenters(Dot dot, Canvas c) {
        Platform.runLater(() -> {
            c.getGraphicsContext2D().setStroke(Color.BLUE);
            c.getGraphicsContext2D().strokeLine(this.location.x, this.location.y, dot.location.x, dot.location.y);
        });
    }
}
