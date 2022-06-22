package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingCloudException;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CloudTest extends TestCase {

    @Test
    public void testGetStudents()throws MissingCloudException {
        Cloud c = new Cloud();
        StudentGroup s = new StudentGroup();
        Colour colour = Colour.GREEN;
        s.addStudent(colour);

        //verifico che se la nuvola è vuta me lo dica
        Assertions.assertThrows(MissingCloudException.class,()->c.getStudents());

        try{
            c.getStudents();
        }catch(MissingCloudException e){
            assertEquals("Cloud empty", e.getMessage());
        }

        //verifico che mi restituisca il gruppo studenti che gli do
        //e svuoti la nuvola
        c.addStudents(s);
        Assertions.assertEquals(s,c.getStudents());
        Assertions.assertThrows(MissingCloudException.class,()->c.getStudents());
    }


    @Test
    public void testAddStudents() throws MissingCloudException {
        Cloud c = new Cloud();
        StudentGroup s = new StudentGroup();
        Colour colour = Colour.GREEN;

        s.addStudent(colour);
        c.addStudents(s);

        //verifico che effettivamente siano stati aggiunti gli studenti
        assertEquals(s, c.getStudents());
    }

    @Test
    public void testEmpty(){

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.addStudent(Colour.RED);
        studentGroup.addStudent(Colour.GREEN);
        studentGroup.addStudent(Colour.BLUE);

        StudentGroup studentGroup1 = new StudentGroup();

        Cloud cloud = new Cloud();
        Cloud cloud1 = new Cloud();
        cloud.addStudents(studentGroup);
        cloud1.addStudents(studentGroup1);

        assertEquals(false, cloud.empty());
        assertEquals(true, cloud1.empty());

    }
}