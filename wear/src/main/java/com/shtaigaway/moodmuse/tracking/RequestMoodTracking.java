package com.shtaigaway.moodmuse.tracking;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private final MoodTrackingScheduler moodTrackingScheduler;

    public RequestMoodTracking(Context context, MoodTrackingScheduler moodTrackingScheduler) {
        this.context = context;
        this.moodTrackingScheduler = moodTrackingScheduler;
    }

    @Override
    public void run() {
        Intent viewIntent = new Intent(context, MainActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.tracking_notification_title))
                .setContentText(context.getString(R.string.tracking_notification_description))
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setLocalOnly(true)
                .setContentIntent(viewPendingIntent);

        NotificationCompat.WearableExtender extender =
                new NotificationCompat.WearableExtender();

        Bitmap background = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.notification_background);
        extender.setBackground(background);

        notificationBuilder.extend(extender);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        notificationManager.notify(Constants.TRACK_MOOD_NOTIFICATION_ID, notificationBuilder.build());
        moodTrackingScheduler.scheduleNextMoodTracking();
    }
}
