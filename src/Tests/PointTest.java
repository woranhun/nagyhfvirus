package Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import simulatorComponents.Point;

/**
 * Point tesztelésére szolgál
 */
public class PointTest {
    /**
     * Origó
     */
    Point p0;

    /**
     * Create (0,0)
     */
    @Before
    public void setUp() {
        p0 = new Point(0, 0);
    }

    /**
     * calcDistance teszt
     */
    @Test
    public void calcDistance() {
        Assert.assertEquals(0, p0.calcDistance(p0), 0);
        Point p1 = new Point(0, 1);
        Assert.assertEquals(1, p0.calcDistance(p1), 0);
        Assert.assertEquals(1, p1.calcDistance(p0), 0);
        Point p2 = new Point(0, 3);
        Assert.assertEquals(3, p0.calcDistance(p2), 0);
        Point p3 = new Point(1, 1);
        Point p4 = new Point(2, 2);
        Assert.assertEquals(1.4142135623730951, p3.calcDistance(p4), 4);
    }

    /**
     * calcDisplacement teszt
     */
    @Test
    public void calcDisplacement() {
        Assert.assertEquals(0, p0.calcDisplacement(p0), 0);
        Point p1 = new Point(0, 1);
        Assert.assertEquals(-1, p0.calcDisplacement(p1), 0);
        Assert.assertEquals(1, p1.calcDisplacement(p0), 0);
        Point p2 = new Point(0, 3);
        Assert.assertEquals(-3, p0.calcDisplacement(p2), 0);
        Point p3 = new Point(1, 1);
        Point p4 = new Point(2, 2);
        Assert.assertEquals(1.4142135623730951, p3.calcDisplacement(p4), 4);
    }

    /**
     * add teszt
     */
    @Test
    public void add() {
        Point res = new Point(0, 0);
        res.add(new Point(1, 1));
        Point check1 = new Point(1, 1);
        Assert.assertEquals(check1.getX(), res.getX(), 0);
        Assert.assertEquals(check1.getY(), res.getY(), 0);

        res.add(new Point(2, 2));
        Point check2 = new Point(3, 3);
        Assert.assertEquals(check2.getX(), res.getX(), 0);
        Assert.assertEquals(check2.getY(), res.getY(), 0);

        Point res2 = new Point(0, 0);
        res2.add(new Point(1, 1));
        Point check3 = new Point(1, 1);
        Assert.assertEquals(check3.getX(), res2.getX(), 0);
        Assert.assertEquals(check3.getY(), res2.getY(), 0);

        Assert.assertEquals(check2.getX(), res.getX(), 0);
        Assert.assertEquals(check2.getY(), res.getY(), 0);
    }

    /**
     * subtract teszt
     */
    @Test
    public void subtract() {
        Point res1 = new Point(1, 1);
        res1.subtract(new Point(0, 0));
        Point check1 = new Point(1, 1);
        Assert.assertEquals(check1.getX(), res1.getX(), 0);
        Assert.assertEquals(check1.getY(), res1.getY(), 0);

        res1.subtract(new Point(2, 2));
        Point check2 = new Point(-1, -1);
        Assert.assertEquals(check2.getX(), res1.getX(), 0);
        Assert.assertEquals(check2.getY(), res1.getY(), 0);

        Point res3 = new Point(4, 4);
        res3.subtract(new Point(1, 1));
        Point check3 = new Point(3, 3);
        Assert.assertEquals(check3.getX(), res3.getX(), 0);
        Assert.assertEquals(check3.getY(), res3.getY(), 0);

        Assert.assertEquals(check2.getX(), res1.getX(), 0);
        Assert.assertEquals(check2.getY(), res1.getY(), 0);
    }

    /**
     * multiply teszt
     */
    @Test
    public void multiply() {
        Point res1 = new Point(1, 1);
        res1.multiply(2);
        Point check1 = new Point(2, 2);
        Assert.assertEquals(check1.getX(), res1.getX(), 0);
        Assert.assertEquals(check1.getY(), res1.getY(), 0);

        res1.multiply(-1);
        Point check2 = new Point(-2, -2);
        Assert.assertEquals(check2.getX(), res1.getX(), 0);
        Assert.assertEquals(check2.getY(), res1.getY(), 0);

        Point res3 = new Point(4, 4);
        res3.multiply(10);
        Point check3 = new Point(40, 40);
        Assert.assertEquals(check3.getX(), res3.getX(), 0);
        Assert.assertEquals(check3.getY(), res3.getY(), 0);
    }

    /**
     * divide teszt
     */
    @Test
    public void divide() {
        Point res1 = new Point(3, 3);
        res1.divide(3);
        Point check1 = new Point(1, 1);
        Assert.assertEquals(check1.getX(), res1.getX(), 0);
        Assert.assertEquals(check1.getY(), res1.getY(), 0);

        Point res2 = new Point(6, 3);
        res2.divide(3);
        Point check2 = new Point(2, 1);
        Assert.assertEquals(check2.getX(), res2.getX(), 0);
        Assert.assertEquals(check2.getY(), res2.getY(), 0);
    }

    /**
     * divide by Zero teszt
     */
    @Test(expected = RuntimeException.class)
    public void divideByZero() {
        Point p = new Point(1, 1);
        p.divide(0);
    }

    /**
     * dotProduct teszt
     */
    @Test
    public void dotProduct() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(3,3);
        Point p4 = new Point(-1,-1);
        Assert.assertEquals(4, p1.dotProduct(p2), 0);
        Assert.assertEquals(4, p2.dotProduct(p1), 0);
        Assert.assertEquals(12, p2.dotProduct(p3), 0);
        Assert.assertEquals(0, p0.dotProduct(p3), 0);
        Assert.assertEquals(-2, p1.dotProduct(p4), 0);
    }
}