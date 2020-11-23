package simulatorComponents;

import javafx.scene.canvas.Canvas;
import simulator.Drawable;
import simulator.SimulatorPlayer;
import simulator.Steppable;

import java.io.Serializable;
import java.util.Random;


public abstract class Dot implements Drawable, Serializable, Steppable,Cloneable {
    Point location = null;
    double radius;
    double speed;
    Point direction;
    public Dot(double x, double y, double r){
        location = new Point(x,y);
        radius= r;
        this.speed=1.0;
    }
    public Dot(double x, double y, double r,double speed){
        location = new Point(x,y);
        radius= r;
        this.speed=speed;
    }
    Dot(Point p, double r){
        location=p;
        radius=r;
    }
    Dot(Point p, double r, double s){
        location=p;
        radius=r;
        this.speed = s;
    }

    public Point getLocation() {
        return location;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        Dot cloned = (Dot) super.clone();
        cloned.location = new Point(this.location);
        cloned.radius=radius;
        cloned.speed=speed;
        if(this.direction==null){
            cloned.direction=null;
        }else{
            cloned.direction=new Point(this.direction);
        }
        return cloned;
    }
    public void setLocation(Point location) {
        this.location = location;
    }

    boolean isCollided(Dot d){
        return location.calcDistance(d.location)<radius+d.radius;
    }


    public void calcDirection(){
        Random r = new Random();
        direction = new Point(r.nextInt(3)-1,((double)r.nextInt(3)-1));
    }

    @Override
    public boolean isCollidedWith(Steppable st) {
        if(this==st)return false;
        return st.isCollidedWith(this);
    }

    @Override
    public boolean isCollidedWith(Dot dot) {
        if(this==dot)return false;
        return this.location.calcDistance(dot.location) < dot.radius + this.radius;
    }

    @Override
    public void init(Canvas c) {
        draw(c);
    }

    protected void remove() {
        SimulatorPlayer.removeSteppable(this);
    }

    protected void bounceBack() {
        if(this.direction==null)calcDirection();
        direction=new Point(-1*direction.x, direction.y*-1);
        location.add(direction);
    }
}
