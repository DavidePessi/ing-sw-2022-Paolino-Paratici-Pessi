package it.polimi.ingsw.NETWORK.MESSAGES;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayloadTest {

    @Test
    void addParameter() {
        Payload pay = new Payload();

        String p1 = "parametro1";
        Object obj = new Object();

        pay.addParameter(p1, obj);

        assertEquals(obj, pay.getParameter(p1));
    }

    @Test
    void getParameter() {
        Payload pay = new Payload();

        String p1 = "parametro1";
        Object obj = new Object();

        pay.addParameter(p1, obj);

        assertEquals(obj, pay.getParameter(p1));
    }

    @Test
    void containsParameter() {
        Payload pay = new Payload();

        String p1 = "parametro1";
        String p2 = "parametro2";
        Object obj = new Object();

        pay.addParameter(p1, obj);

        assertTrue(pay.containsParameter(p1));
        assertFalse(pay.containsParameter(p2));
    }

    @Test
    void testToString() {
        Payload pay = new Payload();

        assertTrue(pay.toString() instanceof String);
    }
}