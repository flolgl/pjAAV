package test;

import app.InputParser;
import org.junit.jupiter.api.Test;
import sac.FabriqueSolvingMethode;
import sac.Objet;
import solving.methods.Dynamique;
import solving.methods.Gluton;
import solving.methods.PSE;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FabriqueSolvingMethodeTest {

    @Test
    void createSolvingMethode() {
        int choice1 = 1;
        int choice2 = 2;
        int choice3 = 3;
        float poids = 30;
        String chemin = ".\\input.txt";
        ArrayList<Objet> objets = InputParser.getObjetsFromInput(chemin);
        FabriqueSolvingMethode fsm = new FabriqueSolvingMethode();

        assertEquals(fsm.createSolvingMethode(choice1, poids, objets).getSolution(), new Gluton(poids, objets).getSolution());
        assertEquals(fsm.createSolvingMethode(choice2, poids, objets).getSolution(), new Dynamique(poids, objets).getSolution());
        //assertEquals(fsm.createSolvingMethode(choice3, poids, objets).getSolution(), new PSE(objets, poids, new ArrayList<>(), 0).getSolution());
    }
}