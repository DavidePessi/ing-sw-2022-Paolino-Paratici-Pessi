package it.polimi.ingsw.MODEL;

import java.io.Serializable;
import java.util.List;

public class MotherNature implements Serializable {
    private Island position;

    public MotherNature(Island position){
        this.position = position;
        this.position.setMotherNature(true);
    }

    /**
     * move mother nature on the island of the parameter
     * @param newIsland
     * @throws NumberFormatException
     */
    public void move(Island newIsland) throws NumberFormatException{
        position.setMotherNature(false);
        position = newIsland;
        position.setMotherNature(true);
    }

    /**
     * returns the position of mother nature
     * @return
     */
    public Island getPosition(){
        return this.position;
    }

    /**
     * return the number of the island where mother nature is on
     * @return
     */
    public int getNumIsland(){
        return position.getNumIsland();
    }
}