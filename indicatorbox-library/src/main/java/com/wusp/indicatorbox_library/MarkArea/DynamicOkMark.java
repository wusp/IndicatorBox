package com.wusp.indicatorbox_library.MarkArea;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.wusp.indicatorbox_library.DynamicPatternDrawer;

/**
 * Created by wusp on 16/4/26.
 */
public class DynamicOkMark implements DynamicPatternDrawer {
    private int mLineWidth;
    private Paint mPaint;
    private Shader mShortShader;
    private int mSmallLineLength;
    private int mLongLineLength;

    public DynamicOkMark(int mLineWidth, int mSmallLineLength, int mLongLineLength) {
        this.mLineWidth = mLineWidth;
        this.mSmallLineLength = mSmallLineLength;
        this.mLongLineLength = mLongLineLength;

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mShortShader = new LinearGradient((float)(25 / Math.sqrt(2)), (float)(25 / Math.sqrt(2)), 0, 0, new int[]{Color.WHITE, Color.parseColor("#BCBCBC")}, null, Shader.TileMode.MIRROR);
    }

    @Override
    public void onDrawPattern(Canvas canvas, int width, int height, float progressFraction) {
        mPaint.setStrokeWidth(mLineWidth);
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        float offsetAlign = (float) (mLineWidth / 2 / Math.sqrt(2));
        if (progressFraction <= 0.5f && progressFraction > 0) {
            mPaint.setShader(mShortShader);
            canvas.drawLine((float) (halfWidth - mSmallLineLength / Math.sqrt(2)),
                    (float) (halfHeight - mSmallLineLength / Math.sqrt(2)),
                    (float) (halfWidth - mSmallLineLength / Math.sqrt(2)
                            + progressFraction * mSmallLineLength / Math.sqrt(2)),
                    (float) (halfHeight - mSmallLineLength / Math.sqrt(2)
                            + progressFraction * mSmallLineLength / Math.sqrt(2)), mPaint);
        }else if (progressFraction < 1.0f && progressFraction > 0.5f){
            mPaint.setColor(Color.WHITE);
            mPaint.setShader(null);
            canvas.drawLine((float) (halfWidth - mSmallLineLength / Math.sqrt(2)),
                    (float) (halfHeight - mSmallLineLength / Math.sqrt(2)),
                    (float) (halfWidth - mSmallLineLength / Math.sqrt(2) / 4 + 2),
                    (float) (halfHeight - mSmallLineLength / Math.sqrt(2) / 4 + 2), mPaint);
            mPaint.setShader(mShortShader);
            canvas.drawLine((float) (halfWidth - mSmallLineLength / Math.sqrt(2) / 4),
                    (float) (halfHeight - mSmallLineLength / Math.sqrt(2) / 4),
                    halfWidth,
                    halfHeight, mPaint);
            mPaint.setShader(null);
            canvas.drawLine(
                    halfWidth - offsetAlign,
                    halfHeight + offsetAlign,
                    (float)(halfWidth - offsetAlign + (progressFraction - 0.5) * 2 * mLongLineLength / Math.sqrt(2)),
                    (float)(halfHeight + offsetAlign - (progressFraction - 0.5) * 2 * mLongLineLength / Math.sqrt(2)), mPaint);
        } else if (progressFraction >= 1.0f){
            mPaint.setColor(Color.WHITE);
            mPaint.setShader(null);
            canvas.drawLine((float) (halfWidth - mSmallLineLength / Math.sqrt(2)),
                    (float) (halfHeight - mSmallLineLength / Math.sqrt(2)),
                    (float) (halfWidth - mSmallLineLength / Math.sqrt(2) / 4 + 2),
                    (float) (halfHeight - mSmallLineLength / Math.sqrt(2) / 4 + 2), mPaint);
            mPaint.setShader(mShortShader);
            canvas.drawLine((float) (halfWidth - mSmallLineLength / Math.sqrt(2) / 4),
                    (float) (halfHeight - mSmallLineLength / Math.sqrt(2) / 4),
                    halfWidth,
                    halfHeight, mPaint);
            mPaint.setShader(null);
            canvas.drawLine(
                    halfWidth - offsetAlign,
                    halfHeight + offsetAlign,
                    (float)(halfWidth - offsetAlign + mLongLineLength / Math.sqrt(2)),
                    (float)(halfHeight + offsetAlign - mLongLineLength / Math.sqrt(2)), mPaint);
        }
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
}
