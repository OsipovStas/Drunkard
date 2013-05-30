package ru.spbau.osipov.drunkard.model;

import ru.spbau.osipov.drunkard.points.Point;

/**
 * @author Osipov Stanislav
 */
public interface ChangeListener {
    void collisionPerformed(GameObject collision);

    void updatePosition(Point nextPosition);
}
