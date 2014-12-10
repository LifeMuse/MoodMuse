package com.shtaigaway.moodmuse.tracking;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.shtaigaway.moodmuse.Constants;
import com.shtaigaway.moodmuse.MainActivity;
import com.shtaigaway.moodmuse.R;

/**
 * Created by Naughty Spirit
 * on 12/11/14.
 */
public class RequestMoodTracking implements Runnable {

    private final Context context;

    public RequestMoodTracking(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        Intent viewIntent = new Intent(context, MainActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(context, 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setContentTitle(context.getString(R.string.tracking_notification_title))
                        .setContentText(context.getString(R.string.tracking_notification_description))
                        .setContentIntent(viewPendingIntent);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        notificationManager.notify(Constants.TRACK_MOOD_NOTIFICATION_ID, notificationBuilder.build());
    }
}
