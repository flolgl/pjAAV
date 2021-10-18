package app;

import sac.FabriqueSolvingMethode;
import sac.NoMethodeException;
import sac.Objet;
import sac.SacADos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 15/10/2021
 * @project pjAAV
 */
public class InputParser {

    /**
     * Permet de récupérer la liste d'objets grâce à la saisie dans le fichier texte
     * @param chemin Le chemin vers le fichier texte contenant la saisie de la liste d'objets
     * @return La liste d'objets
     */
    public static ArrayList<Objet> getObjetsFromInput(String chemin){
        BufferedReader br = getFile(chemin);
        ArrayList<Objet> tabObj = null;
        try {
            tabObj = getInput(br);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
            //e.printStackTrace();
            return null;
        }

        return br;
    }

    /**
     * @brief Permet d'attribuer un numéro à la méthode choisie
     * @param method La méthode saisie
     * @return Le numéro de la méthode saisie
     * @throws Exception Saisie incorrecte
     */
    private static int methodChoice(String method) throws Exception{
        switch(method.toLowerCase(Locale.ROOT)){
            case "glouton":
                return 1;
            case "dynamique":
                return 2;
            case "pse":
                return 3;
            default:
                throw new Exception("Méthode inconnue");
        }
    }

    /**
     * @brief Permet de vérifier que la valeur saisie est numérique
     * @param str La valeur saisie
     * @return true si la valeur est numérique sinon false
     */
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Nombre d'arguments incorrect. Merci de respecter le format : resoudre-sac-a-dos chemin poids-maximal methode(= gluton, dynamique, pse)\n");
            return;
        }
        if (InputParser.getFile(args[0]) == null){
            System.out.println("Fichier inexistant\n");
            return;
        }
        if(!isNumeric(args[1])){
            System.out.println("Poids maximum doit être numérique\n");
            return;
        }

        int choice;
        try{
            choice = methodChoice(args[2]);
        }catch(Exception e){
            System.out.println("Mauvais nom de méthode : "+ args[2] + " au lieu de gluton ou dynamique ou pse)\n");
            return;
        }

        float poids = Float.parseFloat(args[1]);

        SacADos sac = new SacADos(args[0], poids);
        sac.setMethodeSolve(FabriqueSolvingMethode.createSolvingMethode(choice, poids, sac.getTabObjets()));

        try {
            sac.resoudre();
            System.out.println(sac.toString());
        }catch (NoMethodeException e){
            System.out.println("Mauvais nom de méthode : "+ args[2] + " au lieu de gluton ou dynamique ou pse)\n");
        }

    }


}
