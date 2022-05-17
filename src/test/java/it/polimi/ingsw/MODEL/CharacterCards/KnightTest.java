package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Game;
import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class KnightTest extends TestCase {

    public void testConstructor() {
        Game g = new Game("io", "tu");
        Knight k = new Knight(g);

        assertEquals(3, k.getPrice());
        assertEquals("Knight", k.getNameCard());
    }

    public void testEffect() {
        Game g = new Game("io", "tu");
        Knight k = new Knight(g);

        k.effect("io");

        assertEquals("Knight", g.getCharacterCardThrown());
        assertEquals(4, k.getPrice());
    }

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
}