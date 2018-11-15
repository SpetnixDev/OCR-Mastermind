package net.spetnix.project;

import net.spetnix.project.modes.Challenger;
import net.spetnix.project.modes.Defender;
import net.spetnix.project.modes.Duel;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    int rounds = 8;

    void run() {
        getProperties();

        runMenu();
    }

    private void runMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;

        String[] modes = {"Challenger", "Defender", "Duel"};

        do {
            System.out.println("Welcome ! Which mode do you wanna play ?");

            for (int i = 0; i < modes.length; i++) System.out.println((i + 1) + " - " + modes[i]);

            int mode = 0;
            boolean modeError;

            do {
                try {
                    mode = scanner.nextInt();

                    modeError = (mode < 1 || mode > 3);
                } catch (InputMismatchException e) {
                    scanner.next();
                    modeError = true;
                }

                System.out.println(modeError);
            } while (modeError);

            runMode(mode);

            String replay;
            scanner.nextLine();

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
                new Challenger(rounds).run();

                break;
            case 2:
                new Defender(rounds).run();

                break;
            case 3:
                new Duel().run();

                break;
        }
    }

    private void getProperties() {
        //todo: Add file .properties and get config here
    }
}
