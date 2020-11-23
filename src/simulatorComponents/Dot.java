package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import simulator.Drawable;
import simulator.SimulatorPlayer;
import simulator.Steppable;

import java.io.Serializable;
import java.util.Random;


public class Dot implements Drawable, Serializable, Steppable {
    Point location = null;
    double radius;
    double speed;
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
    public void setLocation(Point location) {
        this.location = location;
    }

    boolean isCollided(Dot d){
        return location.calcDistance(d.location)<radius+d.radius;
    }

    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> {
            c.getGraphicsContext2D().setFill(Color.GRAY);
            c.getGraphicsContext2D().fillOval(location.x, location.y, radius,radius);
        });
    }

    public Point calcDirection(){
        Random r = new Random();
        return new Point(r.nextInt(3)-1,((double)r.nextInt(3)-1));
    }

    @Override
    public void step(Canvas c) {
        Point dir = calcDirection();
        for(int i=0;i<speed;++i){
            Point p = new Point(0,0);
            p.add(this.location);
            p.add(dir);
         if(p.isOutOfCanvasTop(c,radius)||p.isOutOfCanvasBottom(c,radius)){
             if(p.isOutOfCanvasLeft(c,radius)){
                 dir=new Point(-1*dir.x, dir.y*-1);

             }else if(p.isOutOfCanvasRight(c,radius)){
                 dir=new Point(-1*dir.x, dir.y*-1);
             }else{
                 dir=new Point(dir.x, dir.y*-1);
             }

         }else if(p.isOutOfCanvasLeft(c,radius)||p.isOutOfCanvasRight(c,radius)){
             dir=new Point(dir.x*-1, dir.y);
         }
            this.location=p;
        }
        draw(c);
    }

    @Override
    public void isCollidedWith(Steppable st) {
        st.hitBy(this);
    }

    @Override
    public void hitBy(Dot dot) {
    }

    @Override
    public void init(Canvas c) {
        draw(c);
    }

    protected void remove() {
        SimulatorPlayer.removeSteppable(this);
    }
}
