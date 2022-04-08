package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest extends TestCase {

    @Test
    public void testStartGame() {
    }

    /*Ci sono 3 casi in cui deve restituire true, vanno poi testati gli altri 2*/
    @Test
    public void testCheckWin() {
        Game g = new Game("io", "tu");
        Assertions.assertEquals(false, g.checkWin());
        for(int i=1; i<=10; i++){
            g.doPlayCard("io", i);
        }
        Assertions.assertEquals(true, g.checkWin());
    }

    @Test
    public void testTheWinnerIs() {
    }

    @Test
    public void testDoMoveMotherNature() {
        Game g = new Game ("io", "tu");

        g.getIsland(0).setMotherNature(true);
        Island oldIsland = g.getIsland(0);

        g.doMoveMotherNature(5);
        Island newIsland = g.getIsland(5);

        Assertions.assertEquals(oldIsland.getHasMotherNature(), false);
        Assertions.assertEquals(newIsland.getHasMotherNature(), true);
    }

    @Test
    public void testDoMoveStudentInDiningRoom() {
        Game g = new Game("io",  "tu");
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.addStudent(Colour.GREEN); //aggiungiamo uno studente di quel colore per testare
        g.getPlayer("io").addStudentsToEntrances(studentGroup);

        int numPrimaStudentiInDiningRoom = g.getPlayer("io").NumStudentsDiningRoom(Colour.GREEN);
        try {
               g.doMoveStudentInDiningRoom("io", Colour.GREEN);
        } catch (MissingStudentException e) {
               System.out.println("Error doing testDoMoveStudentInDiningRoom");
        }

        int numDopoStudentiInDiningRoom = g.getPlayer("io").NumStudentsDiningRoom(Colour.GREEN);
        Assertions.assertEquals(numDopoStudentiInDiningRoom, numPrimaStudentiInDiningRoom+1);
    }

    @Test
    public void testDoTakeCloud() {
        Game g = new Game("io", "tu");
        int num = 1;
        int numStudInEntrancePrima_Green, numStudInEntranceDopo_Green;
        int numStudInEntrancePrima_Blue, numStudInEntranceDopo_Blue;

        Cloud c = new Cloud();
        StudentGroup sg = new StudentGroup();

        sg.addStudent(Colour.GREEN);
        sg.addStudent(Colour.GREEN);
        sg.addStudent(Colour.BLUE);
        g.getCloud(num).addStudents(sg); //aggiungo 3 studenti alla nuvola

        //numStudInEntrancePrima_Blue = g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.BLUE);
        numStudInEntrancePrima_Green = g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.GREEN);
        g.doTakeCloud("io", num);
        numStudInEntranceDopo_Green = g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.GREEN);
        //numStudInEntranceDopo_Blue = g.getPlayer("io").getEntrance().getStudentGroup().countStudentsOfColour(Colour.BLUE);

        Assertions.assertEquals(numStudInEntranceDopo_Green, numStudInEntrancePrima_Green+2);
        //Assertions.assertEquals(numStudInEntranceDopo_Blue, numStudInEntrancePrima_Blue+1);

    }

    //dobbiamo anche controllare gli studenti in entrance
    @Test
    public void testDoMoveStudentInIsland() {
        Game g = new Game("io", "tu");
        int num = 3;
        int green_prima, green_dopo;
        StudentGroup studentGroup = new StudentGroup();

        studentGroup.addStudent(Colour.GREEN); //aggiungiamo uno studente di quel colore per testare


        green_prima = g.getIsland(num).countStudentsOfColour(Colour.GREEN);
        g.getPlayer("io").addStudentsToEntrances(studentGroup);
        g.doMoveStudentInIsland("io", Colour.GREEN, num);

        green_dopo = g.getIsland(num).countStudentsOfColour(Colour.GREEN);

        Assertions.assertEquals(green_dopo, green_prima+1);
    }

    @Test
    public void testGetIsland() {
        Game g = new Game("io", "tu");
        int num = 3;

        Island i = new Island(num);
        Island qualcosaugualea=g.getIsland(num);

        Assertions.assertEquals(i, qualcosaugualea);
    }

    @Test
    public void testDoPlayCard() throws MissingCardException {
        Game g = new Game("io", "tu");
        int num = 3;
        int size_prima = g.getPlayer("io").getDeck().size();
        g.doPlayCard("io", num);
        int size_dopo = g.getPlayer("io").getDeck().size();
        Assertions.assertEquals(size_dopo, size_prima - 1);
        Exception exception = assertThrows(MissingCardException.class, ()->g.getPlayer("io").getDeck().getCard(3));
    }

    @Test
    public void testCheckProfessor() throws MissingStudentException {
        Game g = new Game("io", "tu");

        Colour col = Colour.BLUE;

        //verifico che se nessuno ha studenti nessuno continuerà ad avere professori
        g.checkProfessor(col);
        int numProf = 0;
        numProf = numProf + g.getPlayer("io").numProfessor() + g.getPlayer("tu").numProfessor();

        Assertions.assertEquals(0, numProf);
        Assertions.assertNull(g.getProfessor(col).getOwner());

        //verifico che sia dato ad io se ha più studenti
        StudentGroup sg = new StudentGroup();
        sg.addStudent(col);
        g.getPlayer("io").addStudentsToEntrances(sg);
        g.doMoveStudentInDiningRoom("io", col);
        g.checkProfessor(col);

        numProf = numProf + g.getPlayer("io").numProfessor();
        Assertions.assertEquals(1, g.getPlayer("io").NumStudentsDiningRoom(col));
        Assertions.assertEquals(1, numProf);
        //assertEquals(g.getPlayer("io"), g.getProfessor(col).getOwner());
    }
}