package net.spetnix.project.modes;

import java.util.Scanner;

public class Defender extends Modes {
    Scanner scanner = new Scanner(System.in);

    public Defender(int rounds, String game) {
        super(rounds, game);
    }

    public void run() {
        System.out.println("Welcome in the Defender mode : in this mode, you'll have to create a combination and the computer will have to find it." +
                " The computer will have " + rounds + " rounds to find it.");

        System.out.println("Please enter a combination with 4 numbers :");

        String userCode = scanner.next();

        while (userCode.length() != 4 || !userCode.matches("[0-9]+")) {
            System.out.println("You didn't respect the rule. Enter a valid combination with 4 numbers :");

            userCode = scanner.next();
        }

        runComputerGuess(userCode);
    }
}
