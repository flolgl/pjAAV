package sac;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 18/10/2021
 * @project pjAAV
 */
public class Objet {
    // Nom de l'objet, pour des raisons d'affichage
    private String nom;
    // Poids et valeur (argent) de l'objet
    private double valeur, poids;


    /**
     * @brief Constructeur pour un objet à mettre dans un sac ou non
     * @param valeur La valeur de l'objet
     * @param poids Le poids de l'objet
     * @param nom Le nom de l'objet
     */
    public Objet(double valeur, double poids, String nom){
        this.valeur = valeur;
        this.poids = poids;
        this.nom = nom;
    }

    /**
     * @brief Getter
     * @return La valeur d'un objet divisée par son poids
     */
    public double getRapportVP(){
        return valeur/poids;
    }

    /**
     * @brief Getter
     * @return Le nom de l'objet
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * @brief Getter
     * @return Le poids de l'objet
     */
    public double getPoids(){
        return poids;
    }

    /**
     * @brief Getter
     * @return La valeur de l'objet
     */
    public double getValeur(){
        return valeur;
    }


    /**
     * @brief toString
     * @return Le nom de l'objet avec son poids et sa valeur
     */
    @Override
    public String toString() {
        return "Objet : " + this.nom + " Valeur : " + this.valeur + " Poids : " + this.poids;
    }

}
