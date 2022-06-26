package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.CharacterCards.ConcreteCharacterCard;
import it.polimi.ingsw.MODEL.CharacterCards.Decorator;
import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.MODEL.StudentGroup;

import java.io.Serializable;

public class Minstrell extends ConcreteCharacterCard implements Decorator, Serializable {

    private transient Game game;

    public Minstrell(Game game){
        nameCard = "Minstrell";
        this.game = game;
        initialPrice = 1;
        price = 1;
        initialization();
    }

    @Override
    public void initialization()  {
    }

    @Override
    public void effect(String nickname)throws Exception{
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, Colour colour) throws Exception{
        throw new Exception("Error");
    }

    @Override
    //colour: from Entrance to DiningRoom
    //colour2: from DiningRoom to Entrance
    public void effect(String nickname, Colour colour, Colour colour2) throws Exception {
        if(price==initialPrice){
            price = price+1;
        }
        //tolgo gli studenti

        this.game.getPlayer(nickname).removeStudentFromDiningRoom(colour);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour2);

        StudentGroup temp = new StudentGroup();
        temp.addStudent(colour);
        //aggiungo gli studenti
        this.game.getPlayer(nickname).addStudentToDiningRoom(colour2);
        this.game.getPlayer(nickname).addStudentsToEntrance(temp);
    }

    @Override
    //colour, colour2: from Entrance to DiningRoom
    //colour3, colour4: from DiningRoom to Entrance
    public void effect(String nickname, Colour colour, Colour colour2, Colour colour3, Colour colour4) throws Exception{
        if(price==initialPrice){
            price = price+1;
        }

        //tolgo gli studenti
        this.game.getPlayer(nickname).removeStudentFromDiningRoom(colour);
        this.game.getPlayer(nickname).removeStudentFromDiningRoom(colour2);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour3);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour4);

        StudentGroup temp = new StudentGroup();
        temp.addStudent(colour);
        temp.addStudent(colour2);

        //aggiungo gli studenti
        this.game.getPlayer(nickname).addStudentToDiningRoom(colour3);
        this.game.getPlayer(nickname).addStudentToDiningRoom(colour4);
        this.game.getPlayer(nickname).addStudentsToEntrance(temp);
    }

    @Override

    public void effect(String nickname, Colour colour, Colour colour2, Colour colour3, Colour colour4, Colour colour5, Colour colour6) throws Exception{
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, int num) throws Exception{
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, int num, Colour colour)  throws Exception{
        throw new Exception("Error");
    }
}
