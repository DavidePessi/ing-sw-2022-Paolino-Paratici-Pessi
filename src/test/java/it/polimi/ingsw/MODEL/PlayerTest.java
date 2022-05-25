package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingCardException;
import it.polimi.ingsw.MODEL.Exception.MissingPlayerException;
import it.polimi.ingsw.MODEL.Exception.PossibleWinException;
import junit.framework.TestCase;

public class PlayerTest extends TestCase {

    public void testGetTeam()throws MissingPlayerException {
        Game g = new Game("io", "tu");
        Team t = g.getPlayer("io").getTeam();
        assertTrue(t instanceof Team);
    }

    public void testGetLastPlayedCard()throws MissingPlayerException, MissingCardException, PossibleWinException {
        Game g = new Game("io", "tu");
        g.getPlayer("io").playCard(3);
        Card c = g.getPlayer("io").getLastPlayedCard();

        assertEquals(3, c.getValue());
    }

    public void testGetDeck()throws  MissingPlayerException {
        Game g = new Game("io", "tu");
        Deck d = g.getPlayer("io").getDeck();
        assertTrue(d instanceof Deck);
    }

    public void testGetNicknameClient() throws MissingPlayerException{
        Game g = new Game("io", "tu");
        assertEquals("io", g.getPlayer("io").getNicknameClient());
    }

    public void testGetEntrance() throws MissingPlayerException{
        Game g = new Game("io", "tu");
        Entrance e = g.getPlayer("io").getEntrance();
        assertTrue(e instanceof Entrance);
    }

    public void testNumProfessor() {
        Team t = new Team(ColourTower.BLACK, 8);
        Player p = new Player("io",t);
        int num = 3;

        for(int i = 0; i < num; i++){
            p.addProfessor(new Professor(Colour.BLUE));
        }

        assertEquals(num,p.numProfessor());
    }

    public void testPlayCard() throws MissingPlayerException, MissingCardException, PossibleWinException{
        Game g = new Game("io", "tu");
        int num = 3;
        int numCardInDeck_Prima, numCardInDeck_Dopo;

        numCardInDeck_Prima = g.getPlayer("io").getDeck().size();
        g.getPlayer("io").playCard(num);
        numCardInDeck_Dopo = g.getPlayer("io").getDeck().size();

        assertEquals(numCardInDeck_Dopo, numCardInDeck_Prima-1);
    }

    public void testMoveStudentInDiningRoom() throws Exception{

        Team t = new Team(ColourTower.BLACK,8);
        Player pl = new Player("io", t);
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.addStudent(Colour.GREEN); //aggiungiamo uno studente di quel colore per testare
        pl.addStudentsToEntrance(studentGroup);

        int numPrimaStudentiInDiningRoom = pl.numStudentsDiningRoom(Colour.GREEN);
        pl.moveStudentInDiningRoom(Colour.GREEN);

        int numDopoStudentiInDiningRoom = pl.numStudentsDiningRoom(Colour.GREEN);
        assertEquals(numDopoStudentiInDiningRoom, numPrimaStudentiInDiningRoom+1);
    }

    public void testMoveStudentInIsland() throws Exception{
        Team t = new Team(ColourTower.BLACK, 8);
        Player p = new Player("io",t);
        Island i = new Island(1);
        StudentGroup s = new StudentGroup();
        Colour c = Colour.BLUE;

        s.addStudent(c);

        p.addStudentsToEntrance(s);
        p.moveStudentInIsland(c,i);

        assertEquals(0,p.numStudentsDiningRoom(c));
        assertEquals(1, i.countStudentsOfColour(c));
    }

    public void testAddStudentsToEntrances() {
        Team t = new Team(ColourTower.BLACK,8);
        Player pl = new Player("io", t);
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.addStudent(Colour.GREEN);
        studentGroup.addStudent(Colour.BLUE);
        studentGroup.addStudent(Colour.YELLOW);

        Entrance oldEntrance = pl.getEntrance();
        pl.addStudentsToEntrance(studentGroup);
        Entrance newEntrance = pl.getEntrance();

        oldEntrance.addGroup(studentGroup);
        assertEquals(newEntrance, oldEntrance);
    }

    public void testRemoveProfessor() {
        Team t = new Team(ColourTower.BLACK, 8);
        Player p = new Player("io",t);
        Colour c = Colour.GREEN;
        Professor prof = new Professor(c);

        p.addProfessor(prof);
        p.removeProfessor(prof);

        assertEquals(0, p.numProfessor());
    }

    public void testAddProfessor() {
        Team t = new Team(ColourTower.BLACK, 8);
        Player p = new Player("io",t);
        Colour c = Colour.GREEN;
        Professor prof = new Professor(c);

        p.addProfessor(prof);
        assertEquals(1, p.numProfessor());
    }

    public void testNumStudentsDiningRoom() throws Exception{
        Team t = new Team(ColourTower.BLACK, 8);
        Player p = new Player("io",t);
        StudentGroup s = new StudentGroup();
        Colour c = Colour.GREEN;

        s.addStudent(c);

        p.addStudentsToEntrance(s);
        p.moveStudentInDiningRoom(c);

        assertEquals(1,p.numStudentsDiningRoom(c));
    }
}