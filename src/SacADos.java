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
            this.traiterObjets();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sac);
    }

    private float getPoidsTotal(){
        float poids = 0;
        for(Boite b : sac)
            poids+=b.getPoids();
        return poids;
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
            System.out.println(s);
            //System.out.println(s);
            this.addToSac(s);
            s = br.readLine();
        }
    }

    private void traiterObjetsDyna(){
        float[][] tab = new float[tabBoites.size()][(int)(poidsMaximal+1)];
        for(int j = 0; j<poidsMaximal; j++){
            if(tabBoites.get(0).getPoids()>j)
                tab[0][j] = 0;
            else
                tab[0][j] = tabBoites.get(0).getValeur();
        }

        for(int i = 1; i<tab.length; i++){
            for(int j = 0; j<poidsMaximal; j++){
                if(tabBoites.get(i).getPoids()>j)
                    tab[i][j] = tab[i-1][j];
                else
                    tab[i][j] = maximum(tab[i-1][j], tab[i-1][j-(int)tabBoites.get(i).getPoids()] + tabBoites.get(i).getValeur());

            }
        }

    }

    private float maximum(float premierChoix, float secondChoix){
        return premierChoix>secondChoix?premierChoix:secondChoix;
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

        sb.append("Poids total: ").append(this.getPoidsTotal());

        return sb.toString();
    }

    public static void main(String[] args) {
        SacADos sac = new SacADos("C:\\Users\\ayoub\\OneDrive\\Documents\\GitHub\\pjAAV\\input.txt", 30.0f);
        System.out.println(sac.toString());
    }

    //bug : chips pas pris récupérer dans input.txt
    // j'ai mis le qsort dans le constructeur avant de print, tu peux le déplacer si tu veux le mettre autre part
}
