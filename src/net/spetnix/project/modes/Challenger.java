package net.spetnix.project.modes;

import net.spetnix.project.Game;
import net.spetnix.project.Main;

import java.util.Random;

public class Challenger extends Mode {
    public Challenger(String game, Game g) {
        super(game, g);
    }

    /**
     * Starts the Challenger Mode.
     */
    public void run() {
        Random random = new Random();

        Main.display("\nWelcome in the Challenger mode : your goal will be to find the combination of the computer. You'll have " + rounds + " rounds to find it.");

        String code = "";
        String userCode;

        if (game.equals("HigherLower")) {
            for (int i = 0; i < g.getHigherLowerLength(); i++) code += String.valueOf(random.nextInt(10));
        } else {
            for (int i = 0; i < g.getMastermindLength(); i++) code += String.valueOf(random.nextInt(g.getMastermindPossibilities()));
        }

        Main.display("The computer created its combination.");
        if (g.isDevMode()) Main.display("Combination : " + code);

        int round = 1;

        while (round <= rounds) {
            Main.display("\nRound : " + round);

            userCode = userGuess();

            if (code.equalsIgnoreCase(userCode)) {
                Main.display("Congratulations, you found the combination ! You found it in " + round + ((round == 1) ? " round !" : " rounds !"));

                return;
            } else {
                Main.display("Proposition : " + userCode + " --> " + compareCodes(code, userCode, game));
            }

            round++;
        }

        Main.display("Sadly, you didn't find the combination which was : " + code);
    }
}
