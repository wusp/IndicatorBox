package com.wusp.indicatorbox_library.ViewPagerIndicator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import com.wusp.indicatorbox_library.R;
import com.wusp.indicatorbox_library.ToolBox.Utils;

/**
 * Created by wusp on 16/3/10.
 */
public class PointIndicator extends LinearLayout {
    //默认显示图标的宽度和高度值均为 5 dip
    private static final int INDICATOR_WIDTH = 5;
    private int mIndicatorNum = 0;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;
    private int mIndicatorMargin = -1;
    private ViewPager mViewPager;
    //切换动画
    private int mAnimatorResId = R.animator.scale_with_alpha;
    //翻转动画
    private int mAnimatorReverseResId = 0;
    //选中时的显示效果
    private int mIndicatorBackgroundResId = R.drawable.white_radius;
    //未选中时的显示效果
    private int mIndicatorUnselectedBackgroundResId = R.drawable.white_radius;
    //动画
    private Animator mAnimatorSelected;
    private Animator mAnimatorUnselected;
    //Immediate 用于初始化构建图标 duration = 0;
    private Animator mImmediateAnimatorSelected;
    private Animator mImmediateAnimatorUnselected;
    //排列方式， 默认为水平方式， 在初始化时读取真实的设置值
    private int mOrientation = LinearLayout.HORIZONTAL;
    private int mLastPosition = 0;

