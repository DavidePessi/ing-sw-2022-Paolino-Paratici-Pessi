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

    public int NumStudents(Colour colour) {
        return studentGroup.countStudentsOfColour(colour);

    }
}
