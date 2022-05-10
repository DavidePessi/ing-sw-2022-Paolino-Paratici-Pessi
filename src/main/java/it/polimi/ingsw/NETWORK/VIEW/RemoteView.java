package it.polimi.ingsw.NETWORK.VIEW;

import it.polimi.ingsw.MODEL.Game;
import it.polimi.ingsw.NETWORK.SERVER.ClientConnection;

import it.polimi.ingsw.NETWORK.UTILS.Observer;

public class RemoteView extends View {
    private ClientConnection connection;

    public void showBoard(Game g) {
    }

    public RemoteView(ClientConnection c) {
        this.connection = c;
    }


    private class MessageReceiver implements Observer {

        @Override
        public void update(Object message) {

        }
    }
}