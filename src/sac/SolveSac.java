package sac;

import java.util.ArrayList;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 17/10/2021
 * @project pjAAV
 */
public interface SolveSac {

    /**
     * @brief Méthode permettant de résoudre le problème du sac
     */
    void resoudre();

    /**
     * @brief Getter
     * @return Le sac, solution du problème du sac
     */
    ArrayList<Objet> getSolution();
}
