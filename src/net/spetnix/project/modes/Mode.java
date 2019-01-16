package net.spetnix.project.modes;

import net.spetnix.project.Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class Mode {
    Scanner scanner = new Scanner(System.in);

    protected String game;
    Game g;

    Mode(String game, Game g) {
        this.game = game;
        this.g = g;
    }

    int hlLength = g.getHigherLowerLength();
    int mmLength = g.getMastermindLength();

    int mmPossibilities = g.getMastermindPossibilities();

    /**
     * Starts the current Mode.
     */
    public abstract void run();

    /**
     * Asks the user for a new guess and returns it.
     *
     * @return The new guess of the user.
     */
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

    /**
     * Compares the user's guess and the computer's combination.
     *
     * @param code The computer's combination.
     * @param userCode The user's guess.
     * @param game The game played (HigherLower / Mastermind).
     *
     * @return A string, explaining the user the differences between their guess and the computer's combination.
     */
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

    /**
     * Asks the computer for a new guess depending of the answer of the user and returns it.
     *
     * @param round The current round of the game.
     * @param difference The answer of the user to the computer's guess of the last round.
     * @param codesBefore An array containing the guesses of the computer of the two last rounds.
     *
     * @return The new guess of the computer.
     *
     * @see #returnDifference()
     */
    protected String computerGuess(int round, String difference, String[] codesBefore, String game) {
        Random random = new Random();

        StringBuilder code = new StringBuilder();

        if (round == 1) {
            for (int i = 0; i < 4; i++) code.append(random.nextInt(10));
        } else {
            code = new StringBuilder(codesBefore[1]);

            switch (game) {
                case "HigherLower":
                    for (int i = 0; i < hlLength; i++) {
                        int currentNumber = code.charAt(i);
                        currentNumber -= 48;

                        int newNumber;
                        int numberBefore = 0;

                        if (round > 2) {
                            numberBefore = codesBefore[0].charAt(i) - 48;
                        }

                        switch (difference.charAt(i)) {
                            case '>':
                                if (round == 2 || (round > 2 && numberBefore < currentNumber)) {
                                    newNumber = currentNumber + ((9 - currentNumber) / 2);

                                    if ((9 - currentNumber) % 2 == 0) {
                                        newNumber += 48;
                                    } else {
                                        newNumber += 49;
                                    }
                                } else {
                                    newNumber = currentNumber + ((numberBefore - currentNumber) / 2);

                                    if ((numberBefore - currentNumber) % 2 == 0) {
                                        newNumber += 48;
                                    } else {
                                        newNumber += 49;
                                    }
                                }

                                code.setCharAt(i, (char) newNumber);

                                break;
                            case '<':
                                if (round == 2 || (round > 2 && numberBefore > currentNumber)) {
                                    newNumber = currentNumber - (currentNumber / 2);

                                    if (currentNumber % 2 == 0) {
                                        newNumber += 48;
                                    } else {
                                        newNumber += 47;
                                    }
                                } else {
                                    newNumber = currentNumber - ((currentNumber - numberBefore) / 2);

                                    if ((currentNumber - numberBefore) % 2 == 0) {
                                        newNumber += 48;
                                    } else {
                                        newNumber += 47;
                                    }
                                }

                                code.setCharAt(i, (char) newNumber);

                                break;
                            case '=':
                                code.setCharAt(i, (char) (currentNumber + 48));

                                break;
                        }
                    }

                    break;
                case "Mastermind":
                    ArrayList<Integer> blNumbers = new ArrayList<>();
                    ArrayList<String> codes = new ArrayList<>();
                    ArrayList<String> responses = new ArrayList<>();

                    codes.add(code.toString());
                    responses.add(difference);

                    if (difference.equals("00")) {
                        for (int i = 0; i < mmLength; i++) {
                            blNumbers.add(code.charAt(i) - 48);
                        }

                        for (int i = 0; i < mmLength; i++) {
                            int newNumber;

                            do {
                                newNumber = random.nextInt(10);
                            } while (blNumbers.contains(newNumber));

                            code.setCharAt(i, (char) newNumber);
                        }
                    } else if (difference.equals("10")) {
                        int keptNumberIndex = random.nextInt(mmLength);

                        for (int i = 0; i < mmLength; i++) {
                            

                            if (i != keptNumberIndex) {

                            }
                        }
                    } else if (difference.equals("01")) {

                    }

                    break;
            }
        }

        return code.toString();
    }

    /**
     * Asks the user to create the combination that the computer will have to guess.
     *
     * @return The combination created by the user.
     */
    protected String setupCode() {
        String userCode = scanner.next();

        while (userCode.length() != 4 || !userCode.matches("[0-9]+")) {
            if (userCode.equalsIgnoreCase("stop")) stop();

            System.out.println("You didn't respect the rule. Enter a valid combination with 4 numbers :");

            userCode = scanner.next();
        }

        return userCode;
    }

    /**
     * Asks the user to return to the computer the differences between their combination and the computer's guess.
     *
     * @return A string, explaining the computer the differences between its guess and the user's combination.
     */
    protected String returnDifference() {
        String difference = "";
        boolean containsFalse = false;

        switch (game) {
            case "HigherLower":
                System.out.println("Tell now the computer the result of its proposition with (>, <, =) for each number.");

                difference = scanner.next();

                for (int i = 0; i < difference.length(); i++) containsFalse = (!("<>=".contains(Character.toString(difference.charAt(i)))));

                while (difference.length() != 4 || containsFalse) {
                    if (difference.equalsIgnoreCase("stop")) stop();

                    containsFalse = false;
                    System.out.println("You didn't respect the rule. Enter a valid answer for the computer :");

                    difference = scanner.next();

                    for (int i = 0; i < difference.length(); i++) containsFalse = (!("<>=".contains(Character.toString(difference.charAt(i)))));
                }

                break;
            case "Mastermind":
                System.out.println("Tell now the computer how many well placed numbers he got and misplaced numbers he got. Example : \"12\"");

                difference = scanner.next();

                for (int i = 0; i < difference.length(); i++) containsFalse = (!("0123456789".contains(Character.toString(difference.charAt(i)))));

                while (difference.length() != 2 || containsFalse) {
                    if (difference.equalsIgnoreCase("stop")) stop();

                    containsFalse = false;
                    System.out.println("You didn't respect the rule. Enter a valid answer for the computer :");

                    difference = scanner.next();

                    for (int i = 0; i < difference.length(); i++) containsFalse = (!("0123456789".contains(Character.toString(difference.charAt(i)))));
                }

                break;
        }

        return difference;
    }

    /**
     * Stops the game and returns to the menu.
     */
    private void stop() {
        System.out.println(" ");

        g.runMenu();
    }
}
