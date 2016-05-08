package com.wusp.indicatorbox_library.ProgressBar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.wusp.indicatorbox_library.R;
import com.wusp.indicatorbox_library.ToolBox.DampingInterpolator;

/**
 * Created by wusp on 16/4/18.
 */
public class BounceFreeBar extends SurfaceView implements SurfaceHolder.Callback{
    public static final int STATE_DOWN = 1;
    public static final int STATE_UP = 2;
    private Paint mPaint;
    private Path mPath;
    private int mLineColor;
    private int mPointColor;
    private int mLineWidth;
    private int mLineHeigt;
    private float mDownDistance;
    private float mUpDistance;
    private float freeBallDistance;
    //Used to control bounce down motion.
    private ValueAnimator downController;
    //Used to control bounce up motion.
    private ValueAnimator upController;
    //Used to control free down motin.
    private ValueAnimator freeDownController;
    private AnimatorSet animatorSet;
    private int state;
    private boolean isBounced = false;
    private boolean isBallFreeUp = false;
    private boolean isUpControllerDied = false;
    private boolean isAnimationShowing = false;

    public BounceFreeBar(Context context) {
        super(context);
        init(context, null);
    }

    public BounceFreeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BounceFreeBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        initTypedArray(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mLineHeigt);
        mPath = new Path();
        //For surfaceView.
        getHolder().addCallback(this);
        initController();
    }

    private void initTypedArray(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BounceFreeBar);
        mLineColor = typedArray.getColor(R.styleable.BounceFreeBar_line_color, Color.WHITE);
        mLineWidth = typedArray.getDimensionPixelOffset(R.styleable.BounceFreeBar_line_width, 200);
        mLineHeigt = typedArray.getDimensionPixelOffset(R.styleable.BounceFreeBar_line_height, 2);
        mPointColor = typedArray.getColor(R.styleable.BounceFreeBar_point_color, Color.WHITE);
        typedArray.recycle();
    }

    private void initController() {
        downController = ValueAnimator.ofFloat(0, 1);
        downController.setDuration(500);
        downController.setInterpolator(new DecelerateInterpolator());
        downController.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDownDistance = 50 * (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        downController.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                state = STATE_DOWN;
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
        upController = ValueAnimator.ofFloat(0, 1);
        upController.setDuration(900);
        upController.setInterpolator(new DampingInterpolator());
        upController.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mUpDistance = 50 * (float) animation.getAnimatedValue();
                if (mUpDistance > 50) {
                    isBounced = true;
                    if (!freeDownController.isRunning() && !freeDownController.isStarted() && !isBallFreeUp) {
                        freeDownController.start();
                    }
                }
                postInvalidate();
            }
        });
        upController.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                state = STATE_UP;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isUpControllerDied = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        freeDownController = ValueAnimator.ofFloat(0, 6.8f);
        freeDownController.setDuration(600);
        freeDownController.setInterpolator(new LinearInterpolator());
        freeDownController.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float input = (float) animation.getAnimatedValue();
                freeBallDistance = 34 * input - 5 * input * input;
                if (isUpControllerDied) {
                    postInvalidate();
                }
            }
        });
        freeDownController.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isBallFreeUp = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimationShowing = false;
                startWholeAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet = new AnimatorSet();
        animatorSet.play(downController).before(upController);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimationShowing = true;
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /** Draw the curve base on the bezier curve */
        mPaint.setColor(mLineColor);
        mPath.reset();
        mPath.moveTo(getWidth() / 2 - mLineWidth / 2, getHeight() / 2);
        if (state == STATE_DOWN) {
            mPath.quadTo((float) (getWidth() / 2 - mLineWidth / 2 + mLineWidth * 0.375), getHeight() / 2 + mDownDistance, getWidth() / 2,
                    getHeight() / 2 + mDownDistance);
            mPath.quadTo((float) (getWidth() / 2 + mLineWidth / 2 - mLineWidth * 0.375), getHeight() / 2 + mDownDistance, getWidth() / 2 + mLineWidth / 2,
                    getHeight() / 2);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mPath, mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mPointColor);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2 + mDownDistance - 10, 10, mPaint);
        }else if (state == STATE_UP){
            mPath.quadTo((float) (getWidth() / 2 - mLineWidth / 2 + mLineWidth * 0.375), getHeight() / 2 + (50 - mUpDistance), getWidth() / 2,
                    getHeight() / 2 + (50 - mUpDistance));
            mPath.quadTo((float) (getWidth() / 2 + mLineWidth / 2 - mLineWidth * 0.375), getHeight() / 2 + (50 - mUpDistance), getWidth() / 2 + mLineWidth / 2,
                    getHeight() / 2);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mPath, mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            //Draw the bounce ball.
            mPaint.setColor(mPointColor);
            if (!isBounced) {
                canvas.drawCircle(getWidth() / 2, getHeight() / 2 + (50 - mUpDistance) - 10, 10, mPaint);
            }else{
                canvas.drawCircle(getWidth() / 2, getHeight() / 2 - (freeBallDistance) - 10, 10, mPaint);
            }
        }
        /** Draw the points*/
        mPaint.setColor(mPointColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth() / 2 - mLineWidth / 2, getHeight() / 2, 10, mPaint);
        canvas.drawCircle(getWidth() / 2 + mLineWidth / 2, getHeight() / 2, 10, mPaint);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE){
            startWholeAnimation();
        }else {
            stopAnimation();
        }
    }

    private void stopAnimation() {
        if (downController != null && downController.isRunning()){
            downController.end();
        }
        if (upController != null && upController.isRunning()){
            upController.end();
        }
        if (freeDownController != null && freeDownController.isRunning()){
            freeDownController.end();
        }
    }

    public void startWholeAnimation(){
        if (isAnimationShowing){
            return;
        }
        if (animatorSet.isRunning()){
            animatorSet.end();
            animatorSet.cancel();
        }
        initParameters();
        animatorSet.start();
    }

    private void initParameters() {
        isBounced = false;
        isBallFreeUp = false;
        isUpControllerDied = false;
    }

    private void startUpAnimation(){
        if (upController.isRunning()){
            upController.end();
            upController.cancel();
        }
        upController.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas c = holder.lockCanvas();
        draw(c);
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
