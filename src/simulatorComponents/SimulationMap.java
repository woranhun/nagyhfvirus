package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import simulator.Steppable;

/**
 * SimulationMap osztály
 * A pályát, jelképezi.
 * Feladata, hogy kör elején letörli magát
 */
public class SimulationMap implements Steppable {
    /**
     * SimulationMap konstruktorja, meghívja a SimulationMap init függvényét
     * @param c A kapott canvas
     */
    public SimulationMap(Canvas c) {
        init(c);
    }

    /**
     * Lépés során meghívja saját maga refresh függvényét.
     * @param c A kapott Canvas
     */
    @Override
    public void step(Canvas c) {
        refresh(c);
    }

    /**
     * Inicializálja a SimulationMap-et ( Tehát letörli magát)
     * @param c A kapott Canvas
     */
    @Override
    public void init(Canvas c) {
        draw(c);
    }

    /**
     * Megvizsgálja, hogy tudott-e ütközni egy másik Steppable-el.
     * Mindig hamisat ad vissza, mert a pálya nem ütközik, hanem a háttér szerepét tölti be.
     * @param st A kapott Másik Steppable
     * @return Mindig Hamis
     */
    @Override
    public boolean isCollidedWith(Steppable st) {
        return false;
    }
    /**
     * Megvizsgálja, hogy tudott-e ütközni egy Dot-al.
     * Mindig hamisat ad vissza, mert a pálya nem ütközik, hanem a háttér szerepét tölti be.
     * @param dot A kapott Dot
     * @return Mindig Hamis
     */
    @Override
    public boolean isCollidedWith(Dot dot) {
        return false;
    }

    /**
     * Kezeli, hogy mi történik, ha egy Dot eltalálja.
     * Semmi, mert nem tud egy Dot Pályával ütközni, de a léptethetőség miatt szükséges.
     * @param dot A kapott Dot
     */
    @Override
    public void hitBy(Dot dot) {

    }

    /**
     * Megvizsgálja, hogy aza ablakon kívül esik-e. A háttér nem tud az ablakon kívül esni.
     * A léptethetőség miatt szükséges.
     * @param c A kapott Canvas
     * @return Mindig Hamis
     */
    @Override
    public boolean isOutOfWindow(Canvas c) {
        return false;
    }

    /**
     * Visszahúzza az objektumat a Canvas-ra,
     * A léptethetőség miatt szükséges.
     * @param c A kapott Canvas
     */
    @Override
    public void moveBack(Canvas c) {

    }

    /**
     * Frissíti a Canvas-t.
     * Meghívja a draw(Canvas)-t
     * @param c A kapott Canvas
     */
    @Override
    public void refresh(Canvas c) {
        draw(c);
    }
    /**
     * Letörli a Canvas-t
     * @param c A kapott Canvas
     */
    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight()));
    }
}
