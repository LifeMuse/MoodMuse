package com.shtaigaway.moodmuse.tracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Naughty Spirit
 * on 12/11/14.
 */
public class RebootBroadcastReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = RebootBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if ((intent.getAction().equals(Intent.ACTION_USER_PRESENT))
                || (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))) {
            Bootstrap.startAlwaysOnService(context, intent.getAction());
        }
    }
}
