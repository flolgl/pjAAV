import java.util.ArrayList;

/**
 * @author LE GAL Florian
 * @date 14/10/2021
 * @project pjAAV
 */
public class PSE {
    private PSE haut, bas;
    private ArrayList<Objet> objets;
    private static float borneInf;
    public static float poidsMaximal;

    public static void setBornes(float borneInf){
        PSE.borneInf = borneInf;
    }


    private float getBorneSup(ArrayList<Objet> objets, int indice){
        float poids = getPoidsTotal();
        for (int i = indice; i<objets.size(); i++)
            poids += objets.get(i).getPoids();
        return poids;
    }


    private float getPoidsTotal(){
        float poids = 0;
        for(Objet obj : objets)
            poids+= obj.getPoids();
        return poids;
    }

    private float getValeurTotale(){
        float poids = 0;
        for(Objet obj : objets)
            poids+= obj.getValeur();
        return poids;
    }


    public PSE() {
        this.haut = null;
        this.bas = null;
        this.objets = new ArrayList<>();
    }


    public PSE(ArrayList<Objet> objets, Objet newObjet) {
        this();
        //System.out.println(objets.toString() + newObjet.toString());

        this.objets.addAll(objets);
        if (newObjet != null)
            this.objets.add(newObjet);
    }


    @Override
    public String toString() {
        return toString("\t");
    }

    public String toString(String s) {
        if (haut !=null) {
            if (bas !=null)
                return(s+ "Objets" + objets.toString() +"\n"+ "branche haute" + haut.toString(s+"\t")+ "branche basse"+ bas.toString(s+"\t"));
            else
                return(s+ "Objets" + objets.toString() +"\n"+ "branche haute" +  haut.toString(s+"\t")+"\n");
        }
        else {
            if (bas != null)
                return (s + "Objets" + objets.toString() + "\n\n" + "branche basse" + bas.toString(s + "\t"));
            else
                return (s + "Objets" + objets.toString() + "\n");
        }
    }

    public static PSE fabriquePSE(PSE pse, ArrayList<Objet> tabObjets, int i){
        //System.out.println(tabObjets);


        if (i == tabObjets.size() ||
                pse.getPoidsTotal()+tabObjets.get(i).getPoids()> poidsMaximal ||
                pse.getBorneSup(tabObjets, i)<PSE.borneInf)
            return pse;
        //System.out.println(i);
        //System.out.println(tabObjets.get(i));

        // TODO: Débug ligne 61
        pse.haut = new PSE(pse.objets, tabObjets.get(i));
        pse.bas = new PSE(pse.objets, null);
        i++;

        System.out.println("born inf = " + borneInf);
        borneInf = Math.max(pse.haut.getPoidsTotal(), borneInf);

        fabriquePSE(pse.haut, tabObjets, i);
        fabriquePSE(pse.bas, tabObjets, i); // faire debug

        return pse;
    }

    public static float getBestSolution(PSE pse){
        float max = pse.getPoidsTotal();
        if (pse.haut != null){
            float h = getBestSolution(pse.haut);
            max = Math.max(max, h);
        }
        if (pse.bas != null){
            float b = getBestSolution(pse.bas);
            max = Math.max(max, b);
        }
        return max;

    }

    public static float getBestValeur(PSE pse){
        float max = pse.getValeurTotale();
        if (pse.haut != null){
            float h = getBestValeur(pse.haut);
            max = Math.max(max, h);
        }
        if (pse.bas != null){
            float b = getBestValeur(pse.bas);
            max = Math.max(max, b);
        }
        return max;

    }

    public static void main(String[] args) {
        PSE pse = new PSE();
        SacADos sac = new SacADos("C:\\Users\\User\\Desktop\\td-tp\\pjAAV\\input.txt", 3.0f);
        PSE.setBornes(new Gluton(3.0f, sac.getTabObjets()).getPoidsTotal());
        PSE.poidsMaximal = 3.0f;

        Dynamique dyna = new Dynamique(3.0f, sac.getTabObjets());

        float f = 0, e = 0;
        for(Objet obj : dyna.getSac()) {
            f += obj.getPoids();
            e += obj.getValeur();
        }
        //SacADos sac = new SacADos(3.0f);
        int i = 0;

        //System.out.println(sac.getTabObjets());

        PSE newPse = fabriquePSE(pse, sac.getTabObjets(), i);
        System.out.println(newPse.toString());
        System.out.println("PSE: " + PSE.getBestSolution(newPse) + " " + PSE.getBestValeur(newPse));
        System.out.println("Dyna: " + f + " " + e);


    }

}
