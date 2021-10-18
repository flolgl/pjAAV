package sac;

public class Objet implements Comparable {
    private String nom;
    private float valeur, poids;
    private static int id = 0;
    private int indice;

    public Objet(float valeur, float poids, String nom){
        this.valeur = valeur;
        this.poids = poids;
        this.nom = nom;
        indice = id++;
    }

    public float getRapportVP(){
        return valeur/poids;
    }

    public String getNom(){
        return this.nom;
    }

    public float getPoids(){
        return poids;
    }

    public float getValeur(){
        return valeur;
    }



    @Override
    public String toString() {
        return "["+indice+"]";
    }

    @Override
    public int compareTo(Object o) {
        Objet b = (Objet)o;
        float compa = this.getRapportVP() - b.getRapportVP();
        if (compa == 0.0f)
            return 0;
        else
            return compa < 0 ? -1 : 1;
    }
}