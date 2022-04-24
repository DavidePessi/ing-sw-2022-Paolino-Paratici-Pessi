package it.polimi.ingsw.MODEL;

public class Minstrell extends ConcreteCharacterCard implements Decorator{

    private Game game;

    public Minstrell(Game game){
        nameCard = "Minstrell";
        this.game = game;
    }

    @Override
    public int getPrice() {
        return 1;
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
    //colour: da Entrance a DiningRoom
    //colour2: da DiningRoom a Entrance
    public void effect(String nickname, Colour colour, Colour colour2) throws Exception {
        //tolgo gli studenti
        this.game.getPlayer(nickname).removeStudentFromDiningRoom(colour);
        this.game.getPlayer(nickname).removeStudentFromEntrances(colour2);

        StudentGroup temp = new StudentGroup();
        temp.addStudent(colour);
        //aggiungo gli studenti
        this.game.getPlayer(nickname).addStudentToDiningRoom(colour2);
        this.game.getPlayer(nickname).addStudentsToEntrances(temp);
    }

    @Override
    //colour, colour2: da Entrance a DiningRoom
    //colour3, colour4: da DiningRoom a Entrance
    public void effect(String nickname, Colour colour, Colour colour2, Colour colour3, Colour colour4) throws Exception{
        //tolgo gli studenti
        this.game.getPlayer(nickname).removeStudentFromDiningRoom(colour);
        this.game.getPlayer(nickname).removeStudentFromDiningRoom(colour2);
        this.game.getPlayer(nickname).removeStudentFromEntrances(colour3);
        this.game.getPlayer(nickname).removeStudentFromEntrances(colour4);

        StudentGroup temp = new StudentGroup();
        temp.addStudent(colour);
        temp.addStudent(colour2);

        //aggiungo gli studenti
        this.game.getPlayer(nickname).addStudentToDiningRoom(colour3);
        this.game.getPlayer(nickname).addStudentToDiningRoom(colour4);
        this.game.getPlayer(nickname).addStudentsToEntrances(temp);
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
