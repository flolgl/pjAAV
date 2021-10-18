package sac;


import solving.methods.Dynamique;
import solving.methods.Glouton;
import solving.methods.PSE;

import java.util.ArrayList;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 17/10/2021
 * @project pjAAV
 */
public class FabriqueSolvingMethode {

    /**
     * @brief Permet la création de la méthode et sa résolution
     * @param choice Le numéro de la méthode choisie
     * @param poids Le poids du sac
     * @param objets Les objets à mettre dans le sac (ou non)
     * @return La méthode choisie avec le poids maximum et la liste d'objets à mettre dans le sac (non)
     */
    public static SolveSac createSolvingMethode(int choice, double poids, ArrayList<Objet> objets){
        if(choice == 1)
            return new Glouton(poids, objets);
        else if (choice == 2)
            return new Dynamique(poids, objets);
        else
            //return new PSE(objets,poids, new ArrayList<>(), 0);
            return new PSE(objets,poids);
    }
}
