package com.shtaigaway.moodmuse.tracking;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.shtaigaway.moodmuse.Constants;
import com.shtaigaway.moodmuse.R;

import java.util.Calendar;

/**
 * Created by Naughty Spirit
 * on 12/11/14.
 */

public class Bootstrap {

    public static synchronized void startAlwaysOnService(Context context,
                                                         String loadedFrom) {

        if (!MoodTrackingService.isRunning) {

            Intent startServiceIntent = new Intent(context, MoodTrackingService.class);
            startServiceIntent.putExtra(Constants.STARTUP_ACTION_NAME, loadedFrom);
            context.startService(startServiceIntent);

            Intent startAlarmIntent = new Intent(context, AlarmBroadcastReceiver.class);
            startAlarmIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            startAlarmIntent.putExtra(Constants.STARTUP_ACTION_NAME, loadedFrom);
            startAlarmIntent.setAction(context.getString(R.string.alarm_action_name));
            PendingIntent sender = PendingIntent.getBroadcast(context, 0,
                    startAlarmIntent, 0);
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(System.currentTimeMillis());
            time.add(Calendar.SECOND, 1);

            AlarmManager am = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);


            am.setRepeating(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(),
                    Constants.ALARM_REPEAT_INTERVAL * 1000, sender);

            setBootUpListen(context, true);
        }
    }

    public static synchronized void stopAlwaysOnService(Context context) {

        Intent stopServiceIntent = new Intent(context, MoodTrackingService.class);
        context.stopService(stopServiceIntent);

        // cancel alarm restart
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.setAction(context.getString(R.string.alarm_action_name));
        PendingIntent sender = PendingIntent
                .getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);

        setBootUpListen(context, false);
    }

    private static void setBootUpListen(Context context, boolean isEnabled) {
        int flag = (isEnabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
        ComponentName component = new ComponentName(context,
                RebootBroadcastReceiver.class);

        context.getPackageManager().setComponentEnabledSetting(component, flag,
                PackageManager.DONT_KILL_APP);
    }
}