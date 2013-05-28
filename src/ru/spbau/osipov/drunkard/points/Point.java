package ru.spbau.osipov.drunkard.points;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Osipov Stanislav
 */
public class Point {
    private final int x;
    private final int y;

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

    public List<Point> getAdjacentPoints() {
        ArrayList<Point> adjacentPoints = new ArrayList<Point>();
        for (Direction direction : Direction.values()) {
            adjacentPoints.add(this.adjacentPoint(direction));
        }
        return adjacentPoints;
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

    public Point adjacentPoint(Direction direction) {
        return new Point(x + direction.getDx(), y + direction.getDy());
    }

    public Point randomAdjacentPoint() {
        return adjacentPoint(Direction.getRandomDirection());
    }

}

