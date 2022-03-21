package it.polimi.ingsw.MODEL;

import java.util.*;

public class Game {
    private int numPlayer;
    private Player lastFirstPlayer;
    private List<Cloud> listCloud;
    private List<Player> listPlayer;
    private List<Island> listIsland;
    private Bag bag;
    private MotherNature motherNature;
    private List<Professor> professors;
    private int currentIslad;

    public Game game(Player player1, Player player2) {
        return this;
    }

    public Game game(Player player1, Player player2, Player player3) {
        return this;
    }

    public Game game(Player player1, Player player2, Player player3, Player player4) {
        return this;
    }

    public void startGame() {
    }

    public boolean checkWin() {
        /*if the game is finished and there's a winner return True, else return False*/
        return false;
    }

    public Team theWinnerIs() {
        return null;
    }

    public void doMoveMotherNature(int numIsland){
        motherNature.move();
    }

    public void doMoveStuentToDiningRoom(int idClient, Colour colour) {
        for(int i=0; i<listPlayer.size(); i++)
        {
            if (listPlayer.get(i) != null ){
                if(listPlayer.get(i).idClient == idClient){
                    listPlayer.get(i).moveStudentToDiningRoom(colour);
                }

            }
        }
    }

    //search the id of the player in the player list
    //once is done it calls the method that add the students from the group to the entrance
    //passing as parameter the studentGroup in the cloud that we want to take
    public void doTakeCloud(int idClient, int numCloud) {
            try{
                    for(int i=0; i<listPlayer.size(); i++){
                        if(listPlayer.get(i) != null){
                            if(listPlayer.get(i).idClient == idClient){
                                listPlayer.get(i).addStudentsToEntrances(listCloud.get(numCloud).getStudents());
                            }
                        }
                    }
            } catch (MissingCloudException e) {

            }
    }
}

