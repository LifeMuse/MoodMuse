package com.shtaigaway.moodmuse;

import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.parse.ParseObject;
import com.parse.ParseUser;

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
            ParseObject mood = new ParseObject("MoodRecord");
            mood.add("name", message);
            mood.add("user", ParseUser.getCurrentUser());
            mood.saveInBackground();
        }
        else {
            super.onMessageReceived(messageEvent);
        }
    }
}
