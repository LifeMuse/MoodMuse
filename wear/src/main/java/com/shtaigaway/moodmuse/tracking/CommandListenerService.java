package com.shtaigaway.moodmuse.tracking;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.shtaigaway.moodmuse.Constants;
import com.shtaigaway.moodmuse.MainActivity;
import com.shtaigaway.moodmuse.R;

/**
 * Created by Naughty Spirit
 * on 1/28/15.
 */
public class CommandListenerService extends WearableListenerService {

    private static final String PATH = "/commands";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(PATH)) {
            showTrackingNotification();
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    private void showTrackingNotification() {
        Context context = getApplicationContext();

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
    }
}
