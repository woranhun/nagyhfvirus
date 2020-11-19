package simulatorComponents;

import javafx.scene.canvas.Canvas;
import simulator.Steppable;

public class SimulationMap implements Steppable {
    public SimulationMap(Canvas c) {
        init(c);
    }

    @Override
    public void step(Canvas c) {
        c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
    }

    @Override
    public void init(Canvas c) {
        c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
    }
}
