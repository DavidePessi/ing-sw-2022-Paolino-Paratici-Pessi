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

    public void moveStudentInDiningRoom(Colour colour) {
        try{
            entrance.removeStudent(colour);
        }catch (MissingStudentException e){};
    }

    public void moveStudentInIsland(Colour colour, Island island) {
        try {
            entrance.removeStudent(colour);
            island.addStudent(colour);
        } catch (IllegalArgumentException e) {}  //Do we want to use the MissingStudentException?
    }
}