package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingTowerException;

public class Team {
    private ColourTower colourTower;
    private int numberOfTower;

    public Team (ColourTower colourTower, int numberOfTower){
        this.colourTower = colourTower;
        this.numberOfTower = numberOfTower;
    }

    public int getNumberOfTower(){
        return this.numberOfTower;

    }

    public ColourTower getColourTower(){
        return this.colourTower;
    }

    public void useTowers(int numTowerUsed)throws MissingTowerException {
        if(numTowerUsed >= this.numberOfTower){
            throw new MissingTowerException();
        }
        else{
            this.numberOfTower = this.numberOfTower - numTowerUsed;
        }
    }

    public void takeTowers(int numTowerLost) {
        this.numberOfTower = this.numberOfTower + numTowerLost;
    }
}
