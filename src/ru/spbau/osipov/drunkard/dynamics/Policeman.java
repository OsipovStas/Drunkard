package ru.spbau.osipov.drunkard.dynamics;

import ru.spbau.osipov.drunkard.GameObject;
import ru.spbau.osipov.drunkard.points.Direction;
import ru.spbau.osipov.drunkard.points.Point;
import ru.spbau.osipov.drunkard.statics.LyingDrunkard;
import ru.spbau.osipov.drunkard.statics.SleepingDrunkard;
import ru.spbau.osipov.drunkard.statics.StandingPoliceman;
import ru.spbau.osipov.drunkard.statics.StaticGameObject;

/**
 * @author Osipov Stanislav
 */
public class Policeman extends DynamicGameObject {

    private Point goal;
    private int lockedCounter;
    private boolean busy;

    private final static Point policeStation = new Point(14, 3);

    private final static Policeman JACK = new Policeman();
    private boolean isStopped;


    private Policeman() {
        super(null, null);
        busy = false;
        isStopped = false;
    }

    public boolean isFree() {
        return !busy;
    }

    @Override
    public Point nextMove() {
        if (isStopped) {
            return getPosition();
        }
        if (lockedCounter-- > 0) {
            return super.nextMove();
        }
        Point myPosition = getPosition();
        return chooseDirection(myPosition);
    }

    private Point chooseDirection(Point myPosition) {
        if (myPosition.getY() < goal.getY()) {
            return myPosition.adjacentPoint(Direction.UP);
        } else if (myPosition.getY() > goal.getY()) {
            return myPosition.adjacentPoint(Direction.DOWN);
        } else if (myPosition.getX() < goal.getX()) {
            return myPosition.adjacentPoint(Direction.RIGHT);
        } else if (myPosition.getX() > goal.getX()) {
            return myPosition.adjacentPoint(Direction.LEFT);
        } else {
            return myPosition.randomAdjacentPoint();
        }
    }

    @Override
    public void updatePosition(Point nextPosition) {
        if (nextPosition.equals(goal) && goal.equals(policeStation)) {
            setPosition(nextPosition);
            busy = false;
        } else {
            super.updatePosition(nextPosition);
        }
    }


    @Override
    public void collisionPerformed(GameObject collision) {
        if (collision instanceof Policeman) {
            fireFallEvent(new StandingPoliceman(getPosition()));
        } else if (collision.getPosition().equals(goal) && (collision instanceof SleepingDrunkard || collision instanceof LyingDrunkard)) {
            pickUpDrunkard(collision);
        } else {
            lockedCounter = 10;
            fireMoveEvent(this);
        }
    }

    public Point getGoal() {
        return goal;
    }

    private void pickUpDrunkard(GameObject collision) {
        goal = policeStation;
        firePickUpEvent((StaticGameObject) collision);
        updatePosition(collision.getPosition());
        lockedCounter = 0;
    }

    public static Policeman callThePolice() {
        return JACK;
    }

    public void getTheJob(StaticGameObject wanted) {
        busy = true;
        setPosition(policeStation);
        goal = wanted.getPosition();
        fireMoveEvent(this);
    }

    public void stopPoliceman() {
        isStopped = true;
    }

    public void pullBack() {
        goal = policeStation;
    }

    @Override
    public char getRepresentation() {
        return 'P';
    }
}
