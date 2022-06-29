package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingTowerException;

import java.io.Serializable;

public class Team implements Serializable {
    private ColourTower colourTower;
    private int numberOfTower;

    public Team (ColourTower colourTower, int numberOfTower){
        this.colourTower = colourTower;
        this.numberOfTower = numberOfTower;
    }

    /**
     * returns the number of the tower
     * @return
     */
    public int getNumberOfTower(){
        return this.numberOfTower;

    }

    /**
     * returns the colour of the tower of the team
     * @return
     */
    public ColourTower getColourTower(){
        return this.colourTower;
    }

    /**
     * use tower to dominate an island
     * @param numTowerUsed
     * @throws MissingTowerException
     */
    public void useTowers(int numTowerUsed)throws MissingTowerException {
        if(numTowerUsed >= this.numberOfTower){
            this.numberOfTower = 0;
            throw new MissingTowerException();
        }
        else{
            this.numberOfTower = this.numberOfTower - numTowerUsed;
        }
    }

    /**
     * you lost some towers, and you need to add that towers to yours
     * @param numTowerLost
     */
    public void takeTowers(int numTowerLost) {
        this.numberOfTower = this.numberOfTower + numTowerLost;
    }
}
