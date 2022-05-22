package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;

public class EntranceTest extends TestCase {

    public void testRemoveStudent() throws Exception{
        Entrance e = new Entrance();
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        s.addStudent(c);
        e.addGroup(s);

        e.removeStudent(c);
        assertEquals(0,e.getStudentGroup().countStudentsOfColour(c));
    }

    public void testAddGroup() {
        Entrance e = new Entrance();
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        s.addStudent(c);
        e.addGroup(s);

        assertEquals(1, e.getStudentGroup().countStudentsOfColour(c));
    }

    public void testGetStudentGroup() {
        Entrance e = new Entrance();

        assertTrue(e.getStudentGroup() instanceof StudentGroup);
        assertTrue(e.getStudentGroup() != null);
    }
}