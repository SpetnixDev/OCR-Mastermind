package net.spetnix.project.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Duel extends Mode {
    public Duel(int rounds, String game) {
        super(rounds, game);
    }

    public void run() {
        Random random = new Random();

            System.out.println("\nWelcome in the Duel mode : both Challenger and Defender are played in this mode.\n" +
                "Both of you and the computer will have to create a combination, and the fastest to guess the combination of its opponent wins.\n" +
                "You'll have " + rounds + " rounds to find your opponent's code.\n");

        System.out.println("To start, enter the combination that the computer will have to guess: ");

        String userCode = setupCode();
        String code = "";

        for (int i = 0; i < 4; i++) code += String.valueOf(random.nextInt(10));

        System.out.println("\nThe computer created its combination.");

        String userGuess;
        String computerGuess = "";

        String difference = "";

        int userFound = 0;
        int computerFound = 0;

        int round = 1;

        while (round <= rounds) {
            System.out.println("\nRound : " + round);

            if (userFound == 0) {
                userGuess = userGuess();

                if (userGuess.equalsIgnoreCase(code)) {
                    System.out.println("Congratulations, you found the combination ! You found it in " + round + ((round == 1) ? " round !" : " rounds !"));

                    userFound = round;
                } else {
                    System.out.println("Proposition : " + userGuess + " --> " + compareCodes(code, userGuess, game));
                }
            }

            if (computerFound == 0) {
                computerGuess = computerGuess(round == 1, difference, computerGuess);

                System.out.println("\nComputer's proposition : " + computerGuess);

                if (computerGuess.equalsIgnoreCase(userCode)) {
                    System.out.println("The computer has found your combination in " + round + ((round == 1) ? " round !" : " rounds !"));

                    computerFound = round;
                } else {
                    difference = returnDifference();
                }
            }

            if (userFound != 0 && computerFound != 0) {
                break;
            }

            round++;
        }

        System.out.println("\n- Results : \n");

        if (userFound > 0) {
            System.out.println("You found the combination in : " + userFound + " rounds.");
        } else {
            System.out.println("Sadly, you didn't find the combination which was : " + code);
        }

        if (computerFound > 0) {
            System.out.println("The computer found the combination in : " + computerFound + " rounds.");
        } else {
            System.out.println("Sadly, the computer didn't find the combination which was : " + userCode);
        }

        System.out.println(" ");

        if (userFound < computerFound || (userFound > 0 && computerFound == 0)) {
            System.out.println("You WON !");
        } else if (userFound > computerFound || (computerFound > 0 && userFound == 0)) {
            System.out.println("The computer WON !");
        } else {
            System.out.println("That's a DRAW !");
        }
    }
}
