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

    @Test
    public void testCheckTowers() throws MissingIslandException, PossibleWinException, MissingTowerException {
        Game g = new Game("io", "tu");

        //caso non esiste l'isola
        Assertions.assertThrows(MissingIslandException.class, ()->g.checkTowers(20));

        //caso colore esiste viene presa dal primo team
        Colour col1 = Colour.BLUE;
        StudentGroup s1 = new StudentGroup();
        s1.addStudent(col1);
        s1.addStudent(col1);

        g.getIsland(1).addStudent(col1);//aggiungo gli studenti all'isola
        g.getIsland(1).addStudent(col1);
        g.getIsland(1).setColourTower(ColourTower.BLACK);//setto le torri a black sull'isola
        g.getPlayer("io").addStudentsToEntrances(s1);//aggiungo il gruppo studenti all'entrata di player
        g.getPlayer("io").moveStudentInDiningRoom(col1);//aggiungo 2 studenti alla dinner room di "io"
        g.getPlayer("io").moveStudentInDiningRoom(col1);
        g.checkProfessor(col1);//riassegno il professore in modo che lo abbiamo "io"
        g.checkTowers(1);

        assertEquals(g.getIsland(1).getColourTower(), g.getPlayer("io").getTeam().getColourTower());

        //caso colore esiste viene presa dal secondo team
        Colour col2 = Colour.RED;
        StudentGroup s2 = new StudentGroup();
        s2.addStudent(col2);
        s2.addStudent(col2);

        g.getIsland(2).addStudent(col2);//aggiungo gli studenti all'isola
        g.getIsland(2).addStudent(col2);
        g.getIsland(2).setColourTower(ColourTower.WHITE);//setto le torri a black sull'isola
        g.getPlayer("tu").addStudentsToEntrances(s2);//aggiungo il gruppo studenti all'entrata di player
        g.getPlayer("tu").moveStudentInDiningRoom(col2);//aggiungo 2 studenti alla dinner room di "io"
        g.getPlayer("tu").moveStudentInDiningRoom(col2);
        g.checkProfessor(col2);//riassegno il professore in modo che lo abbiamo "io"
        g.checkTowers(2);

        assertEquals(g.getIsland(2).getColourTower(), g.getPlayer("tu").getTeam().getColourTower());

        //caso di parità
        Colour col3 = Colour.YELLOW;
        StudentGroup s3 = new StudentGroup();
        s3.addStudent(col3);
        s3.addStudent(col3);

        g.getIsland(3).addStudent(col3);//aggiungo gli studenti all'isola
        g.getIsland(3).addStudent(col3);
        g.getIsland(3).setColourTower(ColourTower.WHITE);//setto le torri a black sull'isola
        g.getPlayer("io").addStudentsToEntrances(s3);//aggiungo il gruppo studenti all'entrata di player
        g.getPlayer("io").moveStudentInDiningRoom(col3);//aggiungo 2 studenti alla dinner room di "io"
        g.getPlayer("io").moveStudentInDiningRoom(col3);
        g.checkProfessor(col3);//riassegno il professore in modo che lo abbiamo "io"
        g.getPlayer("tu").addStudentsToEntrances(s3);//aggiungo il gruppo studenti all'entrata di player
        g.getPlayer("tu").moveStudentInDiningRoom(col3);//aggiungo 2 studenti alla dinner room di "io"
        g.getPlayer("tu").moveStudentInDiningRoom(col3);
        g.checkProfessor(col3);//riassegno il professore in modo che lo abbiamo "io"
        g.checkTowers(3);

        assertEquals(g.getIsland(3).getColourTower(), g.getPlayer("io").getTeam().getColourTower());

        //caso possibile vittoria
        Colour col4 = Colour.PINK;
        StudentGroup s4 = new StudentGroup();
        s4.addStudent(col4);
        s4.addStudent(col4);

        g.getIsland(4).addStudent(col4);//aggiungo gli studenti all'isola
        g.getIsland(4).addStudent(col4);
        g.getIsland(4).setColourTower(ColourTower.BLACK);//setto le torri a black sull'isola
        g.getPlayer("io").addStudentsToEntrances(s4);//aggiungo il gruppo studenti all'entrata di player
        g.getPlayer("io").moveStudentInDiningRoom(col4);//aggiungo 2 studenti alla dinner room di "io"
        g.getPlayer("io").moveStudentInDiningRoom(col4);
        g.getPlayer("io").getTeam().useTowers(7);
        g.checkProfessor(col4);//riassegno il professore in modo che lo abbiamo "io"
        g.checkTowers(4);
        //todo controllare ste cazzo di exception che non c'ho capito una sega di cosa posso fare una volta fatta la catch
        Assertions.assertThrows(PossibleWinException.class, ()->g.checkTowers(4));

        //caso di assenza di torri
    }
}