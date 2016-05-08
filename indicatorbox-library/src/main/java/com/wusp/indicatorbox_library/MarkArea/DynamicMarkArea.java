package com.wusp.indicatorbox_library.MarkArea;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.wusp.indicatorbox_library.DynamicPatternDrawer;

/**
 * Created by wusp on 16/4/25.
 */
public class DynamicMarkArea extends View {
    public static final int REMARK_OK = 1;
    public static final int REMARK_FAIL = 2;
    private int mLineWidth = 30;
    private int mSmallLineLength = 100;
    private int mLongLineLength = 200;
    private ValueAnimator controller;
    private float mFraction;
    private DynamicPatternDrawer dynamicPatternDrawer;
    private int patternFlag;

    public DynamicMarkArea(Context context) {
        super(context);
        init();
    }

    public DynamicMarkArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicMarkArea(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        initAnimator();
        initOk();
    }

    private void initAnimator(){
        controller = ValueAnimator.ofInt(200);
        controller.setDuration(600);
        controller.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFraction = animation.getAnimatedFraction();
                postInvalidate();
            }
        });
    }

    private void initOk() {
        patternFlag = REMARK_OK;
        dynamicPatternDrawer = new DynamicOkMark(mLineWidth, mSmallLineLength, mLongLineLength);
        controller.setInterpolator(new DecelerateInterpolator());
    }

    private void setFail(){
        patternFlag = REMARK_FAIL;
        dynamicPatternDrawer = new DynamicFailMark(mLineWidth, (int) (mLongLineLength * 1.5), Color.WHITE);
        controller.setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        dynamicPatternDrawer.onDrawPattern(canvas, getWidth(), getHeight(), mFraction);
    }

    private void startAnimator() {
        if (controller == null){
            return;
        }
        if (controller.isRunning()){
            controller.end();
            controller.cancel();
        }
        controller.start();
    }

    public void perform(){
        if (dynamicPatternDrawer != null){
            startAnimator();
        }
    }

    public int getPatternFlag() {
        return patternFlag;
    }

    public int getmLineWidth() {
        return mLineWidth;
    }

    public void setmLineWidth(int mLineWidth) {
        this.mLineWidth = mLineWidth;
    }

    public int getmSmallLineLength() {
        return mSmallLineLength;
    }

    public void setmSmallLineLength(int mSmallLineLength) {
        this.mSmallLineLength = mSmallLineLength;
    }

    public int getmLongLineLength() {
        return mLongLineLength;
    }

    public void setmLongLineLength(int mLongLineLength) {
        this.mLongLineLength = mLongLineLength;
    }

    public void setDynamicPatternDrawer(DynamicPatternDrawer dynamicPatternDrawer) {
        this.dynamicPatternDrawer = dynamicPatternDrawer;
    }
}
