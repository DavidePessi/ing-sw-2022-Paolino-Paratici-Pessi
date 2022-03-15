package it.polimi.ingsw;

public class Bag {
    private int numBlue;
    private int numGreen;
    private int numRed;
    private int numPink;
    private int numYellow;

    public Bag bag(){
        return this;
    }

    public Colour pullOut(){
        /*This method returns a random color that represents the student randomly extracted from the bag*/
        return Colour.BLUE; /*return messa per non avere l'errore, il metodo è da implementare*/
    }
}
