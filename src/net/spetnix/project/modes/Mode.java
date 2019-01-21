package net.spetnix.project.modes;

import net.spetnix.project.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public abstract class Mode {
    private Scanner scanner = new Scanner(System.in);

    protected String game;
    Game g;

    private int hlLength;
    private int mmLength;

    //private int mmPossibilities;

    private ArrayList<String> possibleCombinations;

    Mode(String game, Game g) {
        this.game = game;
        this.g = g;

        hlLength = g.getHigherLowerLength();
        mmLength = g.getMastermindLength();

        //mmPossibilities = g.getMastermindPossibilities();

        possibleCombinations = createCombinations();
    }

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
        int length;

        if (game.equals("HigherLower")) {
            length = hlLength;
        } else {
            length = mmLength;
        }

        System.out.println("\nEnter a combination with " + length + " numbers :");

        String userCode;

        userCode = scanner.next();

        while (userCode.length() != length || !userCode.matches("[0-9]+")) {
            if (userCode.equalsIgnoreCase("stop")) stop();

            System.out.println("You didn't respect the rule. Enter a valid combination with " + length + " numbers :");

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
                for (int i = 0; i < hlLength; i++) {
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

                for (int i = 0; i < mmLength; i ++) {
                    for (int j = 0; j < mmLength; j ++) {
                       if (checked.contains(j)) continue;

                       if (code.charAt(j) == userCode.charAt(i)) {
                           checked.add(j);

                           break;
                       }
                    }
                }

                int wellPlaced = 0;

                for (int i = 0; i < mmLength; i++) {
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
            if (game.equalsIgnoreCase("HigherLower")) {
                for (int i = 0; i < hlLength; i++) code.append(random.nextInt(10));
            } else {
                for (int i = 0; i < mmLength; i++) code.append(random.nextInt(10));
            }
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
                    possibleCombinations.remove(code.toString());

                    if (difference.equals("00")) {
                        for (int i = 0; i < code.length(); i++) {
                            for (int j = 0; j < possibleCombinations.size(); j++) {
                                if (possibleCombinations.get(j).contains(String.valueOf(code.charAt(i)))) {
                                    possibleCombinations.remove(j);
                                    j--;
                                }
                            }
                        }
                    } else if (difference.charAt(0) == '0') {
                        for (int i = 0; i < code.length(); i++) {
                            for (int j = 0; j < possibleCombinations.size(); j++) {
                                if (possibleCombinations.get(j).charAt(i) == code.charAt(i)) {
                                    possibleCombinations.remove(j);
                                    j--;
                                }
                            }
                        }

                        HashMap<Integer, Integer> checkedIndexes = new HashMap<>();

                        for (int i = 0; i < possibleCombinations.size(); i++) {
                            for (int j = 0; j < possibleCombinations.get(i).length(); j++) {
                                for (int k = 0; k < code.length(); k++) {
                                    if (!checkedIndexes.containsKey(j)) {
                                        if (!checkedIndexes.containsValue(k)) {
                                            if (possibleCombinations.get(i).charAt(j) == code.charAt(k)) {
                                                checkedIndexes.put(j, k);
                                            }
                                        }
                                    }
                                }
                            }

                            int misplaced = difference.charAt(1) - 48;

                            if (checkedIndexes.size() < misplaced) {
                                possibleCombinations.remove(i);
                                i--;
                            }
                        }
                    } else if (difference.charAt(1) == '0') {
                        int a = 0;
                        int wellPlaced = difference.charAt(0) - 48;

                        for (int i = 0; i < possibleCombinations.size(); i++) {
                            for (int j = 0; j < possibleCombinations.get(i).length(); j++) {
                                if (code.charAt(j) == possibleCombinations.get(i).charAt(j)) {
                                    a++;
                                }
                            }

                            if (a != wellPlaced) {
                                possibleCombinations.remove(i);
                                i--;
                            }

                            a = 0;
                        }
                    } else {
                        int wellPlaced = difference.charAt(0) - 48;
                        int misplaced = difference.charAt(1) - 48;

                        int correctNumbers = wellPlaced + misplaced;
                        HashMap<Integer, Integer> checkedIndexes = new HashMap<>();

                        for (int i = 0; i < possibleCombinations.size(); i++) {
                            for (int j = 0; j < possibleCombinations.get(i).length(); j++) {
                                if (code.charAt(j) == possibleCombinations.get(i).charAt(j)) {
                                    checkedIndexes.put(j, j);
                                }
                            }

                            for (int j = 0; j < possibleCombinations.get(i).length(); j++) {
                                for (int k = 0; k < code.length(); k++) {
                                    if (!checkedIndexes.containsKey(j)) {
                                        if (!checkedIndexes.containsValue(k)) {
                                            if (possibleCombinations.get(i).charAt(j) == code.charAt(k)) {
                                                checkedIndexes.put(j, k);
                                            }
                                        }
                                    }
                                }
                            }

                            if (checkedIndexes.size() < correctNumbers) {
                                possibleCombinations.remove(i);
                                i--;
                            }

                            checkedIndexes.clear();
                        }
                    }

                    int index = random.nextInt(possibleCombinations.size());

                    code = new StringBuilder(possibleCombinations.get(index));

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
        int length;

        if (game.equals("HigherLower")) {
            length = hlLength;
        } else {
            length = mmLength;
        }

        String userCode = scanner.next();

        while (userCode.length() != length || !userCode.matches("[0-9]+")) {
            if (userCode.equalsIgnoreCase("stop")) stop();

            System.out.println("You didn't respect the rule. Enter a valid combination with " + length + " numbers :");

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

                while (difference.length() != hlLength || containsFalse) {
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

    protected ArrayList<String> createCombinations() {
        ArrayList<String> combinations = new ArrayList<>();

        for (int i = 0; i < Math.pow(10, mmLength); i++) {
            String number = String.valueOf(i);

            int a = mmLength - number.length();

            for (int j = 0; j < a; j++) {
                number = "0" + number;
            }

            combinations.add(number);
        }

        return combinations;
    }

    /**
     * Stops the game and returns to the menu.
     */
    private void stop() {
        System.out.println(" ");

        g.runMenu();
    }
}
