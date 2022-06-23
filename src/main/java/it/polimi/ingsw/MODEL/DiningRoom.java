package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingStudentException;

import java.io.Serializable;
import java.util.List;

public class DiningRoom implements Serializable {
    private StudentGroup studentGroup;

    public DiningRoom(){
        studentGroup = new StudentGroup();
    }

    /**
     * add a student with the colour of the parameter
     * @param colour
     */
    public void add(Colour colour) {
        studentGroup.addStudent(colour);
    }

    /**
     * remove the student with colour of the parameter
     * @param colour
     * @throws MissingStudentException
     */
    public void remove(Colour colour) throws MissingStudentException {studentGroup.removeStudent(colour);}

    /**
     * returns the number of student with the colour of the parameter
     * @param colour
     * @return
     */
    public int numStudents(Colour colour) {
        return studentGroup.countStudentsOfColour(colour);
    }

    /**
     * returns the student group
     * @return
     */
    public StudentGroup getStudentGroup(){
        return this.studentGroup;
    }
}
