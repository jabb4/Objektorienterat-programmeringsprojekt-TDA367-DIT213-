package com.grouptwelve.roguelikegame.model.effects;

public abstract class Effects implements EffectInterface {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
