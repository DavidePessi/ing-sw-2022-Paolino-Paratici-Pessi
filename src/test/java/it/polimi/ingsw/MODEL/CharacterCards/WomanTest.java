package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WomanTest {

    @Test
    public void testInitialization(){
        Woman woman = new Woman(new Game("io", "tu"));
        woman.initialization();
        int size = woman.getPool().size();
        assertNotEquals(0,size);
        assertEquals(4, size);
    }
}