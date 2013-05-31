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
        boolean even = x % 2 == 0;
        int dy = even ? 0 : 1;
        return Arrays.asList(new Point(x, y - 1), new Point(x, y + 1), new Point(x - 1, y - 1 + dy),
                new Point(x - 1, y + dy), new Point(x + 1, y - 1 + dy), new Point(x + 1, y + dy));
    }
}
