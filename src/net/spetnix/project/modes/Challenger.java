package net.spetnix.project.modes;

import java.util.Random;
import java.util.Scanner;

public class Challenger {
    private int rounds;

    public Challenger(int rounds) {
        this.rounds = rounds;
    }

    public void run() {
        Random random = new Random();

        System.out.println("Welcome in the Challenger modes : your goal will be to find the combination of the computer. You'll have " + rounds + " rounds to find it.");

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

    private String userGuess() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a combination with 4 numbers :");

        String userCode;

        userCode = scanner.next();

        while (userCode.length() != 4 || !userCode.matches("[0-9]+")) {
            System.out.println("You didn't respect the rule. Enter a valid combination with 4 numbers :");

            userCode = scanner.next();
        }

        return userCode;
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
}
