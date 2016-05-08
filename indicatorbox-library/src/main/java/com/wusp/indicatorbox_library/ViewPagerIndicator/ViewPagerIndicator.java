package com.wusp.indicatorbox_library.ViewPagerIndicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.wusp.indicatorbox_library.R;
import com.wusp.indicatorbox_library.RippleCircle.RippleCircle;

/**
 * Created by wusp on 16/4/23.
 */
public class ViewPagerIndicator extends RippleCircle {
    private ViewPager mViewPager;
    private int mSmallTextSize;
    private int mBigTextSize;
    private int mSmallTextColor;
    private int mBigTextColor;
    private int mSlashColor;

    public ViewPagerIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        initTypedArray(context, attributeSet);
        patternDrawer = new ViewPagerTextDrawer(mBigTextSize, mSmallTextSize, mBigTextColor, mSmallTextColor, mSlashColor);
    }

    private void initTypedArray(Context context, AttributeSet attributeSet){
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ViewPagerIndicator);
        mBigTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewPagerIndicator_big_text_size, mCircleRadius);
        mSmallTextSize = typedArray.getDimensionPixelSize(R.styleable.ViewPagerIndicator_small_text_size, mCircleRadius - 6);
        mSmallTextColor = typedArray.getColor(R.styleable.ViewPagerIndicator_small_text_color, Color.WHITE);
        mBigTextColor = typedArray.getColor(R.styleable.ViewPagerIndicator_big_text_color, Color.WHITE);
        mSlashColor = typedArray.getColor(R.styleable.ViewPagerIndicator_slash_color, Color.WHITE);
        typedArray.recycle();
    }

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        if (mViewPager != null && mViewPager.getAdapter() != null) {
            mViewPager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewPager.addOnPageChangeListener(mInternalPageChangeListener);
            mViewPager.getAdapter().registerDataSetObserver(mInternalDataSetObserver);
            mInternalPageChangeListener.onPageSelected(mViewPager.getCurrentItem());
            mViewPager.getAdapter().notifyDataSetChanged();
        }
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mViewPager != null) {
                ((ViewPagerTextDrawer) patternDrawer).setmBigText("" + position);
            }
            startAnimationOnce();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private final DataSetObserver mInternalDataSetObserver = new DataSetObserver() {

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }

        @Override
        public void onChanged() {
            super.onChanged();
            if (mViewPager != null && mViewPager.getAdapter() != null) {
                ((ViewPagerTextDrawer) patternDrawer).setmSmallText("" + mViewPager.getAdapter().getCount());
                        ((ViewPagerTextDrawer) patternDrawer).setmBigText("" + mViewPager.getCurrentItem());
            }
            postInvalidate();
        }
    };
}
