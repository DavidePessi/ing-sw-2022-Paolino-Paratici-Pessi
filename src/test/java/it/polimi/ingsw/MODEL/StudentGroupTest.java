package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;

public class StudentGroupTest extends TestCase {

    public void testCountStudentsOfColour() {
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        s.addStudent(c);
        for(Colour col : Colour.values()){
            if(col == c){
                assertEquals(1, s.countStudentsOfColour(col));
            }
            else{
                assertEquals(0, s.countStudentsOfColour(col));
            }
        }
    }

    public void testRemoveStudent() {
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        s.addStudent(c);
        s.removeStudent(c);

        assertEquals(0, s.countStudentsOfColour(c));
    }

    public void testClear() {
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        s.addStudent(c);
        s.clear();

        assertEquals(0, s.countStudentsOfColour(c));
    }

    public void testSize() {
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;
        int n = 0;

        s.addStudent(c);

        for(Colour col : Colour.values()){
            n = s.countStudentsOfColour(col);
        }

        assertEquals(1, n);
    }

    public void testGet() {
    }

    public void testAddStudents() {
    }

    public void testAddStudent() {
    }

    public void testPullOut() {
    }
}