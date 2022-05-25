package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingCardException;
import it.polimi.ingsw.MODEL.Exception.MissingStudentException;
import it.polimi.ingsw.MODEL.Exception.PossibleWinException;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
    private String nickname;
    private Team team;
    private List<Professor> professors;
    private Deck deck;
    private Card currentCard;
    private DiningRoom diningRoom;
    private Entrance entrance;
    private int numCoins;

    public Player (String nickname, Team team){
        this.nickname = nickname;
        this.team = team;
        diningRoom = new DiningRoom();
        entrance = new Entrance();
        deck = new Deck();
        professors = new ArrayList<>();
        numCoins = 0;
    }

    public Team getTeam(){
        return team;
    }

    public int getNumCoins(){
        return numCoins;
    }

    public Card getLastPlayedCard() throws MissingCardException{
        if(this.currentCard == null) throw new MissingCardException();
        else return currentCard;
    }

    public void receiveCoin(){
        numCoins++;
    }

    public void useCoins(int coins) throws Exception{
        if(numCoins >= coins) {
            numCoins = numCoins - coins;
        }
        else{
            throw new Exception();
        }

    }

    public Deck getDeck(){
        return deck;
    }

    public String getNicknameClient(){
        return nickname;
    }

    public Entrance getEntrance(){return entrance;}

    public int numProfessor(){
        return professors.size();
    }

    public void playCard(int numCard)throws MissingCardException, PossibleWinException {
        currentCard = deck.getCard(numCard);
        deck.removeCard(numCard);

    }

    public void moveStudentInDiningRoom(Colour colour) throws Exception{
        this.removeStudentFromEntrance(colour);
        this.addStudentToDiningRoom(colour);
    }

    public void moveStudentInIsland(Colour colour, Island island) throws MissingStudentException{

        entrance.removeStudent(colour);
        island.addStudent(colour);
    }

    //add students of the group given at the entrance
    public void addStudentsToEntrance(StudentGroup students) {
        entrance.addGroup(students);
    }

    //remove students from the entrance
    public void removeStudentFromEntrance(Colour colour) throws MissingStudentException {
        entrance.removeStudent(colour);
    }

    public void removeStudentFromDiningRoom(Colour colour)throws MissingStudentException{this.diningRoom.remove(colour);}
    //remove students from the entrance
    public void addStudentToDiningRoom(Colour colour) throws Exception{
        if(diningRoom.numStudents(colour) == 10){
            throw new Exception("numero massimo di studenti per entrance raggiunto");
        }
        else {
            diningRoom.add(colour);
            if (diningRoom.numStudents(colour) == 3 || diningRoom.numStudents(colour) == 6 || diningRoom.numStudents(colour) == 9) {
                this.receiveCoin();
            }
        }
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
    public int numStudentsDiningRoom(Colour colour){
        return diningRoom.numStudents(colour);
    }

    //serve per permettere al primo giocatore che lancia la carta di lanciare una carta giocata da un altro giocatore nel turno precedente
    public void setLastPlayedCardZero() {
        this.currentCard = new Card(0,0);
    }

    public boolean professorPresent(Colour colour){
        for(Professor professor : professors){
            if(professor.getColour().equals(colour)){
                return true;
            }
        }
        return false;
    }
}