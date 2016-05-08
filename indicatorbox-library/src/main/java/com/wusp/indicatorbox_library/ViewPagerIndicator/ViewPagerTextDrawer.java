package com.wusp.indicatorbox_library.ViewPagerIndicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.wusp.indicatorbox_library.StaticPatternDrawer;

/**
 * Created by wusp on 16/4/23.
 */
public class ViewPagerTextDrawer implements StaticPatternDrawer {
    private Paint mPaint;
    private int mBigTextSize;
    private int mSmallTextSize;
    private int mBigTextColor;
    private int mSlashColor;
    private int mSmallTextColor;
    private CharSequence mBigText;
    private CharSequence mSmallText;

    public ViewPagerTextDrawer(int bigTextSize, int smallTextSize, int bigTextColor, int smallTextColor, int slashColor) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mBigTextSize = bigTextSize;
        mSmallTextSize = smallTextSize;
        mBigText = "1";
        mSmallText = "1";
        mBigTextColor = bigTextColor;
        mSmallTextColor = smallTextColor;
        mSlashColor = slashColor;
    }

    @Override
    public void onDrawPattern(Canvas canvas, int canvasWidth, int canvasHeight) {
        int halfWidth = canvasWidth / 2;
        int halfHeight = canvasHeight / 2;
        float textYoffset;
        mPaint.setTextSize(mBigTextSize);
        mPaint.setColor(mBigTextColor);
        canvas.drawPoint(halfWidth, halfHeight, mPaint);
        canvas.drawText(mBigText.toString(), halfWidth - mSmallTextSize / 2, halfHeight - (mPaint.descent() + mPaint.ascent() / 2), mPaint);
        mPaint.setColor(mSlashColor);
        canvas.drawText(" / ", halfWidth, halfHeight - (mPaint.descent() + mPaint.ascent() / 2), mPaint);
        textYoffset = (mPaint.descent() + mPaint.ascent() / 2);
        mPaint.setTextSize(mSmallTextSize);
        mPaint.setColor(mSmallTextColor);
        canvas.drawText(mSmallText.toString(), halfWidth + mSmallTextSize / 2, halfHeight - textYoffset, mPaint);
    }

    public int getmBigTextSize() {
        return mBigTextSize;
    }

    public void setmBigTextSize(int mBigTextSize) {
        this.mBigTextSize = mBigTextSize;
        mPaint.setTextSize(mBigTextSize);
    }

    public int getmSmallTextSize() {
        return mSmallTextSize;
    }

    public void setmSmallTextSize(int mSmallTextSize) {
        this.mSmallTextSize = mSmallTextSize;
    }

    public int getmBigTextColor() {
        return mBigTextColor;
    }

    public void setmBigTextColor(int mBigTextColor) {
        this.mBigTextColor = mBigTextColor;
    }

    public int getmSlashColor() {
        return mSlashColor;
    }

    public void setmSlashColor(int mSlashColor) {
        this.mSlashColor = mSlashColor;
    }

    public int getmSmallTextColor() {
        return mSmallTextColor;
    }

    public void setmSmallTextColor(int mSmallTextColor) {
        this.mSmallTextColor = mSmallTextColor;
    }

    public CharSequence getmBigText() {
        return mBigText;
    }

    public void setmBigText(CharSequence mBigText) {
        this.mBigText = mBigText;
    }

    public CharSequence getmSmallText() {
        return mSmallText;
    }

    public void setmSmallText(CharSequence mSmallText) {
        this.mSmallText = mSmallText;
    }
}
