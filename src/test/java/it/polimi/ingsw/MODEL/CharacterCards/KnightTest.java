package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Game;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class KnightTest extends TestCase {

    @Test
    public void testConstructor() {
        Game g = new Game("io", "tu");
        Knight k = new Knight(g);

        assertEquals(3, k.getPrice());
        assertEquals("Knight", k.getNameCard());
    }

    @Test
    public void testEffect() {
        Game g = new Game("io", "tu");
        Knight k = new Knight(g);

        k.effect("io");

        assertEquals("Knight", g.getCharacterCardThrown());
        assertEquals(4, k.getPrice());
    }

    @Test
    public void testTestEffect() {
        Game g = new Game("io", "tu");
        Knight k = new Knight(g);

        Exception exception = assertThrows(Exception.class, ()->k.effect(null,1));
        exception = assertThrows(Exception.class, ()->k.effect(null,1, null));
        exception = assertThrows(Exception.class, ()->k.effect(null, null));
        exception = assertThrows(Exception.class, ()->k.effect(null, null, null));
        exception = assertThrows(Exception.class, ()->k.effect(null, null, null, null, null));
        exception = assertThrows(Exception.class, ()->k.effect(null, null, null, null, null, null, null));
    }

    @Test
    public void testEffect1(){
        Knight knight = new Knight(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> knight.effect("io", 2, Colour.RED));
    }

    @Test
    public void testEffect2(){
        Knight knight = new Knight(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> knight.effect("io", Colour.RED));
    }

    @Test
    public void testEffect3(){
        Knight knight = new Knight(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> knight.effect("io", Colour.GREEN, Colour.RED));
    }

    @Test
    public void testEffect4(){
        Knight knight = new Knight(new Game("io", "tu"));

        assertThrows(Exception.class, ()-> knight.effect("io", Colour.RED, Colour.GREEN, Colour.BLUE, Colour.RED));
    }
}