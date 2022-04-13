package it.polimi.ingsw.MODEL;

public interface Decorator {

    CharacterCard cc=null;

    public void initialization();

    public void effect(String nickname, Colour colour); //satiro, cavaliere,donna, mago
    public void effect(String nickname, Colour c1, Colour c2) throws Exception; //giullare, menestrello
    public void effect(String nickname, Colour c1, Colour c2, Colour c3, Colour c4) throws Exception; //giullare, menestrello
    public void effect(String nickname, Colour c1, Colour c2, Colour c3, Colour c4, Colour c5, Colour c6) throws Exception; //giullare //giullare
    public void effect(String nickname, int num) throws Exception; //pirata
    public void effect(String nickname, int num, Colour colour) throws Exception; //prete

}
