package ru.spbau.osipov.drunkard;

/**
 * @author Osipov Stanislav
 */
public class DrunkgroundView {
    private final static int STEP_NUMBER = 500;
    private Drunkground drunkGround;
    private boolean debug = false;

    public DrunkgroundView() {
        this.drunkGround = new Drunkground();
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void show(int stepNumber) {
        System.out.println("Step # " + stepNumber);
        for (char[] chars : drunkGround.getView()) {
            for (char aChar : chars) {
                System.out.print(aChar);
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
