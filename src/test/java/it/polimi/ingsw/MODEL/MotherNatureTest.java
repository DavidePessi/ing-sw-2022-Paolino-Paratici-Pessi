package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class MotherNatureTest extends TestCase {

    @Test
    public void testMove() {
        Island i1 = new Island(0);
        Island i2 = new Island(5);
        MotherNature m = new MotherNature(i1);

        m.move(i2);

        assertEquals(true,i2.getHasMotherNature());
        assertEquals(false,i1.getHasMotherNature());
        assertEquals(i2,m.getPosition());
    }

    @Test
    public void testGetNumIsland() {
        Island i1 = new Island(0);
        MotherNature m = new MotherNature(i1);

        assertEquals(m.getPosition().getNumIsland(), m.getNumIsland());
    }

    @Test
    public void testGetPosition() {
        Island i1 = new Island(0);
        MotherNature m = new MotherNature(i1);

        assertEquals(i1, m.getPosition());
        assertTrue(m.getPosition() != null);
        assertTrue(m.getPosition() instanceof Island);
    }

}