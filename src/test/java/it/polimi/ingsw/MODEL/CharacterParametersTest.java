package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharacterParametersTest {

    @Test
    void getCharacterName() {
        CharacterParameters characterParameters = new CharacterParameters("gio", "Satyr");
        assertEquals("Satyr", characterParameters.getCharacterName());
    }

    @Test
    void getPlayerName() {
        CharacterParameters characterParameters = new CharacterParameters("gio", "Satyr");
        assertEquals("gio", characterParameters.getPlayerName());
    }

    @Test
    void getNum() {
        CharacterParameters characterParameters = new CharacterParameters("gio", "Satyr", 3);
        assertEquals(3, characterParameters.getNum());
    }

    @Test
    void getColour1() throws Exception{
        CharacterParameters characterParameters = new CharacterParameters("gio", "Satyr", Colour.RED);
        assertEquals(Colour.RED, characterParameters.getColour1());
    }

    @Test
    void getColour2() throws Exception{
        List<Colour> colours = new ArrayList<>();
        colours.add(0,Colour.RED);
        colours.add(1,Colour.GREEN);
        colours.add(2,Colour.BLUE);
        colours.add(3,Colour.PINK);
        colours.add(4,Colour.YELLOW);
        colours.add(5,Colour.YELLOW);
        CharacterParameters characterParameters = new CharacterParameters("gio", "Satyr", colours, 2);
        assertEquals(Colour.GREEN, characterParameters.getColour2());
    }

    @Test
    void getColour3() throws  Exception{
        List<Colour> colours = new ArrayList<>();
        colours.add(0,Colour.RED);
        colours.add(1,Colour.GREEN);
        colours.add(2,Colour.BLUE);
        colours.add(3,Colour.PINK);
        colours.add(4,Colour.YELLOW);
        colours.add(5,Colour.YELLOW);
        CharacterParameters characterParameters = new CharacterParameters("gio", "Satyr", colours, 2);
        assertEquals(Colour.BLUE, characterParameters.getColour3());
    }

    @Test
    void getColour4() throws Exception{
        List<Colour> colours = new ArrayList<>();
        colours.add(0,Colour.RED);
        colours.add(1,Colour.GREEN);
        colours.add(2,Colour.BLUE);
        colours.add(3,Colour.PINK);
        colours.add(4,Colour.YELLOW);
        colours.add(5,Colour.YELLOW);
        CharacterParameters characterParameters = new CharacterParameters("gio", "Satyr", colours, 2);
        assertEquals(Colour.PINK, characterParameters.getColour4());
    }

    @Test
    void getColour5() throws Exception{
        List<Colour> colours = new ArrayList<>();
        colours.add(0,Colour.RED);
        colours.add(1,Colour.GREEN);
        colours.add(2,Colour.BLUE);
        colours.add(3,Colour.PINK);
        colours.add(4,Colour.YELLOW);
        colours.add(5,Colour.YELLOW);
        CharacterParameters characterParameters = new CharacterParameters("gio", "Satyr", colours, 2);
        assertEquals(Colour.YELLOW, characterParameters.getColour5());
    }

    @Test
    void getColour6() throws Exception{
        List<Colour> colours = new ArrayList<>();
        colours.add(0,Colour.RED);
        colours.add(1,Colour.GREEN);
        colours.add(2,Colour.BLUE);
        colours.add(3,Colour.PINK);
        colours.add(4,Colour.YELLOW);
        colours.add(5,Colour.YELLOW);
        CharacterParameters characterParameters = new CharacterParameters("gio", "Satyr", colours, 2);
        assertEquals(Colour.YELLOW, characterParameters.getColour6());
    }
}