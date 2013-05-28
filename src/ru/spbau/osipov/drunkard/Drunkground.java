package ru.spbau.osipov.drunkard;

import ru.spbau.osipov.drunkard.dynamics.Drunkard;
import ru.spbau.osipov.drunkard.dynamics.DynamicGameObject;
import ru.spbau.osipov.drunkard.dynamics.Policeman;
import ru.spbau.osipov.drunkard.points.Point;
import ru.spbau.osipov.drunkard.statics.Column;
import ru.spbau.osipov.drunkard.statics.LyingDrunkard;
import ru.spbau.osipov.drunkard.statics.SleepingDrunkard;
import ru.spbau.osipov.drunkard.statics.StaticGameObject;

import java.util.*;

/**
 * @author Osipov Stanislav
 */
public class Drunkground implements MoveListener {


    private final int groundSize = 15;
    private final Point column = new Point(7, 7);
    private final Point pub = new Point(9, 0);
    private final Point lantern = new Point(10, 3);
    private final Point policeStation = new Point(14, 3);
    private final List<StaticGameObject> wantedList = new LinkedList<StaticGameObject>();
    private final Map<Point, StaticGameObject> staticGameObjects = new HashMap<Point, StaticGameObject>();
    private int stepCounter = 0;
    private Map<Point, DynamicGameObject> currentState = new HashMap<Point, DynamicGameObject>();
    private Map<Point, DynamicGameObject> nextState = new HashMap<Point, DynamicGameObject>();


    private final static char EMPTY = '.';


    public Drunkground() {
        staticGameObjects.put(column, new Column(column));
        staticGameObjects.put(lantern, new StaticGameObject(lantern) {
            @Override
            public char getRepresentation() {
                return 'L';
            }
        });
        Policeman.callThePolice().setDrunkground(this);
    }

    public char[][] getView() {
        char[][] view = new char[groundSize + 1][groundSize + 1];
        for (char[] aView : view) {
            Arrays.fill(aView, EMPTY);
        }
        for (int i = 0; i < view.length; ++i) {
            view[i][0] = ' ';
        }
        Arrays.fill(view[groundSize], ' ');
        view[pub.getX()][pub.getY()] = 'T';
        view[policeStation.getX() + 1][policeStation.getY() + 1] = 'S';
        for (StaticGameObject staticGameObject : staticGameObjects.values()) {
            view[staticGameObject.getPosition().getX()][staticGameObject.getPosition().getY() + 1] = staticGameObject.getRepresentation();
        }
        for (DynamicGameObject dynamicGameObject : currentState.values()) {
            view[dynamicGameObject.getPosition().getX()][dynamicGameObject.getPosition().getY() + 1] = dynamicGameObject.getRepresentation();
        }
        return view;
    }


    public void step() {
        nextState = new HashMap<Point, DynamicGameObject>();
        for (Map.Entry<Point, DynamicGameObject> dynamicGameObjectEntry : currentState.entrySet()) {
            Point nextPosition = dynamicGameObjectEntry.getValue().nextMove();
            moveGameObject(dynamicGameObjectEntry, nextPosition);
        }
        currentState = nextState;
        drunkardGoesHome();
        policemanWatches();
    }

    private void policemanWatches() {
        Policeman policeman = Policeman.callThePolice();
        if (policeman.isFree() && policeStationExitIsFree() && !wantedList.isEmpty()) {
            policeman.getTheJob(wantedList.remove(0));
            currentState.put(policeStation, policeman);
        }

    }

    private boolean policeStationExitIsFree() {
        return !(staticGameObjects.containsKey(policeStation) || currentState.containsKey(policeStation));
    }

    private void drunkardGoesHome() {
        if (itsTime() && pubsExitIsFree()) {
            currentState.put(pub, new Drunkard(this, pub));
        }
    }

    private boolean pubsExitIsFree() {
        return !(staticGameObjects.containsKey(pub) || currentState.containsKey(pub));
    }

    private boolean itsTime() {
        return stepCounter++ % 20 == 0;
    }

    private void moveGameObject(Map.Entry<Point, DynamicGameObject> dynamicGameObjectEntry, Point nextPosition) {
        DynamicGameObject mover = dynamicGameObjectEntry.getValue();
        if (!isValid(nextPosition)) {
            mover.updatePosition(dynamicGameObjectEntry.getKey());
        } else if (staticGameObjects.containsKey(nextPosition)) {
            mover.collisionPerformed(staticGameObjects.get(nextPosition));
        } else if (currentState.containsKey(nextPosition)) {
            currentStateCollision(nextPosition, mover);
        } else if (nextState.containsKey(nextPosition)) {
            mover.collisionPerformed(nextState.get(nextPosition));
        } else {
            mover.updatePosition(nextPosition);
        }
    }

    private boolean isValid(Point position) {
        return position.getX() < groundSize && position.getY() < groundSize && position.getX() >= 0 && position.getY() >= 0;
    }

    private void currentStateCollision(Point nextPosition, DynamicGameObject mover) {
        DynamicGameObject collision = currentState.get(nextPosition);
        if (collision.isStill(nextPosition)) {
            mover.collisionPerformed(collision);
        } else {
            mover.updatePosition(nextPosition);
        }
    }

    @Override
    public void pickUpPerformed(StaticGameObject fallen) {
        staticGameObjects.remove(fallen.getPosition());
    }

    @Override
    public void fallPerformed(StaticGameObject fallen) {
        checkLanternArea(fallen);
        staticGameObjects.put(fallen.getPosition(), fallen);
        checkPoliceman();
    }

    private void checkPoliceman() {
        if (Policeman.callThePolice().isFree()) {
            return;
        }
        if (policeStationIsBlocked()) {
            Policeman.callThePolice().stopPoliceman();
        }
        if (policemanGoalIsBlocked()) {
            Policeman.callThePolice().pullBack();
        }
    }

    private boolean policemanGoalIsBlocked() {
        Point goal = Policeman.callThePolice().getGoal();
        return !goal.equals(policeStation) && !isPointFree(goal);
    }

    private boolean policeStationIsBlocked() {
        return !(policeStationExitIsFree() && isPointFree(policeStation));
    }

    private boolean isPointFree(Point point) {
        for (Point p : point.getAdjacentPoints()) {
            if (isValid(p) && !staticGameObjects.containsKey(p)) {
                return true;
            }
        }
        return false;
    }

    private void checkLanternArea(StaticGameObject fallen) {
        if (fallen instanceof LyingDrunkard || fallen instanceof SleepingDrunkard) {
            if (inLanternArea(fallen)) {
                wantedList.add(fallen);
            }
        }

    }

    private boolean inLanternArea(StaticGameObject fallen) {
        return Math.sqrt(Math.pow(lantern.getX() - fallen.getPosition().getX(), 2) + Math.pow(lantern.getY() - fallen.getPosition().getY(), 2)) < 3.1;
    }

    @Override
    public void movePerformed(DynamicGameObject mover) {
        nextState.put(mover.getPosition(), mover);
    }

    public int getGroundSize() {
        return groundSize;
    }
}
