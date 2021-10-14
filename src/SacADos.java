import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class SacADos {

    private ArrayList<Boite> tabBoites;
    private float poidsMaximal;
    private ArrayList<Boite> sac = new ArrayList<>();

    public SacADos(){
        tabBoites = new ArrayList<>();
        poidsMaximal = -1;
    }

    public SacADos(String chemin, float poidsMaximal){
        this();
        this.poidsMaximal = poidsMaximal;

        BufferedReader br = getFile(chemin);
        try {
            this.getInput(br);
            //this.traiterObjets();
            this.traiterObjetsDynamique();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(sac);
    }

    private float getPoidsTotal(){
        float poids = 0;
        for(Boite b : sac)
            poids+=b.getPoids();
        return poids;
    }
    
    private float getValeurTotale(){
        float valeur = 0;
        for(Boite b : sac)
            valeur+=b.getValeur();
        return valeur;
    }

    /**
     * Première méthode de traitement
     */
    private void traiterObjets(){
        this.quickSort(0, tabBoites.size()-1);
        for(Boite b : tabBoites)
            if(this.getPoidsTotal() + b.getPoids() <= this.poidsMaximal)
                sac.add(b);
    }

    /**
     * Récupère le fichier d'input
     * @param chemin le chemin vers le fichier
     * @return le fichier transformé en lecteur de buffer
     */
    private BufferedReader getFile(String chemin){
        FileReader file = null;
        BufferedReader br = null;
        try {
            file = new FileReader(chemin);
            br = new BufferedReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return br;
    }


    /**
     * Récupère l'input dans une string
     * @param br le lecteur du buffer
     * @throws IOException s'il y a un problème dans la lecture
     */
    private void getInput(BufferedReader br) throws IOException {
        String s = br.readLine();
        while (s != null && this.isFormatOk(s)){
            //System.out.println(s);
            this.addToSac(s);
            s = br.readLine();
        }
    }


    /**
     * Remplissage de la matrice pour la méthode dynamique
     * @return La matrice remplie
     */
    private float[][] fillMatrice(){

        int poidsMax = (int)(poidsMaximal*10);

        float[][] matrice = new float[tabBoites.size()][poidsMax+1];

        // Ajout dans première ligne
        for(int j= 0; j<poidsMax+1; j++){
            if((int)(tabBoites.get(0).getPoids()*10) > j)
                matrice[0][j] = 0;
            else
                matrice[0][j] = tabBoites.get(0).getValeur();
        }

        // Ajout dans les autres lignes
        for(int i= 1; i<tabBoites.size(); i++){
            for(int j= 0; j<poidsMax+1; j++){
                if((int)(tabBoites.get(i).getPoids()*10) > j)
                    matrice[i][j] = matrice[i-1][j];
                else
                    matrice[i][j] = Math.max(matrice[i-1][j], matrice[i-1][j-(int)(tabBoites.get(i).getPoids()*10)] + tabBoites.get(i).getValeur());
            }
        }

        return matrice;

    }

    /**
     * Permet de récupérer le poids minimum pour un benef max
     * @param i dernier indice du tab des objets (ligne avec les plus gros benefs)
     * @param j l'indice représentant les poids
     * @param matrice la matrice déjà remplie
     * @return le poids minimum pour une valeur max
     */
    private int benefOptimal(int i, int j, float[][] matrice){
        while(matrice[i][j] == matrice[i][j-1])
            j--;
        return j;
    }

    /**
     * Méthode permettant de résoudre le problème du sac de façon dynamique
     */
    private void traiterObjetsDynamique() {

        int poidsMax = (int)(poidsMaximal*10);
        float[][] matrice = this.fillMatrice();

        int i = tabBoites.size() - 1;
        int j = this.benefOptimal(i, poidsMax, matrice);

        while(j > 0) {
            while (i > 0 && matrice[i][j] == matrice[i - 1][j])
                i--;
            j = j - (int)(tabBoites.get(i).getPoids() * 10);
            if (j >= 0)
                    sac.add(tabBoites.get(i));
            i--;
        }

    }


    /**
     * Vérification du format de la ligne d'input
     * @param input la ligne d'input
     * @return true si la ligne d'input respecte le format nom ; poids ; valeur
     */
    private boolean isFormatOk(String input){
        return Pattern.matches("^\\s*[A-Za-z0-9\\s]+; [0-9]+.[0-9]+ ; [0-9]+.[0-9]+\\s*", input);
    }

    /**
     * Ajoute l'input au tableau tabBoites
     * @param input la string venant de l'input
     */
    private void addToSac(String input){
        String[] sTab = input.split(";");
        tabBoites.add(new Boite(Float.parseFloat(sTab[2]), Float.parseFloat(sTab[1]), sTab[0]));
    }

    /**
     *
     * @param indexPremier
     * @param indexDernier
     * @return
     */
    private int partition(int indexPremier, int indexDernier) {
        float pivot = tabBoites.get(indexDernier).getRapportVP();
        int i = (indexPremier - 1);
        for(int j = indexPremier; j <= indexDernier - 1; j++) {
            if(tabBoites.get(j).getRapportVP() > pivot) {
                i++;
                Collections.swap(tabBoites, i, j);
            }
        }
        Collections.swap(tabBoites, i + 1, indexDernier);
        return (i + 1);

    }

    /**
     * Tri rapide
     * @param indexPremier
     * @param indexDernier
     */
    public void quickSort(int indexPremier, int indexDernier) {
        if(indexPremier < indexDernier) {
            int partitioningIndex = partition(indexPremier, indexDernier);
            quickSort(indexPremier, partitioningIndex - 1);
            quickSort(partitioningIndex + 1, indexDernier);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tab des boites: \n");
        for(Boite b : tabBoites)
            sb.append(b.getNom()).append("\n");

        sb.append("\n\n").append("Sac à dos: \n");
        for(Boite b : sac)
            sb.append(b.getNom()).append("\n");

        sb.append("Poids total: ").append(this.getPoidsTotal()).append("\n");
        sb.append("Valeur totale: ").append(this.getValeurTotale());

        return sb.toString();
    }

    public static void main(String[] args) {
        SacADos sac = new SacADos("C:\\Users\\ayoub\\OneDrive\\Documents\\GitHub\\pjAAV\\input.txt", 30.0f);
        System.out.println(sac.toString());
    }


}
