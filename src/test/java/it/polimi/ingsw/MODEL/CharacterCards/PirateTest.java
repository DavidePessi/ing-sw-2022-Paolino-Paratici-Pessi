package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.CharacterParameters;
import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Exception.MissingIslandException;
import it.polimi.ingsw.MODEL.Exception.MissingPlayerException;
import it.polimi.ingsw.MODEL.Exception.MissingTowerException;
import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.MODEL.StudentGroup;
import junit.framework.TestCase;

public class PirateTest extends TestCase {

    public void testEffect() throws Exception{
        Game game = new Game("gio", "ila");
        CharacterParameters cp = new CharacterParameters("tu", "Pirate", 3);
        //TODO dobbiamo verificare che esiste poi qualcosa che ci controlla: che "tu" sia nel game, che "tu" stia giocando in quel momento
        ConcreteCharacterCard cc = new Pirate(game);
        game.addCharacterCard(cc);

        StudentGroup sg_io = new StudentGroup();
        sg_io.addStudent(Colour.RED);
        sg_io.addStudent(Colour.RED);
        sg_io.addStudent(Colour.RED);
        sg_io.addStudent(Colour.RED);
        sg_io.addStudent(Colour.RED);
        sg_io.addStudent(Colour.RED);
        sg_io.addStudent(Colour.RED);
        try {
            game.getPlayer("ila").getEntrance().addGroup(sg_io);
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        }

        StudentGroup sg_tu = new StudentGroup();
        sg_tu.addStudent(Colour.GREEN);
        sg_tu.addStudent(Colour.GREEN);
        sg_tu.addStudent(Colour.GREEN);
        sg_tu.addStudent(Colour.GREEN);
        sg_tu.addStudent(Colour.GREEN);
        sg_tu.addStudent(Colour.GREEN);
        sg_tu.addStudent(Colour.GREEN);
        try {
            game.getPlayer("gio").getEntrance().addGroup(sg_tu);
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        }

        try {
            game.getPlayer("gio").playCard(6);
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        }
        try {
            game.getPlayer("ila").playCard(8);
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        }
        try {
            game.getPlayer("gio").moveStudentInDiningRoom(Colour.GREEN);
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        }
        try {
            game.getPlayer("gio").moveStudentInIsland(Colour.GREEN, game.getIsland(3));
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            game.getPlayer("gio").moveStudentInIsland(Colour.GREEN, game.getIsland(3));
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        game.checkProfessor(Colour.GREEN);


        game.doMoveMotherNature(3);
        try {
            game.checkTowers(3);
        } catch (MissingIslandException e) {
            e.printStackTrace();
        } catch (MissingTowerException e) {
            e.printStackTrace();
        }

        try {
            game.getPlayer("ila").moveStudentInDiningRoom(Colour.RED);
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        }
        try {
            game.getPlayer("ila").moveStudentInIsland(Colour.RED, game.getIsland(3));
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            game.getPlayer("ila").moveStudentInIsland(Colour.RED, game.getIsland(3));
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            game.getPlayer("ila").moveStudentInIsland(Colour.RED, game.getIsland(3));
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            game.getPlayer("ila").moveStudentInIsland(Colour.RED, game.getIsland(3));
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            game.getPlayer("ila").moveStudentInIsland(Colour.RED, game.getIsland(3));
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        game.checkProfessor(Colour.RED);

        try {
            game.doPlayCharacterCard(cp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*System.out.println(game.getIsland(3).getColourTower());
        System.out.println(game.getPlayer("ila").getTeam().getColourTower());
        System.out.println(game.getIsland(3).countStudentsOfColour(Colour.RED));
        System.out.println(game.getIsland(3).countStudentsOfColour(Colour.GREEN));
        System.out.println(game.getProfessor(Colour.GREEN).getOwner().getNicknameClient());
        System.out.println(game.getProfessor(Colour.RED).getOwner().getNicknameClient());*/

        try {
            assertEquals(game.getPlayer("ila").getTeam().getColourTower(), game.getIsland(3).getColourTower());
        } catch (MissingPlayerException e) {
            e.printStackTrace();
        } catch (MissingTowerException e) {
            e.printStackTrace();
        }
    }
}