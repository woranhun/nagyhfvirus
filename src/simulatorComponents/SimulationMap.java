package simulatorComponents;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import simulator.Steppable;

/**
 * SimulationMap osztaly
 * A palyat, jelkepezi.
 * Feladata, hogy kor elejen letorli magat
 */
public class SimulationMap implements Steppable {
    /**
     * SimulationMap konstruktorja, meghivja a SimulationMap init fuggvenyet
     * @param c A kapott canvas
     */
    public SimulationMap(Canvas c) {
        init(c);
    }

    /**
     * Lepes soran meghivja sajat maga refresh fuggvenyet.
     * @param c A kapott Canvas
     */
    @Override
    public void step(Canvas c) {
        refresh(c);
    }

    /**
     * Inicializalja a SimulationMap-et ( Tehat letorli magat)
     * @param c A kapott Canvas
     */
    @Override
    public void init(Canvas c) {
        draw(c);
    }

    /**
     * Megvizsgalja, hogy tudott-e utkozni egy masik Steppable-el.
     * Mindig hamisat ad vissza, mert a palya nem utkozik, hanem a hatter szerepet tolti be.
     * @param st A kapott Masik Steppable
     * @return Mindig Hamis
     */
    @Override
    public boolean isCollidedWith(Steppable st) {
        return false;
    }
    /**
     * Megvizsgalja, hogy tudott-e utkozni egy Dot-al.
     * Mindig hamisat ad vissza, mert a palya nem utkozik, hanem a hatter szerepet tolti be.
     * @param dot A kapott Dot
     * @return Mindig Hamis
     */
    @Override
    public boolean isCollidedWith(Dot dot) {
        return false;
    }

    /**
     * Kezeli, hogy mi tortenik, ha egy Dot eltalalja.
     * Semmi, mert nem tud egy Dot Palyaval utkozni, de a leptethetoseg miatt szukseges.
     * @param dot A kapott Dot
     */
    @Override
    public void hitBy(Dot dot) {

    }

    /**
     * Megvizsgalja, hogy aza ablakon kivul esik-e. A hatter nem tud az ablakon kivul esni.
     * A leptethetoseg miatt szukseges.
     * @param c A kapott Canvas
     * @return Mindig Hamis
     */
    @Override
    public boolean isOutOfWindow(Canvas c) {
        return false;
    }

    /**
     * Visszahuzza az objektumat a Canvas-ra,
     * A leptethetoseg miatt szukseges.
     * @param c A kapott Canvas
     */
    @Override
    public void moveBack(Canvas c) {

    }

    /**
     * Frissiti a Canvas-t.
     * Meghivja a draw(Canvas)-t
     * @param c A kapott Canvas
     */
    @Override
    public void refresh(Canvas c) {
        draw(c);
    }
    /**
     * Letorli a Canvas-t
     * @param c A kapott Canvas
     */
    @Override
    public void draw(Canvas c) {
        Platform.runLater(() -> c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight()));
    }
}
