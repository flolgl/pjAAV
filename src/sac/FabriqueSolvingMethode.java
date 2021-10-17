package sac;


import solving.methods.Dynamique;
import solving.methods.Gluton;
import solving.methods.PSE;

import java.util.ArrayList;

/**
 * @author LE GAL Florian
 * @date 17/10/2021
 * @project pjAAV
 */
public class FabriqueSolvingMethode {

    public static SolveSac createSolvingMethode(int choice, float poids, ArrayList<Objet> objets){
        if(choice == 1)
            return new Gluton(poids, objets);
        else if (choice == 2)
            return new Dynamique(poids, objets);
        else
            return new PSE(objets,poids,new ArrayList<Objet>(), 0);
    }
}
