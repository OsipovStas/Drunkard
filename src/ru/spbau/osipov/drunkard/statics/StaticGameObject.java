package ru.spbau.osipov.drunkard.statics;

import ru.spbau.osipov.drunkard.GameObject;
import ru.spbau.osipov.drunkard.points.Point;

/**
 * @author Osipov Stanislav
 */
public abstract class StaticGameObject extends GameObject {

    private final Point position;

    protected StaticGameObject(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

}
