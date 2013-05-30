package ru.spbau.osipov.drunkard.model;

import ru.spbau.osipov.drunkard.dynamics.DynamicGameObject;
import ru.spbau.osipov.drunkard.statics.StaticGameObject;

/**
 * @author Osipov Stanislav
 */
public interface MoveListener {
    void movePerformed(DynamicGameObject mover);

    void fallPerformed(StaticGameObject fallen);

    void pickUpPerformed(StaticGameObject fallen);

    void gonePerformed(DynamicGameObject mover);

    void enterPerformed(DynamicGameObject mover);
}
