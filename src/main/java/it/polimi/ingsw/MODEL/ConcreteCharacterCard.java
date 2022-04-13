package it.polimi.ingsw.MODEL;

public class ConcreteCharacterCard implements CharacterCard{

    String nameCard = "";

    public ConcreteCharacterCard(){};

    public ConcreteCharacterCard(String name){
        nameCard = name;
    }

    @Override
    public String getNameCard(){
        return nameCard;
    }

    @Override
    public int getPrice() {
        return 0;
    }
}
