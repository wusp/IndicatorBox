package com.wusp.indicatorbox_library.Particle;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.AnticipateInterpolator;

import com.wusp.indicatorbox_library.ToolBox.DampingInterpolator;

/**
 * Created by wusp on 16/3/31.
 */
public class GeneralAnimatorGenerator {
    /**
     * Make a heart-beat expand animation.
     * @param duration
     * @return
     */
    public static Animator heartbeatExpandAnimator(int duration){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator xExpandAnimator = new ObjectAnimator();
        xExpandAnimator.setPropertyName("scaleX");
        xExpandAnimator.setFloatValues(0.5f, 1.0f);
        ObjectAnimator yExpandAnimator = new ObjectAnimator();
        yExpandAnimator.setPropertyName("scaleY");
        yExpandAnimator.setFloatValues(0.5f, 1.0f);
        animatorSet.play(xExpandAnimator).with(yExpandAnimator);
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new DampingInterpolator());

        return animatorSet;
    }

    /**
     * Make a damping vibration animation.
     * @param duration
     * @return
     */
    public static ValueAnimator dampingValueAnimator(long duration){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new DampingInterpolator());
        return valueAnimator;
    }

    /**
     * Make a shrink animation.
     * @param duration
     * @return
     */
    public static Animator shrinkAnimator(int duration){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator xExpandAnimator = new ObjectAnimator();
        xExpandAnimator.setPropertyName("scaleX");
        xExpandAnimator.setFloatValues(1.0f, 0.5f);
        ObjectAnimator yExpandAnimator = new ObjectAnimator();
        yExpandAnimator.setPropertyName("scaleY");
        yExpandAnimator.setFloatValues(1.0f, 0.5f);
        animatorSet.play(xExpandAnimator).with(yExpandAnimator);
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new AnticipateInterpolator());
        return animatorSet;
    }
}
