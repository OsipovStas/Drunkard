package ru.spbau.osipov.drunkard.points;

import java.util.List;

/**
 * @author Osipov Stanislav
 */
public interface Topology {
    List<Point> getAdjacentPoints(Point position);
}
