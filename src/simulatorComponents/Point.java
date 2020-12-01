package simulatorComponents;

import javafx.scene.canvas.Canvas;

import java.io.Serializable;


/**
 * Point osztály
 * Feladata: az összetartozó x és y értékek tárolása és ezeken műveletek végzése
 * Abban az esetben, ha vectort fejez ki, akkor a Pont az Origóba tolt vector végpontját jelenti.
 */
public class Point implements Serializable {
    /**
     * x kordináta a bal felső sarokban van a (0,0)
     */
    double x;
    /**
     * y kordináta a bal felső sarokban van a (0,0)
     */
    double y;

    /**
     * Point konstruktura
     *
     * @param x A kapott x kordináta
     * @param y A kapott y kordináta
     */
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Point osztály konstruktora
     *
     * @param p A kapott Pont
     */
    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Két pont között számol távolságot, távolság = ((x1-x2)^2+(y1-y2)^2)^(1/2) képlet segítségével
     *
     * @param p A kapott Pont
     * @return A távolság, csak pozitív vagy 0 lehet
     */
    double calcDistance(Point p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }

    /**
     * Két pont közötti elmozdulás számolása
     *
     * @param p A kapott Pont
     * @return Az elmozdulás vector
     */
    double calcDisplacement(Point p) {
        return this.x - p.x + this.y - p.y;
    }

    /**
     * Két pontot összead x és y kordináták alapján, az eredmény az első operandusban tárolódik
     *
     * @param p A kapott Pont
     */
    void add(Point p) {
        this.x += p.x;
        this.y += p.y;
    }

    /**
     * Egy ponthoz hozzáad egy X és  Y értéket
     *
     * @param x A kapott x érték
     * @param y A kapott y érték
     */
    void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Két pontot kivon x és y kordináták alapján, az eredmény az első operandusban tárolódik
     *
     * @param p A kapott Pont
     */
    void subtract(Point p) {
        this.x -= p.x;
        this.y -= p.y;
    }

    /**
     * Egy pontból kivon egy X és  Y értéket
     *
     * @param x A kapott x érték
     * @param y A kapott y érték
     */
    void subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    /**
     * Egy pont X és Y kordinátáját megszorozza a kapott értékkel
     *
     * @param val A kapott érték
     */
    public void multiply(double val) {
        this.x *= val;
        this.y *= val;
    }

    /**
     * Egy pont X és Y kordinátáját elosztja az értékkel
     *
     * @param val Az érték, amivel leosztunk
     */
    public void divide(double val) {
        if (val == 0)
            throw new RuntimeException("Point divisin with 0");
        this.x /= val;
        this.y /= val;
    }

    /**
     * Skaláris szorzat számolását végzi két vector között
     *
     * @param v A kapott vector
     * @return A kiszámolt skaláris szorzatot visszaadja.
     */
    public double dotProduct(Point v) {
        return this.x * v.x + this.y * v.y;
    }

    /**
     * Megvizsgálja, hogy a pont felül kilóg-e a Canvas-ról
     *
     * @param c A kapott Canvas
     * @param r A kapott korrekciós érték (általában a Dot sugara)
     * @return Igen vagy Nem
     */
    boolean isOutOfCanvasTop(Canvas c, double r) {
        return this.y - 2 * r < 0;
    }

    /**
     * Megvizsgálja, hogy a pont alul kilóg-e a Canvas-ról
     *
     * @param c A kapott Canvas
     * @param r A kapott korrekciós érték (általában a Dot sugara)
     * @return Igen vagy Nem
     */
    boolean isOutOfCanvasBottom(Canvas c, double r) {
        return this.y + 2 * r > c.getHeight();
    }

    /**
     * Megvizsgálja, hogy a pont bal oldalt kilóg-e a Canvas-ról
     *
     * @param c A kapott Canvas
     * @param r A kapott korrekciós érték (általában a Dot sugara)
     * @return Igen vagy Nem
     */
    boolean isOutOfCanvasLeft(Canvas c, double r) {
        return this.x - 2 * r < 0;
    }

    /**
     * Megvizsgálja, hogy a pont jobb oldalt kilóg-e a Canvas-ról
     *
     * @param c A kapott Canvas
     * @param r A kapott korrekciós érték (általában a Dot sugara)
     * @return Igen vagy Nem
     */
    boolean isOutOfCanvasRight(Canvas c, double r) {
        return this.x + 2 * r > c.getWidth();
    }

    /**
     * Megvizsgálja, hogy a pont kilóg-e a Canvas-ról legalább egy oldalon
     *
     * @param c A kapott Canvas
     * @param r A kapott korrekciós érték (általában a Dot sugara)
     * @return Igen vagy Nem
     */
    public boolean isOutOfCanvas(Canvas c, double r) {
        return this.isOutOfCanvasRight(c, r) ||
                this.isOutOfCanvasBottom(c, r) ||
                this.isOutOfCanvasLeft(c, r) ||
                this.isOutOfCanvasTop(c, r);
    }
}
