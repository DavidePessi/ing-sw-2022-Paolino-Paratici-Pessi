package it.polimi.ingsw.NETWORK.MESSAGES;

public enum ServerAction {
    UPDATE_BOARD,
    SET_UP_NICKNAME,
    SET_UP_NUM_PLAYERS,
    SET_UP_GAMEMODE,
    ERROR_SETUP,
    CLIENT_ERROR,
    OK_START,
    PING,
    END_GAME;

}