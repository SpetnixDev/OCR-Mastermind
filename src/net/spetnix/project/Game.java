package net.spetnix.project;

import net.spetnix.project.modes.Challenger;
import net.spetnix.project.modes.Defender;
import net.spetnix.project.modes.Duel;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    int rounds = 8;
    String game;

    void run() {
        getProperties();

        runMenu();
    }

    private void runMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;

        String[] games = {"HigherLower", "Mastermind"};
        String[] modes = {"Challenger", "Defender", "Duel"};

        do {
            System.out.println("Welcome, which game do you wanna play ?");

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
            scanner.nextLine();

            System.out.println("You have chosen the following game : " + game + " ! Which mode do you wanna play ?");

            for (int i = 0; i < modes.length; i++) System.out.println((i + 1) + " - " + modes[i]);

            int modeIndex = 0;
            boolean modeError;

            do {
                try {
                    modeIndex = scanner.nextInt();

                    modeError = (modeIndex < 1 || modeIndex > 3);
                } catch (InputMismatchException e) {
                    scanner.next();
                    modeError = true;
                }
            } while (modeError);

            runMode(modeIndex);
            scanner.nextLine();

            String replay;

            do {
                System.out.println("Do you want to continue to play ? (y/n)");
                replay = scanner.nextLine();
            } while (replay.equalsIgnoreCase("n") && replay.equalsIgnoreCase("y"));

            if (replay.equalsIgnoreCase("n")) stop = true;
        } while (!stop);
    }

    private void runMode(int mode) {
        switch (mode) {
            case 1:
                new Challenger(rounds, game).run();

                break;
            case 2:
                new Defender(rounds, game).run();

                break;
            case 3:
                new Duel(rounds, game).run();

                break;
        }
    }

    private void getProperties() {
        //todo: Add file .properties and get config here
    }
}
