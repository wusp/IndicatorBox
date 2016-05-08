package com.wusp.indicatorbox_library.Particle;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.wusp.indicatorbox_library.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wusp on 16/4/3.
 */
public class ParticleHeartView extends View implements View.OnTouchListener{
    /**These value is not open to be changed by user.*/
    //Used to control animation which is key of the whole view perform dynamic effects..
    private ValueAnimator mParticleAnimator;
    //Let the center bitmap perform a heart-beat movement.
    private ValueAnimator mHeartbeatAnimator;
    //Used to perform the particle motion.
    private ParticleField mParticleField;
    private ParticleField mDisappearField;
    //Used to controll how the particle will act.
    private ArrayList<ParticleMotionController> mParticleMotionControllers;
    private ArrayList<ParticleMotionController> mDisappearParticleMotionControllers;
    private ArrayList<ParticleInitializer> mParticleInitializers;
    //The center of this view.
    private int centerX;
    private int centerY;
    //Used to draw the center bitmap.
    private Matrix mMatrix;
    private Paint mPaint;
    private int centerBitmapHalfWidth;
    private int centerBitmapHalfHeight;
    private Bitmap bitmapDisappearDust;
    private int bitmapDisappearDustHalfWidth;
    private int bitmapDisappearDustHalfHeight;
    private int smallStarTranslateDistance = 40;
    private int bigStarTranslateDistance = 30;
    private int offsetForHeartCenter = 5;
    private boolean isQuiet = true;
    //Indicator whether user has praised this.
    private boolean isPraised = false;
    //Draw the follow circle
    private static final int END_COLOR = Color.parseColor("#11cd6e");
    private static final int START_COLOR = Color.parseColor("#a020ea");
    //Used to draw colorful particles.
    private int[] colorGroup = new int[]{
            Color.parseColor("#DAA520"),    //GoldEnrod
            Color.parseColor("#11cd6e")};    //Green
    /**
     * Follow Circle.
     */
    private ArgbEvaluator argbEvaluator;
    private Paint outerCirclePaint;
    private Paint innerCirclePaint;
    private float outerProgress;
    private float innerProgress;
    private int maxCircleRadius = 70;
    private Bitmap followCircleEmptyBitmap;
    private Canvas followCircleCanvas;
    private Bitmap mCenterBitmap;
    private long mDuration = 500;
    private Random random;
    private OnClickListener onClickListener;

    public ParticleHeartView(Context context) {
        super(context);
        init(context, null);
    }

