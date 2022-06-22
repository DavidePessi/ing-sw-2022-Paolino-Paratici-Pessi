package it.polimi.ingsw.NETWORK.MESSAGES;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerHeaderTest {

    @Test
    void getDescription() {
        String description = "description";
        ServerHeader sh = new ServerHeader(ServerAction.END_GAME, description);

        assertEquals(description, sh.getDescription());
    }

    @Test
    void getServerAction() {
        String description = "description";
        ServerAction sa = ServerAction.END_GAME;
        ServerHeader sh = new ServerHeader(sa, description);

        assertEquals(sa, sh.getServerAction());
    }
}