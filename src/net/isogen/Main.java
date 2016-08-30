package net.isogen;

public class Main {

    public static void main(String[] args) {
        String outputFile   = args[0];
        String username     = args[1];
        int level           = Integer.valueOf(args[2]);

        SudokuGenerator sg = new SudokuGenerator(username, level, outputFile);
        sg.print("$03d");
        sg.removePercentage(20);
        sg.print("$03d");
        sg.writeOutput();
    }
}
