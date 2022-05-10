package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class CharacterParameters {
    private String nickname;
    private String characterName;
    private Colour c1, c2, c3, c4, c5, c6;
    private List<Colour> colours;
    private int num;

    public CharacterParameters(String nickname, String characterName,List<Colour> colours, int num){
        this.nickname = nickname;
        this.characterName = characterName;
        this.c1 = colours.get(0);
        this.c2 = colours.get(1);
        this.c3 = colours.get(2);
        this.c4 = colours.get(3);
        this.c5 = colours.get(4);
        this.c6 = colours.get(5);
        this.num = num;
    }

    public CharacterParameters(String nickname,  String characterName,List<Colour> colours){
        this.nickname = nickname;
        this.characterName = characterName;
        this.c1 = colours.get(0);
        this.colours = colours;
    }

    public CharacterParameters(String nickname, String characterName){
        this.nickname = nickname;
        this.characterName = characterName;
    }

    public CharacterParameters(String nickname, String characterName, int num){
        this.nickname = nickname;
        this.num = num;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getPlayerName() {
        return nickname;
    }

    public int getNum() {
        return num;
    }

    public Colour getColour1()throws Exception{
        if(this.c1 == null){
            throw new Exception();
        }
        else {
            return this.c1;
        }
    }
    public Colour getColour2()throws Exception{
        if(this.c2 == null){
            throw new Exception();
        }
        else {
            return this.c2;
        }
    }
    public Colour getColour3()throws Exception{
        if(this.c3 == null){
            throw new Exception();
        }
        else {
            return this.c3;
        }
    }
    public Colour getColour4()throws Exception{
        if(this.c4 == null){
            throw new Exception();
        }
        else {
            return this.c4;
        }
    }
    public Colour getColour5()throws Exception{
        if(this.c5 == null){
            throw new Exception();
        }
        else {
            return this.c5;
        }
    }
    public Colour getColour6()throws Exception{
        if(this.c6 == null){
            throw new Exception();
        }
        else {
            return this.c6;
        }
    }
}
