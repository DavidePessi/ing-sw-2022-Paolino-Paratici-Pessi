package it.polimi.ingsw.MODEL;

import java.util.*;

public class Player {
    public int idClient;
    private ColourTower tower;
    private List<Professor> professors;
    private Deck deck;
    private Card currentCard;
    //private int[] diningRoom;
    //private int[] entrance;
    private DiningRoom diningRoom;
    private Entrance entrance;

    public Player createPlayer(Colour tower){return this;}
    public void playCard(Card card){};
    public Player getLastPlayedCard(){return this;}

    public void moveStudentToDiningRoom(Colour colour) {
        try{
            entrance.removeStudent(colour);
        }catch (MissingStudentException());
    }
}