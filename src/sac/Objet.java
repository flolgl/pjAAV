package sac;

/**
 * @author LE GAL Florian, BEN FRAJ Ayoub
 * @date 18/10/2021
 * @project pjAAV
 */
public class Objet implements Comparable {
    private String nom;
    private float valeur, poids;
    private static int id = 0;
    private int indice;

    /**
     * @brief Constructeur pour un objet à mettre dans un sac ou non
     * @param valeur La valeur de l'objet
     * @param poids Le poids de l'objet
     * @param nom Le nom de l'objet
     */
    public Objet(float valeur, float poids, String nom){
        this.valeur = valeur;
        this.poids = poids;
        this.nom = nom;
        indice = id++;
    }

    /**
     * @brief Getter
     * @return La valeur d'un objet divisée par son poids
     */
    public float getRapportVP(){
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
    public float getPoids(){
        return poids;
    }

    /**
     * @brief Getter
     * @return La valeur de l'objet
     */
    public float getValeur(){
        return valeur;
    }


    /**
     * @brief toString
     * @return L'identifiant de l'objet
     */
    @Override
    public String toString() {
        return "["+indice+"]";
    }

    /**
     *
     * @param o L'objet compareur
     * @return -1 si inférieur au compareur, 0 si égal et 1 si supérieur
     */
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
