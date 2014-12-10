package com.shtaigaway.moodmuse.tracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shtaigaway.moodmuse.Constants;
import com.shtaigaway.moodmuse.R;

/**
 * Created by Naughty Spirit
 * on 12/11/14.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(context.getString(R.string.alarm_action_name))) {
            String previousAction = intent
                    .getStringExtra(Constants.STARTUP_ACTION_NAME);
            if (previousAction == null || previousAction.length() == 0) {
                previousAction = intent.getAction();
            }
            Bootstrap.startAlwaysOnService(context, previousAction);
        }
    }
}
