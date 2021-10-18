package solving.methods;

import java.util.ArrayList;
import sac.Objet;
import sac.SolveSac;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 15/10/2021
 * @project pjAAV
 */
public class Dynamique implements SolveSac {

    // Le poids max du sac
    private final double poidsMaximal;
    // Le tableau des objets à mettre ou non dans le sac
    private final ArrayList<Objet> tabObjets;
    // Le tableau dynamique stockant le sac solution
    private ArrayList<Objet> sac = new ArrayList<>();

    /**
     * @brief Constructeur de la méthode dynamique contenant le poids max, la liste des objets
     * à mettre dans le sac et le sac
     * @param poidsMaximal Le poids max du sac
     * @param tabObjets Le tableau des objets à mettre ou non dans le sac
     */
    public Dynamique(double poidsMaximal, ArrayList<Objet> tabObjets){
        this.poidsMaximal = poidsMaximal;
        this.tabObjets = new ArrayList<>(tabObjets);
    }


    /**
     * @brief Remplissage de la matrice pour la méthode dynamique
     * @return La matrice remplie
     */
    private double[][] fillMatrice(){

        int poidsMax = (int)(poidsMaximal*10);

        double[][] matrice = new double[tabObjets.size()][poidsMax+1];

        for(int j= 0; j<poidsMax+1; j++){
            if((int)(tabObjets.get(0).getPoids()*10) > j)
                matrice[0][j] = 0;
            else
                matrice[0][j] = tabObjets.get(0).getValeur();
        }

        for(int i = 1; i< tabObjets.size(); i++){
            for(int j= 0; j<poidsMax+1; j++){
                if((int)(tabObjets.get(i).getPoids()*10) > j)
                    matrice[i][j] = matrice[i-1][j];
                else
                    matrice[i][j] = Math.max(matrice[i-1][j], matrice[i-1][j-(int)(tabObjets.get(i).getPoids()*10)] + tabObjets.get(i).getValeur());
            }
        }

        return matrice;

    }

    /**
     * Permet de récupérer le poids minimum pour un benef max
     * @param i Dernier indice de la liste d'objets (ligne avec les plus gros benefs)
     * @param j L'indice représentant les poids
     * @param matrice La matrice déjà remplie
     * @return Le poids minimum pour une valeur max
     */
    private int benefOptimal(int i, int j, double[][] matrice){
        while(matrice[i][j] == matrice[i][j-1])
            j--;
        return j;
    }


    @Override
    public void resoudre() {
        int poidsMax = (int)(poidsMaximal*10);
        double[][] matrice = this.fillMatrice();

        int i = tabObjets.size() - 1;
        int j = this.benefOptimal(i, poidsMax, matrice);

        while(j > 0) {
            while (i > 0 && matrice[i][j] == matrice[i - 1][j])
                i--;
            j = j - (int)(tabObjets.get(i).getPoids() * 10);
            if (j >= 0)
                sac.add(tabObjets.get(i));
            i--;
        }
    }

    @Override
    public ArrayList<Objet> getSolution() {
        return sac;
    }
}
