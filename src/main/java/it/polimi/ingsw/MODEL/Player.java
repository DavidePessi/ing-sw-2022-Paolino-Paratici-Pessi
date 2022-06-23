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

    /**
     * returns the team of that player
     * @return
     */
    public Team getTeam(){
        return team;
    }

    /**
     * returns the number of coin that the player have
     * @return
     */
    public int getNumCoins(){
        return numCoins;
    }

    /**
     * returns the last played card by the player
     * @return
     * @throws MissingCardException
     */
    public Card getLastPlayedCard() throws MissingCardException{
        if(this.currentCard == null) throw new MissingCardException();
        else return currentCard;
    }

    /**
     * increment the number of coin that player have
     */
    public void receiveCoin(){
        numCoins++;
    }

    /**
     * decrement the number of coin
     * @param coins
     * @throws Exception
     */
    public void useCoins(int coins) throws Exception{
        if(numCoins >= coins) {
            numCoins = numCoins - coins;
        }
        else{
            throw new Exception();
        }

    }

    /**
     * returns the deck of the player
     * @return
     */
    public Deck getDeck(){
        return deck;
    }

    /**
     * returns the nickname of the player
     * @return
     */
    public String getNicknameClient(){
        return nickname;
    }

    /**
     * returns the entrance of the player
     * @return
     */
    public Entrance getEntrance(){return entrance;}

    /**
     * return the number of the professor that player have
     * @return
     */
    public int numProfessor(){
        return professors.size();
    }

    /**
     * the player choose a card to play
     * @param numCard
     * @throws MissingCardException
     * @throws PossibleWinException
     */
    public void playCard(int numCard)throws MissingCardException, PossibleWinException {
        currentCard = deck.getCard(numCard);
        deck.removeCard(numCard);

    }

    /**
     * move student from entrance to dining room
     * @param colour
     * @throws Exception
     */
    public void moveStudentInDiningRoom(Colour colour) throws Exception{
        this.removeStudentFromEntrance(colour);
        this.addStudentToDiningRoom(colour);
    }

    /**
     * move student from entrance to island
     * @param colour
     * @param island
     * @throws MissingStudentException
     */
    public void moveStudentInIsland(Colour colour, Island island) throws MissingStudentException{

        entrance.removeStudent(colour);
        island.addStudent(colour);
    }

    /**
     * add students of the group given at the entrance
     * @param students
     */
    public void addStudentsToEntrance(StudentGroup students) {
        entrance.addGroup(students);
    }

    /**
     * remove student from the entrance
     * @param colour
     * @throws MissingStudentException
     */
    public void removeStudentFromEntrance(Colour colour) throws MissingStudentException {
        entrance.removeStudent(colour);
    }

    /**
     * remove student from dining room
     * @param colour
     * @throws MissingStudentException
     */
    public void removeStudentFromDiningRoom(Colour colour)throws MissingStudentException{this.diningRoom.remove(colour);}

    /**
     * add student to dining room
     * @param colour
     * @throws Exception
     */
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

    /**
     * remove the professor to professor's list
     * @param professor
     */
    public void removeProfessor(Professor professor){
        professors.remove(professor);
    }

    /**
     * add a professor to professor's list
     * @param professor
     */
    public void addProfessor(Professor professor){
        professors.add(professor);
    }

    /**
     * count the students in dining room with that colour
     * @param colour
     * @return
     */
    public int numStudentsDiningRoom(Colour colour){
        return diningRoom.numStudents(colour);
    }

    /**
     * It is used to allow the first player who launches the card to launch
     * a card played by another player in the previous round
     */
    public void setLastPlayedCardZero() {
        this.currentCard = new Card(0,0);
    }

    /**
     * returns true is that professor is present on the board
     * @param colour
     * @return
     */
    public boolean professorPresent(Colour colour){
        for(Professor professor : professors){
            if(professor.getColour().equals(colour)){
                return true;
            }
        }
        return false;
    }
}