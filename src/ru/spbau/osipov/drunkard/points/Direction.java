package ru.spbau.osipov.drunkard.points;

import java.util.Random;

/**
 * @author Osipov Stanislav
 */
public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);
    private static final Random RANDOM = new Random();

    private final int dx;
    private final int dy;

    public static Direction getRandomDirection() {
        int randomIndex = RANDOM.nextInt(Direction.values().length);
        return Direction.values()[randomIndex];
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
