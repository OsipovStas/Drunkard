package ru.spbau.osipov.drunkard.points;

import java.util.List;
import java.util.Random;

/**
 * @author Osipov Stanislav
 */
public class Point {
    private final int x;
    private final int y;


    private final static Random r = new Random();

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return x == point.x && y == point.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public Point randomAdjacentPoint(Topology topology) {
        List<Point> adjacentPoints = topology.getAdjacentPoints(this);
        return adjacentPoints.get(r.nextInt(adjacentPoints.size()));
    }

}

