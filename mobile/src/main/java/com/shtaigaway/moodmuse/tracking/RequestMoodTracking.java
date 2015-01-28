package com.shtaigaway.moodmuse.tracking;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Naughty Spirit
 * on 12/11/14.
 */
public class RequestMoodTracking implements Runnable {

    private final MoodTrackingScheduler moodTrackingScheduler;
    private static final String PATH = "/commands";
    private final GoogleApiClient googleApiClient;


    public RequestMoodTracking(GoogleApiClient googleApiClient, MoodTrackingScheduler moodTrackingScheduler) {
        this.googleApiClient = googleApiClient;
        this.moodTrackingScheduler = moodTrackingScheduler;
    }

    @Override
    public void run() {
        new SendToDataLayerThread(PATH, "track").start();
        moodTrackingScheduler.scheduleNextMoodTracking();
    }

    class SendToDataLayerThread extends Thread {
        String path;
        String message;

        SendToDataLayerThread(String p, String msg) {
            path = p;
            message = msg;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
            for (Node node : nodes.getNodes()) {
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), path, message.getBytes()).await();
                if (result.getStatus().isSuccess()) {
                    Log.v("myTag", "Message: {" + message + "} sent to: " + node.getDisplayName());
                } else {
                    // Log an error
                    Log.v("myTag", "ERROR: failed to send Message");
                }
            }
        }
    }
}
