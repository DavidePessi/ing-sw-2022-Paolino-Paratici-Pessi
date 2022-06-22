package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Exception.MissingPlayerException;
import it.polimi.ingsw.MODEL.Exception.MissingStudentException;
import it.polimi.ingsw.MODEL.Game;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JesterTest {

    @Test
    void initialization() {
        Jester jester = new Jester(new Game("io", "tu"));
        jester.initialization();
        int size = jester.getPool().size();
        assertNotEquals(0,size);
        assertEquals(6, size);
    }

    @RepeatedTest(50)
    void effect() throws Exception {
        Game game = new Game("io", "tu");
        Jester jester = new Jester(game);
        game.startGame(false);
        game.addCharacterCard(jester);

        jester.getPool().addStudent(game.getPlayer("io").getEntrance().getStudentGroup().get(0).getColour());

        Colour colour0 = game.getPlayer("io").getEntrance().getStudentGroup().get(0).getColour();
        Colour colour1 = game.getPlayer("io").getEntrance().getStudentGroup().get(1).getColour();
        int num_colour0_entrance = game.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(colour0);
        int num_colour1_entrance = game.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(colour1);


        jester.effect("io", colour0, colour1);


        assertEquals(2, jester.price);


        if (colour0.equals(colour1)) {

            assertEquals(num_colour0_entrance, game.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(colour0));
            assertEquals(1, jester.getPool().countStudentsOfColour(colour0));
        }
        else {
            assertEquals(num_colour0_entrance+1, game.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(colour0));
            assertEquals(num_colour1_entrance-1, game.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(colour1));
            assertEquals(0, jester.getPool().countStudentsOfColour(colour0));

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