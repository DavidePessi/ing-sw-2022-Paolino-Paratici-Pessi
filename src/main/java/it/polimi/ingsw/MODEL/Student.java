package it.polimi.ingsw.MODEL;

public class Student {
    private Colour colour;

    public Student(){
        //getColour deve sempre poter restituire un valore valido quindi inizializzo a blue
        //se non inserisco niente
        this.colour =Colour.BLUE;
    }

    public Student(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }
}