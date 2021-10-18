package test;

import org.junit.jupiter.api.Test;
import sac.SacADos;

import static org.junit.jupiter.api.Assertions.*;

class SacADosTest {

    @Test
    void testToString() {
        String chemin = ".\\input.txt";
        SacADos sac = new SacADos(chemin, 30);
        String output = "Tab des boites: \n" +
                "Lampe \n" +
                "Sac de couchage \n" +
                "Camping gaz \n" +
                "Couteau suisse \n" +
                "Snickers \n" +
                "Tente 2 secondes \n" +
                "Briquet \n" +
                "Coca \n" +
                "Chips \n" +
                "\n" +
                "\n" +
                "Sac à dos: \n" +
                "Poids total: 0.0\n" +
                "Valeur totale: 0.0";
        assertEquals(sac.toString(), output);
    }
}