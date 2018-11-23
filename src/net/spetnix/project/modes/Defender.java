package net.spetnix.project.modes;

import java.util.Scanner;

public class Defender extends Mode {
    Scanner scanner = new Scanner(System.in);

    public Defender(int rounds, String game) {
        super(rounds, game);
    }

    public void run() {
        System.out.println("Welcome in the Defender mode : in this mode, you'll have to create a combination and the computer will have to find it." +
                " The computer will have " + rounds + " rounds to find it.");

        System.out.println("Please enter a combination with 4 numbers :");

        String userCode = setupCode();

        String code = "";
        String difference = "";
        int round = 1;

        while (round <= rounds) {
            System.out.println("Round : " + round);

            code = computerGuess(round == 1, difference, code);

            System.out.println("Proposition : " + code);

            if (code.equalsIgnoreCase(userCode)) {
                System.out.println("The computer has found your combination in " + round + ((round == 1) ? " round !" : " rounds !"));
            } else {
                difference = returnDifference();
            }

            round++;
        }

        System.out.println("Sadly, the computer didn't find the combination which was : " + userCode);
    }
}
