package it.polimi.ingsw.MODEL;

import java.util.*;

// TODO: 22/03/2022 aggiungere una variabile per fare il modulo

public class Game {
    private int numPlayer;
    private Player lastFirstPlayer;
    private List<Cloud> listCloud;
    private List<Player> listPlayer;
    private List<Island> listIsland;
    private List<Team> listTeam;
    private Bag bag;
    private MotherNature motherNature;
    private List<Professor> professors;
    //private int currentIsland;

    public Game(String nickname1, String nickname2) {

        listTeam.add(new Team(ColourTower.WHITE, 8));
        listTeam.add(new Team(ColourTower.BLACK, 8));

        listPlayer.add(new Player(nickname1, listTeam.get(0)));
        listPlayer.add(new Player(nickname2, listTeam.get(1)));

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

    public Game(String nickname1, String nickname2, String nickname3) {

        listTeam.add(new Team(ColourTower.WHITE, 6));
        listTeam.add(new Team(ColourTower.BLACK, 6));
        listTeam.add(new Team(ColourTower.GREY, 6));

        listPlayer.add(new Player(nickname1, listTeam.get(0)));
        listPlayer.add(new Player(nickname2, listTeam.get(1)));
        listPlayer.add(new Player(nickname3, listTeam.get(2)));



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

    public Game(String nickname1, String nickname2, String nickname3, String nickname4) {

        listTeam.add(new Team(ColourTower.WHITE, 8));
        listTeam.add(new Team(ColourTower.BLACK, 8));

        listPlayer.add(new Player(nickname1, listTeam.get(0)));
        listPlayer.add(new Player(nickname2, listTeam.get(1)));
        listPlayer.add(new Player(nickname3, listTeam.get(0)));
        listPlayer.add(new Player(nickname4, listTeam.get(1)));


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

    public void doMoveStudentInDiningRoom(String idClient, Colour colour) throws MissingStudentException {
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
    public void doTakeCloud(String idClient, int numCloud) {
        for (Player player : listPlayer) {
            if (player != null) {
                if (player.getIdClient() == idClient) {
                    player.addStudentsToEntrances(listCloud.get(numCloud).getStudents());
                }
            }
        }
    }

    public void doPlayCard(String nickname, int numCard) {
        for (Player player : listPlayer) {
            if (player != null) {
                if (player.getIdClient() == nickname) {
                    player.playCard(numCard);
                }
            }
        }
    }

    public void doMoveStudentInIsland(String idClient, Colour colour, int numIsland) {
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

    //il metodo ha la funzione di assegnare i professori ai player corretti secondo i loro studenti che
    //possiedono sulla loro DinnerRoom e viene richiamata
    public void checkProfessor(Colour colour){
        Player MaxPlayer = listPlayer.get(0);

        //il primo ciclo mi dice chi è il player con più studenti del colore colour
        for(int i = 1; i < listPlayer.size(); i++){
            if(listPlayer.get(i).NumStudents(colour) > 0 &&
               listPlayer.get(i).NumStudents(colour) > MaxPlayer.NumStudents(colour)){

                //a questo punto cerco il professore del colore voluto nella lista
                for(int j = 1; j < professors.size(); j++){

                    if(professors.get(j).getColour() == colour){
                        //se il professore non ha owner e il player con più studenti ha almeno uno studente
                        //assegno il professore
                        if(professors.get(j).getOwner() == null && MaxPlayer.NumStudents(colour) > 0){
                            MaxPlayer.addProfessor(professors.get(j));
                            professors.get(j).changeOwner(MaxPlayer);

                        }

                        //se il professore ha un owner e il player con più studenti ha più studenti dell'owner
                        //assegno il professore
                        else{
                            if(MaxPlayer.NumStudents(colour) > professors.get(j).getOwner().NumStudents(colour)){
                                MaxPlayer.addProfessor(professors.get(j));
                                professors.get(j).getOwner().removeProfessor(professors.get(j));
                                professors.get(j).changeOwner(MaxPlayer);
                            }
                        }

                    }
                }
            }
        }
    }
}

