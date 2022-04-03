package it.polimi.ingsw.MODEL;

import java.util.*;

public class Player {
    private int idClient; //it's so dangerous!
    private ColourTower tower;
    private List<Professor> professors;
    private Deck deck;
    private Card currentCard;
    private DiningRoom diningRoom;
    private Entrance entrance;

    public Player (int idClient, ColourTower tower){
        this.idClient = idClient;
        this.tower = tower;
        this.deck = new Deck();
        this.diningRoom = new DiningRoom();
        this.entrance = new Entrance();
    }

    public Card getLastPlayedCard(){
        return currentCard;
    }

    public void playCard(int numCard) {
        try {
            currentCard = deck.getCard(numCard);
            deck.removeCard(numCard);
        } catch (MissingCardException e) {
            System.out.println("This card number is not valid, this card is not in the deck!");
        }
    }

    /*public void moveStudentInDiningRoom(Student student) throws MissingStudentException {
        if(student.getColour()==Colour.BLUE || student.getColour()==Colour.GREEN || student.getColour()==Colour.PINK || student.getColour()==Colour.RED || student.getColour()==Colour.YELLOW) {
            entrance.removeStudent(student);
        }
        else throw new MissingStudentException("Student not found");
    }*/

    public void moveStudentInDiningRoom(Colour colour){
        entrance.removeStudent(colour);
        diningRoom.add(colour);
    }

    public void moveStudentInIsland(Colour colour, Island island) {
        try {
            entrance.removeStudent(colour);
            island.addStudent(colour);
        } catch (IllegalArgumentException e) {}  //Do we want to use the MissingStudentException?
    }

    //add students of the group given at the entrance
    public void addStudentsToEntrances(StudentGroup students) {
        entrance.addGroup(students);
    }

    public int getIdClient(){
        return idClient;
    }
}