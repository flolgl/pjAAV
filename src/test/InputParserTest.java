package test;

import app.InputParser;
import jdk.internal.util.xml.impl.Input;
import org.junit.jupiter.api.Test;
import sac.Objet;

import javax.annotation.processing.Filer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    @Test
    void getObjetsFromInput() {
        ArrayList<Objet> listObj = new ArrayList<Objet>();
        String chemin = "C:\\Users\\ayoub\\OneDrive\\Documents\\GitHub\\pjAAV\\input.txt";

        ArrayList<Objet> listObjInput = InputParser.getObjetsFromInput(chemin);
        listObj.addAll(listObjInput);

        for (int i = 0 ; i<=listObj.size()-1 ; i++) {
            assertEquals(listObj.get(i), listObjInput.get(i));
        }
    }
}