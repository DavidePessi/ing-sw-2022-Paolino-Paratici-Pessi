package it.polimi.ingsw.MODEL;

import java.util.List;

public class DiningRoom {

    private StudentGroup studentGroup;

    public void add(Colour colour) {
        studentGroup.addStudent(colour);
    }
}
