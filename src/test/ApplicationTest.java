package test;

import app.Application;
import org.junit.jupiter.api.Test;
import sac.Objet;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    void getObjetsFromInput() {
        ArrayList<Objet> listObj = new ArrayList<Objet>();
        String chemin = ".\\input.txt";

        ArrayList<Objet> listObjInput = Application.getObjetsFromInput(chemin);
        listObj.addAll(listObjInput);

        for (int i = 0 ; i<=listObj.size()-1 ; i++) {
            assertEquals(listObj.get(i), listObjInput.get(i));
        }
    }
}