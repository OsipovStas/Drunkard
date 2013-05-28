package ru.spbau.osipov.drunkard.statics;

import ru.spbau.osipov.drunkard.points.Point;

/**
 * @author Osipov Stanislav
 */
public class Bottle extends StaticGameObject {
    public Bottle(Point position) {
        super(position);
    }

    @Override
    public char getRepresentation() {
        return 'B';
    }
}
