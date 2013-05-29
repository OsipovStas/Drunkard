package ru.spbau.osipov.drunkard.bfs;

import ru.spbau.osipov.drunkard.points.Point;
import ru.spbau.osipov.drunkard.points.Topology;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Osipov Stanislav
 */
public final class PathFinder {

    public static Queue<Point> findPath(Point from, PointValidator to, PointValidator road, Topology topology) {
        return new PathFinder(from, to, road, topology).bfsSearch();
    }

    private Queue<Point> bfsSearch() {
        positionQueue.add(from);
        previousPosition.put(from, null);
        while (!positionQueue.isEmpty()) {
            Point current = positionQueue.poll();
            for (Point adjacentPosition : topology.getAdjacentPoints(current)) {
                if (!isVisited(adjacentPosition) && (road.isSuitable(adjacentPosition) || finalDestination.isSuitable(adjacentPosition))) {
                    previousPosition.put(adjacentPosition, current);
                    positionQueue.add(adjacentPosition);
                    if (finalDestination.isSuitable(adjacentPosition)) {
                        return restorePath(adjacentPosition);
                    }
                }
            }
        }
        return null;
    }

    private boolean isVisited(Point adjacentPosition) {
        return previousPosition.containsKey(adjacentPosition);
    }

    private Queue<Point> restorePath(Point destination) {
        LinkedList<Point> path = new LinkedList<Point>();
        Point current = destination;
        while (current != null) {
            path.addFirst(current);
            current = previousPosition.get(current);
        }
        return path;
    }

    private final Queue<Point> positionQueue = new LinkedList<Point>();
    private final Map<Point, Point> previousPosition = new HashMap<Point, Point>();
    private final Point from;
    private final PointValidator road;
    private final PointValidator finalDestination;
    private final Topology topology;

    private PathFinder(Point from, PointValidator road, PointValidator finalDestination, Topology topology) {
        this.from = from;
        this.road = road;
        this.finalDestination = finalDestination;
        this.topology = topology;
    }
}
