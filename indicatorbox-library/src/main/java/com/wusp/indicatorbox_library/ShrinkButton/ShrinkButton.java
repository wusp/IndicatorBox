package com.wusp.indicatorbox_library.ShrinkButton;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.wusp.indicatorbox_library.DynamicPatternDrawer;

/**
 * Created by wusp on 16/4/17.
 */
public class ShrinkButton extends Button implements View.OnTouchListener{
    public static final int STATE_EXPANDED = 0;
    public static final int STATE_SHRINKING = 1;
    public static final int STATE_SHRINKED = 2;
    public static final int STATE_PROGRESSING = 3;
    public static final int STATE_EXPANDING = 4;
    //The button's width first created.
    private int mButtonInitialWidth;
    //Used to control the progressing drawing.
    private float mProgressFraction = 0;
    //How speed these animations would displayed.
    private int mShrinkingDuration = 400;
    private int mProgressingDuration = 600;
    //Used to control the button shrinking animation.
    private ValueAnimator mShrinkingController;
    //Used to control the progressing animation.
    private ValueAnimator mProgressingController;
    //Used to control the back-expanding animation.
    private ValueAnimator mExpandingController;
    //To remark which
    private int animationState = -1;
    //Used to draw the inner pattern.
    private DynamicPatternDrawer patternDrawer;
    //Used to indicate whether is showing on the front window.
    private boolean isWindowFocused = false;
    //Used to record the button text (if there is).
    private String buttonText;

    public ShrinkButton(Context context) {
        super(context);
        init();
    }

