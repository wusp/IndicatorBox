package com.wusp.indicatorbox_library.Particle;

/**
 * Created by wusp on 16/4/1.
 */
public interface ParticleModifier {
    /** Used to set the particle real state state based on execute time.*/
    void setState(Particle particle, float interpolatorValue);
}
