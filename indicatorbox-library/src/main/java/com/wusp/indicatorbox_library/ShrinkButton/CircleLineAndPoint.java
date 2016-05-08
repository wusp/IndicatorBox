package com.wusp.indicatorbox_library.ShrinkButton;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import com.wusp.indicatorbox_library.DynamicPatternDrawer;

/**
 * Created by wusp on 16/4/22.
 */
public class CircleLineAndPoint implements DynamicPatternDrawer {
    private int lineAngle;
    private int mPointSize;
    private int mDistance;
    private int mLineWidth;
    private int mPatternColor;
    private int mProgressRadius = -1;
    private Paint mPaint;
    private RectF mRect;

    public CircleLineAndPoint() {
        lineAngle = 50;
        mPointSize = 3;
        mDistance = 45;
        mLineWidth = 8;
        mPatternColor = Color.WHITE;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mRect = new RectF();
    }

    public int getLineAngle() {
        return lineAngle;
    }

    public void setLineAngle(int lineAngle) {
        this.lineAngle = lineAngle;
    }

    public int getmLineWidth() {
        return mLineWidth;
    }

    public void setmLineWidth(int mLineWidth) {
        this.mLineWidth = mLineWidth;
    }

    public int getmPatternColor() {
        return mPatternColor;
    }

    public void setmPatternColor(int mPatternColor) {
        this.mPatternColor = mPatternColor;
    }

    @Override
    public void onDrawPattern(Canvas canvas, int width, int height, float progressFraction) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        if (mProgressRadius <= 0){
            mProgressRadius = height / 4;
        }
        mRect.left = halfWidth - mProgressRadius;
        mRect.top = halfHeight - mProgressRadius;
        mRect.right = halfWidth + mProgressRadius;
        mRect.bottom = halfHeight + mProgressRadius;
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setColor(mPatternColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            canvas.drawArc(halfWidth - mProgressRadius, halfHeight - mProgressRadius,
                    halfWidth + mProgressRadius, halfHeight + mProgressRadius, 360 * progressFraction, lineAngle, false, mPaint);
            canvas.drawArc(halfWidth - mProgressRadius, halfHeight - mProgressRadius,
                    halfWidth + mProgressRadius, halfHeight + mProgressRadius, 360 * progressFraction - mDistance, mPointSize, false, mPaint);
        }else {
            canvas.drawArc(mRect, 360 * progressFraction, lineAngle, false, mPaint);
            canvas.drawArc(mRect, 360 * progressFraction - mDistance, mPointSize, false, mPaint);
        }
    }

    public int getProgressRadius() {
        return mProgressRadius;
    }

    public void setProgressRadius(int progressRadius) {
        if (progressRadius < 0){
            return;
        }
        this.mProgressRadius = progressRadius;
    }
}
