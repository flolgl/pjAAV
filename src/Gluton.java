import java.util.ArrayList;
import java.util.Collections;

/**
 * @author LE GAL Florian
 * @date 15/10/2021
 * @project pjAAV
 */
public class Gluton {

    private final float poidsMaximal;
    private final ArrayList<Objet> tabObjets;
    private ArrayList<Objet> sac = new ArrayList<>();

    public Gluton(float poidsMaximal, ArrayList<Objet> tabObjets){
        this.poidsMaximal = poidsMaximal;
        this.tabObjets = new ArrayList<>(tabObjets);
        this.traiterObjets();
    }

    public ArrayList<Objet> getSac() {
        return sac;
    }

    /**
     * Première méthode de traitement
     */
    private void traiterObjets(){
        this.quickSort(0, tabObjets.size()-1);
        for(Objet b : tabObjets)
            if(this.getPoidsTotal() + b.getPoids() <= this.poidsMaximal)
                sac.add(b);
    }


    public float getPoidsTotal(){
        float poids = 0;
        for(Objet b : sac)
            poids+=b.getPoids();
        return poids;
    }

    /**
     *
     * @param indexPremier
     * @param indexDernier
     * @return
     */
    private int partition(int indexPremier, int indexDernier) {
        float pivot = tabObjets.get(indexDernier).getRapportVP();
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
     * Tri rapide
     * @param indexPremier
     * @param indexDernier
     */
    public void quickSort(int indexPremier, int indexDernier) {
        if(indexPremier < indexDernier) {
            int partitioningIndex = partition(indexPremier, indexDernier);
            quickSort(indexPremier, partitioningIndex - 1);
            quickSort(partitioningIndex + 1, indexDernier);
        }
    }

}
