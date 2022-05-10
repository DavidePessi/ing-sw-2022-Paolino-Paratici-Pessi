package it.polimi.ingsw.NETWORK.MESSAGES;

import java.io.Serializable;

public class ServerHeader implements Serializable {
    private ServerAction serverAction;

    public ServerHeader(ServerAction serverAction){
        this.serverAction = serverAction;
    }

    public ServerAction getServerAction(){return serverAction;}
}
