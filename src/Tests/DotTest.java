package Tests;

import org.junit.Assert;
import org.junit.Test;
import simulatorComponents.Dot;

import static org.junit.Assert.*;

/**
 * Dot tesztelese
 */
public class DotTest {
/**
 * utkozes tesztelese
 */
    @Test
    public void isCollidedWith() {
        Dot d1 = new Dot(1,1,2);
        Dot d2 = new Dot(2,2,2);
        Dot d3 = new Dot(10,10,2);
        Assert.assertTrue(d1.isCollidedWith(d2));
        Assert.assertTrue(d2.isCollidedWith(d1));
        Assert.assertFalse(d1.isCollidedWith(d3));
        Assert.assertFalse(d1.isCollidedWith(d1));
    }
}