package solving.methods;

import sac.Objet;
import sac.SolveSac;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 14/10/2021
 * @project pjAAV
 */
public class PSE implements SolveSac {

    private ArrayList<Objet> valeur;
    private int profondeur;
    private float borneSuperieure;
    private PSE bas, haut;

    private static ArrayList<Objet> tabMeilleureValeur;
    private static float borneInferieure;


    /** @brief Constructeur de la méthode PSE contenant le poids max du sac, la liste des objets dans le sac, les objets à
     * mettre dans le sac et un indice indiquant la taille de la branche de l'arbre
     * @param listeObjetsSac Les objets dans le sac
     * @param poidsLimite Le poids max du sac
     * @param tabObj Les objets à mettre dans le sac
     * @param idx La taille de la branche
     */
    public PSE(ArrayList<Objet> listeObjetsSac, float poidsLimite, ArrayList<Objet> tabObj, int idx){
        if (idx <= listeObjetsSac.size()) {

            this.valeur = new ArrayList<>();
            this.valeur.addAll(tabObj);

            this.profondeur = idx;

            this.calculBorneInferieure();
            this.calculBorneSuperieure(listeObjetsSac);


            if (idx != listeObjetsSac.size())
            {
                this.bas = new PSE(listeObjetsSac, poidsLimite, tabObj, idx+1);

                tabObj.set(idx, listeObjetsSac.get(idx));
                if (this.poidsListeObjets(tabObj) <= poidsLimite && this.borneSuperieure> PSE.borneInferieure){
                    this.haut = new PSE(listeObjetsSac, poidsLimite, tabObj, idx+1);
                }
                tabObj.set(idx, null);
            }

        }
    }

    /**
     * @brief Getter
     * @param objets Tous les objets (dans le sac ou non)
     * @return La valeur de la totalité des objets
     */
    public static float getValeur(ArrayList<Objet> objets){
        float valeur = 0;
        for (Objet obj : objets){
            if (obj != null)
                valeur+=obj.getValeur();
        }
        return valeur;
    }

    /**
     * @brief Getter
     * @param objets Tous les objets (dans le sac ou non)
     * @return Le poids de la totalité des objets
     */
    public static float getPoids(ArrayList<Objet> objets){
        float valeur = 0;
        for (Objet obj : objets){
            if (obj != null)
                valeur+=obj.getPoids();
        }
        return valeur;
    }

    /**
     * @brief Méthode qui permet de calculer une borne inférieure du PSE
     */
    public void calculBorneInferieure(){
        if (PSE.getValeur(this.valeur)> PSE.borneInferieure)
            PSE.borneInferieure = PSE.getValeur(this.valeur);
    }


    /**
     * @brief Méthode  qui permet de calculer le poids d'un tableau d'objets
     * @param listeObjets Le tableau d'objets
     * @return Le poids total du tableau d'objets
     */
    public float poidsListeObjets(ArrayList<Objet> listeObjets){
        float res=0.0f;
        for (Objet listeObjet : listeObjets) {
            if (listeObjet != null) {
                res += listeObjet.getPoids();
            }
        }
        return res;
    }

    /**
     * @brief Méthode permettant de calculer une borne supérieure pour une liste d'objets
     * @param listeObjetsSac La liste d'objets
     */
    public void calculBorneSuperieure(ArrayList<Objet> listeObjetsSac){
        float res = 0.0f;
        res += PSE.getValeur(this.valeur); // valeur totale du noeud courant
        for (int i=this.profondeur; i<listeObjetsSac.size(); i++)
            res += listeObjetsSac.get(i).getValeur(); // ajout des valeurs des objets restants

        this.borneSuperieure = res;
    }


    @Override
    public void resoudre() {
        if (PSE.getValeur(this.valeur) == PSE.borneInferieure)
            PSE.tabMeilleureValeur = new ArrayList<>(this.valeur);

        else {
            if (this.bas != null) {
                this.bas.resoudre();
            }
            if (this.haut != null)
                this.haut.resoudre();
        }
    }

    @Override
    public ArrayList<Objet> getSolution() {
        PSE.removeNull();
        return PSE.tabMeilleureValeur;
    }

    /**
     * @brief Méthode permettant de retirer les objets null du tableau aux meilleures valeurs
     */
    public static void removeNull(){
        ArrayList<Objet> sol = new ArrayList<>();
        for(Objet objet : tabMeilleureValeur){
            if (objet != null)
                sol.add(objet);
        }
        tabMeilleureValeur = sol;
            
    }

/*
    public static void main(String[] args) {

        sac.SacADos sac = new sac.SacADos("C:\\Users\\User\\Desktop\\td-tp\\pjAAV\\input.txt", 3.0f);

        solving.methods.Dynamique dyna = new solving.methods.Dynamique(3.0f, sac.getTabObjets());

        sac.Objet[] tabObj = new sac.Objet[sac.getTabObjets().size()];
        sac.Objet[] listeObj = sac.getTabObjets().toArray(new sac.Objet[0]);

        solving.methods.PSE arbre = new solving.methods.PSE(sac.getTabObjets(), 3.0f, tabObj, 0);

        arbre.chercherSolution();

        float f = 0, e = 0;
        for(sac.Objet obj : dyna.getSac()) {
            f += obj.getPoids();
            e += obj.getValeur();
        }
        ArrayList<sac.Objet> tabSolution = arbre.getTabMeilleureValeur();

        System.out.println("Dyna: " + f + " " + e);

        System.out.println(tabSolution);
        System.out.println(solving.methods.PSE.getPoids(tabSolution));
        System.out.println(solving.methods.PSE.getValeur(tabSolution));
    }

 */

}
