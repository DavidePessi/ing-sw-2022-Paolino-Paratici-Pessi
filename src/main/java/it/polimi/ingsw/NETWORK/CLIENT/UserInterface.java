package it.polimi.ingsw.NETWORK.CLIENT;

import it.polimi.ingsw.NETWORK.MESSAGES.ServerMessage;

public interface UserInterface {

    public void showBoard();

    public void update(ServerMessage sm);

}
