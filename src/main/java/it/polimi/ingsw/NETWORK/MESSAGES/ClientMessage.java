package it.polimi.ingsw.NETWORK.MESSAGES;

import java.io.Serializable;

public class ClientMessage  implements Serializable{
    private static final long serialVersionUID = 1L;
    private ClientHeader clientHeader;
    private Payload payload;

    public ClientMessage(ClientHeader cli, Payload pay){
        this.payload = pay;
        this.clientHeader = cli;
    }

    /**
     * returns the payload of the message
     * @return
     */
    public Payload getPayload(){
        return this.payload;
    }

    /**
     * returns the header of the message
     * @return
     */
    public ClientHeader getClientHeader(){
        return  this.clientHeader;
    }

    @Override
    public String toString() {
        return new StringBuffer(clientHeader.toString()).append(payload.toString()).toString();
    }


}
