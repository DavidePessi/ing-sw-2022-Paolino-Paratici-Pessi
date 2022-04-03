package it.polimi.ingsw.MODEL;

import java.util.List;

public class MotherNature {
    private Island position;
    //private int currentPositionOfMotherNature;

    public MotherNature(Island position){
        this.position = position;
        this.position.setMotherNature(true);
    }

    public void move(Island newIsland) throws NumberFormatException{
        position.setMotherNature(false);
        position = newIsland;
        position.setMotherNature(true);
    }

    public int getNumIsland(){
        return position.getNumIsland();
    }
}