package com.wusp.indicatorbox;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wusp.indicatorbox_library.FlashBorder.FlashBorderView;

public class FlashBorderActivity extends AppCompatActivity {
    private FlashBorderView flashBorderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        flashBorderView = (FlashBorderView) findViewById(R.id.flashItemView);
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(700);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                flashBorderView.setmFraction((float) animation.getAnimatedValue());
                flashBorderView.invalidate();   //from UI thread
                flashBorderView.postInvalidate();   //from non-UI thread
            }
        });
        flashBorderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAnimator.start();
            }
        });
    }
}
