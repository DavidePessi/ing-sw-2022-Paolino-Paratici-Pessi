package it.polimi.ingsw.NETWORK.MESSAGES;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerMessageTest {

    @Test
    void getServerHeader() {
        String description = "description";
        ServerHeader sh = new ServerHeader(ServerAction.END_GAME, description);
        Payload pay = new Payload();

        ServerMessage sm = new ServerMessage(sh, pay);

        assertEquals(sh, sm.getServerHeader());

    }

    @Test
    void getPayload() {
        String description = "description";
        ServerHeader sh = new ServerHeader(ServerAction.END_GAME, description);
        Payload pay = new Payload();

        ServerMessage sm = new ServerMessage(sh, pay);

        assertEquals(pay, sm.getPayload());
    }
}