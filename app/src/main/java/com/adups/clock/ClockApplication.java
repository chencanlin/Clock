package com.adups.clock;

import android.support.multidex.MultiDexApplication;

/**
 * Created by libiqin on 2019/4/17 10:25
 */
public class ClockApplication extends MultiDexApplication {

    private static ClockApplication clockApplication;

    public static ClockApplication getInstance() {
        return clockApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        clockApplication = this;
    }
}
