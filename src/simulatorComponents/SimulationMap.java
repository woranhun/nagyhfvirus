package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import simulator.Steppable;

public class SimulationMap implements Steppable {
    public SimulationMap(Canvas c) {
        init(c);
    }

    @Override
    public void step(Canvas c) {
        Platform.runLater(() -> c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight()));
    }

    @Override
    public void init(Canvas c) {
        Platform.runLater(() ->  c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight()));
    }

    @Override
    public boolean isCollidedWith(Steppable st) {
        return false;
    }

    @Override
    public boolean isCollidedWith(Dot dot) {
        return false;
    }

    @Override
    public void hitBy(Dot dot) {

    }

    @Override
    public boolean isOutOfWindow(Canvas c) {
        return false;
    }

    @Override
    public void moveBack(Canvas c) {

    }

    @Override
    public void refresh(Canvas c) {
        Platform.runLater(() -> c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight()));
    }
}
