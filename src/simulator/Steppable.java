package simulator;


import javafx.scene.canvas.Canvas;
import simulatorComponents.Dot;
import simulatorComponents.InfectiousDot;
import simulatorComponents.Point;

public interface Steppable{
    void step(Canvas c);
    void init(Canvas c);
    void isCollidedWith(Steppable st);

    void hitBy(Dot dot);
}
