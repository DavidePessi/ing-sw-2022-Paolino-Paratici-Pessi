package it.polimi.ingsw.MODEL;

public class Woman extends ConcreteCharacterCard implements Decorator{
    private Game game;
    private StudentGroup pool;

    public Woman(Game game){
        nameCard = "Woman";
        this.game = game;
    }

    @Override
    public int getPrice() {
        return 3;
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
            pool.removeStudent(colour);
            try {
                game.getPlayer(nickname).addStudentToDiningRoom(colour);
                pool.addStudent(game.getBag().pullOut());
            }catch(MissingPlayerException e){}
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
}
