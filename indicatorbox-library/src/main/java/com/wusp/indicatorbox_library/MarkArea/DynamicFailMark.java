package com.wusp.indicatorbox_library.MarkArea;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.wusp.indicatorbox_library.DynamicPatternDrawer;

/**
 * Created by wusp on 16/4/26.
 */
public class DynamicFailMark implements DynamicPatternDrawer {
    private int mLineWidth;
    private Paint mPaint;
    private int mLineLength;
    private int mLineColor;

    public DynamicFailMark(int mLineWidth, int mLineLength, int mLineColor) {
        this.mLineWidth = mLineWidth;
        this.mLineLength = mLineLength;
        this.mLineColor = mLineColor;

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onDrawPattern(Canvas canvas, int width, int height, float progressFraction) {
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setColor(mLineColor);
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int pointOffset = (int) (mLineLength / Math.sqrt(2) / 2);
        if (pointOffset > 0){
            if (progressFraction < 0.5f && progressFraction > 0){
                canvas.drawLine(halfWidth - pointOffset, halfHeight - pointOffset,
                        halfWidth - pointOffset + pointOffset * 4 * progressFraction,
                        halfHeight - pointOffset + pointOffset * 4 * progressFraction, mPaint);
            }else if (progressFraction >= 0.5f && progressFraction < 1.0f){
                canvas.drawLine(halfWidth - pointOffset, halfHeight - pointOffset,
                        halfWidth + pointOffset,
                        halfHeight + pointOffset, mPaint);
                canvas.drawLine(halfWidth + pointOffset, halfHeight - pointOffset,
                        halfWidth + pointOffset - pointOffset * 4 * (progressFraction - 0.5f),
                        halfHeight - pointOffset + pointOffset * 4 * (progressFraction - 0.5f), mPaint);
            }else if (progressFraction >= 1.0f){
                canvas.drawLine(halfWidth - pointOffset, halfHeight - pointOffset,
                        halfWidth + pointOffset,
                        halfHeight + pointOffset, mPaint);
                canvas.drawLine(halfWidth + pointOffset, halfHeight - pointOffset,
                        halfWidth - pointOffset,
                        halfHeight + pointOffset, mPaint);
            }
        }
    }

    public int getmLineWidth() {
        return mLineWidth;
    }

    public void setmLineWidth(int mLineWidth) {
        this.mLineWidth = mLineWidth;
    }

    public int getmLineLength() {
        return mLineLength;
    }

    public void setmLineLength(int mLineLength) {
        this.mLineLength = mLineLength;
    }

    public int getmLineColor() {
        return mLineColor;
    }

    public void setmLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }
}
