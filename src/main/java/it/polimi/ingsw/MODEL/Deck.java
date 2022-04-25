package it.polimi.ingsw.MODEL;

import it.polimi.ingsw.MODEL.Exception.MissingCardException;

import java.util.*;

public class Deck {
    private List<Card> listCard;

    public void removeCard(int value){
        Card c = null;

        for(Card card: listCard) {
            if (card.getValue() == value) {
                c = card;
            }
        }
        this.listCard.remove(c);
    }

    public Card getCard(int value) throws MissingCardException {
        Card cardToReturn = null;
        for(Card card : listCard){
            if(card.getValue() == value){
                cardToReturn = card;
            }
        }

        if(cardToReturn == null){
            throw new MissingCardException("The card you want to play is not in the deck");
        }
        return cardToReturn;
    }

    public Deck(){
        listCard = new ArrayList<Card>();

        listCard.add(new Card(1,1));
        listCard.add(new Card(2,1));
        listCard.add(new Card(3,2));
        listCard.add(new Card(4,2));
        listCard.add(new Card(5,3));
        listCard.add(new Card(6,3));
        listCard.add(new Card(7,4));
        listCard.add(new Card(8,4));
        listCard.add(new Card(9,5));
        listCard.add(new Card(10,5));
    }

    public int size(){
        return listCard.size();
    }
}
