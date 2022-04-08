package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;

public class StudentTest extends TestCase {

    public void testGetColour() {
        Colour c = Colour.GREEN;
        Student s1 = new Student(c);

        assertEquals(c, s1.getColour());
        assertTrue(s1.getColour() != null);
        assertTrue(s1.getColour() instanceof Colour);

        Student s2 = new Student();

        assertTrue(s2.getColour() != null);
        assertTrue(s2.getColour() instanceof Colour);
    }
}