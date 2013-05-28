package ru.spbau.osipov.drunkard.statics;

import ru.spbau.osipov.drunkard.points.Point;

/**
 * @author Osipov Stanislav
 */
public class StandingPoliceman extends StaticGameObject {
    public StandingPoliceman(Point position) {
        super(position);
    }

    @Override
    public char getRepresentation() {
        return 'P';
    }
}
