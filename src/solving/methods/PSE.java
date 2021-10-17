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


    /**
     *
     * @param listeObjetsSac
     * @param poidsLimite
     * @param tabObj
     * @param i
     */
    public PSE(ArrayList<Objet> listeObjetsSac, float poidsLimite, Objet[] tabObj, int i){
        if (i <= listeObjetsSac.size()) {

            this.valeur = new ArrayList<>();
            this.valeur.addAll(Arrays.asList(tabObj));

            this.profondeur = i;
            this.calculBorneSuperieure(listeObjetsSac);
            this.calculBorneInferieure();

            if (i != listeObjetsSac.size()){
                this.bas = new PSE(listeObjetsSac, poidsLimite, tabObj, i+1);

                tabObj[i] = listeObjetsSac.get(i);
                if (this.poidsListeObjets(tabObj)<=poidsLimite && this.borneSuperieure> PSE.borneInferieure){
                    // vérification pour raccourcir l'arbre (les combinaisons sans intérêts ne sont pas créées)
                    this.haut = new PSE(listeObjetsSac, poidsLimite, tabObj, i+1);
                }
                tabObj[i] = null; // pour supprimer le dernier objet dans tabObj MAIS AUSSI dans this.value (car référence)
            }

        }
    }

    /**
     *
     * @param objets
     * @return
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
     *
     * @param objets
     * @return
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
     *
     */
    public void calculBorneInferieure(){
        if (PSE.getValeur(this.valeur)> PSE.borneInferieure)
            PSE.borneInferieure = PSE.getValeur(this.valeur);
    }


    /**
     *
     * @param listeObjets
     * @return
     */
    public float poidsListeObjets(Objet[] listeObjets){
        float res=0.0f;
        for (Objet listeObjet : listeObjets) {
            if (listeObjet != null) {
                res += listeObjet.getPoids();
            }
        }
        return res;
    }

    /**
     *
     * @param listeObjetsSac
     */
    public void calculBorneSuperieure(Objet[] listeObjetsSac){
        float res = 0.0f;
        res += PSE.getValeur(this.valeur); // valeur totale du noeud courant
        for (int i=this.profondeur; i<listeObjetsSac.length; ++i){
            res += listeObjetsSac[i].getValeur(); // ajout des valeurs des objets restants
        }
        this.borneSuperieure = res;
    }

    /**
     *
     * @param listeObjetsSac
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
