package sac;

import java.util.ArrayList;

/**
 * @author LE GAL Florian
 * @date 17/10/2021
 * @project pjAAV
 */
public interface SolveSac {

    /**
     * @brief Méthode permettant de résoudre le problème du sac avec la solution choisie
     */
    void resoudre();

    /**
     * @brief Getter
     * @return Le sac de la solution choisie
     */
    ArrayList<Objet> getSolution();
}
