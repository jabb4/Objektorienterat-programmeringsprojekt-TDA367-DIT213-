package com.grouptwelve.roguelikegame.model.EffectsPackage;

public abstract class Effects implements EffectInterface {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
