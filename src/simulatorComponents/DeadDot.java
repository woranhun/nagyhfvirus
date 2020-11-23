package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import simulator.SimulatorPlayer;
import simulator.Steppable;

public class DeadDot extends Dot {
    DeadDot(Point p, double r) {
        super(p, r,0.0);
    }

    public DeadDot(double x, double y, double r) {
        super(x,y,r,0.0);
    }

    public DeadDot(double x, double y, double r, double speedOfDot) {
        super(x,y,r,0.0);
    }
    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> {
            c.getGraphicsContext2D().setFill(Color.BLACK);
            c.getGraphicsContext2D().fillOval(location.x, location.y, radius, radius);
        });
    }
    public void step(Canvas c) {
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
}
