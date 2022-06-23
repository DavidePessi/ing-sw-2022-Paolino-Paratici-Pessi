package it.polimi.ingsw.MODEL;

import java.io.Serializable;

public class Student implements Serializable {
    private Colour colour;

    public Student(){
        this.colour =Colour.BLUE;
    }
    public Student(Colour colour) {
        this.colour = colour;
    }

    /**
     * returns the colour of the student
     * @return
     */
    public Colour getColour() {
        return colour;
    }
}