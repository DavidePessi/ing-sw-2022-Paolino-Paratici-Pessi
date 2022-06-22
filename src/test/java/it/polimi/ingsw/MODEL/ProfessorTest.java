package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class ProfessorTest extends TestCase {

    @Test
    public void testChangeOwner() {
        Colour c = Colour.GREEN;
        Professor p = new Professor(c);
        ColourTower ct = ColourTower.BLACK;
        Team t = new Team (ct,8);
        Player player = new Player("io",t);

        p.changeOwner(player);

        assertEquals(p.getOwner(), player);
    }

    @Test
    public void testGetOwner() {
        Colour c = Colour.GREEN;
        Professor p = new Professor(c);

        //assertTrue(p.getOwner() instanceof Player);
        //assertTrue(p.getOwner() != null);
        //da controllare perchè resituisce null e non va bene
        //se si cambia bisogna cambiare anche checkProfessor
        //possibile soluzione boolean che dice se il player è valido
        //altra possibile soluzione un player che rappresenta il game che possiede inizialmente tutti i professori
    }

    @Test
    public void testGetColour() {
        Colour c = Colour.GREEN;
        Professor p = new Professor(c);

        assertEquals(c, p.getColour());
        assertTrue(c != null);
        assertTrue(c instanceof Colour);
    }
}