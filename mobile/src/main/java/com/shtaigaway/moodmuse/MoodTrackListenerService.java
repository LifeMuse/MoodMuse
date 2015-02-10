package com.shtaigaway.moodmuse;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import it.appspice.android.AppSpice;

/**
 * Created by Naughty Spirit
 * on 1/28/15.
 */
public class MoodTrackListenerService extends WearableListenerService {

    private static final String PATH = "/moods";
    private static final String MOOD_KEY = "mood";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        if (messageEvent.getPath().equals("/moods")) {
            final String message = new String(messageEvent.getData());
            AppSpice.createEvent("moodTrack").with("mood", message).track();
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}
