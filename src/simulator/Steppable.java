package simulator;


import javafx.scene.canvas.Canvas;

public interface Steppable{
    void step(Canvas c);
    void init(Canvas c);

}
