package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Exception.MissingCardException;
import it.polimi.ingsw.MODEL.Game;
import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SatyrTest extends TestCase {

    public void testConstructor() {
        Game g = new Game("io", "tu");
        Satyr s = new Satyr(g);

        assertEquals(3, s.getPrice());
        assertEquals("Satyr", s.getNameCard());
    }

    public void testEffect() {
        Game g = new Game("io", "tu");
        Satyr s = new Satyr(g);

        s.effect("io");

        assertEquals("Satyr", g.getCharacterCardThrown());
        assertEquals(4, s.getPrice());
    }

    public void testTestEffect() {
        Game g = new Game("io", "tu");
        Satyr s = new Satyr(g);

        Exception exception = assertThrows(Exception.class, ()->s.effect(null,1));
        exception = assertThrows(Exception.class, ()->s.effect(null,1, null));
        exception = assertThrows(Exception.class, ()->s.effect(null, null));
        exception = assertThrows(Exception.class, ()->s.effect(null, null, null));
        exception = assertThrows(Exception.class, ()->s.effect(null, null, null, null, null));
        exception = assertThrows(Exception.class, ()->s.effect(null, null, null, null, null, null, null));
    }

}