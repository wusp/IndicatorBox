package com.wusp.indicatorbox;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.wusp.indicatorbox_library.FlashBorder.FlashBorderView;
import com.wusp.indicatorbox_library.RippleCircle.RippleCircle;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FlashBorderAndRippleCircleActivity extends AppCompatActivity {
    public static final int TIME_TO_REFRESH_NEWS = 3000;
    private RippleCircle rcCollect;
    private RippleCircle rcShare;
    private RippleCircle rcComment;
    private RelativeLayout rlContent;
    private FlashBorderView flashBorder1;
    private FlashBorderView flashBorder2;
    private FlashBorderView flashBorder3;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private AnimatorSet animatorSet1;
    private AnimatorSet animatorSet2;
    private AnimatorSet animatorSet3;
    private String[] strGroups1 = new String[]{
            "Emerging markets are turning the corner",
            "10 things in tech you need to know today",
            "Houston's office market is melting down",
            "Texas is handling the oil crash pretty well"
    };
    private String[] strGroups2 = new String[]{
            "Marchés en développement maintiennent un bon élan de développement",
            "Vous devez savoir aujourd’hui 10 communiqués de presse",
            "Houston en immeubles",
            "Texas la crise pétrolière a été bonne"
    };
    private String[] strGroups3 = new String[]{
            "UN buen impulso en los mercados en desarrollo",
            "Necesito saber algo de 10 hoy la prensa",
            "Houston cafés mercado está desintegrando",
            "Texas con buena a la crisis del petróleo"
    };
    private Random random = new Random();
    private ScheduledExecutorService mScheduledExecutorService;
    private Future<?> mScheduledRefreshFuture;
    private Runnable newsRefreshRunnable;
    private boolean isFirstCreated = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashborder_ripplecircle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        rcCollect = (RippleCircle) findViewById(R.id.rcCollected);
        rcShare = (RippleCircle) findViewById(R.id.rcShared);
        rcComment = (RippleCircle) findViewById(R.id.rcCommented);
        rlContent = (RelativeLayout) findViewById(R.id.rlContent);
        flashBorder1 = (FlashBorderView) findViewById(R.id.flashBorder1);
        flashBorder2 = (FlashBorderView) findViewById(R.id.flashBorder2);
        flashBorder3 = (FlashBorderView) findViewById(R.id.flashBorder3);
        setSupportActionBar(toolbar);
        mScheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        prepareNewsRefreshRunnable();
        addMaskLayerToUserPhoto(Color.BLACK, 25,
                (int) (160 * getResources().getDisplayMetrics().density));
        textView1 = new TextView(this);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp1.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp1.gravity = Gravity.CENTER_VERTICAL;
        textView1.setTextSize(16);
        textView1.setTextColor(getResources().getColor(R.color.colorAccent));
        textView1.setPadding(0, 3, 0, 3);
        textView1.setGravity(Gravity.CENTER);
        flashBorder1.addView(textView1, lp1);
        textView2 = new TextView(this);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp2.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp2.gravity = Gravity.CENTER_VERTICAL;
        textView2.setTextSize(16);
        textView2.setTextColor(getResources().getColor(R.color.colorAccent));
        textView2.setPadding(0, 3, 0, 3);
        textView2.setGravity(Gravity.CENTER);
        flashBorder2.addView(textView2, lp2);
        textView3 = new TextView(this);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp3.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp3.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp3.gravity = Gravity.CENTER_VERTICAL;
        textView3.setTextSize(16);
        textView3.setTextColor(getResources().getColor(R.color.colorAccent));
        textView3.setPadding(0, 3, 0, 3);
        textView3.setGravity(Gravity.CENTER);
        flashBorder3.addView(textView3, lp3);
        animatorSet1 = new AnimatorSet();
        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(0, 1);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                flashBorder1.setmFraction((float) animation.getAnimatedValue());
                flashBorder1.postInvalidate();
            }
        });
        ObjectAnimator shrinkInAnimator1 = ObjectAnimator.ofFloat(textView1, "scaleY", 1.0f, 0);
        shrinkInAnimator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                textView1.setText(strGroups1[random.nextInt(4)]);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ObjectAnimator expandOutAnimator1 = ObjectAnimator.ofFloat(textView1, "scaleY", 0, 1.0f);
        animatorSet1.play(shrinkInAnimator1).before(expandOutAnimator1).with(valueAnimator1);
        animatorSet1.setDuration(300);
        animatorSet2 = new AnimatorSet();
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0, 1);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                flashBorder2.setmFraction((float) animation.getAnimatedValue());
                flashBorder2.postInvalidate();
            }
        });
        ObjectAnimator shrinkInAnimator2 = ObjectAnimator.ofFloat(textView2, "scaleY", 1.0f, 0);
        shrinkInAnimator2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                textView2.setText(strGroups2[random.nextInt(4)]);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ObjectAnimator expandOutAnimator2 = ObjectAnimator.ofFloat(textView2, "scaleY", 0, 1.0f);
        animatorSet2.play(shrinkInAnimator2).before(expandOutAnimator2).with(valueAnimator2);
        animatorSet2.setDuration(300);
        animatorSet3 = new AnimatorSet();
        ValueAnimator valueAnimator3 = ValueAnimator.ofFloat(0, 1);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                flashBorder3.setmFraction((float) animation.getAnimatedValue());
                flashBorder3.postInvalidate();
            }
        });
        ObjectAnimator shrinkInAnimator3 = ObjectAnimator.ofFloat(textView3, "scaleY", 1.0f, 0);
        shrinkInAnimator3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                textView3.setText(strGroups3[random.nextInt(4)]);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ObjectAnimator expandOutAnimator3 = ObjectAnimator.ofFloat(textView3, "scaleY", 0, 1.0f);
        animatorSet3.play(shrinkInAnimator3).before(expandOutAnimator3).with(valueAnimator3);
        animatorSet3.setDuration(300);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rcCollect.startInfiniteAnimation();
        rcShare.startInfiniteAnimation();
        rcComment.startInfiniteAnimation();
        if (isFirstCreated) {
            animatorSet1.start();
            animatorSet2.start();
            animatorSet3.start();
        }
        mScheduledRefreshFuture = mScheduledExecutorService.scheduleAtFixedRate(newsRefreshRunnable, 0, TIME_TO_REFRESH_NEWS, TimeUnit.MILLISECONDS);
        isFirstCreated = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScheduledRefreshFuture.cancel(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScheduledExecutorService.shutdown();
        isFirstCreated = true;
    }

    private void addMaskLayerToUserPhoto(int maskColor, int alpha, int height) {
        View maskLayer = new View(rlContent.getContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = height;
        maskLayer.setBackgroundColor(maskColor);
        maskLayer.getBackground().setAlpha(alpha);
        rlContent.addView(maskLayer, lp);
    }

    private void prepareNewsRefreshRunnable(){
        newsRefreshRunnable = new Runnable() {
            @Override
            public void run() {
                switch (random.nextInt(3)) {
                    case 0:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animatorSet1.start();
                            }
                        });
                        break;
                    case 1:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animatorSet2.start();
                            }
                        });
                        break;
                    case 2:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animatorSet3.start();
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };
    }
}
