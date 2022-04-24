package it.polimi.ingsw.MODEL;

public class Wizard extends ConcreteCharacterCard implements Decorator{
    private Game game;

    public Wizard(Game game) {
        nameCard = "Wizard";
        this.game = game;
    }

    @Override
    public int getPrice() {
        return 3;
    }

    @Override
    public void initialization() {

    }

    @Override
    public void effect(String nickname)throws Exception{
        throw new Exception("Error");
    }

    @Override
    public void effect(String nickname, Colour colour) {
        //TODO effeto da guardare, serve un iteratore per i player che non posso chiamare al momento
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
