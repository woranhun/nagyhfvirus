package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class DeadDot extends Dot {
    DeadDot(Point p, double r) {
        super(p, r);
    }

    public DeadDot(double x, double y, double r) {
        super(x,y,r);
    }

    public DeadDot(double x, double y, double r, double speedOfDot) {
        super(x,y,r,speedOfDot);
    }

    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> {
            c.getGraphicsContext2D().setFill(Color.BLACK);
            c.getGraphicsContext2D().fillOval(location.x, location.y, radius, radius);
        });
    }
}
