package com.shtaigaway.moodmuse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;
import com.shtaigaway.moodmuse.tracking.Bootstrap;

/**
 * Created by Naughty Spirit
 * on 12/14/14.
 */
public class MobileApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bootstrap.startAlwaysOnService(this, Constants.STARTUP_ACTION_NAME);
        Parse.initialize(this, ParseConstants.APP_ID, ParseConstants.CLIENT_KEY);
        ParseUser.enableAutomaticUser();
    }
}
