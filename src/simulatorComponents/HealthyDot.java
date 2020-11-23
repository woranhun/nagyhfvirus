package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

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

    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> {
            c.getGraphicsContext2D().setFill(Color.GREEN);
            c.getGraphicsContext2D().fillOval(location.x, location.y, radius, radius);
        });
    }
}
