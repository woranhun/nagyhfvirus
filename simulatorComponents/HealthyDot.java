package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import simulator.SimulatorPlayer;

public class HealthyDot extends Dot {
    HealthyDot(Point p, double r) {
        super(p, r);
    }

    public HealthyDot(double x, double y, double r) {
        super(x, y, r);
    }

    public HealthyDot(double x, double y, double r, double speedOfDot) {
        super(x, y, r, speedOfDot);
    }

    public HealthyDot(Point location, double radius, double speed) {
        super(location,radius,speed);
    }

    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> {
            c.getGraphicsContext2D().setFill(Color.GREEN);
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
    public void hitBy(DeadDot dd) {
    }

    @Override
    public void hitBy(HealthyDot hd) {
    }

    @Override
    public void hitBy(InfectiousDot id) {
    }
}