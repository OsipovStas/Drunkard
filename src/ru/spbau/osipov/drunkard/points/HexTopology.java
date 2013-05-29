package ru.spbau.osipov.drunkard.points;

import java.util.Arrays;
import java.util.List;

/**
 * @author Osipov Stanislav
 */
public class HexTopology implements Topology {
    @Override
    public List<Point> getAdjacentPoints(Point position) {
        int x = position.getX();
        int y = position.getY();
        boolean even = y % 2 == 0;
        int dx = even ? 0 : 1;
        return Arrays.asList(new Point(x - 1, y), new Point(x + 1, y), new Point(x - 1 + dx, y - 1),
                new Point(x + dx, y - 1), new Point(x - 1 + dx, y + 1), new Point(x + dx, y + 1));
    }
}
