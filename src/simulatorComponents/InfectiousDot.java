package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import simulator.SimulatorPlayer;
import simulator.Steppable;

public class InfectiousDot extends Dot {
    InfectiousDot(Point p, double r, double s) {
        super(p, r, s);
    }

    public InfectiousDot(double x, double y, double r) {
        super(x, y, r);
    }

    public InfectiousDot(double x, double y, double r, double speedOfDot) {
        super(x, y, r, speedOfDot);
    }

    @Override
    public void hitBy(Dot dot) {
        if(this.isCollided(dot)){
            SimulatorPlayer.addSteppable(new InfectiousDot(dot.location,dot.radius,dot.speed));
            dot.remove();
        }
    }

    @Override
    public void isCollidedWith(Steppable st) {
        //No need to call here twice
    }

    @Override
    public void draw(Canvas c) {
        Platform.runLater(() ->{
            c.getGraphicsContext2D().setFill(Color.RED);
            c.getGraphicsContext2D().fillOval(location.x, location.y, radius, radius);
        });
    }
}
