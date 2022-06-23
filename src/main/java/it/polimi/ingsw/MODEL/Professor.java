package it.polimi.ingsw.MODEL;

import java.io.Serializable;

public class Professor implements Serializable {
    private Colour typeColour;
    private Player owner;

    public Professor(Colour typeColour){
        this.typeColour = typeColour;
        owner = null;
    }

    /**
     * change the owner of the professor
     * @param owner
     */
    public void changeOwner(Player owner){
        this.owner = owner;
    }

    /**
     * returns the owner of professor
     * @return
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * returns the colour of professor
     * @return
     */
    public Colour getColour(){
        return this.typeColour;
    }
}