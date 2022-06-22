package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class EntranceTest extends TestCase {

    @Test
    public void testRemoveStudent() throws Exception{
        Entrance e = new Entrance();
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        s.addStudent(c);
        e.addGroup(s);

        e.removeStudent(c);
        assertEquals(0,e.getStudentGroup().countStudentsOfColour(c));
    }

    @Test
    public void testAddGroup() {
        Entrance e = new Entrance();
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        s.addStudent(c);
        e.addGroup(s);

        assertEquals(1, e.getStudentGroup().countStudentsOfColour(c));
    }

    @Test
    public void testGetStudentGroup() {
        Entrance e = new Entrance();

        assertTrue(e.getStudentGroup() instanceof StudentGroup);
        assertTrue(e.getStudentGroup() != null);
    }
}