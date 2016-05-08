package com.wusp.indicatorbox_library;

import android.graphics.Canvas;

/**
 * Created by wusp on 16/4/22.
 */
public interface DynamicPatternDrawer {
    void onDrawPattern(Canvas canvas, int width, int height, float progressFraction);
}
