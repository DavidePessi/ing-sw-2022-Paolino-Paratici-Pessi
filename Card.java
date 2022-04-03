package it.polimi.ingsw.MODEL;

public class Card {

    private int value;
    private int movements;

    public Card(int value, int movements){
        this.value = value;
        this.movements=movements;
    }

    public int getValue(){
        return this.value;
    }

    public int getMovements(){
        return this.movements;
    }
}
