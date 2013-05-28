package ru.spbau.osipov.drunkard.dynamics;

import ru.spbau.osipov.drunkard.ChangeListener;
import ru.spbau.osipov.drunkard.GameObject;
import ru.spbau.osipov.drunkard.MoveListener;
import ru.spbau.osipov.drunkard.points.Point;
import ru.spbau.osipov.drunkard.statics.StaticGameObject;

/**
 * @author Osipov Stanislav
 */
public abstract class DynamicGameObject extends GameObject implements ChangeListener {

    private Point position;
    private MoveListener drunkground;

    protected DynamicGameObject(MoveListener drunkground, Point position) {
        this.drunkground = drunkground;
        this.position = position;
    }

    public void setDrunkground(MoveListener drunkground) {
        this.drunkground = drunkground;
    }


    protected void fireMoveEvent(DynamicGameObject mover) {
        drunkground.movePerformed(mover);
    }

    protected void fireFallEvent(StaticGameObject fallen) {
        drunkground.fallPerformed(fallen);
    }

    protected void firePickUpEvent(StaticGameObject fallen) {
        drunkground.pickUpPerformed(fallen);
    }

    @Override
    public void updatePosition(Point nextPosition) {
        position = nextPosition;
        drunkground.movePerformed(this);
    }

    @Override
    public Point getPosition() {
        return new Point(position);
    }

    public void setPosition(Point nextPosition) {
        this.position = nextPosition;
    }

    public boolean isStill(Point position) {
        return this.position.equals(position);
    }

    public Point nextMove() {
        return position.randomAdjacentPoint();
    }
}
