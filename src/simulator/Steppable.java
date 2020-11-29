package simulator;


import javafx.scene.canvas.Canvas;
import simulatorComponents.*;

public interface Steppable{
    void step(Canvas c);
    void init(Canvas c);
    boolean isCollidedWith(Steppable st);
    boolean isCollidedWith(Dot dot);

    void hitBy(Dot dot);

    boolean isOutOfWindow(Canvas c);

    void moveBack(Canvas c);

    void refresh(Canvas c);
}
