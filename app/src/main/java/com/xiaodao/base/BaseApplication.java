package com.xiaodao.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by android on 2016/4/6.
 */
public class BaseApplication extends Application {

    public static Context mComtext;
    public static Thread mThread;

    @Override
    public void onCreate() {
        super.onCreate();

        mComtext = this;
        mThread = Thread.currentThread();
    }
}
