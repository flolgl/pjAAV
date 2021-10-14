import java.util.ArrayList;

/**
 * @author LE GAL Florian
 * @date 14/10/2021
 * @project pjAAV
 */
public class PSE {
    private PSE haut, bas;
    private ArrayList<Objet> objets;

    public PSE() {
        this.haut = null;
        this.bas = null;
        this.objets = new ArrayList<>();
    }

    public PSE(Objet objet) {
        this();
        this.objets.add(objet);
    }

    public PSE(ArrayList<Objet> objets) {
        this();
        this.objets.addAll(objets);
    }

    public PSE(Objet objets, PSE haut, PSE bas) {
        this(objets);
        this.haut = haut;
        this.bas = bas;
    }

    @Override
    public String toString() {
        return toString("\t");
    }

    public String toString(String s) {
        if (haut !=null) {
            if (bas !=null)
                return(s+ objets.toString() +"\n"+ haut.toString(s+"\t")+ bas.toString(s+"\t"));
            else
                return(s+ objets.toString() +"\n"+ haut.toString(s+"\t")+"\n");
        }
        else {
            if (bas != null)
                return (s + objets.toString() + "\n\n" + bas.toString(s + "\t"));
            else
                return (s + objets.toString() + "\n");
        }
    }

    public static PSE fabriquePSE(PSE pse, ArrayList<Objet> tabObjets, int i){
        if (i == tabObjets.size())
            return pse;

        // TODO: Débug ligne 61
        pse.bas = new PSE(pse.objets);
        pse.haut = new PSE(pse.objets);
        System.out.println(tabObjets.get(i));
        Objet obj = tabObjets.get(i);
        pse.haut.objets.add(obj);
        i++;
        fabriquePSE(pse.bas, pse.objets, i); // faire debug
        fabriquePSE(pse.haut, pse.haut.objets, i);

        return pse;
    }

    public static void main(String[] args) {
        PSE pse = new PSE();
        SacADos sac = new SacADos("C:\\Users\\User\\Desktop\\td-tp\\pjAAV\\input.txt", 3.0f);
        int i = 0;


        PSE newPse = fabriquePSE(pse, sac.getTabObjets(), i);
        System.out.println(newPse.toString());


    }

}
