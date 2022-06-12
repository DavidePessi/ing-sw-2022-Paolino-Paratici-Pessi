package it.polimi.ingsw.NETWORK.MESSAGES;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientHeaderTest {

    @Test
    void getClientAction() {
        String s = "nickname";
        ClientHeader ch = new ClientHeader(s, ClientAction.PLAY_ACTION);

        assertEquals(ClientAction.PLAY_ACTION, ch.getClientAction());
    }

    @Test
    void testToString() {
        String s = "nickname";
        ClientHeader ch = new ClientHeader(s, ClientAction.PLAY_ACTION);

        assertTrue(ch.toString() instanceof String);
    }
}