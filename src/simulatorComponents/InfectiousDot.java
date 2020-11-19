package simulatorComponents;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class InfectiousDot extends Dot {
    InfectiousDot(Point p, double r) {
        super(p, r);
    }

    public InfectiousDot(double x, double y, double r) {
        super(x, y, r);
    }

    public InfectiousDot(double x, double y, double r, double speedOfDot) {
        super(x, y, r, speedOfDot);
    }

    @Override
    public void draw(Canvas c) {
        c.getGraphicsContext2D().setFill(Color.RED);
        c.getGraphicsContext2D().fillOval(location.x, location.y, radius, radius);
    }
}
