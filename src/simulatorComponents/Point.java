package simulatorComponents;

import javafx.scene.canvas.Canvas;

import java.io.Serializable;

public class Point implements Serializable {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point location) {
        this.x=location.x;
        this.y=location.y;
    }

    double calcDistance(Point p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }

    void add(Point p) {
        this.x += p.x;
        this.y += p.y;
    }

    boolean isOutOfCanvasTop(Canvas c, double r) {
        return this.y + r > c.getHeight() - 1;
    }

    boolean isOutOfCanvasBottom(Canvas c, double r) {
        return this.y - r < 0;
    }

    boolean isOutOfCanvasLeft(Canvas c, double r) {
        return this.x - r < 0;
    }

    boolean isOutOfCanvasRight(Canvas c, double r) {
        return this.x + r > c.getWidth() - 1;
    }

    public boolean isOutOfCanvas(Canvas c) {
        return this.y > c.getHeight() - 1 || this.x > c.getWidth() - 1 || this.y < 0 || this.x < 0;
    }

    public boolean isOutOfCanvas(Canvas c, double radius) {
        return this.isOutOfCanvasRight(c, radius) || this.isOutOfCanvasBottom(c, radius) || this.isOutOfCanvasLeft(c, radius) || this.isOutOfCanvasTop(c, radius);
    }

}
