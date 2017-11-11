package com.cyder.atsushi.youtubesync;

import android.app.Application;
import android.content.Context;

/**
 * Created by atsushi on 2017/11/11.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}