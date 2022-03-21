package it.polimi.ingsw.MODEL;

public class Bag {
    private int numBlue;
    private int numGreen;
    private int numRed;
    private int numPink;
    private int numYellow;

    public Bag(){
        numBlue = 0;
        numGreen = 0;
        numPink = 0;
        numRed = 0;
        numYellow = 0;
    }

    public Colour pullOut(){
        /*This method returns a random color that represents the student randomly extracted from the bag*/

        /*We have to use a function that returns a random colour of thr enum and then we have to check if the number of
         * students of that colour is bigger than zero and if it is we have to decrement the number of the students of
         * the extracted colour and return the colour. If there aren't student of the extracted colour, instead, we have
         * to reextract a colour till when the student of the extracted colour exists */


        return Colour.BLUE; /*return messa per non avere l'errore, il metodo è da implementare*/
    }
}