    public ShrinkButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShrinkButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        animationState = STATE_EXPANDED;
        patternDrawer = new CircleLineAndPoint();
        setOnTouchListener(this);
    }

    private void initShrinkingAnimationController(){
        mShrinkingController = ValueAnimator.ofFloat(0, 1);
        mShrinkingController.setDuration(mShrinkingDuration);
        mShrinkingController.setInterpolator(new DecelerateInterpolator());
        mShrinkingController.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams lp = getLayoutParams();
                lp.width = (int) (mButtonInitialWidth - (mButtonInitialWidth - lp.height) * (float) animation.getAnimatedValue());
                setLayoutParams(lp);
            }
        });
        mShrinkingController.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //Make text disappeared.
                buttonText = getText().toString();
                setText(null);
                animationState = STATE_SHRINKING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationState = STATE_SHRINKED;
                startProgressingAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initExpandingAnimationController(){
        mExpandingController = ValueAnimator.ofFloat(0, 1);
        mExpandingController.setDuration(mShrinkingDuration);
        mExpandingController.setInterpolator(new DecelerateInterpolator());
        mExpandingController.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams lp = getLayoutParams();
                lp.width = (int) (lp.height + (mButtonInitialWidth - lp.height) * (float) animation.getAnimatedValue());
                setLayoutParams(lp);
            }
        });
        mExpandingController.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animationState = STATE_EXPANDING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationState = STATE_EXPANDED;
                if (null != buttonText){
                    setText(buttonText);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initProgressingAnimationController(){
        mProgressingController = ValueAnimator.ofFloat(0, 1);
        mProgressingController.setDuration(mProgressingDuration);
        mProgressingController.setRepeatCount(ValueAnimator.INFINITE);
        mProgressingController.setInterpolator(new DecelerateInterpolator());
        mProgressingController.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgressFraction = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mProgressingController.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animationState = STATE_PROGRESSING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationState = STATE_SHRINKED;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (animationState > 0){
            ViewGroup.LayoutParams lp = getLayoutParams();
            lp.width = getHeight();
            setLayoutParams(lp);
            setText(null);
            if (animationState == STATE_PROGRESSING) {
                startProgressingAnimation();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((animationState == STATE_PROGRESSING || animationState == STATE_SHRINKED) && patternDrawer != null) {
                patternDrawer.onDrawPattern(canvas, getWidth(), getHeight(), mProgressFraction);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus){
            //Showed on the front window.
            isWindowFocused = true;
        }else{
            isWindowFocused = false;
            stop();
            //Release All resource;
            if (mShrinkingController != null){
                mShrinkingController.removeAllListeners();
                mShrinkingController.removeAllUpdateListeners();
                mShrinkingController = null;
            }
            if (mProgressingController != null){
                mProgressingController.removeAllListeners();
                mProgressingController.removeAllUpdateListeners();
                mProgressingController = null;
            }
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt("animation_state", animationState);
        bundle.putParcelable("super_state", super.onSaveInstanceState());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            animationState = (bundle.getInt("animation_state"));
            state = bundle.getParcelable("super_state");
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * Start the whole series of animation, include shrinking and progrssing.
     */
    public void startWholeAnimation(){
        if (animationState > 0 || !isWindowFocused){
            return;
        }
        //Initial those animation fraction firstly.
        initAnimationParameters();
        if (mShrinkingController == null || mProgressingController == null){
            initShrinkingAnimationController();
            initProgressingAnimationController();
        }
        if (mShrinkingController.isRunning() || mProgressingController.isRunning()){
            mShrinkingController.end();
            mShrinkingController.cancel();
            mProgressingController.end();
            mProgressingController.cancel();
        }
        mShrinkingController.start();
    }

    /**
     * Start progressing animation separately.
     */
    private void startProgressingAnimation(){
        if (animationState == STATE_PROGRESSING || !isWindowFocused){
            return;
        }
        if (mProgressingController == null){
            initProgressingAnimationController();
        }
        if (mProgressingController.isRunning()) {
            mProgressingController.end();
            mProgressingController.cancel();
        }
        mProgressingController.start();
    }

    /**
     * Start back-expanding animation.
     */
    private void startExpandingAnimation(){
        if (!isWindowFocused)   return;
        if (mExpandingController == null){
            initExpandingAnimationController();
        }
        if (mExpandingController.isRunning() || mExpandingController.isStarted()){
            mExpandingController.end();
            mExpandingController.cancel();
        }
        mExpandingController.start();
    }

    /**
     * Stop all animations including shrinking and progressing.
     */
    public void stop(){
        if (mShrinkingController != null && mShrinkingController.isRunning()){
            mShrinkingController.end();
            mShrinkingController.cancel();
        }
        if (mProgressingController != null && mProgressingController.isRunning()){
            mProgressingController.end();
            mProgressingController.cancel();
        }
    }

    private void initAnimationParameters(){
        mProgressFraction = 0;
        mButtonInitialWidth = getWidth();
    }

    public int getAnimationState() {
        return animationState;
    }

    public void setAnimationState(int animationState) {
        if (animationState < 0 || animationState > 3) {
            return;
        }
        this.animationState = animationState;
    }

    public int getShrinkingDuration() {
        return mShrinkingDuration;
    }

    public void setShrinkingDuration(int shrinkingDuration) {
        if (shrinkingDuration < 0){
            return;
        }
        this.mShrinkingDuration = shrinkingDuration;
        if (mShrinkingController != null){
            mShrinkingController.setDuration(this.mShrinkingDuration);
        }
    }

    public int getProgressingDuration() {
        return mProgressingDuration;
    }

    public void setProgressingDuration(int progressingDuration) {
        if (progressingDuration < 0){
            return;
        }
        this.mProgressingDuration = progressingDuration;
        if (mProgressingController != null){
            mProgressingController.setDuration(this.mProgressingDuration);
        }
    }

    public DynamicPatternDrawer getPatternDrawer() {
        return patternDrawer;
    }

    public void setPatternDrawer(DynamicPatternDrawer patternDrawer) {
        this.patternDrawer = patternDrawer;
    }

    public void reset(){
        //Stop the previous progressing animation.
        stop();
        startExpandingAnimation();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)){
            case MotionEvent.ACTION_DOWN:
                if (!isWindowFocused){
                    return true;
                }
                if (animationState == STATE_EXPANDED){
                    startWholeAnimation();
                }
                this.callOnClick();
                return true;
            case MotionEvent.ACTION_UP:

                return false;
        }
        return false;
    }
}
