package ru.spbau.osipov.drunkard.view;

import ru.spbau.osipov.drunkard.model.Drunkground;
import ru.spbau.osipov.drunkard.points.HexTopology;
import ru.spbau.osipov.drunkard.points.RectangularTopology;

/**
 * @author Osipov Stanislav
 */
public class DrunkgroundView {
    private final static int STEP_NUMBER = 500;
    private final Drunkground drunkGround;
    private boolean debug = false;
    private boolean hex = false;

    public DrunkgroundView(boolean hex) {
        this.hex = hex;
        if (hex) {
            this.drunkGround = new Drunkground(new HexTopology());
        } else {
            this.drunkGround = new Drunkground(new RectangularTopology());
        }
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void show(int stepNumber) {
        System.out.println("Step # " + stepNumber);
        char[][] view = drunkGround.getView();
        for (int i = 0; i < view.length; ++i) {
            char[] chars = view[i];
            if (hex && i > 0 && i % 2 == 0) {
                System.out.print(" ");
            }
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }

    }

    public void startWalking() {
        for (int i = 0; i <= STEP_NUMBER; ++i) {
            drunkGround.step();
            if (i % 100 == 0 || debug) {
                show(i);
            }
        }
    }
}
