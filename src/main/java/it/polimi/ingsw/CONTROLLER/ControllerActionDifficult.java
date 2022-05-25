package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.MODEL.CharacterParameters;
import it.polimi.ingsw.MODEL.Exception.PossibleWinException;
import it.polimi.ingsw.MODEL.Game;

import java.util.List;

public class ControllerActionDifficult extends ControllerAction {

    public ControllerActionDifficult(Game game, List<String> nicknames) {
        super(game, nicknames);
    }

    @Override
    public void useCharacter(CharacterParameters charPar) throws Exception {
        try {
            game.doPlayCharacterCard(charPar);
        } catch (PossibleWinException e) {
            game.notifyWin();
        }

    }
}