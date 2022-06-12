package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;

public class TeamTest extends TestCase {

    public void testGetNumberOfTower() {
        ColourTower ct = ColourTower.BLACK;
        int nt = 7;
        Team t = new Team(ct, nt);

        assertEquals(nt, t.getNumberOfTower());
    }

    public void testGetColourTower(){
        ColourTower ct = ColourTower.BLACK;
        int nt = 7;
        Team t = new Team(ct, nt);

        assertEquals(ct, t.getColourTower());
        assertNotNull(t.getColourTower());
        assertTrue(t.getColourTower() instanceof ColourTower);
    }
}