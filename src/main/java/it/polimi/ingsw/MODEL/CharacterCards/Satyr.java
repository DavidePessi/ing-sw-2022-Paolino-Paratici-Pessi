package it.polimi.ingsw.MODEL.CharacterCards;


import it.polimi.ingsw.MODEL.CharacterCards.ConcreteCharacterCard;
import it.polimi.ingsw.MODEL.CharacterCards.Decorator;
import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Game;

import java.io.Serializable;

public class Satyr extends ConcreteCharacterCard implements Decorator, Serializable {

    private Game game;

    public Satyr(Game game) {
        nameCard = "Satyr";
        this.game = game;
        initialPrice = 3;
        price = 3;
    }

    @Override
    public void initialization() {

    }
    @Override
    public void effect(String nickname){
        if(price == initialPrice){
            price = price+1;
        }
        //torri non valgono niente
        game.setCardThrown(this.nameCard);
    }

    @Override
    public void effect(String nickname, Colour colour) throws Exception{
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, Colour colour, Colour colour2) throws Exception {
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, Colour colour, Colour colour2, Colour colour3, Colour colour4) throws Exception{
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, Colour colour, Colour colour2, Colour colour3, Colour colour4, Colour colour5, Colour colour6) throws Exception{
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, int num) throws Exception{
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, int num, Colour colour)  throws Exception{
        throw new Exception("Error");
    }
}
