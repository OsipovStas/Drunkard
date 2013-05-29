package ru.spbau.osipov.drunkard.buildings;

import ru.spbau.osipov.drunkard.dynamics.DynamicGameObject;
import ru.spbau.osipov.drunkard.points.Point;
import ru.spbau.osipov.drunkard.points.Topology;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Osipov Stanislav
 */
public abstract class Building {

    public Point getPosition() {
        return position;
    }

    protected final List<DynamicGameObject> visitors = new CopyOnWriteArrayList<DynamicGameObject>();
    private final Point position;

    protected Building(Point position) {
        this.position = position;
    }

    public void nextMove(Topology topology) {
        for (DynamicGameObject visitor : visitors) {
            visitor.nextMove(topology);
        }
    }


}
