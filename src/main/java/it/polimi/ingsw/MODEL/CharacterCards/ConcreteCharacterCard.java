package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.CharacterCards.CharacterCard;

public class ConcreteCharacterCard implements CharacterCard {

    String nameCard = "";
    protected int initialPrice;
    protected int price;

    public ConcreteCharacterCard(){};

    /*public ConcreteCharacterCard(String name){
        nameCard = name;
    }*/
    @Override
    public String getNameCard(){
        return nameCard;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
