package it.polimi.ingsw.NETWORK.MESSAGES;

import java.io.Serializable;

public class ServerMessage implements Serializable {
    private ServerHeader serverHeader;
    private Payload payload;

    public ServerMessage(ServerHeader ser, Payload pay){
        this.payload = pay;
        this.serverHeader = ser;
    }

    /**
     * returns the server header
     * @return
     */
    public ServerHeader getServerHeader(){return this.serverHeader;}

    /**
     * returns the payload
     * @return
     */
    public Payload getPayload(){
        return this.payload;
    }

}
