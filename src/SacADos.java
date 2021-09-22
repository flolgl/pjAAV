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

    private void traiterObjets(){
        Collections.sort(tabBoites);
        for(Boite b : tabBoites)
            if(this.getPoidsTotal() + b.getPoids() <= this.poidsMaximal)
                sac.add(b);
    }
    
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

    private void getInput(BufferedReader br) throws IOException {
        String s = br.readLine();
        while (s != null && this.isFormatOk(s)){
            //System.out.println(s);
            this.addToSac(s);
            s = br.readLine();
        }
    }

    private boolean isFormatOk(String input){
        /*
        int nbPointVirg = 0;

        for(char c : input.toCharArray())
            if (c == ';')
                nbPointVirg++;
         */
        return Pattern.matches("^[A-Za-z0-9\\s]+; [0-9]+.[0-9]+ ; [0-9]+.[0-9]+", input);
    }

    private void addToSac(String input){
        String[] sTab = input.split(";");
        tabBoites.add(new Boite(Float.parseFloat(sTab[2]), Float.parseFloat(sTab[1]), sTab[0]));
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
        SacADos sac = new SacADos("C:\\Users\\Flori\\Desktop\\pjAAV\\input.txt", 30.0f);
        System.out.println(sac.toString());
    }
}
