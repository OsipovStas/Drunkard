package ru.spbau.osipov.drunkard;

/**
 * @author Osipov Stanislav
 */
public class Main {
    public static void main(String[] args) {
        DrunkgroundView drunkgroundView = new DrunkgroundView();
        if (args.length != 0 && args[0].equals("debug")) {
            drunkgroundView.setDebug(true);
        }
        drunkgroundView.startWalking();
    }

}
