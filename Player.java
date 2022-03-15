package it.polimi.ingsw;

import java.util.*;

public class Player {
    private ColourTower tower;
    private List<Professor> professors;
    private Deck deck;
    private Card currentCard;
    private int[] diningRoom;
    private int[] entrance;

    public Player createPlayer(Colour tower){return this;}
    public void playCard(Card card){};
    public Player getLastPlayedCard(){return this;}

}