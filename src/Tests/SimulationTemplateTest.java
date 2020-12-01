package Tests;

import org.junit.Assert;
import org.junit.Test;
import simulator.SimulationTemplate;
import simulatorComponents.Dot;

import static org.junit.Assert.*;

/**
 * Simulation Template osztaly tesztje
 */
public class SimulationTemplateTest {

    /**
     * create Dot tesztje
     */
    @Test
    public void createDot() {
        SimulationTemplate st = new SimulationTemplate();
        Assert.assertEquals(0,st.getDots().size());
        st.addDot(new Dot(1,2,10));
        Assert.assertEquals(1,st.getDots().size());
        Assert.assertEquals(1,st.getDots().get(0).getLocation().getX(),0);
        Assert.assertEquals(2,st.getDots().get(0).getLocation().getY(),0);
        Assert.assertEquals(10,st.getDots().get(0).getRadius(),0);
        st.addDot(new Dot(3,4,20));
        Assert.assertEquals(2,st.getDots().size());
        Assert.assertEquals(3,st.getDots().get(1).getLocation().getX(),0);
        Assert.assertEquals(4,st.getDots().get(1).getLocation().getY(),0);
        Assert.assertEquals(20,st.getDots().get(1).getRadius(),0);
        st = new SimulationTemplate();
        Assert.assertEquals(0,st.getDots().size());
    }
}