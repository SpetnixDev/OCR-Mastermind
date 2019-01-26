package net.spetnix.project.modes;

import net.spetnix.project.Game;
import net.spetnix.project.Main;

public class Defender extends Mode {
    public Defender(String game, Game g) {
        super(game, g);
    }

    /**
     * Starts the Defender Mode.
     */
    public void run() {
        Main.display("\nWelcome in the Defender mode : in this mode, you'll have to create a combination and the computer will have to find it." +
                " The computer will have " + rounds + " rounds to find it.");

        if (game.equals("HigherLower")) {
            Main.display("Please enter a combination with " + hlLength + " numbers :");
        } else {
            Main.display("Please enter a combination with " + mmLength + " numbers :");
        }

        String userCode = setupCode();

        String code;
        String difference = "";
        int round = 1;

        String[] codes = {"", ""};

        while (round <= rounds) {
            Main.display("Round : " + round);

            code = computerGuess(round, difference, codes, game);

            codes[0] = codes[1];
            codes[1] = code;

            Main.display("Computer's proposition : " + code);

            if (code.equalsIgnoreCase(userCode)) {
                Main.display("The computer has found your combination in " + round + ((round == 1) ? " round !" : " rounds !"));

                return;
            } else {
                difference = returnDifference();
            }

            round++;
        }

        Main.display("Sadly, the computer didn't find the combination which was : " + userCode);
    }
}
