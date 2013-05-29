package ru.spbau.osipov.drunkard.buildings;

import ru.spbau.osipov.drunkard.dynamics.Lazar;
import ru.spbau.osipov.drunkard.points.Point;

/**
 * @author Osipov Stanislav
 */
public class RecyclePoint extends Building {

    public RecyclePoint(Point position) {
        super(position);
    }

    public void letLazarIn(Lazar lazar) {
        visitors.add(lazar);
    }

    public void exit(Lazar lazar) {
        visitors.remove(lazar);
    }
}
