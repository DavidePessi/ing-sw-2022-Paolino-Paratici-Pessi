package it.polimi.ingsw.NETWORK.MESSAGES;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientMessageTest {

    @Test
    void getPayload() {
        Payload pay = new Payload();
        String nickname = "nickname";
        ClientHeader ch = new ClientHeader(nickname, ClientAction.PLAY_ACTION);

        ClientMessage cm = new ClientMessage(ch, pay);

        assertEquals(pay, cm.getPayload());
    }

    @Test
    void getClientHeader() {
        Payload pay = new Payload();
        String nickname = "nickname";
        ClientHeader ch = new ClientHeader(nickname, ClientAction.PLAY_ACTION);

        ClientMessage cm = new ClientMessage(ch, pay);

        assertEquals(ch, cm.getClientHeader());
    }

    @Test
    void testToString() {
        Payload pay = new Payload();
        String nickname = "nickname";
        ClientHeader ch = new ClientHeader(nickname, ClientAction.PLAY_ACTION);

        ClientMessage cm = new ClientMessage(ch, pay);

        assertTrue(cm.toString() instanceof String);
    }
}