package com.wusp.indicatorbox_library.RippleCircle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.wusp.indicatorbox_library.R;
import com.wusp.indicatorbox_library.StaticPatternDrawer;
import com.wusp.indicatorbox_library.ToolBox.Utils;

/**
 * Created by wusp on 16/4/15.
 */
public class RippleCircle extends View{
    private Paint staticPaint;
    //Used to draw ripple expandable circle
    private Paint dynamicPaint;
    //Under circle radius
    protected int mCircleRadius;
    //Under circle background
    private int mCircleColor;
    //The max radius the whole circle will expand to.
    //For re-size this view, the mMaxRadius is equals to mCircleRadius + 16 for now.
    protected int mMaxRadius;
    //The ripple expandable circle color.
    private int mRippleColor;
    //Used to calculate the ripple expandable circle width and position.
    private float mRippleProgress;
    //Animation duration
    private long mDuration;
    //The ripple width
    private int mRippleWidth;
    private boolean isRippling = false;
    protected StaticPatternDrawer patternDrawer;
    //Used to controll the ripple animation.
    private ValueAnimator rippleAnimator;
    
    public RippleCircle(Context context) {
        super(context);
        init(context, null);
    }

    public RippleCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RippleCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initTypedArray(context, attrs);
        staticPaint = new Paint();
        dynamicPaint = new Paint();
        mMaxRadius = mCircleRadius + mRippleWidth;
        mDuration = 800;
        initRippleAnimator();
    }

    private void initRippleAnimator() {
        rippleAnimator = ValueAnimator.ofFloat(0, 1);
        rippleAnimator.setDuration(mDuration);
        rippleAnimator.setInterpolator(new DecelerateInterpolator());
        rippleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRippleProgress = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        rippleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isRippling = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isRippling = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initTypedArray(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleCircle);
        mCircleRadius = typedArray.getDimensionPixelOffset(R.styleable.RippleCircle_circle_radius, 28);
        mCircleColor = typedArray.getColor(R.styleable.RippleCircle_circle_color, Color.BLACK);
        mRippleColor = typedArray.getColor(R.styleable.RippleCircle_ripple_color, mCircleColor);
        mRippleWidth = typedArray.getDimensionPixelOffset(R.styleable.RippleCircle_ripple_width, 20);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int resultWidth = Utils.getAdjustedSize(mMaxRadius * 2, widthMeasureSpec);
        int resultHeight = Utils.getAdjustedSize(mMaxRadius * 2, heightMeasureSpec);
        setMeasuredDimension(resultWidth, resultHeight);
    }

    /**
     * For view re-create, you should save the text currently showed on window which might be changed during a series of processing.
     * @return
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRippling", isRippling);
        bundle.putParcelable("superState", super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            isRippling = bundle.getBoolean("isRippling");
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

    /**If this view is attached to window, and before the state chaned the rippling is play,
     * start animation automatically.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isRippling){
            startAnimation();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int halfWidth = getWidth() / 2;
        int halfHeight = getHeight() / 2;
        /**Draw circle background*/
        staticPaint.setAntiAlias(true);
        staticPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        staticPaint.setColor(mCircleColor);
        canvas.drawCircle(halfWidth, halfHeight, mCircleRadius, staticPaint);
        /**Draw text*/
        if (patternDrawer != null) {
            patternDrawer.onDrawPattern(canvas, getWidth(), getHeight());
        }
        /**Draw the dynamic ripple effects*/
        if (mRippleProgress > 0 && mRippleProgress < 1.0f
                && isRippling) {
            dynamicPaint.setStyle(Paint.Style.STROKE);
            dynamicPaint.setAntiAlias(true);
            dynamicPaint.setColor(mRippleColor);
            dynamicPaint.setStrokeWidth((mMaxRadius - mCircleRadius) * mRippleProgress);
            dynamicPaint.setAlpha((int) (125 - 100 * mRippleProgress));
            canvas.drawCircle(halfWidth, halfHeight, mCircleRadius + (mMaxRadius - mCircleRadius) * mRippleProgress / 2, dynamicPaint);
        }
    }

    public void startInfiniteAnimation(){
        if (rippleAnimator == null){
            initRippleAnimator();
        }else {
            rippleAnimator.setRepeatCount(ValueAnimator.INFINITE);
            rippleAnimator.setDuration(mDuration);
        }
        startAnimation();
    }

    public void startAnimationOnce(){
        if (rippleAnimator == null){
            initRippleAnimator();
        }else {
            rippleAnimator.setRepeatCount(0);
            rippleAnimator.setDuration(mDuration);
        }
        startAnimation();
    }

    public void stopAnimation(){
        if (rippleAnimator != null && rippleAnimator.isRunning()){
            rippleAnimator.end();
            rippleAnimator.cancel();
        }
    }

    private void startAnimation(){
        if (rippleAnimator == null){
            return;
        }
        if (rippleAnimator.isRunning()){
            rippleAnimator.end();
            rippleAnimator.cancel();
        }
        rippleAnimator.start();
    }

    public StaticPatternDrawer getPatternDrawer() {
        return patternDrawer;
    }

    public void setPatternDrawer(StaticPatternDrawer patternDrawer) {
        this.patternDrawer = patternDrawer;
    }

    public int getmCircleColor() {
        return mCircleColor;
    }

    public void setCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
        postInvalidate();
    }

    public int getmRippleColor() {
        return mRippleColor;
    }

    public void setRippleColor(int mRippleColor) {
        this.mRippleColor = mRippleColor;
        postInvalidate();
    }

    public long getmDuration() {
        return mDuration;
    }

    public void setDuration(long mDuration) {
        this.mDuration = mDuration;
        postInvalidate();
    }

    public void setmRippleColor(int mRippleColor) {
        this.mRippleColor = mRippleColor;
    }

    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    public int getmCircleRadius() {
        return mCircleRadius;
    }

    public void setmCircleRadius(int mCircleRadius) {
        this.mCircleRadius = mCircleRadius;
    }

    public int getmRippleWidth() {
        return mRippleWidth;
    }

    public void setmRippleWidth(int mRippleWidth) {
        this.mRippleWidth = mRippleWidth;
    }

    public void setmDuration(long mDuration) {
        this.mDuration = mDuration;
    }
}
