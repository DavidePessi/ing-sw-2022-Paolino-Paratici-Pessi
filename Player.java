package it.polimi.ingsw.MODEL;

import java.io.PrintWriter;
import java.util.*;

public class Player {
    private String nickname;
    private ColourTower tower;
    private Team team;
    private List<Professor> professors;
    private Deck deck;
    private Card currentCard;
    private DiningRoom diningRoom;
    private Entrance entrance;

    public Player (String nickname, ColourTower tower){
        this.nickname = nickname;
        this.tower = tower;
        diningRoom = new DiningRoom();
        entrance = new Entrance();
        deck = new Deck();
    }

    public Team getTeam(){
        return team;
    }
    public Card getLastPlayedCard(){
        return currentCard;
    }

    public Deck getDeck(){
        return deck;
    }

    public void playCard(int numCard){
        try{
            currentCard = deck.getCard(numCard);
            deck.removeCard(numCard);
        } catch (MissingCardException e){
            e.printStackTrace();
        }
    }

    public void moveStudentInDiningRoom(Colour colour){
        entrance.removeStudent(colour);
        diningRoom.add(colour);
    }

    public void moveStudentInIsland(Colour colour, Island island) {
        try {
            entrance.removeStudent(colour);
            island.addStudent(colour);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }  //Do we want to use the MissingStudentException?
    }

    //add students of the group given at the entrance
    public void addStudentsToEntrances(StudentGroup students) {
        entrance.addGroup(students);
    }

    public String getNicknameClient(){
        return nickname;
    }
}