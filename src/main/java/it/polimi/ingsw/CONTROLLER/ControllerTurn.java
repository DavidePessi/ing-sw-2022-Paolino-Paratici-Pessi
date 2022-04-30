package it.polimi.ingsw.CONTROLLER;

import it.polimi.ingsw.CONTROLLER.Exception.WrongActionException;
import it.polimi.ingsw.CONTROLLER.Exception.WrongClientException;
import it.polimi.ingsw.MODEL.Colour;
import it.polimi.ingsw.MODEL.Exception.MissingCardException;
import it.polimi.ingsw.MODEL.Exception.MissingPlayerException;
import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.NETWORK.UTILS.Observer;

import java.util.List;

public class ControllerTurn implements Observer{
    private Game game;
    private ControllerAction controllerAction;
    private List<String> clientList;
    private String currentClient;
    private boolean mulligan;

    public ControllerTurn(ControllerAction controllerAction, Game game, List<String> nicknames){
        this.clientList.addAll(nicknames);
        this.game=game;
        this.controllerAction=controllerAction;
        this.mulligan= true;
        currentClient = clientList.get(0);
    }

    //restituisce true se il Sender sta facendo il suo turno (è il currentClient)
    public boolean verifyClient(String sender){
        if(sender.equals(this.currentClient)){
            return true;
        }
        else{
            return false;
        }
    }

    //richiama verifyClient e dopodiche richiama la funzione corretta in ControllerAction
    public void callAction(Action action, String nickname, Colour colourParameter, int numberParameter)throws WrongClientException {
        if(verifyClient(nickname)){

            if(mulligan==true){
                if(action.equals(Action.PlayCard)) {
                    try {
                        this.controllerAction.playCard(nickname, numberParameter);
                    } catch (MissingCardException e) {}
                    this.endTurn();
                }
            }
            else{
                if(action.equals(Action.MoveMotherNature)){
                    try{
                        this.controllerAction.moveMotherNature(numberParameter);
                    }catch(WrongActionException e){}
                }
                else if(action.equals(Action.MoveStudentInDiningRoom)){
                    try {
                        this.controllerAction.moveStudentInDiningRoom(nickname, colourParameter);
                    }catch(WrongActionException e){}
                }
                else if(action.equals(Action.MoveStudentInIsland)){
                    try {
                        this.controllerAction.moveStudentInIsland(nickname, colourParameter, numberParameter);
                    }catch(WrongActionException e){}
                }
                else if(action.equals(Action.TakeCloud)){
                    try {
                        this.controllerAction.takeCloud(nickname, numberParameter);
                        this.endTurn();

                    }catch(WrongActionException e){}
                }
                else if(action.equals(Action.UseCharacter)){
                    this.controllerAction.useCharacter(numberParameter);
                }
            }
        }
        else{
            throw new WrongClientException();
        }
    }

    public void setCurrentClient(String newCurrentClient){
        this.currentClient = newCurrentClient;
    }

    public String getCurrentClient(){
        return this.currentClient;
    }

    public void endTurn(){
        //se il giocatore è l'ultimo allora significa che è finito il giro e dobbiamo rilanciare le carte
        if(currentClient.equals(clientList.get(clientList.size()-1))){

            String temp = "";
            if(mulligan==true){
                mulligan = false;
                //riordino
                for(int i=0; i<clientList.size()-1; i++){
                    for(int j=i+1; j< clientList.size(); j++){
                        try {
                            if(game.getPlayer(clientList.get(i)).getLastPlayedCard().getValue() > game.getPlayer(clientList.get(j)).getLastPlayedCard().getValue()){
                                temp = clientList.get(i);
                                clientList.set(i,clientList.get(j));
                                clientList.set(j,temp);
                            }
                        } catch (MissingPlayerException e) {}
                    }
                }
            }
            else{
                mulligan = true;
                currentClient = this.clientList.get(0);
                //tutti i client hanno la carta giocata 0,0 così si può scegliere qualsiasi tipo di carta senza avere problemi
                for(String client : clientList){
                    try {
                        game.getPlayer(client).setLastPlayedCardZero();
                    } catch (MissingPlayerException e) {}
                }

            }
        }
        //altrimenti vado al prossimo
        else{
            for(int i = 0 ; i < clientList.size(); i++){
                if(this.clientList.get(i).equals(currentClient)){
                    currentClient = this.clientList.get(i+1);
                }
            }
        }
    }

    @Override
    public void update(Object message) {
        //this.callAction(message......, message.......);
    }
}