import java.util.ArrayList;

public class SacADos {

    private ArrayList<Objet> tabObjets;
    private float poidsMaximal;
    private ArrayList<Objet> sac = new ArrayList<>();

    public SacADos(){
        tabObjets = new ArrayList<>();
        poidsMaximal = -1;
    }

    public SacADos(String chemin, float poidsMaximal){
        this();
        this.poidsMaximal = poidsMaximal;
        this.tabObjets = InputParser.getObjetsFromInput(chemin);

        //sac = new Gluton(poidsMaximal, tabObjets).getSac();
        //sac = new Dynamique(poidsMaximal, tabObjets).getSac();

        //System.out.println(sac);
    }

    public void glutonSolve(){
        sac = new Gluton(poidsMaximal, tabObjets).getSac();
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

    public static void main(String[] args) {
        SacADos sac = new SacADos("C:\\Users\\User\\Desktop\\td-tp\\pjAAV\\input.txt", 3.0f);
        System.out.println(sac.toString());
    }


}
