package com.wusp.indicatorbox_library.ToolBox;

import android.view.View;

/**
 * Created by wusp on 16/4/24.
 */
public class Utils {
    /**
     * Adjust the size based on measureSpec.
     * @param desiredSize
     * @param measureSpec
     * @return
     */
    public static int getAdjustedSize(int desiredSize, int measureSpec){
        int result = desiredSize;
        switch(View.MeasureSpec.getMode(measureSpec)){
            case View.MeasureSpec.AT_MOST:
                result = Math.min(desiredSize, View.MeasureSpec.getSize(measureSpec));
                break;
            case View.MeasureSpec.EXACTLY:
                result = View.MeasureSpec.getSize(measureSpec);
                break;
            case View.MeasureSpec.UNSPECIFIED:
                break;
        }
        return result;
    }
}
