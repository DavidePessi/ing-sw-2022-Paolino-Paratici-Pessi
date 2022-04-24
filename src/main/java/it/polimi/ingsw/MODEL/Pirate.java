package it.polimi.ingsw.MODEL;

public class Pirate extends ConcreteCharacterCard implements Decorator{
    private Game game;

    public Pirate(Game game) {
        nameCard = "Pirate";
        this.game = game;
    }

    @Override
    public int getPrice() {
        return 3;
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
        game.checkTowers(num);
    }

    @Override
    public void effect(String nickname, int num, Colour colour) throws Exception{
        throw new Exception("Error");
    }
}
