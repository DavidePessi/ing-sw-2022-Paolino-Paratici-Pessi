package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest extends TestCase {

    @Test
    public void testGame1(){
        assertTrue(new Game("tu","io") instanceof Game);
        assertTrue(new Game("tu","io","egli") instanceof Game);
        assertTrue(new Game("tu","io","egli","noi") instanceof Game);
    }

    /*@Test
    public void testStartGame() throws MissingStudentException, MissingCloudException, MissingPlayerException {
        Game g = new Game("io", "tu");
        g.startGame();

        //verifico che tutte le nuvole hanno studenti sopra
        assertTrue(g.getCloud(0).getStudents() instanceof StudentGroup);
        assertTrue(g.getCloud(1).getStudents() instanceof StudentGroup);

        //verifico che tutti i player hanno ricevuto 7 studenti
        assertEquals(7, g.getPlayer("io").getEntrance().getStudentGroup().size());
        assertEquals(7, g.getPlayer("tu").getEntrance().getStudentGroup().size());

        //verifico che ogni isola ha uno studente
        for(int i = 0; i < 12; i++){
            int  tot = 0;
            for(Colour c : Colour.values()) {
                tot = tot +g.getIsland(i).countStudentsOfColour(c);
            }
            if(i != 0 && i != 6)
                assertEquals(1, tot);
            else{
                assertEquals(0, tot);
            }
        }

        //verifico che tolgo un totale di 30 studenti dalla bag
        assertEquals(100, g.getBag().size());
    }*/

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
    public void testTheWinnerIs() throws MissingPlayerException, MissingTowerException {
        Game g = new Game("io", "tu");
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        //caso parità fra torri e io ha più torri di tu
        s.addStudent(c);
        g.getPlayer("io").addStudentsToEntrance(s);
        g.getPlayer("io").moveStudentInDiningRoom(c);
        g.checkProfessor(c);

        assertEquals(g.getPlayer("io").getTeam() ,g.theWinnerIs());

        //caso tu ha più torri di io
        g.getPlayer("tu").getTeam().useTowers(2);

        assertEquals(g.getPlayer("tu").getTeam(), g.theWinnerIs());

        //caso in cui abbiamo una parità e vince qualcuno che non è io
        g.getPlayer("io").getTeam().useTowers(2);
        s.addStudent(c);
        g.getPlayer("tu").addStudentsToEntrance(s);
        g.getPlayer("tu").moveStudentInDiningRoom(c);
        g.getPlayer("tu").moveStudentInDiningRoom(c);
        g.checkProfessor(c);

        assertEquals(g.getPlayer("tu").getTeam(), g.theWinnerIs());
    }


    /*@Test
    public void testDoMoveMotherNature() throws MissingPlayerException {
        Game g = new Game ("io", "tu");

        g.getIsland(0).setMotherNature(true);
        Island oldIsland = g.getIsland(0);

        g.getPlayer("io").playCard(4);
        g.doMoveMotherNature(2);
        Island newIsland = g.getIsland(3);

        Assertions.assertEquals(oldIsland.getHasMotherNature(), false);
        Assertions.assertEquals(newIsland.getHasMotherNature(), true);
    }*/

    @Test
    public void testDoMoveStudentInDiningRoom()throws MissingPlayerException {
        Game g = new Game("io",  "tu");
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.addStudent(Colour.GREEN); //aggiungiamo uno studente di quel colore per testare
        g.getPlayer("io").addStudentsToEntrance(studentGroup);

        int numPrimaStudentiInDiningRoom = g.getPlayer("io").numStudentsDiningRoom(Colour.GREEN);

        g.doMoveStudentInDiningRoom("io", Colour.GREEN);

        int numDopoStudentiInDiningRoom = g.getPlayer("io").numStudentsDiningRoom(Colour.GREEN);
        Assertions.assertEquals(numDopoStudentiInDiningRoom, numPrimaStudentiInDiningRoom+1);

        //caso di eccezione
        g.doMoveStudentInDiningRoom("giorgio", Colour.GREEN);

    }

    @Test
    public void testDoTakeCloud()throws MissingCloudException, MissingPlayerException {
        Game g = new Game("io", "tu");
        int num = 1;
        int numStudInEntrancePrima_Green, numStudInEntranceDopo_Green;
        int numStudInEntrancePrima_Blue, numStudInEntranceDopo_Blue;

        //Cloud c = new Cloud();
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
    public void testDoMoveStudentInIsland() throws MissingPlayerException{
        Game g = new Game("io", "tu");
        int num = 3;
        int green_prima, green_dopo;
        StudentGroup studentGroup = new StudentGroup();

        studentGroup.addStudent(Colour.GREEN); //aggiungiamo uno studente di quel colore per testare


        green_prima = g.getIsland(num).countStudentsOfColour(Colour.GREEN);
        g.getPlayer("io").addStudentsToEntrance(studentGroup);
        g.doMoveStudentInIsland("io", Colour.GREEN, num);

        green_dopo = g.getIsland(num).countStudentsOfColour(Colour.GREEN);

        Assertions.assertEquals(green_dopo, green_prima+1);
    }

    @Test
    public void testGetIsland() {
        Game g = new Game("io", "tu");
        int num = 3;

        Island i = new Island(num);
        Island qualcosaugualea = g.getIsland(num);
        Assertions.assertEquals(i, qualcosaugualea);

        assertThrows(IllegalArgumentException.class, ()->g.getIsland(20));
    }

    @Test
    public void testDoPlayCard() throws MissingCardException, MissingPlayerException {
        Game g = new Game("io", "tu");
        int num = 3;
        int size_prima = g.getPlayer("io").getDeck().size();
        g.doPlayCard("io", num);
        int size_dopo = g.getPlayer("io").getDeck().size();
        Assertions.assertEquals(size_dopo, size_prima - 1);
        Exception exception = assertThrows(MissingCardException.class, ()->g.getPlayer("io").getDeck().getCard(3));
    }

    @Test
    public void testCheckProfessor() throws MissingStudentException, MissingPlayerException, MissingProfessorException {
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
        g.getPlayer("io").addStudentsToEntrance(sg);
        g.doMoveStudentInDiningRoom("io", col);
        g.checkProfessor(col);

        numProf = numProf + g.getPlayer("io").numProfessor();
        Assertions.assertEquals(1, g.getPlayer("io").numStudentsDiningRoom(col));
        Assertions.assertEquals(1, numProf);
        assertEquals(g.getPlayer("io"), g.getProfessor(col).getOwner());

        //verifico che sia dato a tu e tolto ad io se u ha più studenti
        sg.addStudent(col);
        g.getPlayer("tu").addStudentsToEntrance(sg);
        g.doMoveStudentInDiningRoom("tu", col);
        g.doMoveStudentInDiningRoom("tu", col);
        g.checkProfessor(col);

        Assertions.assertEquals(1, g.getPlayer("tu").numProfessor());
        Assertions.assertEquals(0, g.getPlayer("io").numProfessor());
        assertEquals(g.getPlayer("tu"), g.getProfessor(col).getOwner());
    }

    @Test
    public void testCheckTowers() throws MissingIslandException, MissingPlayerException, MissingTowerException {
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
        g.getPlayer("io").addStudentsToEntrance(s1);//aggiungo il gruppo studenti all'entrata di player
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
        g.getPlayer("tu").addStudentsToEntrance(s2);//aggiungo il gruppo studenti all'entrata di player
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
        g.getPlayer("io").addStudentsToEntrance(s3);//aggiungo il gruppo studenti all'entrata di player
        g.getPlayer("io").moveStudentInDiningRoom(col3);//aggiungo 2 studenti alla dinner room di "io"
        g.getPlayer("io").moveStudentInDiningRoom(col3);
        g.checkProfessor(col3);//riassegno il professore in modo che lo abbiamo "io"
        g.getPlayer("tu").addStudentsToEntrance(s3);//aggiungo il gruppo studenti all'entrata di player
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
        g.getPlayer("io").addStudentsToEntrance(s4);//aggiungo il gruppo studenti all'entrata di player
        g.getPlayer("io").moveStudentInDiningRoom(col4);//aggiungo 2 studenti alla dinner room di "io"
        g.getPlayer("io").moveStudentInDiningRoom(col4);
        g.getPlayer("io").getTeam().useTowers(6);//considerando i casi precedeni rimangono 7 orri ad "io"
        g.checkProfessor(col4);//riassegno il professore in modo che lo abbiamo "io"

        Assertions.assertThrows(MissingTowerException.class, ()->g.checkTowers(4));

        //caso di assenza di torri
    }

    @Test
    public void TestGetPlayer()throws MissingPlayerException{
        Game g = new Game("io","tu");

        assertNotNull(g.getPlayer("io"));
        assertTrue(g.getPlayer("io") instanceof Player);
        assertThrows(MissingPlayerException.class, ()->g.getPlayer("giorgio"));
    }

    @Test
    public void TestGetProfessor()throws MissingProfessorException{
        Game g = new Game("io", "tu");
        Colour c = Colour.GREEN;
        assertNotNull(g.getProfessor(c));
        assertTrue(g.getProfessor(c) instanceof Professor);
    }

    @Test public void TestGetBag(){
        Game g = new Game("io","tu");
        assertNotNull(g.getBag());
        assertTrue(g.getBag() instanceof Bag);
    }
}