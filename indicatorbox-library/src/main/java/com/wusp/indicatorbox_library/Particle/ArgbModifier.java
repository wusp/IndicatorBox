package com.wusp.indicatorbox_library.Particle;

import android.animation.ArgbEvaluator;

/**
 * Base on ArgbEvaluator.
 * Created by wusp on 16/4/13.
 */
public class ArgbModifier implements ParticleModifier {
    private int startColor;
    private int endColor;
    private ArgbEvaluator argbEvaluator;

    public ArgbModifier(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        argbEvaluator = new ArgbEvaluator();
    }

    @Override
    public void setState(Particle particle, float interpolatorValue) {
        if (interpolatorValue < 0){
            particle.getmPaint().setColor(startColor);
        }else if (interpolatorValue >= 1){
            particle.getmPaint().setColor(endColor);
        }else{
            particle.getmPaint().setColor((Integer) argbEvaluator.evaluate(interpolatorValue, startColor, endColor));
        }
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }
}
