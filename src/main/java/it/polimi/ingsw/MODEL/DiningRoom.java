package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingStudentException;

import java.io.Serializable;
import java.util.List;

public class DiningRoom implements Serializable {

    private StudentGroup studentGroup;

    public DiningRoom(){
        studentGroup = new StudentGroup();
    }

    public void add(Colour colour) {
        studentGroup.addStudent(colour);
    }

    public void remove(Colour colour) throws MissingStudentException {studentGroup.removeStudent(colour);}

    public int numStudents(Colour colour) {
        return studentGroup.countStudentsOfColour(colour);
    }
    public StudentGroup getStudentGroup(){
        return this.studentGroup;
    }
}
