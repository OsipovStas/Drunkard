package ru.spbau.osipov.drunkard;

import ru.spbau.osipov.drunkard.view.DrunkgroundView;

/**
 * @author Osipov Stanislav
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.exit(1);
        }
        DrunkgroundView drunkgroundView = new DrunkgroundView(Boolean.parseBoolean(args[0]));
        if (args.length > 1 && args[1].equals("debug")) {
            drunkgroundView.setDebug(true);
        }
        drunkgroundView.startWalking();
    }

}
