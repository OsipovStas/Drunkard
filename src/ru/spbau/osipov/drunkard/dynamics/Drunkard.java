package ru.spbau.osipov.drunkard.dynamics;

import ru.spbau.osipov.drunkard.GameObject;
import ru.spbau.osipov.drunkard.MoveListener;
import ru.spbau.osipov.drunkard.points.Point;
import ru.spbau.osipov.drunkard.statics.Bottle;
import ru.spbau.osipov.drunkard.statics.Column;
import ru.spbau.osipov.drunkard.statics.LyingDrunkard;
import ru.spbau.osipov.drunkard.statics.SleepingDrunkard;

import java.util.Random;

/**
 * @author Osipov Stanislav
 */
public class Drunkard extends DynamicGameObject {

    private boolean hasBottle = true;
    private final static Random rng = new Random();


    public Drunkard(MoveListener drunkground, Point position) {
        super(drunkground, position);
    }


    @Override
    public void updatePosition(Point nextPosition) {
        if (hasBottle && !nextPosition.equals(getPosition()) && rng.nextInt(100) > 70) {
            fireFallEvent(new Bottle(getPosition()));
            hasBottle = false;
        }
        super.updatePosition(nextPosition);
    }

    @Override
    public void collisionPerformed(GameObject collision) {
        if (collision instanceof Column || collision instanceof SleepingDrunkard) {
            fireFallEvent(new SleepingDrunkard(getPosition()));
        } else if (collision instanceof Bottle) {
            fireFallEvent(new LyingDrunkard(getPosition()));
        } else {
            fireMoveEvent(this);
        }
    }

    @Override
    public char getRepresentation() {
        return 'D';
    }
}
