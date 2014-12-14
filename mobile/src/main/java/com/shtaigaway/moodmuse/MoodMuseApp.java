package com.shtaigaway.moodmuse;

import android.app.Application;

import com.parse.ParseUser;

/**
 * Created by Naughty Spirit
 * on 12/14/14.
 */
public class MoodMuseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseUser.enableAutomaticUser();
    }
}
