package com.wusp.indicatorbox_library.Particle;

/**
 * Created by wusp on 16/4/1.
 */
public class TranslateModifier implements ParticleModifier {
    private float mInitialX;
    private float mInitialY;
    private float mFinalX;
    private float mFinalY;
    private float mValueXIncrement;
    private float mValueYIncrement;

    public TranslateModifier(float mInitialX, float mInitialY,
                             float mFinalX, float mFinalY) {
        this.mInitialX = mInitialX;
        this.mInitialY = mInitialY;
        this.mFinalX = mFinalX;
        this.mFinalY = mFinalY;
        this.mValueXIncrement = mFinalX - mInitialX;
        this.mValueYIncrement = mFinalY - mInitialY;
    }

    public TranslateModifier() {
    }

    @Override
    public void setState(Particle particle, float interpolatorValue) {
        if (interpolatorValue <= 0){
            particle.setCurrentX(mInitialX);
            particle.setCurrentY(mInitialY);
        }else if (interpolatorValue >= 1){
            particle.setCurrentX(mFinalX);
            particle.setCurrentY(mFinalY);
        }else{
            particle.setCurrentX(mInitialX + mValueXIncrement * interpolatorValue);
            particle.setCurrentY(mInitialY + mValueYIncrement * interpolatorValue);
        }
    }

    public float getmInitialX() {
        return mInitialX;
    }

    public void setmInitialX(float mInitialX) {
        this.mInitialX = mInitialX;
    }

    public float getmInitialY() {
        return mInitialY;
    }

    public void setmInitialY(float mInitialY) {
        this.mInitialY = mInitialY;
    }

    public float getmFinalX() {
        return mFinalX;
    }

    public void setmFinalX(float mFinalX) {
        this.mFinalX = mFinalX;
    }

    public float getmFinalY() {
        return mFinalY;
    }

    public void setmFinalY(float mFinalY) {
        this.mFinalY = mFinalY;
    }

    public float getmValueXIncrement() {
        return mValueXIncrement;
    }

    public void setmValueXIncrement(float mValueXIncrement) {
        this.mValueXIncrement = mValueXIncrement;
    }

    public float getmValueYIncrement() {
        return mValueYIncrement;
    }

    public void setmValueYIncrement(float mValueYIncrement) {
        this.mValueYIncrement = mValueYIncrement;
    }
}
