package com.wusp.indicatorbox_library.Particle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.List;

/**
 * Created by wusp on 16/4/1.
 */
public class Particle {
    protected Bitmap mImage;
    private float mCurrentX;
    private float mCurrentY;
    private float mScale = 1f;
    private int mAlpha = 255;
    private int mCircleRadius;
    private Matrix mMatrix;
    private Paint mPaint;
    private int mBitmapHalfWidth;
    private int mBitmapHalfHeight;
    private List<ParticleModifier> modifiers;

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMatrix = new Matrix();
        mScale = 1f;
        mAlpha = 255;
    }

    public Particle(ParticleInitializer initializer) {
        if (initializer != null){
            this.mImage = initializer.getParticleBitmap();
            init();
        }
    }

    /**Default to draw a circle*/
    public Particle(){
        mCircleRadius = 5;
        init();
    }

    public void configure(float emiterX, float emiterY){
        mBitmapHalfWidth = mImage.getWidth() / 2;
        mBitmapHalfHeight = mImage.getHeight() / 2;
    }

    public void update(float interpolatorValue){
        //Calcute the milliseconds that the process has been executed.
        for (ParticleModifier modifier : modifiers){
            modifier.setState(this, interpolatorValue);
        }
    }

    public void draw(Canvas canvas){
        if (mImage != null) {
            mMatrix.reset();
            //Scale settings.
            //Translate settings.
            mMatrix.postScale(mScale, mScale, mImage.getWidth() / 2, mImage.getHeight() / 2);
            mMatrix.postTranslate(mCurrentX, mCurrentY);
            //Alpha settings.
            mPaint.setAlpha(mAlpha);
            canvas.drawBitmap(mImage, mMatrix, mPaint);
        }else if (mCircleRadius >= 0){
            //Just draw a circle
            mPaint.setAlpha(mAlpha);
            canvas.drawCircle(mCurrentX, mCurrentY, mCircleRadius * mScale, mPaint);
        }
    }

    /**Variant Settings*/
    public void setCurrentX(float currentX) {
        this.mCurrentX = currentX;
    }

    public void setCurrentY(float currentY) {
        this.mCurrentY = currentY;
    }

    public void setScale(float scale) {
        this.mScale = scale;
    }

    public void setAlpha(int alpha) {
        this.mAlpha = alpha;
    }

    public List<ParticleModifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<ParticleModifier> modifiers) {
        this.modifiers = modifiers;
    }

    public Bitmap getmImage() {
        return mImage;
    }

    public void setmImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public Paint getmPaint() {
        return mPaint;
    }
}
