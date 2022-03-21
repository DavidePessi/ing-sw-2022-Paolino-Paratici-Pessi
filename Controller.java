package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Game;

public class Controller {

    private Game game;
    private Client client;

    public void placeStudentDinner (Colour student){}

    public void placeStudentIsland(Colour student, int numIsola){}

    public void takeCloud(int idClient, int numCloud){ game.doTakeCloud(idClient, numCloud);}

    public void useCharacter(int numPersonaggio){}

    public void playCard(int numCarta){}

    public void moveStudentToDinigRoom(int idClient, Colour colour){
        game.doMoveStuentToDiningRoom(idClient,colour);
    }

    public void moveMotherNature(int numIsland){
        game.doMoveMotherNature(numIsland);
    }
}
