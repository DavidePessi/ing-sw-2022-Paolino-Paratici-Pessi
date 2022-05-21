package it.polimi.ingsw.NETWORK.VIEW;

import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.NETWORK.MESSAGES.ClientMessage;
import it.polimi.ingsw.NETWORK.MESSAGES.ServerMessage;
import it.polimi.ingsw.NETWORK.SERVER.ClientConnection;

import it.polimi.ingsw.NETWORK.SERVER.SocketClientConnection;
import it.polimi.ingsw.NETWORK.UTILS.Observer;

public class RemoteView extends View {
    private SocketClientConnection connection;

    public void showBoard(Game g) {
    }

    public RemoteView(SocketClientConnection c) {
        this.connection = c;
    }

    @Override
    public void update(Object message){
        if(message instanceof ClientMessage){
            notify(message);
        } else if(message instanceof ServerMessage){
            connection.asyncSend(message);
            //System.out.println("update remote view corretta");
        }
    }


}