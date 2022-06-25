package it.polimi.ingsw.MODEL.CharacterCards;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.MODEL.Exception.MissingStudentException;
import it.polimi.ingsw.MODEL.StudentGroup;

import java.io.Serializable;

public class Jester extends ConcreteCharacterCard implements Decorator, Serializable {

    private transient Game game;
    private StudentGroup pool;

    public Jester(Game game){
        nameCard = "Jester";
        this.game = game;
        pool = new StudentGroup();
        initialPrice = 1;
        price = 1;
        initialization();
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
    //colour: from pool to entrance
    //colour2: from entrance to pool
    public void effect(String nickname, Colour colour, Colour colour2) throws Exception {
        if(price==initialPrice){
            price = price+1;
        }
        this.pool.removeStudent(colour);
        this.game.getPlayer(nickname).removeStudentFromEntrance(colour2);

        this.pool.addStudent(colour2);
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.addStudent(colour);
        this.game.getPlayer(nickname).addStudentsToEntrance(studentGroup);
    }

    @Override
    //colour, colour2: from pool to entrance
    //colour3, colour4: from entrance to pool
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
        StudentGroup studentGroup = new StudentGroup();
        StudentGroup studentGroup1 = new StudentGroup();
        studentGroup.addStudent(colour);
        studentGroup1.addStudent(colour2);
        this.game.getPlayer(nickname).addStudentsToEntrance(studentGroup);
        this.game.getPlayer(nickname).addStudentsToEntrance(studentGroup1);
    }

    @Override
    //colour, colour2, colour3: from pool to entrance
    //colour4, colour5, colour6: from entrance to pool
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

        StudentGroup studentGroup = new StudentGroup();
        StudentGroup studentGroup1 = new StudentGroup();
        StudentGroup studentGroup2 = new StudentGroup();
        studentGroup.addStudent(colour);
        studentGroup1.addStudent(colour2);
        studentGroup2.addStudent(colour3);
        this.game.getPlayer(nickname).addStudentsToEntrance(studentGroup);
        this.game.getPlayer(nickname).addStudentsToEntrance(studentGroup1);
        this.game.getPlayer(nickname).addStudentsToEntrance(studentGroup2);
    }

    @Override
    public void effect(String nickname, int num) throws Exception{
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, int num, Colour colour)  throws Exception{
        throw new Exception("Error");
    }

    /**
     * returns the pull of the jester
     * @return
     */
    public StudentGroup getPool(){
        return this.pool;
    }
}
