package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.VIEW.Client;

import java.util.*;

// TODO: 05/04/2022 metodo del controller che acquisisce i vari nickname

public class ControllerAction {

    private Game game;
    private List<Client> listClient;
    private Action currentAction;

    public void createGame(String nickname1, String nickname2){
        game = new Game(nickname1, nickname2);
    }

    public void createGame(String nickname1, String nickname2, String nickname3){
        game = new Game(nickname1, nickname2, nickname3);
    }

    public void createGame(String nickname1, String nickname2, String nickname3, String nickname4){
        game = new Game(nickname1, nickname2, nickname3, nickname4);
    }



    public void playCard(String nickname, int numCard){
        this.game.doPlayCard(nickname, numCard);
    }



    public void moveMotherNature(int numMovement)throws WrongActionException{
        if(currentAction == Action.MoveMotherNature) {
            this.game.doMoveMotherNature(numMovement);
            this.currentAction = Action.MoveStudent1;
        }
        else{
            throw new WrongActionException();
        }
    }


    //il client chiama lo spostamento di uno studente in diningroom:
    //1. richiamo la funzione se è il momento di spostare gli studenti (altrimenti lancio un'eccezione)
    //2. richiamo il controllo professori per verificare che i professori siano assegnati correttamente
    //3. cambio azione corrente
    public void moveStudentInDiningRoom(String nickname, Colour colour)throws WrongActionException{
        if(currentAction == Action.MoveStudent1 || currentAction == Action.MoveStudent2 || currentAction == Action.MoveStudent3) {

            game.doMoveStudentInDiningRoom(nickname, colour);
            if(this.currentAction == Action.MoveStudent1){
                this.currentAction = Action.MoveStudent2;

                this.game.checkProfessor(colour);
            }
            else if(this.currentAction == Action.MoveStudent2){
                this.currentAction = Action.MoveStudent3;

                this.game.checkProfessor(colour);
            }
            else if(this.currentAction == Action.MoveStudent3){
                this.currentAction = Action.TakeCloud;

                this.game.checkProfessor(colour);
            }

        }
        else{
            throw new WrongActionException();
        }
    }


    //il client chiama lo spostamento di uno studente su un'isola
    //1. richiamo la funzione se è il momento di spostare gli studenti (altrimenti lancio un'eccezione)
    //2. se è l'ultimo studente devo verifico le influenze sulll'isola e sostituisco eventuali torri
    //3. cambio azione corrente
    public void moveStudentInIsland(String nickname, Colour colour, int numIsland)throws WrongActionException{
        if(currentAction == Action.MoveStudent1 || currentAction == Action.MoveStudent2 || currentAction == Action.MoveStudent3) {
            this.game.doMoveStudentInIsland(nickname, colour, numIsland);

            if(this.currentAction == Action.MoveStudent1){
                this.currentAction = Action.MoveStudent2;

            }
            else if(this.currentAction == Action.MoveStudent2){
                this.currentAction = Action.MoveStudent3;

            }
            else if(this.currentAction == Action.MoveStudent3){
                this.currentAction = Action.TakeCloud;

                try {
                    this.game.checkTowers(4);
                }catch(MissingTowerException e){
                    if(game.checkWin()){
                        game.theWinnerIs();
                    }
                }catch(MissingIslandException e){}
            }
        }
        else{
            throw new WrongActionException();
        }
    }



    public void takeCloud(String nickname, int numCloud)throws WrongActionException{
        try {
            if(currentAction.equals(Action.TakeCloud)){
                    game.doTakeCloud(nickname, numCloud);
                    this.currentAction = Action.MoveMotherNature;
                }
            else {
                throw new WrongActionException();
            }
        }catch (MissingCloudException e) {
            throw new WrongActionException();
        }
    }

    public void useCharacter(int numCharacter){}

}