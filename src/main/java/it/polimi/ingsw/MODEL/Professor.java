package it.polimi.ingsw.MODEL;

public class Professor {
    private Colour typeColour;
    private Player owner;

    public Professor(Colour typeColour){
        this.typeColour = typeColour;
        owner = null;
    }

    public void changeOwner(Player owner){
        this.owner = owner;
    }

    public Player getOwner(){
        return owner;
    }

    public Colour getColour(){
        return this.typeColour;
    }
}
