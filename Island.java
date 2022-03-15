package it.polimi.ingsw;

public class Island {
    private int numBlue;
    private int numGreen;
    private int numRed;
    private int numPink;
    private int numYellow;
    private int numTower;
    private ColourTower colourTower;


    /*Why don't we pass 2 attributes of type Island to the constructor? */
    public Island island(int nB, int nG, int nR, int nP, int nY, int nSI) {
        return this;
    }

    public int getBlue() {
        return numBlue;
    }

    public int getGreen() {
        return numGreen;
    }

    public int getPink() {
        return numPink;
    }

    public int getRed() {
        return numRed;
    }

    public int getYellow() {
        return numYellow;
    }

    public void addStudent(Colour student){

    }
}
