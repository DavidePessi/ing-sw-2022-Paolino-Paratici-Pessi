package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class DiningRoomTest extends TestCase {

    @Test
    public void testAdd() {
        DiningRoom d = new DiningRoom();
        Colour c = Colour.GREEN;

        d.add(c);
        assertEquals(1, d.numStudents(c));
    }

    @Test
    public void testNumStudents() {
        DiningRoom d = new DiningRoom();
        Colour c = Colour.GREEN;

        assertEquals(0, d.numStudents(c));

        d.add(c);
        assertEquals(d.getStudentGroup().countStudentsOfColour(c), d.numStudents(c));
    }

    @Test
    public void testRemove() throws  Exception{

        DiningRoom diningRoom = new DiningRoom();
        diningRoom.getStudentGroup().addStudent(Colour.GREEN);
        diningRoom.getStudentGroup().addStudent(Colour.RED);
        diningRoom.remove(Colour.GREEN);

        assertEquals(0, diningRoom.numStudents(Colour.GREEN));
        assertEquals(1,diningRoom.numStudents(Colour.RED));

    }

    @Test
    public void testGetStudentGroup() {
        DiningRoom d = new DiningRoom();

        assertTrue(d.getStudentGroup() instanceof StudentGroup);
        assertTrue(d.getStudentGroup() != null);
    }
}