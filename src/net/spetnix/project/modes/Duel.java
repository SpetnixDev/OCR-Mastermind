package net.spetnix.project.modes;

public class Duel extends Modes {
    public Duel(int rounds, String game) {
        super(rounds, game);
    }

    public void run() {
        System.out.println("Welcome in the Defender mode : both Challenger and Defender are played in this mode.\n" +
                "Both of you and the computer will have to create a combination, and the fastest to guess the combination of its opponent wins.\n" +
                "You'll have " + rounds + " rounds to find your opponent's code.");


    }
}
