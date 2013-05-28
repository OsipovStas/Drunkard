package ru.spbau.osipov.drunkard.statics;

import ru.spbau.osipov.drunkard.points.Point;

/**
 * @author Osipov Stanislav
 */
public class LyingDrunkard extends StaticGameObject {

    public LyingDrunkard(Point position) {
        super(position);
    }

    @Override
    public char getRepresentation() {
        return '&';
    }
}
