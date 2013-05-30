package ru.spbau.osipov.drunkard.model;

import ru.spbau.osipov.drunkard.bfs.PointValidator;
import ru.spbau.osipov.drunkard.buildings.RecyclePoint;
import ru.spbau.osipov.drunkard.dynamics.Drunkard;
import ru.spbau.osipov.drunkard.dynamics.DynamicGameObject;
import ru.spbau.osipov.drunkard.dynamics.Lazar;
import ru.spbau.osipov.drunkard.points.Point;
import ru.spbau.osipov.drunkard.points.Topology;
import ru.spbau.osipov.drunkard.statics.Column;
import ru.spbau.osipov.drunkard.statics.StaticGameObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Osipov Stanislav
 */
public final class Drunkground implements MoveListener {


    private final int groundSize = 15;
    private final Point pub = new Point(9, 0);
    private final Topology topology;
    private final Map<Point, StaticGameObject> staticGameObjects = new HashMap<Point, StaticGameObject>();
    private int stepCounter = 0;
    private Map<Point, DynamicGameObject> currentState = new HashMap<Point, DynamicGameObject>();
    private Map<Point, DynamicGameObject> nextState = new HashMap<Point, DynamicGameObject>();
    private final RecyclePoint recyclePoint = new RecyclePoint(new Point(0, 4));

    private final static char EMPTY = '.';


    public Drunkground(Topology topology) {
        this.topology = topology;
        Point column = new Point(7, 7);
        staticGameObjects.put(column, new Column(column));
        Point lantern = new Point(10, 3);
        staticGameObjects.put(lantern, new StaticGameObject(lantern) {
            @Override
            public char getRepresentation() {
                return 'L';
            }
        });
        recyclePoint.letLazarIn(new Lazar(this, recyclePoint, recyclePoint.getPosition()));
    }

    public char[][] getView() {
        char[][] view = new char[groundSize + 2][groundSize + 1];
        for (char[] aView : view) {
            Arrays.fill(aView, EMPTY);
        }
        for (int i = 0; i < view.length; ++i) {
            view[i][0] = ' ';
        }
        Arrays.fill(view[groundSize + 1], ' ');
        Arrays.fill(view[0], ' ');
        view[pub.getX() + 1][pub.getY()] = 'T';
        view[recyclePoint.getPosition().getX()][recyclePoint.getPosition().getY() + 1] = 'R';
        for (StaticGameObject staticGameObject : staticGameObjects.values()) {
            view[staticGameObject.getPosition().getX() + 1][staticGameObject.getPosition().getY() + 1] = staticGameObject.getRepresentation();
        }
        for (DynamicGameObject dynamicGameObject : currentState.values()) {
            view[dynamicGameObject.getPosition().getX() + 1][dynamicGameObject.getPosition().getY() + 1] = dynamicGameObject.getRepresentation();
        }
        return view;
    }


    public void step() {
        nextState = new HashMap<Point, DynamicGameObject>();
        for (Map.Entry<Point, DynamicGameObject> dynamicGameObjectEntry : currentState.entrySet()) {
            Point nextPosition = dynamicGameObjectEntry.getValue().nextMove(topology);
            moveGameObject(dynamicGameObjectEntry, nextPosition);
        }
        recyclePoint.nextMove(topology);
        currentState = nextState;
        drunkardGoesHome();
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

    public boolean isFreePoint(Point point) {
        return !(staticGameObjects.containsKey(point) || currentState.containsKey(point) || nextState.containsKey(point));
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

    public PointValidator emptyPositionValidator() {
        return new PointValidator() {
            @Override
            public boolean isSuitable(Point point) {
                return isFreePoint(point) && isValid(point);
            }
        };
    }

    public Topology getTopology() {
        return topology;
    }

    public PointValidator staticObjectValidator(final Class<? extends StaticGameObject> staticObjectClass) {
        return new PointValidator() {
            @Override
            public boolean isSuitable(Point point) {
                return staticGameObjects.containsKey(point) && staticGameObjects.get(point).getClass().getName().equals(staticObjectClass.getName());
            }
        };

    }


    @Override
    public void pickUpPerformed(StaticGameObject fallen) {
        staticGameObjects.remove(fallen.getPosition());
    }

    @Override
    public void fallPerformed(StaticGameObject fallen) {
        staticGameObjects.put(fallen.getPosition(), fallen);
    }


    @Override
    public void movePerformed(DynamicGameObject mover) {
        nextState.put(mover.getPosition(), mover);
    }

    @Override
    public void gonePerformed(DynamicGameObject mover) {
        nextState.remove(mover.getPosition());
    }

    @Override
    public void enterPerformed(DynamicGameObject mover) {
        nextState.put(mover.getPosition(), mover);
    }
}
