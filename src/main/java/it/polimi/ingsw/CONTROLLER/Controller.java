package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.VIEW.Client;

import java.util.*;

// TODO: 21/03/2022 nel metodo takeCloud, numCloud>4...dobbiamo mettere un attributo che ci dice quante nuvole ci sono (perché in base al num di giocatori cambiano)

public class Controller {

    private Game game;
    private List<Client> listClient;

    public void takeCloud(String nickname, int numCloud) throws MissingCloudException {
        if(numCloud>=0 && numCloud<=4) {
            game.doTakeCloud(nickname, numCloud);
        }
        else throw new MissingCloudException("Cloud not found");
    }

    public void createGame(String nickname1, String nickname2){
        game = new Game(nickname1, nickname2);
    }

    public void createGame(String nickname1, String nickname2, String nickname3){
        game = new Game(nickname1, nickname2, nickname3);
    }

    public void createGame(String nickname1, String nickname2, String nickname3, String nickname4){
        game = new Game(nickname1, nickname2, nickname3, nickname4);
    }

    public void useCharacter(int numCharacter){}

    public void playCard(String nickname, int numCard){
        game.doPlayCard(nickname, numCard);
    }

    public void moveStudentInDiningRoom(String nickname, Colour colour){
        try {
            game.doMoveStudentInDiningRoom(nickname,colour);
        } catch (MissingStudentException e) {
            e.printStackTrace();
        }
    }

    public void moveStudentInIsland(String nickname, Colour colour, int numIsland){
        game.doMoveStudentInIsland(nickname, colour, numIsland);
    }

    public void moveMotherNature(int numMovement){
        game.doMoveMotherNature(numMovement);
    }
}

