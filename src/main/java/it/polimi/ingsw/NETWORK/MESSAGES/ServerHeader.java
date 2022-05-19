package it.polimi.ingsw.NETWORK.MESSAGES;

import java.io.Serializable;

public class ServerHeader implements Serializable {
    private ServerAction serverAction;
    private String description;

    public ServerHeader(ServerAction serverAction, String description){
        this.serverAction = serverAction;
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public ServerAction getServerAction(){return serverAction;}
}
