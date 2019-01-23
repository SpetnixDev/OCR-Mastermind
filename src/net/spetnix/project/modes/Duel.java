package net.spetnix.project.modes;

import net.spetnix.project.Game;
import net.spetnix.project.Main;

import java.util.Random;

public class Duel extends Mode {
    public Duel(String game, Game g) {
        super(game, g);
    }

    /**
     * Starts the Duel Mode.
     */
    public void run() {
        Random random = new Random();

        Main.display("\nWelcome in the Duel mode : both Challenger and Defender are played in this mode.\n" +
                "Both of you and the computer will have to create a combination, and the fastest to guess the combination of its opponent wins.\n" +
                "You'll have " + g.getRounds() + " rounds to find your opponent's code.\n");

        String code = "";

        if (game.equals("HigherLower")) {
            Main.display("To start, enter the combination that the computer will have to guess: (" + g.getHigherLowerLength() + " numbers)");

            for (int i = 0; i < g.getHigherLowerLength(); i++) code += String.valueOf(random.nextInt(10));
        } else {
            Main.display("Please enter a combination with " + g.getMastermindLength() + " numbers : (" + g.getMastermindLength() + " numbers)");

            for (int i = 0; i < g.getMastermindLength(); i++) code += String.valueOf(random.nextInt(g.getMastermindPossibilities()));
        }

        String userCode = setupCode();

        Main.display("\nThe computer created its combination.");
        if (g.isDevMode()) Main.display("Combination : " + code);

        String userGuess;
        String computerGuess;

        String difference = "";

        int userFound = 0;
        int computerFound = 0;

        int round = 1;

        String[] computerGuesses = {"", ""};

        while (round <= g.getRounds()) {
            Main.display("\nRound : " + round);

            if (userFound == 0) {
                userGuess = userGuess();

                if (userGuess.equalsIgnoreCase(code)) {
                    Main.display("Congratulations, you found the combination ! You found it in " + round + ((round == 1) ? " round !" : " rounds !"));

                    userFound = round;
                } else {
                    Main.display("Proposition : " + userGuess + " --> " + compareCodes(code, userGuess, game));
                }
            }

            if (computerFound == 0) {
                computerGuess = computerGuess(round, difference, computerGuesses, game);

                computerGuesses[0] = computerGuesses[1];
                computerGuesses[1] = computerGuess;

                Main.display("\nComputer's proposition : " + computerGuess);

                if (computerGuess.equalsIgnoreCase(userCode)) {
                    Main.display("The computer has found your combination in " + round + ((round == 1) ? " round !" : " rounds !"));

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

        printResults(code, userCode, computerFound, userFound);
    }

    /**
     * Displays the results of the game.
     *
     * @param code The computer's code.
     * @param userCode The user's code.
     * @param computerFound The number of rounds the computer took to find the combination.
     * @param userFound The number of rounds the user took to find the combination.
     */
    private void printResults(String code, String userCode, int computerFound, int userFound) {
        Main.display("\n- Results : \n");

        if (userFound > 0) {
            Main.display("You found the combination in : " + userFound + " rounds.");
        } else {
            Main.display("Sadly, you didn't find the combination which was : " + code);
        }

        if (computerFound > 0) {
            Main.display("The computer found the combination in : " + computerFound + " rounds.");
        } else {
            Main.display("Sadly, the computer didn't find the combination which was : " + userCode);
        }

        Main.display(" ");

        if ((userFound != 0 && userFound < computerFound) || (userFound != 0 && computerFound == 0)) {
            Main.display("You WON !");
        } else if ((computerFound != 0 && computerFound < userFound) || (computerFound != 0 && userFound == 0)) {
            Main.display("The computer WON !");
        } else {
            Main.display("That's a DRAW !");
        }
    }
}
