package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.CONTROLLER.Exception.WrongActionException;
import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.MODEL.Exception.*;

import java.util.*;


public class ControllerAction {

    protected Game game;
    protected List<String> listClient;
    protected Action currentAction;
    
    public ControllerAction(Game game, List<String> nicknames){
        listClient = new ArrayList<>();
        for(String n : nicknames){
            listClient.add(n);
        }
        this.game = game;
        this.currentAction = Action.MoveStudent1;
    }

    /*
    * il metodo ha la funzione di verificare se la carta giocata dall'utente nickname
    * è valida ovvero, se nessuno ha ancora giocato quella carta o (nel caso sia già stata giocata)
    * tutte le carte del player sono state giocate da altri quindi il player può giocarne una qualsiasi
     */
    public boolean checkCardToPlay(int numCard, String nickname){
        boolean toReturn = true;
        //controllo se la carta che voglio giocare è già stata giocata
        //se non è stata giocata ritorno true(cioè puoi giocarla), altrimenti devo verificare se ho altre carte giocabili
        for(String client : listClient){
            try {
                if(client != nickname) {
                    try {
                        if (game.getPlayer(client).getLastPlayedCard().getValue() == numCard) {
                            toReturn = false;
                            break;
                        }
                    }catch(MissingCardException e){}
                }
            } catch (MissingPlayerException e) {
                e.printStackTrace();
            }
        }

        //controllo se ho altre carte che sono giocabili, cioè nessun altro giocatore le ha giocate
        //se si allora ritorno false(non posso giocare la carta), altrimenti ritorno true(cioè la giochi lo stesso perchè tutte le carte che hai in mano sono state giocate)
        if (toReturn == false){

            try {
                toReturn = true;
                boolean temp;
                for(int i=0;i<game.getPlayer(nickname).getDeck().size();i++){
                    temp = false;
                    for(String s: listClient){
                        try {
                            if(s != nickname) {
                                if (game.getPlayer(s).getLastPlayedCard().getValue() == game.getPlayer(nickname).getDeck().getCardOfIndex(i).getValue()) {
                                    temp = true;
                                }
                            }
                        } catch (MissingCardException e) {}
                    }
                    if(temp == false){
                        toReturn = false;
                    }
                }
            } catch (MissingPlayerException e) {
                e.printStackTrace();
            }
        }
        return toReturn;
    }


    public void playCard(String nickname, int numCard) throws MissingCardException {
        if(checkCardToPlay(numCard,nickname)){
            this.game.doPlayCard(nickname, numCard);
        } else{
            throw new MissingCardException("carta selezionata non giocabile");
        }
    }


    public void moveMotherNature(int numMovement)throws Exception {
        if(currentAction == Action.MoveMotherNature) {

            try{
                this.game.doMoveMotherNature(numMovement);
                setCurrentAction(Action.TakeCloud);
            } catch (MissingTowerException e) {
                if (game.checkWin()) {
                    game.theWinnerIs();
                    //todo per fine game
                }
            } catch (MissingIslandException e){}
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
            try {
                game.doMoveStudentInDiningRoom(nickname, colour);

                if (this.currentAction == Action.MoveStudent1) {
                    setCurrentAction(Action.MoveStudent2);

                    this.game.checkProfessor(colour);
                } else if (this.currentAction == Action.MoveStudent2) {
                    setCurrentAction(Action.MoveStudent3);

                    this.game.checkProfessor(colour);
                } else if (this.currentAction == Action.MoveStudent3) {
                    setCurrentAction(Action.MoveMotherNature);

                    this.game.checkProfessor(colour);
                }
            }catch(MissingStudentException e){
                game.notifyError("studente non presente nella Entrance", nickname);
            }catch(Exception e){
                game.notifyError(e.getMessage(), nickname);
            }

        }
        else{
            throw new WrongActionException();
        }
    }

    /*
    * il client chiama lo spostamento di uno studente su un'isola
    * 1. richiamo la funzione se è il momento di spostare gli studenti (altrimenti lancio un'eccezione)
    * 2. se è l'ultimo studente devo verifico le influenze sulll'isola e sostituisco eventuali torri
    * 3. cambio azione corrente
    */
    public void moveStudentInIsland(String nickname, Colour colour, int numIsland)throws WrongActionException{
        if(currentAction == Action.MoveStudent1 || currentAction == Action.MoveStudent2 || currentAction == Action.MoveStudent3) {
            try {
                this.game.doMoveStudentInIsland(nickname, colour, numIsland);

                if (this.currentAction == Action.MoveStudent1) {
                    setCurrentAction(Action.MoveStudent2);

                } else if (this.currentAction == Action.MoveStudent2) {
                    setCurrentAction(Action.MoveStudent3);

                } else if (this.currentAction == Action.MoveStudent3) {
                    setCurrentAction(Action.MoveMotherNature);
                }

            } catch(MissingStudentException e){
                game.notifyError("studente non presente nella Entrance", nickname);
            } catch(IllegalArgumentException e){
                game.notifyError(e.getMessage(), nickname);
            }
        }
        else{
            throw new WrongActionException();
        }
    }


    public void takeCloud(String nickname, int numCloud)throws WrongActionException{

        if(currentAction.equals(Action.TakeCloud)){
            try {
                game.doTakeCloud(nickname, numCloud);
                setCurrentAction(Action.MoveStudent1);
            } catch(MissingCloudException e){
                game.notifyError("nuvola selezionata vuota seleziona un'altra nuvola", nickname);
            }

        } else {
            throw new WrongActionException();
        }
    }

    public void useCharacter(CharacterParameters charPar) throws Exception {
        game.notifyError("mossa non valida in gamemode: easy", charPar.getPlayerName());

    }

    public void setCurrentAction(Action action){
        this.currentAction = action;
    }

    public Action getCurrentAction(){
        return this.currentAction;
    }
}