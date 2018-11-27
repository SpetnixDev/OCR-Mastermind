package net.spetnix.project.modes;

import net.spetnix.project.Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class Mode {
    Scanner scanner = new Scanner(System.in);
    protected int rounds;

    protected String game;
    Game g;

    Mode(int rounds, String game, Game g) {
        this.rounds = rounds;
        this.game = game;
        this.g = g;
    }

    public abstract void run();

    protected String userGuess() {
        System.out.println("\nEnter a combination with 4 numbers :");

        String userCode;

        userCode = scanner.next();

        while (userCode.length() != 4 || !userCode.matches("[0-9]+")) {
            if (userCode.equalsIgnoreCase("stop")) stop();

            System.out.println("You didn't respect the rule. Enter a valid combination with 4 numbers :");

            userCode = scanner.next();
        }

        return userCode;
    }

    protected String compareCodes(String code, String userCode, String game) {
        String difference = "";

        switch (game) {
            case "HigherLower":
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

                break;
            case "Mastermind":
                ArrayList<Integer> checked = new ArrayList<>();

                for (int i = 0; i < 4; i ++) {
                    for (int j = 0; j < 4; j ++) {
                       if (checked.contains(j)) continue;

                       if (code.charAt(j) == userCode.charAt(i)) {
                           checked.add(j);

                           break;
                       }
                    }
                }

                int wellPlaced = 0;

                for (int i = 0; i < 4; i++) {
                    int number = code.charAt(i);
                    int userNumber = userCode.charAt(i);

                    if (number == userNumber) {
                        wellPlaced ++;
                    }
                }

                String wellPlacedMsg = (wellPlaced == 0) ? "" : "- " + wellPlaced + " well placed ";
                String misplacedMsg = (checked.size() - wellPlaced == 0) ? "" : "- " + (checked.size() - wellPlaced) + " misplaced";

                difference = (wellPlaced + checked.size() == 0) ? "No matches" : wellPlacedMsg + misplacedMsg;

                break;
        }

        return difference;
    }

    protected String computerGuess(boolean isFirstRound, String difference, String codeBefore) {
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

    protected String setupCode() {
        String userCode = scanner.next();

        while (userCode.length() != 4 || !userCode.matches("[0-9]+")) {
            if (userCode.equalsIgnoreCase("stop")) stop();

            System.out.println("You didn't respect the rule. Enter a valid combination with 4 numbers :");

            userCode = scanner.next();
        }

        return userCode;
    }

    protected String returnDifference() {
        String difference;

        System.out.println("Tell now the computer the result of its proposition with (>, <, =) for each number.");

        difference = scanner.next();
        boolean containsFalse = false;

        for (int i = 0; i < difference.length(); i++) containsFalse = (!("<>=".contains(Character.toString(difference.charAt(i)))));

        while (difference.length() != 4 || containsFalse) {
            if (difference.equalsIgnoreCase("stop")) stop();

            containsFalse = false;
            System.out.println("You didn't respect the rule. Enter a valid answer for the computer :");

            difference = scanner.next();

            for (int i = 0; i < difference.length(); i++) containsFalse = (!("<>=".contains(Character.toString(difference.charAt(i)))));
        }

        return difference;
    }

    private void stop() {
        System.out.println(" ");
        g.runMenu();
    }
}
