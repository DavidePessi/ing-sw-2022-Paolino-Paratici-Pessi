package it.polimi.ingsw.MODEL;

public class Professor {
    private Colour typeColour;
    private Player owner;

    public Professor(Colour typeColour){
        this.typeColour = typeColour;
        owner = null;
    }

    void changeOwner(Player owner){
    }

    Player getOwner(){
        return this.owner;
    }
}
