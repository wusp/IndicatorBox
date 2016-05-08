package com.wusp.indicatorbox_library.ProgressBar;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.wusp.indicatorbox_library.R;

/**
 * Created by wusp on 16/3/11.
 */
public class LineProgressBar extends View {
    private boolean isShowing = true;
    private int goal = 100;
    private long duration = 200;
    private int progress;
    private int mReachedColor;
    private int mUnReachedColor;
    private int mCompletedColor;
    private float mBarHeight;
    private Paint mPaint;
    private boolean isGoalReached = progress >= goal;

    public LineProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public LineProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineProgressBar, 0, 0);
        mReachedColor = typedArray.getColor(R.styleable.LineProgressBar_progressed_color, Color.rgb(0, 239, 255));
        mUnReachedColor = typedArray.getColor(R.styleable.LineProgressBar_unprogressed_color, Color.parseColor("#E6E6E6"));
        mCompletedColor = typedArray.getColor(R.styleable.LineProgressBar_completed_color, Color.rgb(0, 71, 255));
        mBarHeight = typedArray.getDimensionPixelSize(R.styleable.LineProgressBar_bar_height, 3);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        isShowing = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Cause the width was set to MATCH_PARENT
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        switch (MeasureSpec.getMode(heightMeasureSpec)){
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = (int) Math.min(heightSize, mBarHeight);
                break;
            default:
                height = (int) mBarHeight;
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int halfHeight = getHeight() / 2;
        int progressEndX = (int) (getWidth() * progress / 100f);
        mPaint.setStrokeWidth(mBarHeight);
        mPaint.setColor(mReachedColor);
        //Draw the reached line.
        canvas.drawLine(0, halfHeight, progressEndX, halfHeight, mPaint);
        //Draw the unreached line.
        mPaint.setColor(mUnReachedColor);
        canvas.drawLine(progressEndX, halfHeight, getWidth(), halfHeight, mPaint);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt("progress", progress);
        bundle.putParcelable("superState", super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            state = bundle.getParcelable("superState");
            setProgress(bundle.getInt("progress"));
        }
        super.onRestoreInstanceState(state);
    }

    public void setProgress(int progress){
        float currentProgress = -1;
        if (progress == 100){
            currentProgress = this.progress;
        }
        if (currentProgress != -1 && isShowing) {
            flashOut((int) ((100 - currentProgress) / 25) * (int)duration);
        }
        this.progress = progress;
        mReachedColor = currentColorDuringTransition(mReachedColor, mCompletedColor,
                ((float) progress / (float) goal));
        updateProgressReached();
        invalidate();
    }

    private void flashOut(int duration){
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(this, "backgroundColor",
                new ArgbEvaluator(), mCompletedColor, Color.parseColor("#ffffff"));
        colorAnimator.setDuration(duration);
        colorAnimator.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 0.0f);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        set.play(colorAnimator).with(objectAnimator);
        if (!set.isStarted()){
            set.start();
        }
        isShowing = false;
    }

    private LineProgressBar getLineProgressBar(){
        return this;
    }

    private void updateProgressReached(){
        isGoalReached = progress >= goal;
    }

    /**
     * Use HSV to calculate the color transition.
     * @param startColor startColor
     * @param endColor  endColor
     * @param fraction fraction
     * @return int current color.
     */
    private int currentColorDuringTransition(int startColor, int endColor, float fraction){
        final float[] from = new float[3], to = new float[3];
        //fetch startColor
        Color.colorToHSV(startColor, from);
        //fetch endColor
        Color.colorToHSV(endColor, to);
        final float[] hsv = new float[3];
        hsv[0] = from[0] + (to[0] - from[0]) * fraction;
        hsv[1] = from[1] + (to[1] - from[1]) * fraction;
        hsv[2] = from[2] + (to[2] - from[2]) * fraction;
        return Color.HSVToColor(hsv);
    }
}
