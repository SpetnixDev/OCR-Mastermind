package net.spetnix;

import java.util.Random;
import java.util.Scanner;

public class MoreLess {
    private int rounds;
    Scanner sc = new Scanner(System.in);

    public MoreLess(int rounds) {
        this.rounds = rounds;
    }

    public void run() {
        System.out.println("Which mode do you wanna play ?");

        String[] modes = {"Challenger", "Defender", "Duel"};
        int index = 1;

        for (String mode : modes) {
            System.out.println(index + " - " + mode);
            index++;
        }

        int mode = sc.nextInt();

        switch (modes[mode - 1]) {
            case "Challenger":
                runChallenger();

                break;
            case "Defender":
                runDefender();

                break;
            case "Duel":
                runDuel();

                break;
        }
    }

    private Random random = new Random();

    private void runChallenger() {
        System.out.println("Welcome in the Challenger mode : your goal will be to find the combination of the computer. You'll have " + rounds + " rounds to find it.");

        String code = "";
        String userCode;

        for (int i = 0; i < 4; i++) code += String.valueOf(random.nextInt(10));

        int round = 1;

        while (round <= rounds) {
            System.out.println("Round : " + round);

            userCode = userGuess();

            if (code.equalsIgnoreCase(userCode)) {
                System.out.println("Congratulations, you found the combination ! You found it in " + round + ((round == 1) ? " round !" : " rounds !"));

                return;
            } else {
                System.out.println("Proposition : " + userCode + " --> " + compareCodes(code, userCode));
            }

            round++;
        }

        System.out.println("Sadly, you didn't find the combination which was : " + code);
    }

    private void runDefender() {
        System.out.println("Welcome in the Defender mode : in this mode, you'll have to create a combination and the computer will have to find." +
                " The computer will have " + rounds + " rounds to find it.");

        System.out.println("Please enter a combination with 4 numbers :");

        String userCode = sc.next();

        runComputerGuess(userCode);
    }

    private void runDuel() {

    }

    private String compareCodes(String code, String userCode) {
        String difference = "";

        for (int i = 0; i < 4; i++) {
            int number = code.charAt(i);
            int userNumber = userCode.charAt(i);

            if (number > userNumber) {
                difference += ">";
            } else if (number == userNumber) {
                difference += "=";
            } else {
                difference += "<";
            }
        }

        return difference;
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
                difference = sc.next();
            }

            round++;
        }

        System.out.println("Sadly, the computer didn't find the combination which was : " + userCode);
    }

    private String userGuess() {
        System.out.println("Enter a combination with 4 numbers :");

        String userCode;

        userCode = sc.next();

        while (userCode.length() != 4 || !userCode.matches("[0-9]+")) {
            System.out.println("You didn't respect the rule. Enter a valid combination with 4 numbers :");

            userCode = sc.next();
        }

        return userCode;
    }

    private String computerGuess(boolean isFirstRound, String difference, String codeBefore) {
        StringBuilder code = new StringBuilder();

        if (isFirstRound) {
            for (int i = 0; i < 4; i++) code.append(String.valueOf(random.nextInt(10)));
        } else {
            code = new StringBuilder(codeBefore);

            for (int i = 0; i < 4; i++) {
                int currentNumber = code.charAt(i);

                switch (difference.charAt(i)) {
                    case '>':
                        code.setCharAt(i, (char) (currentNumber + (9 - currentNumber) / 2));

                        break;
                    case '<':
                        code.setCharAt(i, (char) (currentNumber - (currentNumber - 1) / 2));

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
