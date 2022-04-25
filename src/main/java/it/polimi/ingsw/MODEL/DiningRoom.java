package it.polimi.ingsw.MODEL;

import java.util.List;

public class DiningRoom {

    private StudentGroup studentGroup;

    public DiningRoom(){
        studentGroup = new StudentGroup();
    }

    public void add(Colour colour) {
        studentGroup.addStudent(colour);
    }

    public void remove(Colour colour){studentGroup.removeStudent(colour);}

    public int numStudents(Colour colour) {
        return studentGroup.countStudentsOfColour(colour);
    }
    public StudentGroup getStudentGroup(){
        return this.studentGroup;
    }
}
