package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class StudentTest extends TestCase {

    @Test
    public void testGetColour() {
        Colour c = Colour.GREEN;
        Student s1 = new Student(c);

        assertEquals(c, s1.getColour());
        assertNotNull(s1.getColour());
        assertTrue(s1.getColour() instanceof Colour);

        Student s2 = new Student();

        assertNotNull(s2.getColour());
        assertTrue(s2.getColour() instanceof Colour);
    }
}