package sac;

import app.InputParser;
import solving.methods.Gluton;

import java.util.ArrayList;

public class SacADos {

    private ArrayList<Objet> tabObjets;
    private float poidsMaximal;
    private ArrayList<Objet> sac = new ArrayList<>();
    private SolveSac methodeSolve = null;

    public SacADos(){
        tabObjets = new ArrayList<>();
        poidsMaximal = -1;
    }

    public SacADos(String chemin, float poidsMaximal){
        this();
        this.poidsMaximal = poidsMaximal;
        this.tabObjets = InputParser.getObjetsFromInput(chemin);

        //sac = new solving.methods.Gluton(poidsMaximal, tabObjets).getSac();
        //sac = new solving.methods.Dynamique(poidsMaximal, tabObjets).getSac();

        //System.out.println(sac);
    }

    public void setMethodeSolve(SolveSac methode){
        methodeSolve = methode;
    }

    public void resoudre(int choice) throws NoMethodeException {
        if (methodeSolve == null)
            throw new NoMethodeException();
        methodeSolve.resoudre();
        sac = methodeSolve.getSolution();
    }


    public ArrayList<Objet> getTabObjets() {
        return tabObjets;
    }

    private float getPoidsTotal(){
        float poids = 0;
        for(Objet b : sac)
            poids+=b.getPoids();
        return poids;
    }
    
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

    /*
    public static void main(String[] args) {
        sac.SacADos sac = new sac.SacADos(".\\input.txt", 3.0f);
        System.out.println(sac.toString());
    }

     */


}
