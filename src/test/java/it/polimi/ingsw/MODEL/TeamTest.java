package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class TeamTest extends TestCase {

    @Test
    public void testGetNumberOfTower() {
        ColourTower ct = ColourTower.BLACK;
        int nt = 7;
        Team t = new Team(ct, nt);

        assertEquals(nt, t.getNumberOfTower());
    }

    @Test
    public void testGetColourTower(){
        ColourTower ct = ColourTower.BLACK;
        int nt = 7;
        Team t = new Team(ct, nt);

        assertEquals(ct, t.getColourTower());
        assertNotNull(t.getColourTower());
        assertTrue(t.getColourTower() instanceof ColourTower);
    }
}