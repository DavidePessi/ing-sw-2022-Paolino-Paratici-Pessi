package it.polimi.ingsw.MODEL;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {

    private int value;
    private int movements;

    /**
     * initialize an object Card
     * @param value
     * @param movements
     */
    public Card(int value, int movements) {
        this.value = value;
        this.movements = movements;
    }

    /**
     * returns the value of the card
     * @return
     */
    public int getValue() {
        return this.value;
    }

    /**
     * returns the maximum number of movement
     * @return
     */
    public int getMovement() {
        return this.movements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && movements == card.movements;
    }

}
