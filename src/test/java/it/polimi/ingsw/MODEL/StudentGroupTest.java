package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class StudentGroupTest extends TestCase {

    @Test
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

    @Test
    public void testRemoveStudent() throws Exception{
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        s.addStudent(c);
        s.removeStudent(c);

        assertEquals(0, s.countStudentsOfColour(c));
    }

    @Test
    public void testClear() {
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        s.addStudent(c);
        s.clear();

        assertEquals(0, s.countStudentsOfColour(c));
    }

    @Test
    public void testSize() {
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;
        int n = 0;

        s.addStudent(c);

        for(Colour col : Colour.values()){
            n = n + s.countStudentsOfColour(col);
        }

        assertEquals(1, n);
    }

    @Test
    public void testGet() {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.addStudent(Colour.GREEN);
        //studentGroup.addStudent(Colour.PINK);
        int pos = 0;
        Colour colour = Colour.RED;

        colour = studentGroup.get(pos).getColour();

        assertEquals(Colour.GREEN, colour);

    }

    @Test
    public void testAddStudents() {
        StudentGroup studentGroup = new StudentGroup();
        StudentGroup s = new StudentGroup();
        s.addStudent(Colour.GREEN);
        int numAfter, numBefore;
        int colourAfter, colourBefore;

        numBefore = studentGroup.size();
        colourBefore = studentGroup.countStudentsOfColour(Colour.GREEN);
        studentGroup.addStudents(s);
        numAfter = studentGroup.size();
        colourAfter = studentGroup.countStudentsOfColour(Colour.GREEN);

        assertEquals(colourAfter, colourBefore+1);
        assertEquals(numAfter, numBefore+1);

    }

    @Test
    public void testAddStudent() {
        StudentGroup studentGroup = new StudentGroup();
        int numAfter, numBefore;
        int colourAfter, colourBefore;

        numBefore = studentGroup.size();
        colourBefore = studentGroup.countStudentsOfColour(Colour.GREEN);
        studentGroup.addStudent(Colour.GREEN);
        colourAfter = studentGroup.countStudentsOfColour(Colour.GREEN);
        numAfter = studentGroup.size();

        assertEquals(colourAfter,colourBefore+1);
        assertEquals(numAfter, numBefore+1);
    }
}