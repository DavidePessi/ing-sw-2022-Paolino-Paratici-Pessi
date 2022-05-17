package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Game;
import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostManTest extends TestCase {

    public void testConstructor() {
        Game g = new Game("io", "tu");
        PostMan p = new PostMan(g);

        assertEquals(1, p.getPrice());
        assertEquals("PostMan", p.getNameCard());
    }

    public void testEffect() throws Exception{
        Game g = new Game("io", "tu");
        PostMan p = new PostMan(g);

        p.effect("io");

        assertEquals("PostMan", g.getCharacterCardThrown());
        assertEquals(2, p.getPrice());
    }

    public void testTestEffect() {
        Game g = new Game("io", "tu");
        PostMan p = new PostMan(g);

        Exception exception = assertThrows(Exception.class, ()->p.effect(null,1));
        exception = assertThrows(Exception.class, ()->p.effect(null,1, null));
        exception = assertThrows(Exception.class, ()->p.effect(null, null));
        exception = assertThrows(Exception.class, ()->p.effect(null, null, null));
        exception = assertThrows(Exception.class, ()->p.effect(null, null, null, null, null));
        exception = assertThrows(Exception.class, ()->p.effect(null, null, null, null, null, null, null));
    }
}