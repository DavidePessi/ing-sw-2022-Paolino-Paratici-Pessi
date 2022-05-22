package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.CONTROLLER.Exception.WrongClientException;
import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.MODEL.Exception.MissingCardException;
import junit.framework.TestCase;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerTurnTest extends TestCase {

    @Test
    public void testVerifyClient() {
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");

        ControllerAction ca = new ControllerAction(g,l);
        ControllerTurn ct = new ControllerTurn(ca, g, l);

        ct.setCurrentClient("io");
        assertEquals(true, ct.verifyClient("io"));
        assertEquals(false, ct.verifyClient("tu"));
    }


    @RepeatedTest(30)
    public void testCallCharacter() throws Exception {
        //caso in cui voglio lanciare una carta personaggio

        Game g = new Game("io", "tu");
        boolean easy = false;
        g.startGame(easy);
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");

        g.getPlayer("io").receiveCoin();
        g.getPlayer("io").receiveCoin();
        g.getPlayer("io").receiveCoin();
        g.getPlayer("io").receiveCoin();

        ControllerAction ca = new ControllerActionDifficult(g, l);
        ControllerTurn ct = new ControllerTurn(ca, g, l);

        if (g.getCharacterCard(0).getNameCard() == "Knight") {
            ct.setMulligan(false);
            ct.setCurrentClient("io");

            ct.callAction(Action.UseCharacter, "io", null, 0, new CharacterParameters("io", "Knight"));

            assertEquals("Knight", g.getCharacterCardThrown());
        }

    }

    @Test
    public void testCallAction() throws Exception{
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");

        ControllerAction ca = new ControllerAction(g,l);
        ControllerTurn ct = new ControllerTurn(ca, g, l);

        //caso in cui voglio giocare una carta, siccome stiamo lanciando
        // una carta tutti deve cambiare il player a fine azione
        ct.setMulligan(true);
        ct.callAction(Action.PlayCard, "io", null,9,null);

        assertEquals(9, g.getPlayer("io").getLastPlayedCard().getValue());
        assertEquals("tu", ct.getCurrentClient());


        //caso in cui voglio muovere madre natura
        ct.setMulligan(false);
        ct.setCurrentClient("io");

        ca.setCurrentAction(Action.MoveMotherNature);
        ct.callAction(Action.MoveMotherNature, "io", null, 2, null);

        assertEquals(false, g.getIsland(0).getHasMotherNature());
        assertEquals(true, g.getIsland(2).getHasMotherNature());
        assertEquals("io", ct.getCurrentClient());

        //caso in cui voglio muovere uno studente nella dinnerRoom
        ca.setCurrentAction(Action.MoveStudent1);
        StudentGroup s1 = new StudentGroup();
        s1.addStudent(Colour.GREEN);
        g.getPlayer("io").addStudentsToEntrance(s1);


        ct.callAction(Action.MoveStudentInDiningRoom, "io", Colour.GREEN, 0, null);

        assertEquals(0, g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.GREEN));
        assertEquals(1, g.getPlayer("io").numStudentsDiningRoom(Colour.GREEN));
        assertEquals("io", ct.getCurrentClient());


        //caso in cui voglio muovere uno studente sull'isola
        ca.setCurrentAction(Action.MoveStudent1);
        StudentGroup s2 = new StudentGroup();
        s2.addStudent(Colour.GREEN);
        g.getPlayer("io").addStudentsToEntrance(s2);

        ct.callAction(Action.MoveStudentInIsland, "io", Colour.GREEN, 1, null);

        assertEquals(1, g.getIsland(1).countStudentsOfColour(Colour.GREEN));
        assertEquals("io", ct.getCurrentClient());


        //caso in cui voglio prendere una nuvola
        //anche in questo caso dopo l'ultima mossa cambio turno
        ca.setCurrentAction(Action.TakeCloud);
        StudentGroup s = new StudentGroup();
        s.addStudent(Colour.GREEN);
        s.addStudent(Colour.BLUE);
        s.addStudent(Colour.BLUE);
        g.getCloud(1).addStudents(s);


        ct.callAction(Action.TakeCloud, "io", null, 1, null);


        assertEquals(2, g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.BLUE));
        assertEquals(1, g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.GREEN));
        assertEquals(0, g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.YELLOW));
        assertEquals(0, g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.PINK));
        assertEquals(0, g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.RED));
        assertEquals("tu", ct.getCurrentClient());

        //verifico il caso in cui chiamo una mossa che non posso fare perchè non è il mio turno
        ct.setCurrentClient("io");

        Exception exception = assertThrows(WrongClientException.class, ()->ct.callAction(Action.TakeCloud,"gigio", null, -1,null));

        //sbaglio mossa e lo notifico per ogni ramo
        ca.setCurrentAction(Action.MoveMotherNature);
        ct.callAction(Action.TakeCloud, "io", null, 1, null);

        ca.setCurrentAction(Action.TakeCloud);
        ct.callAction(Action.MoveMotherNature, "io", null, 1, null);

        ca.setCurrentAction(Action.TakeCloud);
        ct.callAction(Action.MoveStudentInIsland, "io", null, 1, null);

        ca.setCurrentAction(Action.TakeCloud);
        ct.callAction(Action.MoveStudentInDiningRoom, "io", null, 1, null);
    }

    @Test
    public void testSetCurrentClient() {
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");

        ControllerAction ca = new ControllerAction(g,l);
        ControllerTurn ct = new ControllerTurn(ca, g, l);

        ct.setCurrentClient("tu");
        assertEquals("tu", ct.getCurrentClient());

        ct.setCurrentClient("io");
        assertEquals("io", ct.getCurrentClient());

    }

    @Test
    public void testGetCurrentClient() {
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");

        ControllerAction ca = new ControllerAction(g,l);
        ControllerTurn ct = new ControllerTurn(ca, g, l);

        assertNotNull(ct.getCurrentClient());

        ct.setCurrentClient("io");
        assertTrue(ct.getCurrentClient() instanceof String);
    }

    @Test
    public void testEndTurn() throws Exception{
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");

        ControllerAction ca = new ControllerAction(g,l);
        ControllerTurn ct = new ControllerTurn(ca, g, l);

        //verifico che passo al prossimo player
        //(l'ordine iniziale è infatti quello della lista)
        ct.setCurrentClient("io");
        ct.endTurn();
        assertEquals("tu", ct.getCurrentClient());

        //verifico che devo rilanciare le carte e non sono ancora state lanciate
        //quindi devo settare a 0 le carte lanciate prima esettare il nuovo currentClient al primo
        ct.setCurrentClient("tu");
        ct.setMulligan(false);
        ct.endTurn();

        assertEquals("io", ct.getCurrentClient());
        assertEquals(new Card(0,0), g.getPlayer("io").getLastPlayedCard());
        assertEquals(new Card(0,0), g.getPlayer("tu").getLastPlayedCard());

        //sono ora nel caso in cui tutti i giocatori hanno lanciato una carta
        //e devo ricalcolare l'ordine
        ct.setCurrentClient("tu");
        ct.setMulligan(true);
        g.doPlayCard("io", 2);
        g.doPlayCard("tu",1);

        ct.endTurn();
        assertEquals("tu", ct.getCurrentClient());
        assertEquals(false, ct.getMulligan());
    }

    @Test
    public void testSetMulligan() {
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");

        ControllerAction ca = new ControllerAction(g,l);
        ControllerTurn ct = new ControllerTurn(ca, g, l);

        ct.setMulligan(true);
        assertEquals(true, ct.getMulligan());

        ct.setMulligan(false);
        assertEquals(false, ct.getMulligan());
    }

    @Test
    public void testGetMulligan() {
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");

        ControllerAction ca = new ControllerAction(g,l);
        ControllerTurn ct = new ControllerTurn(ca, g, l);

        assertNotNull(ct.getMulligan());


    }
}