package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;

public class DiningRoomTest extends TestCase {

    public void testAdd() {
        DiningRoom d = new DiningRoom();
        Colour c = Colour.GREEN;

        d.add(c);
        assertEquals(1, d.numStudents(c));
    }

    public void testNumStudents() {
        DiningRoom d = new DiningRoom();
        Colour c = Colour.GREEN;

        assertEquals(0, d.numStudents(c));

        d.add(c);
        assertEquals(d.getStudentGroup().countStudentsOfColour(c), d.numStudents(c));
    }

    public void testGetStudentGroup() {
        DiningRoom d = new DiningRoom();

        assertTrue(d.getStudentGroup() instanceof StudentGroup);
        assertTrue(d.getStudentGroup() != null);
    }
}