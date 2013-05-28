package ru.spbau.osipov.drunkard.statics;

import ru.spbau.osipov.drunkard.points.Point;

/**
 * @author Osipov Stanislav
 */
public class SleepingDrunkard extends StaticGameObject {

    public SleepingDrunkard(Point position) {
        super(position);
    }

    @Override
    public char getRepresentation() {
        return 'Z';
    }
}
