package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;

public class CardTest extends TestCase {

    public void testGetValue() {
        int n = 1;
        Card c = new Card(n, 1);

        assertEquals(n, c.getValue());
    }

    public void testGetMovement() {
        int n = 1;
        Card c = new Card(1, n);

        assertEquals(n, c.getMovement());
    }
}