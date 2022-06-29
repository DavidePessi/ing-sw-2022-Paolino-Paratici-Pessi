package it.polimi.ingsw.NETWORK.UTILS;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    protected List<Observer<T>> observers = new ArrayList<>();

    /**
     * adds an observer to the list
     * @param observer
     */
    public void addObserver(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }


    /**
     * notify all observable in the list
     * @param message
     */
    public void notify(Object message){
        synchronized (observers) {
            for (Observer<T> observer : observers) {
                observer.update(message);

            }
        }
    }

}
