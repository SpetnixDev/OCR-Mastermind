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

    int rounds = 8;

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
        int next = 0;

        System.out.println("Welcome !");

        do {
            setGame();

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
                new Challenger(rounds, game, g).run();

                break;
            case 2:
                new Defender(rounds, game, g).run();

                break;
            case 3:
                new Duel(rounds, game, g).run();

                break;
        }
    }

    /**
     * Asks the user which game they wanna play.
     *
     * @see #setMode()
     */
    private void setGame() {
        String[] games = {"HigherLower", "Mastermind"};

        System.out.println("\nWhich game do you wanna play ?");

        for (int i = 0; i < games.length; i++) System.out.println((i + 1) + " - " + games[i]);

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

        System.out.println("You have chosen the following game : " + game + " !");

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

        System.out.println("\nWhich mode do you wanna play ?");

        for (int i = 0; i < modes.length; i++) System.out.println((i + 1) + " - " + modes[i]);

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
                System.out.println("\nWhat do you wanna do ?");
                System.out.println("1 - Replay");
                System.out.println("2 - Play a different game");
                System.out.println("3 - Quit game");

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

    private void getProperties() {
        //todo: Add file .properties and get config here
        final Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("src/config.properties");

            prop.load(input);
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
