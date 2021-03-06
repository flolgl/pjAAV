package solving.methods;

import sac.Objet;
import sac.SolveSac;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 15/10/2021
 * @project pjAAV
 */
public class Glouton implements SolveSac{

    // Le poids max du sac
    private final double poidsMaximal;
    // Le tableau des objets à mettre ou non dans le sac
    private final ArrayList<Objet> tabObjets;
    // Le tableau dynamique stockant le sac solution
    private ArrayList<Objet> sac = new ArrayList<>();

    /**
     * @brief Constructeur de la méthode gloutonne contenant le poids max, la liste des objets
     * à mettre dans le sac et le sac
     * @param poidsMaximal Poids maximum du sac
     * @param tabObjets Liste des objets à mettre dans le sac
     */
    public Glouton(double poidsMaximal, ArrayList<Objet> tabObjets){
        this.poidsMaximal = poidsMaximal;
        this.tabObjets = new ArrayList<>(tabObjets);
    }

    /**
     * @brief Getter
     * @return Le poids total des objets actuellement dans le sac
     */
    public double getPoidsTotal(){
        double poids = 0;
        for(Objet b : sac)
            poids+=b.getPoids();
        return poids;
    }

    /**
     * @brief Méthode de partition de la liste des objets à mettre dans le sac
     * @param indexPremier Indice du premier objet de la partition de la liste d'objets à ranger dans l'ordre décroissant
     * @param indexDernier Indice du dernier objet de la partition de la liste d'objets à ranger dans l'ordre décroissant
     * @return Incrémentation de l'indice afin de mettre fin à la récursivité
     */
    private int partition(int indexPremier, int indexDernier) {
        double pivot = tabObjets.get(indexDernier).getRapportVP();
        int i = (indexPremier - 1);
        for(int j = indexPremier; j <= indexDernier - 1; j++) {
            if(tabObjets.get(j).getRapportVP() > pivot) {
                i++;
                Collections.swap(tabObjets, i, j);
            }
        }
        Collections.swap(tabObjets, i + 1, indexDernier);
        return (i + 1);

    }

    /**
     * @brief Tri rapide avec partition et appel récursif
     * @param indexPremier Indice du premier objet de la liste d'objets à ranger dans l'ordre décroissant
     * @param indexDernier Indice du dernier objet de la liste d'objets à ranger dans l'ordre décroissant
     */
    private void quickSort(int indexPremier, int indexDernier) {
        if(indexPremier < indexDernier) {
            int partitioningIndex = partition(indexPremier, indexDernier);
            quickSort(indexPremier, partitioningIndex - 1);
            quickSort(partitioningIndex + 1, indexDernier);
        }
    }

    @Override
    public void resoudre() {
        this.quickSort(0, tabObjets.size()-1);
        for(Objet b : tabObjets)
            if(this.getPoidsTotal() + b.getPoids() <= this.poidsMaximal)
                sac.add(b);
    }


    @Override
    public ArrayList<Objet> getSolution() {
        return sac;
    }
}
