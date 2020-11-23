package simulator;


import javafx.scene.canvas.Canvas;
import simulatorComponents.*;

public interface Steppable{
    void step(Canvas c);
    void init(Canvas c);
    void isCollidedWith(Steppable st);

    void hitBy(Dot dot);
    void hitBy(DeadDot dd);
    void hitBy(HealthyDot hd);
}
