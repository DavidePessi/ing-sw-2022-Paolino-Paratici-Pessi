package it.polimi.ingsw.MODEL;

public class Student {
    private Colour colour;

    public Student(){}

    public Student(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }
}