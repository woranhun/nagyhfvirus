package simulator;


import javafx.scene.canvas.Canvas;
import simulatorComponents.Dot;

/**
 * Interfész, amely  a léptethető dolgokat valósítja meg
 */
public interface Steppable {
    /**
     * Egy körben végzendő lépések
     *
     * @param c A kapott Canvas
     */
    void step(Canvas c);

    /**
     * Szimuláció elején végzendő lépések
     *
     * @param c A kapott Canvas
     */
    void init(Canvas c);

    /**
     * Ütközött-e a kapott Steppable-el?
     *
     * @param st A kapott Másik Steppable
     * @return Igen vagy Nem
     */
    boolean isCollidedWith(Steppable st);

    /**
     * Ütközött-e a kapott Dot-al?
     *
     * @param dot A kapott Másik Dot
     * @return Igen vagy Nem
     */
    boolean isCollidedWith(Dot dot);

    /**
     * Dottal való ütközést lekezelő függvény
     *
     * @param dot A kapott Dot
     */
    void hitBy(Dot dot);

    /**
     * A léptethető dolog a Canvason kívül tartózkodik-e?
     *
     * @param c A kapott Canvas
     * @return Igen vagy Nem
     */
    boolean isOutOfWindow(Canvas c);

    /**
     * Frissíti a léptethető dolgot a Canvason
     *
     * @param c A kapott Canvas
     */
    void refresh(Canvas c);

    /**
     * Kirajzolja a léptethető dolgot a Canvason
     *
     * @param c A kapott Canvas
     */
    void draw(Canvas c);

    /**
     * Visszahúzza a léptethető dolgot a Canvasra
     *
     * @param c A kapott Canvas
     */
    void moveBack(Canvas c);
}
