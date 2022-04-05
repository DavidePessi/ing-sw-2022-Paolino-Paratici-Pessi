package it.polimi.ingsw.MODEL;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Player {
    private String nickname;
    private Team team;
    private List<Professor> professors;
    private Deck deck;
    private Card currentCard;
    private DiningRoom diningRoom;
    private Entrance entrance;

    public Player (String nickname, Team team){
        this.nickname = nickname;
        this.team = team;
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

    public DiningRoom getDiningRoom(){
        return diningRoom;
    }

    public int numProfessor(){
        return professors.size();
    }

    public void playCard(int numCard){
        try{
            currentCard = deck.getCard(numCard);
            deck.removeCard(numCard);
        } catch (MissingCardException e){
            System.out.println("This card number is not valid, this card is not in the deck!");
        }
    }

    public void moveStudentInDiningRoom(Colour colour){
        entrance.removeStudent(colour);
        diningRoom.add(colour);
    }

    public void moveStudentInIsland(Colour colour, Island island){
        try {
            entrance.removeStudent(colour);
            island.addStudent(colour);
        } catch (IllegalArgumentException e) {
            System.out.println("You can't move the student with this color in the Island");
        }  //Do we want to use the MissingStudentException?
    }

    //add students of the group given at the entrance
    public void addStudentsToEntrances(StudentGroup students) {
        entrance.addGroup(students);
    }

    public String getNicknameClient(){
        return nickname;
    }

    //rimuove il professore dalla lista dei professori
    public void removeProfessor(Professor professor){
        professors.remove(professor);
    }

    //aggiunge un professore alla lista dei professori
    public void addProfessor(Professor professor){
        professors.add(professor);
    }

    //conta il numero di studenti posizionati in DinnerRoom del colore colour
    public int NumStudents(Colour colour){
        return this.diningRoom.NumStudents(colour);
    }
}