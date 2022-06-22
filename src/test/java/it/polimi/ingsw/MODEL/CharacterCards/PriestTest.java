package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriestTest {

    @Test
    public void testInitialization(){
        Priest priest = new Priest(new Game("io", "tu"));
        priest.initialization();
        int size = priest.getPool().size();
        assertNotEquals(0,size);
        assertEquals(4, size);
    }
}