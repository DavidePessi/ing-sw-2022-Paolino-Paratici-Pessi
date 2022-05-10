package it.polimi.ingsw.NETWORK.MESSAGES;
import java.io.Serializable;

public class ClientHeader implements Serializable {
    private ClientAction clientAction;
    private String nickname;

    public ClientHeader(String nickname, ClientAction clientAction){
        this.clientAction = clientAction;
        this.nickname = nickname;
    }


    public String toString() {
        return new StringBuffer(clientAction.toString()).append(nickname).toString();
    }
}
