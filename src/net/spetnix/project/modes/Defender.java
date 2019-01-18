package net.spetnix.project.modes;

import net.spetnix.project.Game;
public class Defender extends Mode {
    public Defender(String game, Game g) {
        super(game, g);
    }

    /**
     * Starts the Defender Mode.
     */
    public void run() {
        System.out.println("\nWelcome in the Defender mode : in this mode, you'll have to create a combination and the computer will have to find it." +
                " The computer will have " + g.getRounds() + " rounds to find it.");

        if (game.equals("HigherLower")) {
            System.out.println("Please enter a combination with " + g.getHigherLowerLength() + " numbers :");
        } else {
            System.out.println("Please enter a combination with " + g.getMastermindLength() + " numbers :");
        }

        String userCode = setupCode();

        String code;
        String difference = "";
        int round = 1;

        String[] codes = {"", ""};

        while (round <= g.getRounds()) {
            System.out.println("Round : " + round);

            code = computerGuess(round, difference, codes, game);

            codes[0] = codes[1];
            codes[1] = code;

            System.out.println("Computer's proposition : " + code);

            if (code.equalsIgnoreCase(userCode)) {
                System.out.println("The computer has found your combination in " + round + ((round == 1) ? " round !" : " rounds !"));

                return;
            } else {
                difference = returnDifference();
            }

            round++;
        }

        System.out.println("Sadly, the computer didn't find the combination which was : " + userCode);
    }
}
