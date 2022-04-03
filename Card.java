package it.polimi.ingsw.MODEL;

public class Card {

    private int value;
    private int movements;

    public Card(int value, int movements){
        this.value = value;
        this.movements = movements;
    }

    public int getValue(){
        return value;
    }

    public int getMovement(){
        return movements;
    }
}
