package com.shtaigaway.moodmuse;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Naughty Spirit
 * on 12/7/14.
 */
public class ListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        if (messageEvent.getPath().equals("/message_path")) {
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