    public PointIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public PointIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs){
        handleTypedArray(context, attrs);
        checkIndicatorConfig(context);
    }

    /**
     * 获取ViewPagerIndicator的个性化设置值
     * @param context
     * @param attrs
     */
    private void handleTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PointIndicator);
        mIndicatorWidth = typedArray.getDimensionPixelOffset(R.styleable.PointIndicator_indicator_width, -1);
        mIndicatorHeight = typedArray.getDimensionPixelOffset(R.styleable.PointIndicator_indicator_height, -1);
        mIndicatorMargin = typedArray.getDimensionPixelOffset(R.styleable.PointIndicator_indicator_gap, -1);
        mIndicatorBackgroundResId = typedArray.getResourceId(R.styleable.PointIndicator_indicator_selected_src, R.drawable.white_radius);
        mIndicatorUnselectedBackgroundResId = typedArray.getResourceId(R.styleable.PointIndicator_indicator_unselected_src, R.drawable.white_radius);
        mAnimatorResId = typedArray.getResourceId(R.styleable.PointIndicator_indicator_animator, R.animator.scale_with_alpha);
        typedArray.recycle();
    }

    private void checkIndicatorConfig(Context context) {
        mIndicatorWidth = (mIndicatorWidth < 0) ? dip2px(INDICATOR_WIDTH) : mIndicatorWidth;
        mIndicatorHeight = (mIndicatorHeight < 0) ? dip2px(INDICATOR_WIDTH) : mIndicatorHeight;
        mIndicatorMargin = (mIndicatorMargin < 0) ? dip2px(INDICATOR_WIDTH) : mIndicatorMargin;
        mAnimatorResId = (mAnimatorResId == 0) ? R.animator.scale_with_alpha : mAnimatorResId;
        mAnimatorSelected = AnimatorInflater.loadAnimator(context, mAnimatorResId);
        mImmediateAnimatorSelected = AnimatorInflater.loadAnimator(context, mAnimatorResId);
        mImmediateAnimatorSelected.setDuration(0);
        if (mAnimatorReverseResId == 0){
            mAnimatorUnselected = AnimatorInflater.loadAnimator(context, mAnimatorResId);
            mImmediateAnimatorUnselected = AnimatorInflater.loadAnimator(context, mAnimatorResId);
            mAnimatorUnselected.setInterpolator(new ReverseInterpolator());
            mImmediateAnimatorUnselected.setInterpolator(new ReverseInterpolator());
            mImmediateAnimatorUnselected.setDuration(0);
        }else{
            mAnimatorUnselected = AnimatorInflater.loadAnimator(context, mAnimatorReverseResId);
            mImmediateAnimatorUnselected = AnimatorInflater.loadAnimator(context, mAnimatorReverseResId);
            mImmediateAnimatorUnselected.setDuration(0);
        }
        mIndicatorBackgroundResId = (mIndicatorBackgroundResId == 0) ? R.drawable.white_radius : mIndicatorBackgroundResId;
        mIndicatorUnselectedBackgroundResId = (mIndicatorUnselectedBackgroundResId == 0) ? R.drawable.white_radius : mIndicatorBackgroundResId;
        mOrientation = getOrientation();
        if (mOrientation == HORIZONTAL){
            setGravity(Gravity.CENTER_VERTICAL);
        }else{
            setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int resultWidth = Utils.getAdjustedSize(mIndicatorNum * dip2px(INDICATOR_WIDTH) * 2 * (mIndicatorNum + 1) * mIndicatorMargin, widthMeasureSpec);
        int resultHeight = Utils.getAdjustedSize(dip2px(INDICATOR_WIDTH) * 2 + 5, heightMeasureSpec);
        setMeasuredDimension(resultWidth, resultHeight);
    }

    public void setViewPager(ViewPager viewPager){
        mViewPager = viewPager;
        if (mViewPager != null && mViewPager.getAdapter() != null){
            mViewPager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewPager.addOnPageChangeListener(mInternalPageChangeListener);
            mViewPager.getAdapter().registerDataSetObserver(mInternalDataSetObserver);
            mInternalPageChangeListener.onPageSelected(viewPager.getCurrentItem());
        }
    }

    public void setIndicatorNum(int num){
        mIndicatorNum = num;
        createIndicators();
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mViewPager.getAdapter() == null || mIndicatorNum <= 0){
                return;
            }
            if (mAnimatorSelected.isRunning()){
                mAnimatorSelected.end();
                mAnimatorSelected.cancel();
            }
            if (mAnimatorUnselected.isRunning()){
                mAnimatorUnselected.end();
                mAnimatorUnselected.cancel();
            }
            if (mLastPosition >= 0){
                View currentIndicator = getChildAt(mLastPosition);
                currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
                mAnimatorUnselected.setTarget(currentIndicator);
                mAnimatorUnselected.start();
            }
            //handle position for Infinite
            if (position >= mIndicatorNum)
                position = convertForInifite(position);
            View selectedIndicator = getChildAt(position);
            selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
            mAnimatorSelected.setTarget(selectedIndicator);
            mAnimatorSelected.start();
            mLastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            int convertedCurrentItem = convertForInifite(mViewPager.getCurrentItem());
            int currentCount = getChildCount();
            if (mIndicatorBackgroundResId == currentCount){
                return;
            }else if (mLastPosition < mIndicatorNum){
                mLastPosition = convertedCurrentItem;
            }else{
                mLastPosition = -1;
            }
            createIndicators();
        }
    };

    /**
     * 绘制Indicator
     */
    private void createIndicators(){
        removeAllViews();
        if (mIndicatorNum <= 0){
            return;
        }
        //handle currentItem for Infinite
        int currentItem = convertForInifite(mViewPager.getCurrentItem());
        for (int i = 0; i < mIndicatorNum; i++){
            if (currentItem == i){
                //选中
                addIndicator(mIndicatorBackgroundResId, mImmediateAnimatorSelected);
            }else{
                addIndicator(mIndicatorUnselectedBackgroundResId, mImmediateAnimatorUnselected);
            }
        }
    }

    /**
     * 添加单个指示器
     * @param backgroundResId
     * @param animator
     */
    private void addIndicator(@DrawableRes int backgroundResId, Animator animator){
        if (animator.isRunning()){
            animator.end();
            animator.cancel();
        }
        View indicator = new View(getContext());
        indicator.setBackgroundResource(backgroundResId);
        addView(indicator, mIndicatorWidth, mIndicatorHeight);
        LayoutParams lp = (LayoutParams) indicator.getLayoutParams();
        if (mOrientation == LinearLayout.HORIZONTAL){
            lp.leftMargin = mIndicatorMargin;
            lp.rightMargin = mIndicatorMargin;
        }else if (mOrientation == LinearLayout.VERTICAL){
            lp.topMargin = mIndicatorMargin;
            lp.bottomMargin = mIndicatorMargin;
        }
        indicator.setLayoutParams(lp);
        animator.setTarget(indicator);
        animator.start();
    }

    /**
     * 将dp转换为px.
     * @param value
     * @return
     */
    private int dip2px(float value){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    /**
     * 为了正常适配无限循环时相关值做的转换
     * @param value 需要转换的值
     * @return 经过mIndicatorNum 转换后的值
     */
    private int convertForInifite(int value){
        return value % mIndicatorNum;
    }

    private class ReverseInterpolator implements Interpolator {

        @Override
        public float getInterpolation(float v) {
            return Math.abs(1.0f - v);
        }
    }
}
