package com.shtaigaway.moodmuse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Naughty Spirit
 * on 1/28/15.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, getString(R.string.mood_tracking_started), Toast.LENGTH_LONG).show();
        finish();
    }
}
