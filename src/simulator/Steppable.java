package simulator;


import javafx.scene.canvas.Canvas;
import simulatorComponents.*;

public interface Steppable{
    void step(Canvas c);
    void init(Canvas c);
    boolean isCollidedWith(Steppable st);
    boolean isCollidedWith(Dot dot);

    void hitBy(NeutralDot dot);
    void hitBy(DeadDot dot);
    void hitBy(HealthyDot dot);
    void hitBy(InfectiousDot dot);
}
