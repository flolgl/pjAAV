package sac;

import app.InputParser;
import solving.methods.Gluton;

import java.util.ArrayList;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 18/10/2021
 * @project pjAAV
 */
public class SacADos {

    private ArrayList<Objet> tabObjets;
    private float poidsMaximal;
    private ArrayList<Objet> sac = new ArrayList<>();
    private SolveSac methodeSolve = null;

    /**
     * @brief Constructeur du problème du sac à dos
     */
    public SacADos(){
        tabObjets = new ArrayList<>();
        poidsMaximal = -1;
    }

    /**
     * @brief Constructeur du problème du sac à dos
     * @param chemin Le chemin absolu vers le fichier contenant les objets
     * @param poidsMaximal Le poids maximum que peut supporter le sac
     */
    public SacADos(String chemin, float poidsMaximal){
        this();
        this.poidsMaximal = poidsMaximal;
        this.tabObjets = InputParser.getObjetsFromInput(chemin);
    }

    /**
     * @brief Setter
     * @param methode La méthode à résoudre
     */
    public void setMethodeSolve(SolveSac methode){
        methodeSolve = methode;
    }

    /**
     * @brief Méthode qui fait appel à l'algorithme en fonction de la méthode choisie précédemment
     * @throws NoMethodeException Méthode choisie inexistante
     */
    public void resoudre() throws NoMethodeException {
        if (methodeSolve == null)
            throw new NoMethodeException();
        methodeSolve.resoudre();
        sac = methodeSolve.getSolution();
    }


    /**
     * @brief Getter
     * @return La liste d'objet à mettre dans le sac (ou non)
     */
    public ArrayList<Objet> getTabObjets() {
        return tabObjets;
    }

    /**
     * @brief Getter
     * @return Le poids total actuel du sac
     */
    private float getPoidsTotal(){
        float poids = 0;
        for(Objet b : sac)
            poids+=b.getPoids();
        return poids;
    }

    /**
     * @brief Getter
     * @return La valeur totale actuelle dans le sac
     */
    private float getValeurTotale(){
        float valeur = 0;
        for(Objet b : sac)
            valeur+=b.getValeur();
        return valeur;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tab des boites: \n");
        for(Objet b : tabObjets)
            sb.append(b.getNom()).append("\n");

        sb.append("\n\n").append("Sac à dos: \n");
        for(Objet b : sac)
            sb.append(b.getNom()).append("\n");

        sb.append("Poids total: ").append(this.getPoidsTotal()).append("\n");
        sb.append("Valeur totale: ").append(this.getValeurTotale());

        return sb.toString();
    }


}
