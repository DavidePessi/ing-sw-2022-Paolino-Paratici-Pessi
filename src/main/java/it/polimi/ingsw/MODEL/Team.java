package it.polimi.ingsw.MODEL;

public class Team {
    private ColourTower colourTower;
    private int numberOfTower;

    public Team (ColourTower colourTower, int numberOfTower){
        this.colourTower = colourTower;
        this.numberOfTower = numberOfTower;
    }

    public int getNumberOfTower(){
        return numberOfTower;
    }
}
