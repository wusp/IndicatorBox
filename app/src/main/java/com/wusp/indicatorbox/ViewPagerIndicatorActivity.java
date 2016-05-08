package com.wusp.indicatorbox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wusp.indicatorbox_library.ViewPagerIndicator.PointIndicator;
import com.wusp.indicatorbox_library.ViewPagerIndicator.ViewPagerIndicator;

public class ViewPagerIndicatorActivity extends AppCompatActivity {
    private ViewPagerIndicator indicator;
    private PointIndicator pointIndicator;
    private ViewPager viewPager;
    private int[] imageRes = {
            R.drawable.android_1,
            R.drawable.android_2,
            R.drawable.android_3
    };
    private SparseArray<View> viewSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_indicator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        indicator = (ViewPagerIndicator) findViewById(R.id.vpIndicator);
        pointIndicator = (PointIndicator) findViewById(R.id.pointIndicator);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        pointIndicator.setViewPager(viewPager);
        pointIndicator.setIndicatorNum(adapter.getCount());
        setSupportActionBar(toolbar);
        viewSparseArray = new SparseArray<>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    indicator.startInfiniteAnimation();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Snackbar.make(viewPager, "Click fab button to start infinite ripple animation.", Snackbar.LENGTH_LONG)
                .setAction("Ok", null)
                .show();
    }

    class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getBaseContext());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            imageView.setImageResource(imageRes[position]);
            container.addView(imageView);
            viewSparseArray.put(position, imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            viewSparseArray.remove(position);
        }
    }

}
