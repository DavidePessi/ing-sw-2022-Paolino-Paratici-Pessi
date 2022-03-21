package it.polimi.ingsw.MODEL;

// TODO: 21/03/2022 aggiungere una variabile per fare il modulo

import java.util.List;

public class MotherNature {
    private Island position;
    private int currentPositionOfMotherNature;

    public MotherNature(Island position){
        this.position = position;
    }

    public void move(int numMovement, List<Island> listIsland) throws NumberFormatException{
        if (numMovement == 0){
            throw new NumberFormatException();
        }
        else{
            position.setMotherNature(false); //the last one island hasn't mother nature
            currentPositionOfMotherNature = (currentPositionOfMotherNature + numMovement) % 12; //!change this, becuase in the future island will be less
            position = listIsland.get(currentPositionOfMotherNature);
            position.setMotherNature(true); //the current island now has mother nature
        }
    }
}