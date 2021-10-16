import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 15/10/2021
 * @project pjAAV
 */
public class InputParser {
    public static ArrayList<Objet> getObjetsFromInput(String chemin){
        BufferedReader br = getFile(chemin);
        ArrayList<Objet> tabObj = null;
        try {
            tabObj = getInput(br);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tabObj;
    }


    /**
     * Récupère l'input dans une string
     * @param br le lecteur du buffer
     * @throws IOException s'il y a un problème dans la lecture
     */
    private static ArrayList<Objet> getInput(BufferedReader br) throws IOException {
        ArrayList<Objet> objs = new ArrayList<>();
        String s = br.readLine();
        while (s != null && isFormatOk(s)){
            //System.out.println(s);
            objs.add(getNewObjet(s));
            s = br.readLine();
        }
        return objs;
    }


    /**
     * Ajoute l'input au tableau tabBoites
     * @param input la string venant de l'input
     */
    private static Objet getNewObjet(String input){
        String[] sTab = input.split(";");
        return new Objet(Float.parseFloat(sTab[2]), Float.parseFloat(sTab[1]), sTab[0]);
    }


    /**
     * Vérification du format de la ligne d'input
     * @param input la ligne d'input
     * @return true si la ligne d'input respecte le format nom ; poids ; valeur
     */
    private static boolean isFormatOk(String input){
        return Pattern.matches("^\\s*[A-Za-z0-9\\s]+; [0-9]+.[0-9]+ ; [0-9]+.[0-9]+\\s*", input);
    }


    /**
     * Récupère le fichier d'input
     * @param chemin le chemin vers le fichier
     * @return le fichier transformé en lecteur de buffer
     */
    private static BufferedReader getFile(String chemin){
        FileReader file = null;
        BufferedReader br = null;
        try {
            file = new FileReader(chemin);
            br = new BufferedReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return br;
    }

}
