package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Exception.MissingPlayerException;
import it.polimi.ingsw.MODEL.Exception.MissingStudentException;
import it.polimi.ingsw.MODEL.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JesterTest {

    @Test
    void initialization() {
        Jester jester = new Jester(new Game("io", "tu"));
        int size = jester.getPool().size();
        assertNotEquals(0,size);
        assertEquals(6, size);
    }

    @RepeatedTest(50)
    void effect() throws Exception {
        Game game = new Game("io", "tu");
        game.startGame(false);

        Jester jester = new Jester(game);
        game.addCharacterCard(jester);

        Colour colour = game.getPlayer("io").getEntrance().getStudentGroup().get(0).getColour();
        Colour colour2 = game.getPlayer("io").getEntrance().getStudentGroup().get(1).getColour();

        int num_colour_jester = jester.getPool().countStudentsOfColour(colour);
        int num_colour2_jester = jester.getPool().countStudentsOfColour(colour2);
        int num_colour_game = game.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(colour);
        int num_colour2_game = game.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(colour2);

        if(jester.getPool().countStudentsOfColour(colour)>0) {
            jester.effect("io", colour, colour2);

            if(!colour.equals(colour2)) {
                assertEquals(num_colour_jester - 1, jester.getPool().countStudentsOfColour(colour));
                assertEquals(num_colour2_jester + 1, jester.getPool().countStudentsOfColour(colour2));
                assertEquals(num_colour_game + 1, game.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(colour) );
                assertEquals(num_colour2_game - 1, game.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(colour2) );
            }
            else{
                assertEquals(num_colour_jester, jester.getPool().countStudentsOfColour(colour));
                assertEquals(num_colour_game, game.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(colour));
            }
        }
    }

    @Test
    void testEffect() {
        Jester jester = new Jester(new Game("io", "tu"));

        assertThrows(Exception.class, ()->jester.effect("io", 2, Colour.RED));
    }

    @Test
    void testEffect1(){
        Jester jester = new Jester(new Game("io", "tu"));

        assertThrows(Exception.class, ()->jester.effect("io", 2));
    }

    @Test
    void testEffect2() {
        Jester jester = new Jester(new Game("io", "tu"));

        assertThrows(Exception.class, ()->jester.effect("io"));
    }

    @Test
    void testEffect3() {
        Jester jester = new Jester(new Game("io", "tu"));

        assertThrows(Exception.class, ()->jester.effect("io", Colour.RED));
    }

    @Test
    void testEffect4() {
    }

    @Test
    void testEffect5() {
    }
}