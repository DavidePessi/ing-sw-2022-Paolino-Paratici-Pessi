package it.polimi.ingsw.CONTROLLER;
import it.polimi.ingsw.MODEL.*;

import junit.framework.TestCase;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;



public class ControllerActionDifficultTest extends TestCase {

    @RepeatedTest(100)
    public void testUseCharacter() throws Exception{
        Game g = new Game("io", "tu");
        boolean easy = false;
        g.startGame(easy);
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");
        ControllerAction ca = new ControllerActionDifficult(g, l);
        g.getPlayer("io").receiveCoin();
        g.getPlayer("io").receiveCoin();
        g.getPlayer("io").receiveCoin();


        if (g.getCharacterCard(0).getNameCard() == "Knight") {

            ca.useCharacter(new CharacterParameters("io", "Knight"));

            assertEquals("Knight", g.getCharacterCardThrown());
        } else if(g.getCharacterCard(0).getNameCard() == "PostMan"){

            ca.useCharacter(new CharacterParameters("io", "PostMan"));

            assertEquals("PostMan", g.getCharacterCardThrown());
        } else if(g.getCharacterCard(0).getNameCard() == "Satyr"){

            ca.useCharacter(new CharacterParameters("io", "Satyr"));
        }
    }
}
