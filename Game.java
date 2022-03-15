package it.polimi.ingsw;

import java.util.List;

public class Game {
    private int numPlayer;
    private Player lastFirstPlayer;
    private List<Cloud> listCloud;
    private List<Player> listPlayer;
    private List<Island> listIsland;
    private Bag bag;
    private MotherNature motherNature;
    private List<Professor> professors;


    public Game game(Player player1, Player player2){
        return this;
    }

    public Game game(Player player1, Player player2, Player player3){
        return this;
    }

    public Game game(Player player1, Player player2, Player player3, Player player4){
        return this;
    }

    public void startGame(){
        return;
    }

    public boolean checkWin(){
        /*if the game is finished and there's a winner return True, else return False*/
        return false;
    }

    public Team theWinnerIs(){
        return winner;
    }

}
