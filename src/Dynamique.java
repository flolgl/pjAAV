import java.util.ArrayList;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 15/10/2021
 * @project pjAAV
 */
public class Dynamique {


    private final float poidsMaximal;
    private final ArrayList<Objet> tabObjets;
    private ArrayList<Objet> sac = new ArrayList<>();

    /**
     * @brief Constructeur de la méthode dynamique contenant le poids max, la liste des objets
     * à mettre dans le sac et le sac
     * @param poidsMaximal
     * @param tabObjets
     */
    public Dynamique(float poidsMaximal, ArrayList<Objet> tabObjets){
        this.poidsMaximal = poidsMaximal;
        this.tabObjets = new ArrayList<>(tabObjets);
        this.traiterObjets();
    }

    /**
     * @brief Getter
     * @return Le sac de la méthode dynamique
     */
    public ArrayList<Objet> getSac() {
        return sac;
    }

    /**
     * @brief Méthode permettant de résoudre le problème du sac de façon dynamique
     */
    private void traiterObjets() {

        int poidsMax = (int)(poidsMaximal*10);
        float[][] matrice = this.fillMatrice();

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

    /**
     * @brief Remplissage de la matrice pour la méthode dynamique
     * @return La matrice remplie
     */
    private float[][] fillMatrice(){

        int poidsMax = (int)(poidsMaximal*10);

        float[][] matrice = new float[tabObjets.size()][poidsMax+1];

        // Ajout dans première ligne
        for(int j= 0; j<poidsMax+1; j++){
            if((int)(tabObjets.get(0).getPoids()*10) > j)
                matrice[0][j] = 0;
            else
                matrice[0][j] = tabObjets.get(0).getValeur();
        }

        // Ajout dans les autres lignes
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
    private int benefOptimal(int i, int j, float[][] matrice){
        while(matrice[i][j] == matrice[i][j-1])
            j--;
        return j;
    }


}
