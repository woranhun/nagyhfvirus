package simulatorComponents;

import javafx.scene.canvas.Canvas;

import java.io.Serializable;


/**
 * Point osztaly
 * Feladata: az osszetartozo x es y ertekek tarolasa es ezeken muveletek vegzese
 * Abban az esetben, ha vectort fejez ki, akkor a Pont az Origoba tolt vector vegpontjat jelenti.
 */
public class Point implements Serializable {
    /**
     * x kordinata a bal felso sarokban van a (0,0)
     */
    double x;
    /**
     * y kordinata a bal felso sarokban van a (0,0)
     */
    double y;

    /**
     * Point konstruktura
     *
     * @param x A kapott x kordinata
     * @param y A kapott y kordinata
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Point osztaly konstruktora
     *
     * @param p A kapott Pont
     */
    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * X getter
     * @return x erteke
     */
    public double getX() {
        return x;
    }
    /**
     * y getter
     * @return y erteke
     */
    public double getY(){
        return y;
    }

    /**
     * Ket pont kozott szamol tavolsagot, tavolsag = ((x1-x2)^2+(y1-y2)^2)^(1/2) keplet segitsegevel
     *
     * @param p A kapott Pont
     * @return A tavolsag, csak pozitiv vagy 0 lehet
     */
    public double calcDistance(Point p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }

    /**
     * Ket pont kozotti elmozdulas szamolasa
     *
     * @param p A kapott Pont
     * @return Az elmozdulas vector
     */
    public double calcDisplacement(Point p) {
        return this.x - p.x + this.y - p.y;
    }

    /**
     * Ket pontot osszead x es y kordinatak alapjan, az eredmeny az elso operandusban tarolodik
     *
     * @param p A kapott Pont
     */
    public void add(Point p) {
        this.x += p.x;
        this.y += p.y;
    }

    /**
     * Egy ponthoz hozzaad egy X es  Y erteket
     *
     * @param x A kapott x ertek
     * @param y A kapott y ertek
     */
    void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Ket pontot kivon x es y kordinatak alapjan, az eredmeny az elso operandusban tarolodik
     *
     * @param p A kapott Pont
     */
    public void subtract(Point p) {
        this.x -= p.x;
        this.y -= p.y;
    }

    /**
     * Egy pontbol kivon egy X es  Y erteket
     *
     * @param x A kapott x ertek
     * @param y A kapott y ertek
     */
    void subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    /**
     * Egy pont X es Y kordinatajat megszorozza a kapott ertekkel
     *
     * @param val A kapott ertek
     */
    public void multiply(double val) {
        this.x *= val;
        this.y *= val;
    }

    /**
     * Egy pont X es Y kordinatajat elosztja az ertekkel
     *
     * @param val Az ertek, amivel leosztunk
     */
    public void divide(double val) {
        if (val == 0)
            throw new RuntimeException("Point divisin with 0");
        this.x /= val;
        this.y /= val;
    }

    /**
     * Skalaris szorzat szamolasat vegzi ket vector kozott
     *
     * @param v A kapott vector
     * @return A kiszamolt skalaris szorzatot visszaadja.
     */
    public double dotProduct(Point v) {
        return this.x * v.x + this.y * v.y;
    }

    /**
     * Megvizsgalja, hogy a pont felul kilog-e a Canvas-rol
     *
     * @param c A kapott Canvas
     * @param r A kapott korrekcios ertek (altalaban a Dot sugara)
     * @return Igen vagy Nem
     */
    boolean isOutOfCanvasTop(Canvas c, double r) {
        return this.y - 2 * r < 0;
    }

    /**
     * Megvizsgalja, hogy a pont alul kilog-e a Canvas-rol
     *
     * @param c A kapott Canvas
     * @param r A kapott korrekcios ertek (altalaban a Dot sugara)
     * @return Igen vagy Nem
     */
    boolean isOutOfCanvasBottom(Canvas c, double r) {
        return this.y + 2 * r > c.getHeight();
    }

    /**
     * Megvizsgalja, hogy a pont bal oldalt kilog-e a Canvas-rol
     *
     * @param c A kapott Canvas
     * @param r A kapott korrekcios ertek (altalaban a Dot sugara)
     * @return Igen vagy Nem
     */
    boolean isOutOfCanvasLeft(Canvas c, double r) {
        return this.x - 2 * r < 0;
    }

    /**
     * Megvizsgalja, hogy a pont jobb oldalt kilog-e a Canvas-rol
     *
     * @param c A kapott Canvas
     * @param r A kapott korrekcios ertek (altalaban a Dot sugara)
     * @return Igen vagy Nem
     */
    boolean isOutOfCanvasRight(Canvas c, double r) {
        return this.x + 2 * r > c.getWidth();
    }

    /**
     * Megvizsgalja, hogy a pont kilog-e a Canvas-rol legalabb egy oldalon
     *
     * @param c A kapott Canvas
     * @param r A kapott korrekcios ertek (altalaban a Dot sugara)
     * @return Igen vagy Nem
     */
    public boolean isOutOfCanvas(Canvas c, double r) {
        return this.isOutOfCanvasRight(c, r) ||
                this.isOutOfCanvasBottom(c, r) ||
                this.isOutOfCanvasLeft(c, r) ||
                this.isOutOfCanvasTop(c, r);
    }
}
