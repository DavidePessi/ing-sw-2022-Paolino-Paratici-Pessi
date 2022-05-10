package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.CONTROLLER.Exception.WrongActionException;
import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Exception.MissingCardException;
import it.polimi.ingsw.MODEL.Exception.MissingStudentException;
import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.MODEL.Island;
import it.polimi.ingsw.MODEL.StudentGroup;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerActionTest extends TestCase {

    public void testCheckCardToPlay() throws Exception{
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");
        ControllerAction ca = new ControllerAction(g, l);

        //caso in cui la carta è già stata lanciata e ho delle possibilità giocabili
        g.doPlayCard("io", 1);
        assertEquals(false, ca.checkCardToPlay(1, "tu"));

        //caso in cui la carta non è stata giocata da nessuno
        assertEquals(true, ca.checkCardToPlay(2, "tu"));

        //caso in cui sono obbligato a giocare quella carta
        g.getPlayer("tu").playCard(2);
        g.getPlayer("tu").playCard(3);
        g.getPlayer("tu").playCard(4);
        g.getPlayer("tu").playCard(5);
        g.getPlayer("tu").playCard(6);
        g.getPlayer("tu").playCard(7);
        g.getPlayer("tu").playCard(8);
        g.getPlayer("tu").playCard(9);
        g.getPlayer("tu").playCard(10);

        assertEquals(true, ca.checkCardToPlay(1, "tu"));

    }

    public void testPlayCard() throws Exception{
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");
        ControllerAction ca = new ControllerAction(g, l);
        int num = 3;

        int size_prima = g.getPlayer("io").getDeck().size();

        ca.playCard("io", num);

        int size_dopo = g.getPlayer("io").getDeck().size();

        Assertions.assertEquals(size_dopo, size_prima - 1);
        Exception exception = assertThrows(MissingCardException.class, ()->g.getPlayer("io").getDeck().getCard(3));
    }

    public void testMoveMotherNature() throws Exception{
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");
        ControllerAction ca = new ControllerAction(g, l);

        Island oldIsland = g.getIsland(0);

        g.getPlayer("io").playCard(4);
        assertThrows(WrongActionException.class, ()->ca.moveMotherNature(2));

        ca.setCurrentAction(Action.MoveMotherNature);
        ca.moveMotherNature(2);
        Island newIsland = g.getIsland(2);

        Assertions.assertEquals(oldIsland.getHasMotherNature(), false);
        Assertions.assertEquals(newIsland.getHasMotherNature(), true);
        Assertions.assertEquals(Action.TakeCloud,ca.getCurrentAction());
    }

    public void testMoveStudentInDiningRoom() throws Exception{
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");
        ControllerAction ca = new ControllerAction(g, l);

        StudentGroup studentGroup = new StudentGroup();
        studentGroup.addStudent(Colour.GREEN); //aggiungiamo uno studente di quel colore per testare
        studentGroup.addStudent(Colour.GREEN);
        studentGroup.addStudent(Colour.GREEN);
        studentGroup.addStudent(Colour.GREEN);
        g.getPlayer("io").addStudentsToEntrance(studentGroup);

        int numPrimaStudentiInDiningRoom = g.getPlayer("io").numStudentsDiningRoom(Colour.GREEN);

        ca.moveStudentInDiningRoom("io",Colour.GREEN);

        int numDopoStudentiInDiningRoom = g.getPlayer("io").numStudentsDiningRoom(Colour.GREEN);
        Assertions.assertEquals(numDopoStudentiInDiningRoom, numPrimaStudentiInDiningRoom+1);

        //caso di eccezioni
        ca.setCurrentAction(Action.TakeCloud);
        assertThrows(WrongActionException.class, ()->ca.moveStudentInDiningRoom("io",Colour.GREEN));

        //caso moveStudent2
        ca.setCurrentAction(Action.MoveStudent2);
        ca.moveStudentInDiningRoom("io",Colour.GREEN);

        assertEquals(Action.MoveStudent3, ca.getCurrentAction());
        //caso moveStudent3
        ca.moveStudentInDiningRoom("io",Colour.GREEN);

        assertEquals(Action.MoveMotherNature, ca.getCurrentAction());
    }

    public void testMoveStudentInIsland() throws Exception {
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");
        ControllerAction ca = new ControllerAction(g, l);
        int num = 3;
        int green_prima, green_dopo;

        StudentGroup studentGroup = new StudentGroup();

        studentGroup.addStudent(Colour.GREEN); //aggiungiamo uno studente di quel colore per testare
        studentGroup.addStudent(Colour.GREEN);
        studentGroup.addStudent(Colour.GREEN);

        green_prima = g.getIsland(num).countStudentsOfColour(Colour.GREEN);

        g.getPlayer("io").addStudentsToEntrance(studentGroup);
        ca.moveStudentInIsland("io", Colour.GREEN, num);

        green_dopo = g.getIsland(num).countStudentsOfColour(Colour.GREEN);

        Assertions.assertEquals(green_dopo, green_prima+1);

        //caso eccezione
        ca.setCurrentAction(Action.TakeCloud);
        assertThrows(WrongActionException.class, ()->ca.moveStudentInIsland("io", Colour.GREEN, num));

        //caso MoveStudent2
        ca.setCurrentAction(Action.MoveStudent2);
        ca.moveStudentInIsland("io", Colour.GREEN, num);

        assertEquals(Action.MoveStudent3, ca.getCurrentAction());

        //caso MoveStudent3
        ca.moveStudentInIsland("io", Colour.GREEN, num);

        assertEquals(Action.MoveMotherNature, ca.getCurrentAction());
    }

    public void testTakeCloud() throws Exception{
        Game g = new Game("io", "tu");
        List<String> l = new ArrayList<>();
        l.add("io");
        l.add("tu");
        ControllerAction ca = new ControllerAction(g, l);

        int num = 1;
        int numStudInEntrancePrima_Green, numStudInEntranceDopo_Green;

        StudentGroup sg = new StudentGroup();

        sg.addStudent(Colour.GREEN);
        sg.addStudent(Colour.GREEN);
        sg.addStudent(Colour.BLUE);
        g.getCloud(num).addStudents(sg); //aggiungo 3 studenti alla nuvola


        numStudInEntrancePrima_Green = g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.GREEN);

        assertThrows(WrongActionException.class, ()->ca.takeCloud("io", num));

        ca.setCurrentAction(Action.TakeCloud);

        ca.takeCloud("io", num);
        numStudInEntranceDopo_Green = g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.GREEN);

        Assertions.assertEquals(numStudInEntranceDopo_Green, numStudInEntrancePrima_Green+2);
    }

    public void testUseCharacter() {
    }
}