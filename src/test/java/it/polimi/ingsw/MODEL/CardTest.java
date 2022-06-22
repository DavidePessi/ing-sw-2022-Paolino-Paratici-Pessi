package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class CardTest extends TestCase {

    @Test
    public void testGetValue() {
        int n = 1;
        Card c = new Card(n, 1);

        assertEquals(n, c.getValue());
    }

    @Test
    public void testGetMovement() {
        int n = 1;
        Card c = new Card(1, n);

        assertEquals(n, c.getMovement());
    }
}