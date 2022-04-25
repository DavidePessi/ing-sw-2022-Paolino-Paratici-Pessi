package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.MODEL.Exception.MissingStudentException;
import it.polimi.ingsw.MODEL.StudentGroup;

public class Priest extends ConcreteCharacterCard implements Decorator {
    private Game game;
    private StudentGroup pool;

    public Priest(Game game) {
        nameCard = "Priest";
        this.game = game;
        initialPrice = 1;
        price = 1;
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
        throw new Exception("Error");
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
    public void effect(String nickname, int num, Colour colour)  {
        if(price==initialPrice){
            price = price+1;
        }
        pool.removeStudent(colour);
        game.getIsland(num).addStudent(colour);

        try {
            pool.addStudent(game.getBag().pullOut());
        }catch(MissingStudentException e){}
    }
}
