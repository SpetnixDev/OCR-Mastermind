package net.spetnix.project.modes;

import java.util.Random;
import java.util.Scanner;

public class Defender {
    Scanner scanner = new Scanner(System.in);

    private int rounds;

    public Defender(int rounds) {
        this.rounds = rounds;
    }

    public void run() {
        System.out.println("Welcome in the Defender modes : in this modes, you'll have to create a combination and the computer will have to find." +
                " The computer will have " + rounds + " rounds to find it.");

        System.out.println("Please enter a combination with 4 numbers :");

        String userCode = scanner.next();

        while (userCode.length() != 4 || !userCode.matches("[0-9]+")) {
            System.out.println("You didn't respect the rule. Enter a valid combination with 4 numbers :");

            userCode = scanner.next();
        }

        runComputerGuess(userCode);
    }

    private void runComputerGuess(String userCode) {
        String code = "";
        String difference = "";
        int round = 1;

        while (round <= rounds) {
            System.out.println("Round : " + round);

            code = computerGuess(round == 1, difference, code);

            System.out.println("Proposition : " + code);

            if (code.equalsIgnoreCase(userCode)) {
                System.out.println("The computer has found your combination in " + round + ((round == 1) ? " round !" : " rounds !"));

                return;
            } else {
                System.out.println("Tell now the computer the result of its proposition with (>, <, =) for each number.");

                difference = scanner.next();
                boolean containsFalse = false;

                for (int i = 0; i < difference.length(); i++) {
                    if (!("<>=".contains(Character.toString(difference.charAt(i))))) containsFalse = true;
                }

                while (difference.length() != 4 || containsFalse) {
                    System.out.println("You didn't respect the rule. Enter a valid answer for the computer :");
                    difference = scanner.next();
                }
            }

            round++;
        }

        System.out.println("Sadly, the computer didn't find the combination which was : " + userCode);
    }

    private String computerGuess(boolean isFirstRound, String difference, String codeBefore) {
        Random random = new Random();

        StringBuilder code = new StringBuilder();

        if (isFirstRound) {
            for (int i = 0; i < 4; i++) code.append(String.valueOf(random.nextInt(10)));
        } else {
            code = new StringBuilder(codeBefore);

            for (int i = 0; i < 4; i++) {
                int currentNumber = code.charAt(i);

                switch (difference.charAt(i)) {
                    case '>':
                        code.setCharAt(i, (char) (currentNumber + 1));

                        break;
                    case '<':
                        code.setCharAt(i, (char) (currentNumber - 1));

                        break;
                    case '=':
                        code.setCharAt(i, (char) (currentNumber));

                        break;
                }
            }
        }

        return code.toString();
    }
}
