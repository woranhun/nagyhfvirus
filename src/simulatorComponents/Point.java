package simulatorComponents;

import javafx.scene.canvas.Canvas;

import java.io.Serializable;


//ToDo Separate Point & Vector
public class Point implements Serializable {
    static final long serialVersionUID = 666;
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point location) {
        this.x = location.x;
        this.y = location.y;
    }

    double calcDistance(Point p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }

    double calcDisplacement(Point p) {
        return this.x - p.x + this.y - p.y;
    }

    void add(Point p) {
        this.x += p.x;
        this.y += p.y;
    }

    void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    void subtract(Point p) {
        this.x -= p.x;
        this.y -= p.y;
    }

    void subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
    }


    boolean isOutOfCanvasTop(Canvas c, double r) {
        return this.y-2*r < 0;
    }

    boolean isOutOfCanvasBottom(Canvas c, double r) {
        return this.y+2*r > c.getHeight();
    }

    boolean isOutOfCanvasLeft(Canvas c, double r) {
        return this.x-2*r < 0;
    }

    boolean isOutOfCanvasRight(Canvas c, double r) {
        return this.x+2*r > c.getWidth();
    }


    public boolean isOutOfCanvas(Canvas c, double radius) {
        return this.isOutOfCanvasRight(c, radius) ||
                this.isOutOfCanvasBottom(c, radius) ||
                this.isOutOfCanvasLeft(c, radius) ||
                this.isOutOfCanvasTop(c, radius);
    }

    public void divide(double v) {
        if (v == 0)
            throw new RuntimeException("Point divisin with 0");
        this.x /= v;
        this.y /= v;
    }

    public double dotProduct(Point v) {
        return this.x * v.x + this.y * v.y;
    }

    public void multiply(double v) {
        this.x *= v;
        this.y *= v;
    }
}
