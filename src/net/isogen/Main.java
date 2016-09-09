package net.isogen;

public class Main {

    public static void main(String[] args) {
        String action       = args[0];
        String username     = args[1];
        int level           = Integer.valueOf(args[2]);


        SudokuGenerator sg = new SudokuGenerator(username, level);
        switch(action){
            case "generate":
            {
                System.out.println("{" + sg.get_difficulty() + "}");
                sg.print("d");
                break;
            }
            case "verify":
            {
                String solution     = args[3];
                if(sg.verify(solution.split(" "))){
                    System.out.println("True");
                }
                else{
                    System.out.println("That solution didn't work");
                }
                break;
            }
        }

    }
}
