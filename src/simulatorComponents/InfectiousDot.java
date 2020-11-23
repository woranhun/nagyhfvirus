package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import simulator.SimulatorPlayer;

import java.util.Random;

public class InfectiousDot extends Dot {
    double infChance;
    double mortChance;
    double healChance;
    int sinceInfectedTickCount=0;

    InfectiousDot(Point p, double r, double s) {
        super(p, r, s);
    }

    public InfectiousDot(double x, double y, double r) {
        super(x, y, r);
    }

    public InfectiousDot(double x, double y, double r, double speedOfDot, double inf, double mort, double heal) {
        super(x, y, r, speedOfDot);
        infChance = inf;
        mortChance = mort;
        healChance = heal;
    }

    public InfectiousDot(Point location, double radius, double speed, double infChance, double mortChance, double healChance) {
        super(location,radius,speed);
        this.infChance=infChance;
        this.mortChance=mortChance;
        this.healChance=healChance;
    }

    @Override
    public void init(Canvas c) {
        draw(c);
        sinceInfectedTickCount=0;
    }

    @Override
    public void hitBy(NeutralDot dot) {
        Random random = new Random();
        double randomVal = random.nextDouble();
        if (this.isCollided(dot)) {
            if(randomVal<infChance){
                SimulatorPlayer.addSteppable(new InfectiousDot(dot.location, dot.radius, dot.speed, infChance,mortChance,healChance));
                dot.remove();
            }
        }
    }

    @Override
    public void hitBy(InfectiousDot id) {
    }

    @Override
    public void hitBy(DeadDot dd){
    }
    @Override
    public void hitBy(HealthyDot hd){
    }


    public void step(Canvas c) {
        Random random = new Random();
        double randomVal = random.nextDouble();
        if(sinceInfectedTickCount>100){
            if (randomVal < mortChance) {
                SimulatorPlayer.addSteppable(new DeadDot(this.location, this.radius));
                SimulatorPlayer.removeSteppable(this);
                return;
            } else {
                if(randomVal < healChance){
                    SimulatorPlayer.addSteppable(new HealthyDot(this.location,this.radius,this.speed));
                    SimulatorPlayer.removeSteppable(this);
                    return;
                }
            }
        }
        sinceInfectedTickCount++;
        calcDirection();
        if(direction.y!=0&&direction.x!=0){
            for(int i=0;i<speed;++i){
                this.location.add(direction);
                if(this.location.isOutOfCanvas(c,radius)){
                    this.bounceBack();
                }
                for(Dot d : SimulatorPlayer.getCollideWith(this)){
                    if(this.location.calcDistance(d.location)<=this.radius+d.radius){
                        d.hitBy(this);
                        this.bounceBack();
                    }
                }
                if(SimulatorPlayer.getRemove().contains(this)){
                    return;
                }
            }
        }
        draw(c);
    }


    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> {
            c.getGraphicsContext2D().setFill(Color.RED);
            c.getGraphicsContext2D().fillOval(location.x, location.y, radius, radius);
        });
    }
}
