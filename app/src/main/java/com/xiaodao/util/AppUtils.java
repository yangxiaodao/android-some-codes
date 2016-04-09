package com.xiaodao.util;

import android.content.Context;
import android.util.DisplayMetrics;

import com.xiaodao.base.BaseApplication;

/**
 * Created by android on 2016/4/6.
 */
public class AppUtils {

    /**
     * 获取全局上下文对象
     * @return
     */
    public static Context getContext(){
        return BaseApplication.mComtext;
    }

    /**
     * 当前是否在主线程执行
     * @return
     */
    public static boolean isRunOnUiThread(){
        return Thread.currentThread() == BaseApplication.mThread;
    }

    public static int dp2px(int dp){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (dp*density + 0.5f);
    }
}
