package ru.spbau.osipov.drunkard.dynamics;

import ru.spbau.osipov.drunkard.Drunkground;
import ru.spbau.osipov.drunkard.GameObject;
import ru.spbau.osipov.drunkard.bfs.PathFinder;
import ru.spbau.osipov.drunkard.bfs.PointValidator;
import ru.spbau.osipov.drunkard.buildings.RecyclePoint;
import ru.spbau.osipov.drunkard.points.Point;
import ru.spbau.osipov.drunkard.points.Topology;
import ru.spbau.osipov.drunkard.statics.Bottle;

import java.util.Queue;

/**
 * @author Osipov Stanislav
 */
public final class Lazar extends DynamicGameObject {

    private final Drunkground drunkground;
    private boolean hasBottle = false;
    private boolean spendingMoney = true;

    private final RecyclePoint recyclePoint;
    private final Point spawn;
    private int money = FEE;
    private final static int FEE = 30;

    private Queue<Point> path = null;

    public Lazar(Drunkground drunkground, RecyclePoint recyclePoint, Point spawn) {
        super(drunkground, spawn);
        this.drunkground = drunkground;
        this.recyclePoint = recyclePoint;
        this.spawn = spawn;
    }

    @Override
    public Point nextMove(Topology topology) {
        if (haveFun()) {
            assert spendingMoney;
            --money;
            return getPosition();
        }
        if (spendingMoney) {
            if (drunkground.isFreePoint(spawn)) {
                recyclePoint.exit(this);
                fireEnterEvent(this, spawn);
                spendingMoney = false;
                return spawn;
            }
            return getPosition();
        }
        if (path == null) {
            path = hasBottle ? goToRecycle() : findBottle();
            if (path != null) {
                path.poll();
            }
        }
        if (path != null) {
            return follow();
        }
        return super.nextMove(topology);

    }

    private Point follow() {
        assert path != null;
        if (path.size() == 0) {
            if (hasBottle) {
                return enterRecyclePoint();
            }
        }
        return path.poll();
    }

    private Point pickupBottle(Bottle bottle) {
        firePickUpEvent(bottle);
        hasBottle = true;
        return getPosition();
    }

    private Point enterRecyclePoint() {
        recyclePoint.letLazarIn(this);
        hasBottle = false;
        spendingMoney = true;
        money = FEE;
        path = null;
        return recyclePoint.getPosition();
    }

    @Override
    public void updatePosition(Point nextPosition) {
        super.updatePosition(nextPosition);
        if (spendingMoney) {
            fireGoneEvent(this);
        }
    }

    private Queue<Point> goToRecycle() {
        return PathFinder.findPath(getPosition(), drunkground.emptyPositionValidator(), new PointValidator() {
            @Override
            public boolean isSuitable(Point point) {
                return recyclePoint.getPosition().equals(point);
            }
        }, drunkground.getTopology());
    }

    private Queue<Point> findBottle() {
        return PathFinder.findPath(getPosition(), drunkground.emptyPositionValidator(), drunkground.staticObjectValidator(Bottle.class), drunkground.getTopology());
    }

    private boolean haveFun() {
        return money > 0;
    }

    @Override
    public void collisionPerformed(GameObject collision) {
        if (collision instanceof Lazar && spendingMoney) {
            fireGoneEvent(this);
            return;
        }
        if (collision instanceof Bottle) {
            pickupBottle((Bottle) collision);
        }
        path = null;
        fireMoveEvent(this);
    }

    @Override
    public char getRepresentation() {
        return 'z';
    }
}
