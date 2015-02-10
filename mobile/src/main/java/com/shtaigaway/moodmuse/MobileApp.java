package com.shtaigaway.moodmuse;

import android.app.Application;

import com.shtaigaway.moodmuse.tracking.Bootstrap;

import it.appspice.android.AppSpice;

/**
 * Created by Naughty Spirit
 * on 12/14/14.
 */
public class MobileApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bootstrap.startAlwaysOnService(this, Constants.STARTUP_ACTION_NAME);
        AppSpice.init(getApplicationContext(), "AppSpice token", AppSpiceConstants.APP_ID);
    }
}
