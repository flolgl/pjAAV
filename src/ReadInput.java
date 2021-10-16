import java.util.Scanner;

public class ReadInput {

    public static int functionChoice(String function) {
        if(function.equals("resoudre-sac-a-dos"))
            return 0;
        return -1;
    }

    public static void methodChoice(String method) throws Exception{
        switch(method){
            case "glouton":
                System.out.println("GLOUT");
                break;
            case "dynamique":
                System.out.println("DYNA");
                break;
            case "pse":
            case "PSE":
                System.out.println("PSE");
                break;
            default:
                throw new Exception("Méthode inconnue");
        }
    }

    // path regex :    "([a-zA-Z]:)?(\\[a-zA-Z0-9_-]+)+\\?"

    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.print("$>");

        String input = sc.nextLine(); //input
        String[] inputArgs = input.split(" ");                //split
        if(inputArgs.length != 4)
            throw new Exception("nombre d'arguments incorrect");    //vérification du nombre d'arguments
        if(functionChoice(inputArgs[0]) == -1)
            throw new Exception("fonction incorrecte");             //vérification du nom de la fonction
        float weightChoice = Float.parseFloat(inputArgs[2]);        //vérification du poids max
        methodChoice(inputArgs[3]);                                 //vérification de la méthode choisie


    }

}
