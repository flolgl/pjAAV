import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author LE GAL Florian
 * @date 14/10/2021
 * @project pjAAV
 */
public class PSE {

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
     */
    public void chercherSolution(){
        if (PSE.getValeur(this.valeur) == PSE.borneInferieure)
            PSE.tabMeilleureValeur = new ArrayList<>(this.valeur);

        else {
            if (this.bas != null) {
                this.bas.chercherSolution();
            }
            if (this.haut != null)
                this.haut.chercherSolution();
        }
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


    public ArrayList<Objet> getTabMeilleureValeur(){
        return PSE.tabMeilleureValeur;
    }



    public static void main(String[] args) {

        SacADos sac = new SacADos("C:\\Users\\User\\Desktop\\td-tp\\pjAAV\\input.txt", 3.0f);

        Dynamique dyna = new Dynamique(3.0f, sac.getTabObjets());

        Objet[] tabObj = new Objet[sac.getTabObjets().size()];
        Objet[] listeObj = sac.getTabObjets().toArray(new Objet[0]);

        PSE arbre = new PSE(sac.getTabObjets(), 3.0f, tabObj, 0);

        arbre.chercherSolution();

        float f = 0, e = 0;
        for(Objet obj : dyna.getSac()) {
            f += obj.getPoids();
            e += obj.getValeur();
        }
        ArrayList<Objet> tabSolution = arbre.getTabMeilleureValeur();

        System.out.println("Dyna: " + f + " " + e);

        System.out.println(tabSolution);
        System.out.println(PSE.getPoids(tabSolution));
        System.out.println(PSE.getValeur(tabSolution));
    }

}
