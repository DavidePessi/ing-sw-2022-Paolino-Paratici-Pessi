package it.polimi.ingsw.MODEL;

import java.util.*;

// TODO: 22/03/2022 aggiungere una variabile per fare il modulo

public class Game {
    private int numPlayer;
    private Player lastFirstPlayer;
    private List<Cloud> listCloud;
    private List<Player> listPlayer;
    private List<Island> listIsland;
    private Bag bag;
    private MotherNature motherNature;
    private List<Professor> professors;
    //private int currentIsland;

    public Game(int idClient1, int idClient2) {
        listPlayer.add(new Player(idClient1, ColourTower.BLACK));
        listPlayer.add(new Player(idClient2, ColourTower.WHITE));

        listCloud.add(new Cloud());
        listCloud.add(new Cloud());

        bag = new Bag();

        listIsland.add(new Island(0));
        listIsland.add(new Island(1));
        listIsland.add(new Island(2));
        listIsland.add(new Island(3));
        listIsland.add(new Island(4));
        listIsland.add(new Island(5));
        listIsland.add(new Island(6));
        listIsland.add(new Island(7));
        listIsland.add(new Island(8));
        listIsland.add(new Island(9));
        listIsland.add(new Island(10));
        listIsland.add(new Island(11));

        motherNature = new MotherNature(listIsland.get(0));

        professors.add(new Professor(Colour.BLUE));
        professors.add(new Professor(Colour.GREEN));
        professors.add(new Professor(Colour.PINK));
        professors.add(new Professor(Colour.RED));
        professors.add(new Professor(Colour.YELLOW));
    }

    public Game(int idClient1, int idClient2, int idClient3) {
        listPlayer.add(new Player(idClient1, ColourTower.BLACK));
        listPlayer.add(new Player(idClient2, ColourTower.WHITE));
        listPlayer.add(new Player(idClient3, ColourTower.GREY));

        listCloud.add(new Cloud());
        listCloud.add(new Cloud());
        listCloud.add(new Cloud());

        bag = new Bag();

        listIsland.add(new Island(0));
        listIsland.add(new Island(1));
        listIsland.add(new Island(2));
        listIsland.add(new Island(3));
        listIsland.add(new Island(4));
        listIsland.add(new Island(5));
        listIsland.add(new Island(6));
        listIsland.add(new Island(7));
        listIsland.add(new Island(8));
        listIsland.add(new Island(9));
        listIsland.add(new Island(10));
        listIsland.add(new Island(11));

        motherNature = new MotherNature(listIsland.get(0));

        professors.add(new Professor(Colour.BLUE));
        professors.add(new Professor(Colour.GREEN));
        professors.add(new Professor(Colour.PINK));
        professors.add(new Professor(Colour.RED));
        professors.add(new Professor(Colour.YELLOW));

    }

    public Game(int idClient1, int idClient2, int idClient3, int idClient4) {
        listPlayer.add(new Player(idClient1, ColourTower.BLACK));
        listPlayer.add(new Player(idClient2, ColourTower.WHITE));
        listPlayer.add(new Player(idClient3, ColourTower.BLACK));
        listPlayer.add(new Player(idClient4, ColourTower.WHITE));


        listCloud.add(new Cloud());
        listCloud.add(new Cloud());
        listCloud.add(new Cloud());
        listCloud.add(new Cloud());


        bag = new Bag();

        listIsland.add(new Island(0));
        listIsland.add(new Island(1));
        listIsland.add(new Island(2));
        listIsland.add(new Island(3));
        listIsland.add(new Island(4));
        listIsland.add(new Island(5));
        listIsland.add(new Island(6));
        listIsland.add(new Island(7));
        listIsland.add(new Island(8));
        listIsland.add(new Island(9));
        listIsland.add(new Island(10));
        listIsland.add(new Island(11));

        motherNature = new MotherNature(listIsland.get(0));

        professors.add(new Professor(Colour.BLUE));
        professors.add(new Professor(Colour.GREEN));
        professors.add(new Professor(Colour.PINK));
        professors.add(new Professor(Colour.RED));
        professors.add(new Professor(Colour.YELLOW));
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

    public void doMoveMotherNature(int numMovement) { //metti eccezione
        /*
        1. viene sommato il numero dell'isola su cui si trova madre natura con il numero di spostamenti che deve fare
        2. si fa modulo 12, si trova il numero dell'isola su cui madre natura si deve spostare
        3. questo numero è dato alla funzione getIsland, che ritorna l'oggetto isola avente quel numero

        1. it sums the number of island on which mother nature is and the umber of steps she has to do
        2. it does mod 12 to find the number of the island on which MotherNature has to move
        3. this number is given by the function getIsland(), which returns the Island object having that number
         */
        motherNature.move(getIsland((numMovement+motherNature.getNumIsland())%12));
    }

    public void doMoveStudentInDiningRoom(int idClient, Colour colour) throws MissingStudentException {
        for (Player player : listPlayer) {
            if (player != null) {
                if (player.getIdClient() == idClient) {
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
                if (player.getIdClient() == idClient) {
                    player.addStudentsToEntrances(listCloud.get(numCloud).getStudents());
                }
            }
        }
    }

    public void doPlayCard(int idClient, int numCard) {
        for (Player player : listPlayer) {
            if (player != null) {
                if (player.getIdClient() == idClient) {
                    player.playCard(numCard);
                }
            }
        }
    }

    public void doMoveStudentInIsland(int idClient, Colour colour, int numIsland) {
        for (Player player : listPlayer) {

            if (player.getIdClient() == idClient) {
                player.moveStudentInIsland(colour, this.getIsland(numIsland));
            }
        }
    }

    public Island getIsland(int numIsland) throws IllegalArgumentException {
        Island island = null;
        if (numIsland < 0 || numIsland > listIsland.size()) {
            throw new IllegalArgumentException();
        } else {
            for (Island value : listIsland) {

                if (value.getNumIsland() == numIsland) {
                    island = value;
                }
            }
        }
        return island;
    }
}

