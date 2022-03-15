package MODEL;

public class Professor {
    private Colour typeColour;
    private Player Owner;

    public Professor(Colour typeColour){
        this.typeColour = typeColour;
    }

    void changeOwner(Player owner){
    }

    Player getOwner(){
        return this.Owner;
    }
}
