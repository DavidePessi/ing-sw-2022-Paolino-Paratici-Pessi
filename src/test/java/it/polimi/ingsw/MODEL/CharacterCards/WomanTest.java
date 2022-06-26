package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WomanTest {

    @Test
    public void testInitialization(){
        Woman woman = new Woman(new Game("io", "tu"));
        int size = woman.getPool().size();
        assertNotEquals(0,size);
        assertEquals(4, size);
    }

    @Test
    public void effect() {

        Woman woman = new Woman(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> woman.effect("io"));

    }

    @Test
    public void effect2() {

        Woman woman = new Woman(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> woman.effect("io", 2));

    }

    @Test
    public void effect3() {

        Woman woman = new Woman(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> woman.effect("io", Colour.GREEN, Colour.RED));

    }

    @Test
    public void effect4() {

        Woman woman = new Woman(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> woman.effect("io", Colour.GREEN, Colour.RED, Colour.BLUE, Colour.RED));

    }

    @Test
    public void effect5() {

        Woman woman = new Woman(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> woman.effect("io", Colour.GREEN, Colour.RED, Colour.PINK, Colour.BLUE, Colour.PINK,Colour.PINK));

    }

    @Test
    public void effect6() {

        Woman woman = new Woman(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> woman.effect("io", 2));

    }

    @Test
    public void effect7() {

        Woman woman = new Woman(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> woman.effect("io", 2, Colour.RED));

    }
}