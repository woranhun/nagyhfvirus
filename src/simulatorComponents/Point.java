package simulatorComponents;

import javafx.scene.canvas.Canvas;

import java.io.Serializable;

public class Point implements Serializable {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
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

}
