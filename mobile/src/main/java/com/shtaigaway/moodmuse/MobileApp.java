package com.shtaigaway.moodmuse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by Naughty Spirit
 * on 12/14/14.
 */
public class MobileApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, ParseConstants.APP_ID, ParseConstants.CLIENT_KEY);
        ParseUser.enableAutomaticUser();
    }
}
