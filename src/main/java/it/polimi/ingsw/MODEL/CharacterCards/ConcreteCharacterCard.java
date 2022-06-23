package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.CharacterCards.CharacterCard;

import java.io.Serializable;

public class ConcreteCharacterCard extends CharacterCard implements Serializable {
    String nameCard = "";
    protected int initialPrice;
    protected int price;

    public ConcreteCharacterCard(){}
    public ConcreteCharacterCard(String name, int price){
        nameCard = name;
        this.price = price;
    }

    @Override
    public String getNameCard(){
        return nameCard;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
