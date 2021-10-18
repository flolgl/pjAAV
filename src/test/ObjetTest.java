package test;

import org.junit.jupiter.api.Test;
import sac.Objet;

import static org.junit.jupiter.api.Assertions.*;

class ObjetTest {

    @Test
    void testToString() {
        Objet obj1 = new Objet(2, 3, "obj1");
        Objet obj2 = new Objet(2, 3, "obj2");
        Objet obj3 = new Objet(2, 3, "obj3");
        assertEquals(obj1.toString(), "Objet : obj1 Valeur : 2.0 Poids : 3.0");
        assertEquals(obj2.toString(), "Objet : obj2 Valeur : 2.0 Poids : 3.0");
        assertEquals(obj3.toString(), "Objet : obj3 Valeur : 2.0 Poids : 3.0");

    }

}