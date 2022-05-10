package it.polimi.ingsw.NETWORK.SERVER;
import it.polimi.ingsw.NETWORK.UTILS.Observer;

public interface ClientConnection{

    void closeConnection();

    void addObserver(Observer<String> observer);

    void asyncSend(Object message);
}
