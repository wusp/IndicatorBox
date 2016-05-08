package com.wusp.indicatorbox_library.FlashBorder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.wusp.indicatorbox_library.DynamicPatternDrawer;
import com.wusp.indicatorbox_library.R;
import com.wusp.indicatorbox_library.ToolBox.Utils;

/**
 * Created by wusp on 16/5/3.
 */
public class FlashBorderView extends LinearLayout {
    private Paint mPaint;
    private int mTargetHeight = 50;
    private int mTargetWidth = 450;
    private int mItemHeight;
    private int mItemWidth;
    private int mBorderWidth;
    private int mBorderColor;
    private float mFraction;
    private DynamicPatternDrawer patternDrawer;

    public FlashBorderView(Context context) {
        super(context);
        init(context, null);
    }

    public FlashBorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FlashBorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        initTypeArray(context, attrs);
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        patternDrawer = new DynamicPatternDrawer() {
            @Override
            public void onDrawPattern(Canvas canvas, int width, int height, float progressFraction) {
                mPaint.setStrokeWidth(mBorderWidth);
                mPaint.setColor(mBorderColor);
                if(progressFraction > 0 && progressFraction < 0.9f){
                    //Draw the first fours lines.
                    canvas.drawLine(getWidth() / 2,
                            getHeight() / 2 - mItemHeight / 2,
                            getWidth() / 2 + mItemWidth / 2 * progressFraction / 0.9f,
                            getHeight() / 2 - mItemHeight / 2,
                            mPaint);
                    canvas.drawLine(getWidth() / 2,
                            getHeight() / 2 - mItemHeight / 2,
                            getWidth() / 2 - mItemWidth / 2 * progressFraction / 0.9f,
                            getHeight() / 2 - mItemHeight / 2,
                            mPaint);
                    canvas.drawLine(getWidth() / 2,
                            getHeight() / 2 + mItemHeight / 2,
                            getWidth() / 2 + mItemWidth / 2 * progressFraction / 0.9f,
                            getHeight() / 2 + mItemHeight / 2,
                            mPaint);
                    canvas.drawLine(getWidth() / 2,
                            getHeight() / 2 + mItemHeight / 2,
                            getWidth() / 2 - mItemWidth / 2 * progressFraction / 0.9f,
                            getHeight() / 2 + mItemHeight / 2,
                            mPaint);
                }else if (progressFraction >= 0.9f && progressFraction <= 1.0f){
                    //Draw the fist two lines.
                    canvas.drawLine(getWidth() / 2 - mItemWidth / 2,
                            getHeight() / 2 - mItemHeight / 2,
                            getWidth() / 2 - mItemWidth / 2 + mItemWidth,
                            getHeight() / 2 - mItemHeight / 2,
                            mPaint);
                    canvas.drawLine(getWidth() / 2 - mItemWidth / 2,
                            getHeight() / 2 + mItemHeight / 2,
                            getWidth() / 2 - mItemWidth / 2 + mItemWidth,
                            getHeight() / 2 + mItemHeight / 2,
                            mPaint);
                    //Draw the second two size lines.
                    canvas.drawLine(getWidth() / 2 - mItemWidth / 2,
                            getHeight() / 2 - mItemHeight / 2,
                            getWidth() / 2 - mItemWidth / 2,
                            getHeight() / 2 - mItemHeight / 2 + mItemHeight / 2 * (progressFraction - 0.9f) / 0.1f,
                            mPaint);
                    canvas.drawLine(getWidth() / 2 - mItemWidth / 2,
                            getHeight() / 2 + mItemHeight / 2,
                            getWidth() / 2 - mItemWidth / 2,
                            getHeight() / 2 + mItemHeight / 2 - mItemHeight / 2 * (progressFraction - 0.9f) / 0.1f,
                            mPaint);
                    canvas.drawLine(getWidth() / 2 + mItemWidth / 2,
                            getHeight() / 2 - mItemHeight / 2,
                            getWidth() / 2 + mItemWidth / 2,
                            getHeight() / 2 - mItemHeight / 2 + mItemHeight / 2 * (progressFraction - 0.9f) / 0.1f,
                            mPaint);
                    canvas.drawLine(getWidth() / 2 + mItemWidth / 2,
                            getHeight() / 2 + mItemHeight / 2,
                            getWidth() / 2 + mItemWidth / 2,
                            getHeight() / 2 + mItemHeight / 2 - mItemHeight / 2 * (progressFraction - 0.9f) / 0.1f,
                            mPaint);
                }
            }
        };
    }

    private void initTypeArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlashBorderView);
        mBorderColor = typedArray.getColor(R.styleable.FlashBorderView_border_color, getResources().getColor(R.color.colorAccent));
        mBorderWidth = typedArray.getDimensionPixelOffset(R.styleable.FlashBorderView_border_width, 5);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int resultWidth = Utils.getAdjustedSize(mTargetWidth, widthMeasureSpec);
        int resultHeight = Utils.getAdjustedSize(mTargetHeight, heightMeasureSpec);
        mItemHeight = resultHeight;
        mItemWidth = resultWidth;
        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        patternDrawer.onDrawPattern(canvas, mItemWidth, mItemHeight, mFraction);
    }

    public float getmFraction() {
        return mFraction;
    }

    public void setmFraction(float mFraction) {
        this.mFraction = mFraction;
    }

    public int getmBorderColor() {
        return mBorderColor;
    }

    public void setmBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
    }

    public int getmBorderWidth() {
        return mBorderWidth;
    }

    public void setmBorderWidth(int mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
    }

    public void setPatternDrawer(DynamicPatternDrawer patternDrawer) {
        this.patternDrawer = patternDrawer;
    }
}
