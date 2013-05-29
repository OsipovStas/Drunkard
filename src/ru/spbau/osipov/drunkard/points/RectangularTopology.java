package ru.spbau.osipov.drunkard.points;

import java.util.Arrays;
import java.util.List;

/**
 * @author Osipov Stanislav
 */
public final class RectangularTopology implements Topology {
    @Override
    public List<Point> getAdjacentPoints(Point position) {
        int x = position.getX();
        int y = position.getY();
        return Arrays.asList(new Point(x, y + 1), new Point(x - 1, y), new Point(x, y - 1), new Point(x + 1, y));
    }
}
