package ru.spbau.osipov.drunkard.statics;

import ru.spbau.osipov.drunkard.points.Point;

/**
 * @author Osipov Stanislav
 */
public class Column extends StaticGameObject {

    public Column(Point position) {
        super(position);
    }

    @Override
    public char getRepresentation() {
        return 'C';
    }
}
