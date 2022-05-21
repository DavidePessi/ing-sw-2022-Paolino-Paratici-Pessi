package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.MODEL.Exception.MissingStudentException;
import it.polimi.ingsw.MODEL.StudentGroup;

import java.io.Serializable;

public class Jester extends ConcreteCharacterCard implements Decorator, Serializable {

    private Game game;
    private StudentGroup pool;

    public Jester(Game game){
        nameCard = "Jester";
        this.game = game;
        pool = new StudentGroup();
        initialPrice = 1;
        price = 1;
    }

    @Override
    public void initialization()  {
        try {
            for(int i = 0; i < 6; i++) {
                pool.addStudent(game.getBag().pullOut());
            }
        }catch(MissingStudentException e){}

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
    //colour: da pool a entrance
    //colour2: da entrance a pool
    public void effect(String nickname, Colour colour, Colour colour2) throws Exception {
        if(price==initialPrice){
            price = price+1;
        }
        this.pool.removeStudent(colour);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour2);

        this.pool.addStudent(colour2);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour);
    }

    @Override
    //colour, colour2: da pool a entrance
    //colour3, colour4: da entrance a pool
    public void effect(String nickname, Colour colour, Colour colour2, Colour colour3, Colour colour4) throws Exception{
        if(price==initialPrice){
            price = price+1;
        }
        //tolgo gli studenti
        this.pool.removeStudent(colour);
        this.pool.removeStudent(colour2);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour3);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour4);

        //aggiungo gli studenti
        this.pool.addStudent(colour3);
        this.pool.addStudent(colour4);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour2);
    }

    @Override
    //colour, colour2, colour3: da pool a entrance
    //colour4, colour5, colour6: da entrance a pool
    public void effect(String nickname, Colour colour, Colour colour2, Colour colour3, Colour colour4, Colour colour5, Colour colour6) throws Exception{
        if(price==initialPrice){
            price = price+1;
        }
        //tolgo gli studenti
        this.pool.removeStudent(colour);
        this.pool.removeStudent(colour2);
        this.pool.removeStudent(colour3);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour4);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour5);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour6);

        //aggiungo gli studenti
        this.pool.addStudent(colour4);
        this.pool.addStudent(colour5);
        this.pool.addStudent(colour6);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour2);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour3);
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
