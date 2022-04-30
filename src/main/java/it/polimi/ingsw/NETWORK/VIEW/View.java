package it.polimi.ingsw.NETWORK.VIEW;

import it.polimi.ingsw.NETWORK.UTILS.Observable;
import it.polimi.ingsw.NETWORK.UTILS.Observer;

public abstract class View extends Observable implements Observer {
    @Override
    public void update(Object arg) {
    }
}