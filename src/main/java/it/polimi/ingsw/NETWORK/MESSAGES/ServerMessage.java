package it.polimi.ingsw.NETWORK.MESSAGES;

import java.io.Serializable;

public class ServerMessage implements Serializable {
    private ServerHeader serverHeader;
    private Payload payload;

    public ServerMessage(ServerHeader ser, Payload pay){
        this.payload = pay;
        this.serverHeader = ser;
    }
    public ServerHeader getServerHeader(){return this.serverHeader;}

    public Payload getPayload(){
        return this.payload;
    }

}
