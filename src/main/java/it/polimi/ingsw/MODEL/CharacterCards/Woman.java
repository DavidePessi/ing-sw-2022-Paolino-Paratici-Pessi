package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.MODEL.Exception.MissingPlayerException;
import it.polimi.ingsw.MODEL.Exception.MissingStudentException;
import it.polimi.ingsw.MODEL.Exception.PossibleWinException;

import java.io.Serializable;

public class Woman extends ConcreteCharacterCard implements Decorator, Serializable {
    private Game game;
    private StudentGroup pool;

    public Woman(Game game){
        nameCard = "Woman";
        this.game = game;
        initialPrice = 3;
        price = 3;
        pool = new StudentGroup();
    }

    @Override
    public void initialization()  {
        try {
            for(int i = 0; i < 4; i++) {
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
        if(pool.size() == 0){throw new Exception("mancanza di studenti");}

        else{
            try {
                pool.removeStudent(colour);
            }catch(IllegalArgumentException e){
                throw new Exception("studente non presente sulla carta personaggio");
            }
            try {
                game.getPlayer(nickname).addStudentToDiningRoom(colour);
                pool.addStudent(game.getBag().pullOut());
            }catch(MissingPlayerException e){
            } catch(MissingStudentException e){
                throw new PossibleWinException();
            }
        }

        if(price==initialPrice){
            price = price+1;
        }

    }

    @Override
    public void effect(String nickname, Colour colour, Colour colour2) throws Exception {
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, Colour colour, Colour colour2, Colour colour3, Colour colour4) throws Exception{
        throw new Exception("Error");
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

    public StudentGroup getPool(){
        return this.pool;
    }
}
