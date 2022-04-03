package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.VIEW.Client;

import java.util.*;

// TODO: 21/03/2022 nel metodo takeCloud, numCloud>4...dobbiamo mettere un attributo che ci dice quante nuvole ci sono (perché in base al num di giocatori cambiano)

public class Controller {

    private Game game;
    private List<Client> listClient;


    public void takeCloud(int idClient, int numCloud) throws MissingCloudException {
        if(numCloud>=0 && numCloud<=4) {
            game.doTakeCloud(idClient, numCloud);
        }
        else throw new MissingCloudException("Cloud not found");
    }

    public void useCharacter(int numCharacter){}

    public void playCard(int idClient, int numCard){
        game.doPlayCard(idClient, numCard);
    }

    public void moveStudentInDiningRoom(int idClient, Colour colour){
        try {
            game.doMoveStudentInDiningRoom(idClient,colour);
        } catch (MissingStudentException e) {
            e.printStackTrace();
        }
    }

    public void moveStudentInIsland(int idClient, Colour colour, int numIsland){
        game.doMoveStudentInIsland(idClient, colour, numIsland);
    }

    public void moveMotherNature(int numMovement){
        game.doMoveMotherNature(numMovement);
    }
}

