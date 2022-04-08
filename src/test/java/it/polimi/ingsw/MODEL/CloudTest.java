package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;

public class CloudTest extends TestCase {

    public void testGetStudents() {
        Cloud c = new Cloud();

        assertTrue(c.getStudents() instanceof StudentGroup);
        assertTrue(c.getStudents() != null);
    }

    public void testAddStudents() {
        Cloud c = new Cloud();
        StudentGroup s = new StudentGroup();
        Colour colour = Colour.GREEN;

        s.addStudent(colour);
        c.addStudents(s);

        assertEquals(s, c.getStudents());
    }
}