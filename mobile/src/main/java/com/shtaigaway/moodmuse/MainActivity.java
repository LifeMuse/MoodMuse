package com.shtaigaway.moodmuse;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.shtaigaway.moodmuse.tracking.Bootstrap;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bootstrap.startAlwaysOnService(this, Constants.STARTUP_ACTION_NAME);
    }
}
