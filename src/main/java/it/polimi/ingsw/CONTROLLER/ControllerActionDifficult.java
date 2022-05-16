package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.MODEL.CharacterParameters;
import it.polimi.ingsw.MODEL.Game;

import java.util.List;

public class ControllerActionDifficult extends ControllerAction{

    public ControllerActionDifficult(Game game, List<String> nicknames) {
        super(game, nicknames);
    }

    //TODO: guardare questo: per poter chiamare game.doPlayCharacterCard(charPar) ho dovuto mettere game protected invece che private in ControllerAction... ho messo tutti gli attributi di ControllerAction protected
    @Override
    public void useCharacter(CharacterParameters charPar) throws Exception {
        game.doPlayCharacterCard(charPar);

    }

}