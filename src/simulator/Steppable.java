package simulator;


import javafx.scene.canvas.Canvas;
import simulatorComponents.Dot;

/**
 * Interfesz, amely  a leptetheto dolgokat valositja meg
 */
public interface Steppable {
    /**
     * Egy korben vegzendo lepesek
     *
     * @param c A kapott Canvas
     */
    void step(Canvas c);

    /**
     * Szimulacio elejen vegzendo lepesek
     *
     * @param c A kapott Canvas
     */
    void init(Canvas c);

    /**
     * utkozott-e a kapott Steppable-el?
     *
     * @param st A kapott Masik Steppable
     * @return Igen vagy Nem
     */
    boolean isCollidedWith(Steppable st);

    /**
     * utkozott-e a kapott Dot-al?
     *
     * @param dot A kapott Masik Dot
     * @return Igen vagy Nem
     */
    boolean isCollidedWith(Dot dot);

    /**
     * Dottal valo utkozest lekezelo fuggveny
     *
     * @param dot A kapott Dot
     */
    void hitBy(Dot dot);

    /**
     * A leptetheto dolog a Canvason kivul tartozkodik-e?
     *
     * @param c A kapott Canvas
     * @return Igen vagy Nem
     */
    boolean isOutOfWindow(Canvas c);

    /**
     * Frissiti a leptetheto dolgot a Canvason
     *
     * @param c A kapott Canvas
     */
    void refresh(Canvas c);

    /**
     * Kirajzolja a leptetheto dolgot a Canvason
     *
     * @param c A kapott Canvas
     */
    void draw(Canvas c);

    /**
     * Visszahuzza a leptetheto dolgot a Canvasra
     *
     * @param c A kapott Canvas
     */
    void moveBack(Canvas c);
}
