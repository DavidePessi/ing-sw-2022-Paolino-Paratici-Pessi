package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriestTest {

    @Test
    public void testInitialization(){
        Priest priest = new Priest(new Game("io", "tu"));
        int size = priest.getPool().size();
        assertNotEquals(0,size);
        assertEquals(4, size);
    }

    @Test
    public void effect() {

        Priest priest = new Priest(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> priest.effect("io"));

    }

    @Test
    public void effect1() {

        Priest priest = new Priest(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> priest.effect("io", Colour.GREEN));

    }

    @Test
    public void effect2() {

        Priest priest = new Priest(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> priest.effect("io", Colour.GREEN, Colour.RED));

    }

    @Test
    public void effect3() {

        Priest priest = new Priest(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> priest.effect("io", Colour.GREEN, Colour.RED, Colour.BLUE, Colour.PINK));

    }

    @Test
    public void effect4() {

        Priest priest = new Priest(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> priest.effect("io", Colour.GREEN, Colour.RED, Colour.BLUE, Colour.PINK, Colour.RED, Colour.PINK));

    }

    @Test
    public void effect5() {

        Priest priest = new Priest(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> priest.effect("io", 2));

    }

}