package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinstrellTest {


    @Test
    void effect() {

        Minstrell minstrell = new Minstrell(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> minstrell.effect("io"));

    }

    @Test
    void testEffect() {
        Minstrell minstrell = new Minstrell(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> minstrell.effect("io", Colour.RED));
    }

    @Test
    void testEffect1() {
        Minstrell minstrell = new Minstrell(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> minstrell.effect("io", Colour.RED, Colour.GREEN, Colour.BLUE, Colour.RED, Colour.RED, Colour.GREEN));
    }

    @Test
    void testEffect2() {
        Minstrell minstrell = new Minstrell(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> minstrell.effect("io", 2, Colour.PINK));
    }

    @Test
    void testEffect3() {
        Minstrell minstrell = new Minstrell(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> minstrell.effect("io", 2));
    }
}