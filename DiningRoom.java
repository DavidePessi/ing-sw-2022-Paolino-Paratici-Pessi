package it.polimi.ingsw.MODEL;

import java.util.List;

public class DiningRoom {

    private StudentGroup studentGroup;

    public void add(Colour colour) {

        studentGroup.addStudent(colour);
    }

    public int NumStudents(Colour colour) {
        if (colour == Colour.BLUE) {
            return studentGroup.countBlueStudent();
        } else if (colour == Colour.GREEN) {
                return studentGroup.countGreenStudent();
        } else if (colour == Colour.YELLOW) {
            return studentGroup.countYellowStudent();
        } else if (colour == Colour.PINK) {
            return studentGroup.countPinkStudent();
        } else
            return studentGroup.countRedStudent();

    }
}
