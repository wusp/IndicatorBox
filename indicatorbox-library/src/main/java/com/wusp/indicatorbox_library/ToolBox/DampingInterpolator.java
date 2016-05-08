package com.wusp.indicatorbox_library.ToolBox;

import android.view.animation.Interpolator;

/**
 * This interpolator would perform a damping vibration curve which has been accustomed.
 * Created by wusp on 16/3/31.
 */
public class DampingInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        return (float) (1 - Math.exp(-3 * input) * Math.cos(10 * input));
    }
}
