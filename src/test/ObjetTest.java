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
        assertEquals(obj1.toString(), "[0]");
        assertEquals(obj2.toString(), "[1]");
        assertEquals(obj3.toString(), "[2]");
    }

    @Test
    void compareTo() {
        Objet obj1 = new Objet(2, 40, "obj1");
        Objet obj2 = new Objet(4, 40, "obj2");
        Objet obj3 = new Objet(4, 40, "obj3");
        assertEquals(obj1.compareTo(obj2), -1);
        assertEquals(obj2.compareTo(obj1), 1);
        assertEquals(obj2.compareTo(obj3), 0);
    }
}