package ru.spbau.osipov.drunkard;

import ru.spbau.osipov.drunkard.dynamics.DynamicGameObject;
import ru.spbau.osipov.drunkard.statics.StaticGameObject;

/**
 * @author Osipov Stanislav
 */
public interface MoveListener {
    void movePerformed(DynamicGameObject mover);

    void fallPerformed(StaticGameObject fallen);

    void pickUpPerformed(StaticGameObject fallen);
}
