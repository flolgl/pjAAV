package solving.methods;

import sac.Objet;
import sac.SolveSac;

import java.util.ArrayList;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 14/10/2021
 * @project pjAAV
 */
public class PSE implements SolveSac {

    // Le sac de chaque noeud, autrement dit, la valeur de chaque noeud
    private ArrayList<Objet> sacNoeud;
    // La profondeur de chaque noeud
    private int profondeur;
    // La borne supérieure du noeud, autrement dit, le max possible en prenant en compte les objets dans le sac du noeud + ceux restant
    private double borneSuperieure;
    // La branche inférieure et la branche supérieure
    private PSE bas, haut;

    // Le tableau dynamique static stockant le sac solution
    private static ArrayList<Objet> sacSolution;
    // La borne inférieure de l'arbre, autrement dit, la solution minimale
    private static double borneInferieure;
    // Le poids max du sac
    private static double poidsMaximal;

    /**
     * Constructeur de la classe. Ce dernier ne construit que la racine et appelle le second constructeur
     * @param tabObjets Le tableau des objets à mettre ou non dans le sac
     * @param poidsMaximal Le poids max du sac
     */
    public PSE(ArrayList<Objet> tabObjets, double poidsMaximal){
        PSE.poidsMaximal = poidsMaximal;
        this.profondeur = 0;
        this.sacNoeud = new ArrayList<>();
        this.borneSuperieure = this.getNewBorneSup(tabObjets);
        Glouton gl = new Glouton(poidsMaximal, tabObjets);
        PSE.borneInferieure = gl.getPoidsTotal();

        ArrayList<Objet> nextPSE = new ArrayList<>(1);
        nextPSE.add(tabObjets.get(profondeur));

        this.bas = new PSE(tabObjets, new ArrayList<>(), this);
        //System.out.println();
        this.haut = new PSE(tabObjets, nextPSE, this);

    }

    /**
     * Constructeur privé construisant l'ensemble de l'arbre sauf la racine. Ce constructeur est récursif
     * @param tabObjets Le tableau des objets à mettre ou non dans le sac
     * @param sacNoeud Le sac du nouveau noeud
     * @param root la racine de ce noeud, autrement dit, le noeud avec la profondeur - 1 de celui-ci
     */
    private PSE(ArrayList<Objet> tabObjets, ArrayList<Objet> sacNoeud, PSE root){
        this.profondeur = root.profondeur+1;
        this.sacNoeud = new ArrayList<>(sacNoeud);
        PSE.borneInferieure = this.getNewBorneInf();

        if (profondeur < tabObjets.size()) {

            this.bas = new PSE(tabObjets, this.sacNoeud,this);

            ArrayList<Objet> nextPseSac = getNextSac(tabObjets);

            this.borneSuperieure = this.getNewBorneSup(tabObjets);

            if (PSE.getPoids(nextPseSac)<= poidsMaximal && this.borneSuperieure> PSE.borneInferieure)
                this.haut = new PSE(tabObjets, nextPseSac, this);
        }
    }

    /**
     * Construit le sac du fils supérieur
     * @param tabObjets Le tableau des objets à mettre ou non dans le sac
     * @return Le sac du fils supérieur
     */
    private ArrayList<Objet> getNextSac(ArrayList<Objet> tabObjets){
        ArrayList<Objet> nextSac = new ArrayList<>(this.sacNoeud.size()+1);
        nextSac.addAll(this.sacNoeud);
        nextSac.add(tabObjets.get(profondeur));
        return nextSac;
    }


    /**
     * Permet de connaître la valeur du tableau dynamique envoyé en argument
     * @param tabObjets Le tableau dynamique pour lequel on souhaite connaître la valeur
     * @return La valeur du tableau dynamique
     */
    private static double getValeur(ArrayList<Objet> tabObjets){
        double valeur = 0;
        for (Objet obj : tabObjets)
            valeur+=obj.getValeur();

        return valeur;
    }

    /**
     * Permet de connaître le poids du tableau dynamique envoyé en argument
     * @param tabObjets Le tableau dynamique pour lequel on souhaite connaître le poids
     * @return Le poids du tableau dynamique
     */
    private static double getPoids(ArrayList<Objet> tabObjets){
        double poids = 0;
        for (Objet obj : tabObjets){
            poids+=obj.getPoids();
        }
        return poids;
    }

    /**
     * Permet de calculer la nouvelle borne inférieure
     * @return La nouvelle borne inférieure (elle peut être la même que l'ancienne)
     */
    private double getNewBorneInf(){
        return Math.max(PSE.getValeur(this.sacNoeud), PSE.borneInferieure);
    }

    /**
     * Permet de calculer la nouvelle borne supérieure d'un noeud
     * @param tabObjets Le tableau d'objets (sac) du noeud
     */
    private double getNewBorneSup(ArrayList<Objet> tabObjets){
        double res = 0;
        res += PSE.getValeur(this.sacNoeud); // valeur totale du noeud courant
        for (int i=this.profondeur; i<tabObjets.size(); i++)
            res += tabObjets.get(i).getValeur(); // ajout des valeurs des objets restants

        return res;
    }


    @Override
    public void resoudre() {
        if (PSE.getValeur(this.sacNoeud) == PSE.borneInferieure)
            PSE.sacSolution = new ArrayList<>(this.sacNoeud);
        else {
            if (this.bas != null)
                this.bas.resoudre();
            if (this.haut != null)
                this.haut.resoudre();
        }
    }

    @Override
    public ArrayList<Objet> getSolution() {
        return PSE.sacSolution;
    }


    public static void main(String[] args) {

        sac.SacADos sac = new sac.SacADos("C:\\Users\\User\\Desktop\\td-tp\\pjAAV\\input.txt", 3.0f);

        solving.methods.Dynamique dyna = new solving.methods.Dynamique(8.0f, sac.getTabObjets());

        sac.Objet[] tabObj = new sac.Objet[sac.getTabObjets().size()];
        sac.Objet[] listeObj = sac.getTabObjets().toArray(new sac.Objet[0]);

        //solving.methods.PSE arbre = new solving.methods.PSE(sac.getTabObjets(), 10.0f, new ArrayList<>(), 0);
        solving.methods.PSE arbre = new solving.methods.PSE(sac.getTabObjets(), 8.0f);

        Glouton gl = new Glouton(8.0f,sac.getTabObjets());

        arbre.resoudre();
        gl.resoudre();
        dyna.resoudre();
        double f = 0, e = 0, h=0, g = 0;
        for(sac.Objet obj : dyna.getSolution()) {
            f += obj.getPoids();
            e += obj.getValeur();
        }
        ArrayList<sac.Objet> tabSolution = arbre.getSolution();

        System.out.println("Dyna: " + f + " " + e);
        System.out.println(dyna.getSolution());

        System.out.println("PSE: " + PSE.getPoids(tabSolution) + " " + PSE.getValeur(tabSolution));
        System.out.println(tabSolution);

        for(sac.Objet obj : gl.getSolution()) {
            g += obj.getPoids();
            h += obj.getValeur();
        }
        System.out.println("Glouton: " + g + " " + h);
        System.out.println(gl.getSolution());


    }



}