    public ParticleHeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        //setFilterBitmap() and setAntiAlias used together to make drawing bitmap anti-aliased.
        mPaint.setFilterBitmap(true);
        mPaint.setAntiAlias(true);
        mMatrix = new Matrix();
        bitmapDisappearDust = BitmapFactory.decodeResource(getResources(), R.drawable.icon_red_dust);
        bitmapDisappearDustHalfWidth = bitmapDisappearDust.getWidth() / 2;
        bitmapDisappearDustHalfHeight = bitmapDisappearDust.getHeight() / 2;
        random = new Random(bitmapDisappearDust.getByteCount());
        mCenterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_un_praised);
        centerBitmapHalfWidth = mCenterBitmap.getWidth() / 2;
        centerBitmapHalfHeight = mCenterBitmap.getHeight() / 2;
        this.setOnTouchListener(this);
        isPraised = false;
        //Follow circle
        outerCirclePaint = new Paint();
        innerCirclePaint = new Paint();
        outerCirclePaint.setStyle(Paint.Style.FILL);
        outerCirclePaint.setAntiAlias(true);
        innerCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        innerCirclePaint.setAntiAlias(true);
        argbEvaluator = new ArgbEvaluator();
    }

    /**
     * Convert animationFraction to some parameters used by Follow Circle.
     * @param animationFraction
     */
    private void outputFollowCircleParameter(float animationFraction){
        outerCirclePaint.setColor((Integer) argbEvaluator.evaluate(animationFraction, START_COLOR, END_COLOR));
        outerProgress = animationFraction * 0.3f + 0.7f;
        innerProgress = animationFraction;
    }

    /**
     * Init the particle field.
     */
    private void initParticleField(Context context, AttributeSet attributeSet) {
        BaseOneParticleInitializer initializerOne = new BaseOneParticleInitializer();
        initializerOne.setParticleBitmap(bitmapDisappearDust);
        BaseOneParticleInitializer initializerTwo = new BaseOneParticleInitializer();
        initializerTwo.setParticleBitmap(bitmapDisappearDust);
        BaseOneParticleInitializer initializerThree = new BaseOneParticleInitializer();
        initializerThree.setParticleBitmap(bitmapDisappearDust);
        BaseOneParticleInitializer initializerFour = new BaseOneParticleInitializer();
        initializerFour.setParticleBitmap(bitmapDisappearDust);
        BaseOneParticleInitializer initializerFive = new BaseOneParticleInitializer();
        initializerFive.setParticleBitmap(bitmapDisappearDust);
        BaseOneParticleInitializer initializerSix = new BaseOneParticleInitializer();
        initializerSix.setParticleBitmap(bitmapDisappearDust);
        BaseOneParticleInitializer initializerSeven = new BaseOneParticleInitializer();
        initializerSeven.setParticleBitmap(bitmapDisappearDust);
        BaseOneParticleInitializer initializerEight = new BaseOneParticleInitializer();
        initializerEight.setParticleBitmap(bitmapDisappearDust);
        mParticleInitializers = new ArrayList<>();
        mParticleInitializers.add(initializerOne);
        mParticleInitializers.add(initializerTwo);
        mParticleInitializers.add(initializerThree);
        mParticleInitializers.add(initializerFour);
        mParticleInitializers.add(initializerFive);
        mParticleInitializers.add(initializerSix);
        mParticleInitializers.add(initializerSeven);
        mParticleInitializers.add(initializerEight);
        //Default circle.
        mParticleField = new ParticleField.ViewGenerator()
                .setContext(context)
                .setAttributeSet(attributeSet)
                .setParticleNumbers(8)
                .generate();
        //Custom bitmap.
        mDisappearField = new ParticleField.ViewGenerator()
                .setContext(context)
                .setAttributeSet(attributeSet)
                .setParticleInitializers(mParticleInitializers)
                .generate();
    }

    /**
     * Init the how particles would act motion.
     */
    private void initParticleMotion(){
        mParticleMotionControllers = new ArrayList<>();
        mDisappearParticleMotionControllers = new ArrayList<>();
        int littleOffset = 10;
        int offsetOne = 0;
        int smallStarOffsetToCenter = offsetOne + 5;
        /**Small ones*/
        ParticleMotionController disappear1 = new ParticleMotionController();
        disappear1.setNumber(0);
        disappear1.addModifier(new TranslateModifier(centerX - smallStarOffsetToCenter - bitmapDisappearDustHalfWidth,
                centerY - bitmapDisappearDustHalfHeight,
                centerX - smallStarOffsetToCenter - smallStarTranslateDistance - bitmapDisappearDustHalfWidth,
                centerY - bitmapDisappearDustHalfHeight));
        mDisappearParticleMotionControllers.add(disappear1);
        ParticleMotionController disappear2 = new ParticleMotionController();
        disappear2.setNumber(1);
        disappear2.addModifier(new TranslateModifier(centerX - bitmapDisappearDustHalfWidth,
                centerY - smallStarOffsetToCenter - bitmapDisappearDustHalfHeight,
                centerX - bitmapDisappearDustHalfWidth,
                centerY - smallStarOffsetToCenter - smallStarTranslateDistance - bitmapDisappearDustHalfHeight));
        mDisappearParticleMotionControllers.add(disappear2);
        ParticleMotionController disappear3 = new ParticleMotionController();
        disappear3.setNumber(2);
        disappear3.addModifier(new TranslateModifier(centerX + smallStarOffsetToCenter - bitmapDisappearDustHalfWidth,
                centerY - bitmapDisappearDustHalfHeight,
                centerX + smallStarOffsetToCenter + smallStarTranslateDistance - bitmapDisappearDustHalfWidth,
                centerY - bitmapDisappearDustHalfHeight));
        mDisappearParticleMotionControllers.add(disappear3);
        ParticleMotionController disappear4 = new ParticleMotionController();
        disappear4.setNumber(3);
        disappear4.addModifier(new TranslateModifier(centerX - bitmapDisappearDustHalfWidth,
                centerY + smallStarOffsetToCenter - bitmapDisappearDustHalfHeight,
                centerX - bitmapDisappearDustHalfWidth,
                centerY + smallStarOffsetToCenter + smallStarTranslateDistance - bitmapDisappearDustHalfHeight));
        mDisappearParticleMotionControllers.add(disappear4);
        ParticleMotionController info1 = new ParticleMotionController();
        info1.setNumber(0);
        info1.addModifier(new TranslateModifier(centerX - littleOffset,
                centerY,
                centerX - smallStarTranslateDistance - littleOffset,
                centerY));
        mParticleMotionControllers.add(info1);
        ParticleMotionController info2 = new ParticleMotionController();
        info2.setNumber(1);
        info2.addModifier(new TranslateModifier(centerX,
                centerY  - littleOffset,
                centerX,
                centerY - smallStarTranslateDistance  - littleOffset));
        mParticleMotionControllers.add(info2);
        ParticleMotionController info3 = new ParticleMotionController();
        info3.setNumber(2);
        mParticleMotionControllers.add(info3);
        info3.addModifier(new TranslateModifier(centerX + littleOffset,
                centerY,
                centerX + smallStarTranslateDistance + littleOffset,
                centerY));
        ParticleMotionController info4 = new ParticleMotionController();
        info4.setNumber(3);
        info4.addModifier(new TranslateModifier(centerX,
                centerY + littleOffset,
                centerX,
                centerY + smallStarTranslateDistance + littleOffset));
        mParticleMotionControllers.add(info4);
        for (int i = 0; i < 4; i++){
            mParticleMotionControllers.get(i).addModifier(new ScaleModifier(0.8f, 0.8f + 0.2f * random.nextFloat()));
            mParticleMotionControllers.get(i).addModifier(new AlphaModifier(255, 100));
            random.setSeed(System.currentTimeMillis() + i * 100);
            mParticleMotionControllers.get(i).addModifier(getRandomArgbModifier());
        }
        for (int i = 0; i < 4; i++){
            mDisappearParticleMotionControllers.get(i).addModifier(new ScaleModifier(0.2f, 0.2f + 0.1f * random.nextFloat()));
            mDisappearParticleMotionControllers.get(i).addModifier(new AlphaModifier(255, 100));
        }
        /**Big ones*/
        ParticleMotionController disappear5 = new ParticleMotionController();
        disappear5.setNumber(4);
        disappear5.addModifier(new TranslateModifier(centerX - offsetOne - bitmapDisappearDustHalfWidth,
                centerY - offsetOne - bitmapDisappearDustHalfHeight - offsetForHeartCenter,
                centerX - offsetOne - bigStarTranslateDistance - bitmapDisappearDustHalfWidth,
                centerY - offsetOne - bigStarTranslateDistance - bitmapDisappearDustHalfHeight - offsetForHeartCenter));
        mDisappearParticleMotionControllers.add(disappear5);
        ParticleMotionController disappear6 = new ParticleMotionController();
        disappear6.setNumber(5);
        disappear6.addModifier(new TranslateModifier(centerX + offsetOne - bitmapDisappearDustHalfWidth,
                centerY - offsetOne - bitmapDisappearDustHalfHeight - offsetForHeartCenter,
                centerX + offsetOne + bigStarTranslateDistance - bitmapDisappearDustHalfWidth,
                centerY - offsetOne - bigStarTranslateDistance - bitmapDisappearDustHalfHeight - offsetForHeartCenter));
        mDisappearParticleMotionControllers.add(disappear6);
        ParticleMotionController disappear7 = new ParticleMotionController();
        disappear7.setNumber(6);
        disappear7.addModifier(new TranslateModifier(centerX + offsetOne - bitmapDisappearDustHalfWidth,
                centerY + offsetOne - bitmapDisappearDustHalfHeight - offsetForHeartCenter,
                centerX + offsetOne + bigStarTranslateDistance - bitmapDisappearDustHalfWidth,
                centerY + offsetOne + bigStarTranslateDistance - bitmapDisappearDustHalfHeight - offsetForHeartCenter));
        mDisappearParticleMotionControllers.add(disappear7);
        ParticleMotionController disappear8 = new ParticleMotionController();
        disappear8.setNumber(7);
        disappear8.addModifier(new TranslateModifier(centerX - offsetOne - bitmapDisappearDustHalfWidth,
                centerY + offsetOne - bitmapDisappearDustHalfHeight - offsetForHeartCenter,
                centerX - offsetOne - bigStarTranslateDistance - bitmapDisappearDustHalfWidth,
                centerY + offsetOne + bigStarTranslateDistance - bitmapDisappearDustHalfHeight - offsetForHeartCenter));
        mDisappearParticleMotionControllers.add(disappear8);
        ParticleMotionController info5 = new ParticleMotionController();
        info5.setNumber(4);
        info5.addModifier(new TranslateModifier(centerX - littleOffset,
                centerY - littleOffset,
                (float)(centerX - bigStarTranslateDistance * Math.sin(Math.PI / 4) - littleOffset ),
                (float)(centerY - bigStarTranslateDistance * Math.sin(Math.PI / 4)) - littleOffset));
        mParticleMotionControllers.add(info5);
        ParticleMotionController info6 = new ParticleMotionController();
        info6.setNumber(5);
        info6.addModifier(new TranslateModifier(centerX + littleOffset,
                centerY - littleOffset,
                (float)(centerX + bigStarTranslateDistance * Math.sin(Math.PI / 4) + littleOffset),
                (float)(centerY - bigStarTranslateDistance * Math.sin(Math.PI / 4)) - littleOffset));
        mParticleMotionControllers.add(info6);
        ParticleMotionController info7 = new ParticleMotionController();
        info7.setNumber(6);
        info7.addModifier(new TranslateModifier(centerX + littleOffset,
                centerY + littleOffset,
                (float)(centerX + bigStarTranslateDistance * Math.sin(Math.PI / 4) + littleOffset),
                (float)(centerY + bigStarTranslateDistance * Math.sin(Math.PI / 4)) + littleOffset));
        mParticleMotionControllers.add(info7);
        ParticleMotionController info8 = new ParticleMotionController();
        info8.setNumber(7);
        info8.addModifier(new TranslateModifier(centerX - littleOffset,
                centerY + littleOffset,
                (float)(centerX - bigStarTranslateDistance * Math.sin(Math.PI / 4) - littleOffset),
                (float)(centerY + bigStarTranslateDistance * Math.sin(Math.PI / 4)) + littleOffset));
        mParticleMotionControllers.add(info8);
        for (int i = 4; i < mParticleMotionControllers.size(); i++){
            if (i < 8) {
                mParticleMotionControllers.get(i).addModifier(new ScaleModifier(0.9f, 0.9f + 0.3f * random.nextFloat()));
                mParticleMotionControllers.get(i).addModifier(new AlphaModifier(240, 200));
                random.setSeed(System.currentTimeMillis() + i * 100);
                mParticleMotionControllers.get(i).addModifier(getRandomArgbModifier());
            }
        }
        for (int i = 4; i < mDisappearParticleMotionControllers.size(); i++){
            if (i < 8) {
                mDisappearParticleMotionControllers.get(i).addModifier(new ScaleModifier(0.3f, 0.3f + 0.1f * random.nextFloat()));
                mDisappearParticleMotionControllers.get(i).addModifier(new AlphaModifier(240, 200));
            }
        }
        /**Set to particle*/
        if (mParticleMotionControllers.size() != mParticleField.getmParticles().size()){
            return;
        }
        for (int i = 0; i < mParticleField.getmParticles().size(); i++){
            mParticleField.getmParticles().get(i).setModifiers(mParticleMotionControllers.get(i).getModifierList());
        }
        if (mDisappearParticleMotionControllers.size() != mDisappearField.getmParticles().size()){
            return;
        }
        for (int i = 0; i < mDisappearField.getmParticles().size(); i++){
            mDisappearField.getmParticles().get(i).setModifiers(mDisappearParticleMotionControllers.get(i).getModifierList());
        }
    }

    /**
     * To get random argb modifier which used to draw a color-gradient particle.
     * @return
     */
    private ArgbModifier getRandomArgbModifier(){
        int firstRandomInt = random.nextInt(2);
        int secondRandomInt = random.nextInt(2);
        return new ArgbModifier(colorGroup[firstRandomInt], colorGroup[secondRandomInt]);
    }

    /**
     * Init the valueAnimator.
     */
    private void initAnimation(long duration){
        //Define a value animator to controll the motion.
        mParticleAnimator = ValueAnimator.ofInt(0, (int)duration);
        mParticleAnimator.setDuration(duration);
        mParticleAnimator.setInterpolator(new DecelerateInterpolator());
        mParticleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (isPraised) {
                    //Cause onAnimationStart change this.
                    outputFollowCircleParameter(animation.getAnimatedFraction());
                }
                postInvalidate();
            }
        });
        mParticleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //changed to praised.
                if (!isPraised) {
                    mCenterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_praised);
                    centerBitmapHalfWidth = mCenterBitmap.getWidth() / 2;
                    centerBitmapHalfHeight = mCenterBitmap.getHeight() / 2;
                }else{
                    mCenterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_un_praised);
                    centerBitmapHalfWidth = mCenterBitmap.getWidth() / 2;
                    centerBitmapHalfHeight = mCenterBitmap.getHeight() / 2;
                }
                isPraised = !isPraised;
                isQuiet = !isQuiet;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isQuiet = !isQuiet;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isQuiet = !isQuiet;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mHeartbeatAnimator = GeneralAnimatorGenerator.dampingValueAnimator(duration);
        mHeartbeatAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        followCircleEmptyBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        followCircleCanvas = new Canvas(followCircleEmptyBitmap);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        centerX = (r - l) / 2;
        centerY = (b - t) / 2;
        initParticleField(getContext(), null);
        initParticleMotion();
        initAnimation(mDuration);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /** CenterBitmap scale motion */
        if (mCenterBitmap != null) {
            mMatrix.reset();
            mMatrix.postScale(mHeartbeatAnimator.getAnimatedFraction() * 0.1f + 0.5f,
                    mHeartbeatAnimator.getAnimatedFraction() * 0.1f + 0.5f,
                    centerBitmapHalfWidth, centerBitmapHalfHeight);
            mMatrix.postTranslate(centerX - centerBitmapHalfWidth, centerY - centerBitmapHalfHeight);
            canvas.drawBitmap(mCenterBitmap, mMatrix, mPaint);
        }
        /**To draw the follow circle*/
        if (outerProgress < 1 && isPraised) {
            followCircleCanvas.drawCircle(getWidth() / 2, getHeight() / 2, outerProgress * maxCircleRadius / 2, outerCirclePaint);
            followCircleCanvas.drawCircle(getWidth() / 2, getHeight() / 2, innerProgress * maxCircleRadius / 2, innerCirclePaint);
            canvas.drawBitmap(followCircleEmptyBitmap, 0, 0, null);
        }
        /** Draw the particle party */
        if (!isQuiet) {
            if (isPraised) {
                //This control makes the particle do not show during the animation first phase.
                for (int i = 0; i < mParticleField.getmParticles().size(); i++) {
                    if ((mParticleAnimator.getAnimatedFraction() < 0.3f && i >= 4 && i < 8)
                            || (mParticleAnimator.getAnimatedFraction() < 0.5f && i < 4))
                        continue;
                    mParticleField.getmParticles().get(i).update(mParticleAnimator.getAnimatedFraction());
                    mParticleField.getmParticles().get(i).draw(canvas);
                }
            }else{
                for (int i = 0; i < mDisappearField.getmParticles().size(); i++) {
                    if ((mParticleAnimator.getAnimatedFraction() < 0.3f && i >= 4 && i < 8)
                            || (mParticleAnimator.getAnimatedFraction() < 0.5f && i < 4))
                        continue;
                    mDisappearField.getmParticles().get(i).update(mParticleAnimator.getAnimatedFraction());
                    mDisappearField.getmParticles().get(i).draw(canvas);
                }
            }
        }
    }

    public void startAnimation(){
        if (mParticleAnimator != null){
            if (mParticleAnimator.isRunning()){
                mParticleAnimator.end();
                mParticleAnimator.cancel();
            }
            mParticleAnimator.start();
        }
        if (mHeartbeatAnimator != null){
            if (mHeartbeatAnimator.isRunning()){
                mHeartbeatAnimator.end();
                mHeartbeatAnimator.cancel();
            }
            mHeartbeatAnimator.start();
        }
    }

    public long getmDuration() {
        return mDuration;
    }

    public void setmDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    public Bitmap getmCenterBitmap() {
        return mCenterBitmap;
    }

    public void setmCenterBitmap(Bitmap mCenterBitmap) {
        this.mCenterBitmap = mCenterBitmap;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(MotionEventCompat.getActionMasked(event)){
            case MotionEvent.ACTION_DOWN:
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
                startAnimation();
                callOnClick();
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:

                return false;
        }
        return false;
    }
}
