package com.toyuong.util;

import android.content.Context;

/**
 * 涉及到屏幕显示的工具类
 * */
public class DisplayManager {

    /**
     * dp转成px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
