package com.wusp.indicatorbox_library.Particle;

import android.graphics.Bitmap;

/**
 * Created by wusp on 16/4/3.
 */
public class BaseOneParticleInitializer implements ParticleInitializer {
    private int number;
    private int mStartX;
    private int mStartY;
    private Bitmap particleBitmap;

    public BaseOneParticleInitializer(int number, int mStartX, int mStartY, Bitmap particleBitmap) {
        this.number = number;
        this.mStartX = mStartX;
        this.mStartY = mStartY;
        this.particleBitmap = particleBitmap;
    }

    public BaseOneParticleInitializer() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getmStartX() {
        return mStartX;
    }

    public void setmStartX(int mStartX) {
        this.mStartX = mStartX;
    }

    public int getmStartY() {
        return mStartY;
    }

    public void setmStartY(int mStartY) {
        this.mStartY = mStartY;
    }

    public Bitmap getParticleBitmap() {
        return particleBitmap;
    }

    public void setParticleBitmap(Bitmap particleBitmap) {
        this.particleBitmap = particleBitmap;
    }
}
