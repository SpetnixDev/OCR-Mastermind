package net.spetnix.project;

import net.spetnix.project.modes.Challenger;
import net.spetnix.project.modes.Defender;
import net.spetnix.project.modes.Duel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

/**
 * This class contains the whole game.
 */
public class Game {
    Scanner scanner = new Scanner(System.in);

    private int rounds;
    private int higherLowerLength;
    private int mastermindLength;
    private int mastermindPossibilities;

    private boolean devMode = false;

    String game;
    int mode;

    boolean stop = false;

    public Game() {
        getProperties();
    }

    /**
     * Runs the menu.
     */
    public void runMenu() {
        Main.display("Welcome !");

        do {
            System.out.println(this);

            setDevMode();

            runEndMenu();
        } while (!stop);
    }

    /**
     * Starts the chosen mode.
     *
     * @param mode The chosen mode.
     * @param g An instance of the Game class.
     *
     * @see Game
     */
    private void runMode(int mode, Game g) {
        switch (mode) {
            case 1:
                new Challenger(game, g).run();

                break;
            case 2:
                new Defender(game, g).run();

                break;
            case 3:
                new Duel(game, g).run();

                break;
        }
    }

    /**
     * Asks the user if they want to enable the Developer Mode.
     */
    private void setDevMode() {
        Main.display("\nDo you wanna enable the Developer Mode ?");

        int choice = 0;
        boolean choiceError;

        do {
            try {
                choice = scanner.nextInt();

                choiceError = (choice != 0 && choice != 1);
            } catch (InputMismatchException e) {
                scanner.next();
                choiceError = true;
            }
        } while (choiceError);

        if (choice == 0) {
            System.out.println("You have chosen not to enable the Developer Mode.");

            setDevMode(false);
        } else {
            System.out.println("You have chosen to enable the Developer Mode.");

            setDevMode(true);
        }

        scanner.nextLine();

        setGame();
    }

    /**
     * Asks the user which game they wanna play.
     *
     * @see #setMode()
     */
    private void setGame() {
        String[] games = {"HigherLower", "Mastermind"};

        Main.display("\nWhich game do you wanna play ?");

        for (int i = 0; i < games.length; i++) Main.display((i + 1) + " - " + games[i]);

        int gameIndex = 0;
        boolean gameError;

        do {
            try {
                gameIndex = scanner.nextInt();

                gameError = (gameIndex < 1 || gameIndex > 2);
            } catch (InputMismatchException e) {
                scanner.next();
                gameError = true;
            }
        } while (gameError);

        game = games[gameIndex - 1];

        Main.display("You have chosen the following game : " + game + " !");

        scanner.nextLine();

        setMode();
    }

    /**
     * Asks the user which mode they wanna play.
     *
     * @see #setGame()
     * @see #runMode(int, Game)
     */
    private void setMode() {
        String[] modes = {"Challenger", "Defender", "Duel", "Go back"};

        Main.display("\nWhich mode do you wanna play ?");

        for (int i = 0; i < modes.length; i++) Main.display((i + 1) + " - " + modes[i]);

        int modeIndex = 0;
        boolean modeError;

        do {
            try {
                modeIndex = scanner.nextInt();

                modeError = (modeIndex < 1 || modeIndex > 4);
            } catch (InputMismatchException e) {
                scanner.next();
                modeError = true;
            }
        } while (modeError);

        scanner.nextLine();

        if (modeIndex == 4) {
            setGame();
        } else {
            mode = modeIndex;

            runMode(mode, this);
        }
    }

    private void runEndMenu() {
        int replay = 0;
        boolean replayError;

        do {
            try {
                Main.display("\nWhat do you wanna do ?");
                Main.display("1 - Replay");
                Main.display("2 - Play a different game");
                Main.display("3 - Quit game");

                replay = scanner.nextInt();

                replayError = (replay > 3 || replay < 1);
            } catch (InputMismatchException e) {
                scanner.next();
                replayError = true;
            }
        } while (replayError);

        if (replay == 1) {
            runMode(mode, this);
        } else if (replay == 3) {
            stop = true;
        }
    }

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        devMode = devMode;
    }

    public int getRounds() {
        return rounds;
    }

    public int getHigherLowerLength() {
        return higherLowerLength;
    }

    public int getMastermindLength() {
        return mastermindLength;
    }

    public int getMastermindPossibilities() {
        return mastermindPossibilities;
    }

    private void getProperties() {
        //todo: Add file .properties and get config here
        final Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("src/config.properties");

            prop.load(input);

            rounds = Integer.valueOf(prop.getProperty("rounds"));
            higherLowerLength = Integer.valueOf(prop.getProperty("higherLowerLength"));
            mastermindLength = Integer.valueOf(prop.getProperty("mastermindLength"));
            mastermindPossibilities = Integer.valueOf(prop.getProperty("mastermindPossibilities"));
        } catch (final IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
