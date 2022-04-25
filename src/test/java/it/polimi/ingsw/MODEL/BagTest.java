package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingStudentException;
import junit.framework.TestCase;

public class BagTest extends TestCase {

    public void testSize()throws MissingStudentException {
        Bag b = new Bag();
        Colour c;

        assertTrue(b.size() <= 130 && b.size() >= 0);

        for(int i = 0; i < b.size();){
            c = b.pullOut();
        }
        assertTrue(b.size() == 0);
    }

    public void testStartingPullOut() {
        Bag b = new Bag();
        StudentGroup s = new StudentGroup();

        int size1 = b.size();
        s.addStudents(b.startingPullOut());
        int size2 = b.size();

        assertEquals(size1 - 10, size2);
        for(Colour colour : Colour.values()) {
            assertEquals(2, s.countStudentsOfColour(colour));
        }
    }

    public void testPullOut() throws MissingStudentException{
        Bag b = new Bag();
        Colour c;

        int size1 = b.size();
        c = b.pullOut();
        int size2 = b.size();

        assertEquals(size1 - 1, size2);

        assertTrue(b.pullOut() instanceof Colour);
        assertTrue(b.pullOut() != null);
    }
}