import java.util.Scanner;

public class ReadInput {

    public static int functionChoice(String function) {
        return function.equals("resoudre-sac-a-dos") ? 0 : -1;
    }

    public static void pathChoice(String path) {
        InputParser.isFormatOk(path);
        InputParser.getFile(path);
    }

    public static int methodChoice(String method) throws Exception{
        switch(method){
            case "glouton":
                return 1;
            case "dynamique":
                return 2;
            case "pse":
            case "PSE":
                return 3;
            default:
                throw new Exception("Méthode inconnue");
        }
    }

    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.print("$>");
        String input = sc.nextLine(); //input
        String[] inputArgs = input.split(" ");                //split

        if(inputArgs.length != 4)
            throw new Exception("nombre d'arguments incorrect");    //vérification du nombre d'arguments

        if(functionChoice(inputArgs[0]) == -1)
            throw new Exception("fonction incorrecte");             //vérification du nom de la fonction

        String path = inputArgs[1];
        pathChoice(path);                                           //vérification du chemin

        float weightChoice = Float.parseFloat(inputArgs[2]);        //vérification du poids max

        int methodNumber = methodChoice(inputArgs[3]);              //vérification de la méthode choisie

        SacADos sac = new SacADos(path, weightChoice);
        System.out.println(sac);
        // si methodNumber == 1, faire gluton, == 2 faire dyna et == 3 faire PSE

    }

}
