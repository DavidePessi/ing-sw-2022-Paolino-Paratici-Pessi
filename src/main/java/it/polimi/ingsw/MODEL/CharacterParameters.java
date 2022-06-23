package it.polimi.ingsw.MODEL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CharacterParameters implements Serializable {
    private String nickname;
    private String characterName;
    private Colour c1, c2, c3, c4, c5, c6;
    private List<Colour> colours;
    private int num;

    /**
     * initialize an object CharacterParameter
     * @param nickname
     * @param characterName
     * @param colours
     * @param num
     */
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

    /**
     * initialize an object CharacterParameter
     * @param nickname
     * @param characterName
     * @param num
     * @param colour
     */
    public CharacterParameters(String nickname, String characterName, int num , Colour colour){
        this.nickname = nickname;
        this.characterName = characterName;
        this.num = num;
        this.c1 = colour;
    }

    /**
     * initialize an object CharacterParameter
     * @param nickname
     * @param characterName
     * @param colours
     */
    public CharacterParameters(String nickname,  String characterName, List<Colour> colours){
        this.nickname = nickname;
        this.characterName = characterName;
        this.c1 = colours.get(0);
        this.colours = colours;
    }

    /**
     * initialize an object CharacterParameter
     * @param nickname
     * @param characterName
     */
    public CharacterParameters(String nickname, String characterName){
        this.nickname = nickname;
        this.characterName = characterName;
    }

    /**
     * initialize an object CharacterParameter
     * @param nickname
     * @param characterName
     * @param colour
     */
    public CharacterParameters(String nickname, String characterName, Colour colour){
        this.nickname = nickname;
        this.characterName = characterName;
        this.c1 = colour;
    }

    /**
     * initialize an object CharacterParameter
     * @param nickname
     * @param characterName
     * @param num
     */
    public CharacterParameters(String nickname, String characterName, int num){
        this.nickname = nickname;
        this.characterName = characterName;
        this.num = num;
    }

    /**
     * returns the name of character
     * @return
     */
    public String getCharacterName() {
        return characterName;
    }

    /**
     * returns the nickname of those who are playing the card
     * @return
     */
    public String getPlayerName() {
        return nickname;
    }

    public int getNum() {
        return num;
    }

    /**
     * returns the colour #1
     * @return
     * @throws Exception
     */
    public Colour getColour1()throws Exception{
        if(this.c1 == null){
            throw new Exception();
        }
        else {
            return this.c1;
        }
    }
    /**
     * returns the colour #2
     * @return
     * @throws Exception
     */
    public Colour getColour2()throws Exception{
        if(this.c2 == null){
            throw new Exception();
        }
        else {
            return this.c2;
        }
    }
    /**
     * returns the colour #3
     * @return
     * @throws Exception
     */
    public Colour getColour3()throws Exception{
        if(this.c3 == null){
            throw new Exception();
        }
        else {
            return this.c3;
        }
    }
    /**
     * returns the colour #4
     * @return
     * @throws Exception
     */
    public Colour getColour4()throws Exception{
        if(this.c4 == null){
            throw new Exception();
        }
        else {
            return this.c4;
        }
    }
    /**
     * returns the colour #5
     * @return
     * @throws Exception
     */
    public Colour getColour5()throws Exception{
        if(this.c5 == null){
            throw new Exception();
        }
        else {
            return this.c5;
        }
    }
    /**
     * returns the colour #6
     * @return
     * @throws Exception
     */
    public Colour getColour6()throws Exception{
        if(this.c6 == null){
            throw new Exception();
        }
        else {
            return this.c6;
        }
    }
}
