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
    private int currentIsland;

    public Game(Player player1, Player player2) {
    }

    public Game(Player player1, Player player2, Player player3) {
    }

    public Game(Player player1, Player player2, Player player3, Player player4) {
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

    public void doMoveMotherNature(int numMovement) {
        motherNature.move(numMovement, listIsland);
    }

    /*public void doMoveStuentInDiningRoom(int idClient, Student student) {
        for (Player player : listPlayer) {
            if (player != null) {
                if (player.idClient == idClient) {
                    try {
                        //elimina lo studente dalla entrance prima di crearne uno nuovo in dining room
                        player.moveStudentInDiningRoom(student);
                    } catch (MissingStudentException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }*/

    public void doMoveStuentInDiningRoom(int idClient, Colour colour) throws MissingStudentException {
        for (Player player : listPlayer) {
            if (player != null) {
                if (player.idClient == idClient) {
                    player.moveStudentInDiningRoom(colour);
                }

            } else {
                throw new MissingStudentException("Student not found");
            }
        }
    }

    //search the id of the player in the player list
    //once is done it calls the method that add the students from the group to the entrance
    //passing as parameter the studentGroup in the cloud that we want to take
    public void doTakeCloud(int idClient, int numCloud) {
        for (Player player : listPlayer) {
            if (player != null) {
                if (player.idClient == idClient) {
                    player.addStudentsToEntrances(listCloud.get(numCloud).getStudents());
                }
            }
        }
    }

    public void doMoveStudentInIsland(int idClient, Colour colour, int numIsland) {
        for (Player player : listPlayer) {

            if (player.idClient == idClient) {
                player.moveStudentInIsland(colour, this.getIsland(numIsland));
            }
        }
    }

    public Island getIsland(int numIsland) throws IllegalArgumentException {
        Island island = null;
        if (numIsland < 0 || numIsland > listIsland.size()) {
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < listIsland.size(); i++) {

                if (listIsland.get(i).numIsland == numIsland) {
                    island = listIsland.get(i);
                }
            }
        }
        return island;


    }
}

