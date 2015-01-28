package com.shtaigaway.moodmuse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.shtaigaway.moodmuse.mood.Mood;
import com.shtaigaway.moodmuse.mood.MoodListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.shtaigaway.moodmuse.R.drawable;
import static com.shtaigaway.moodmuse.R.id;
import static com.shtaigaway.moodmuse.R.string;

public class MainActivity extends Activity implements WearableListView.ClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private List<Mood> moodList;
    GoogleApiClient googleApiClient;

    private static final String PATH = "/moods";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMoodList();
        setupMoodListView();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
    }


    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    private void createMoodList() {
        moodList = new ArrayList<>();
        moodList.add(new Mood(getString(string.angry_mood), drawable.angry_mood));
        moodList.add(new Mood(getString(string.bad_mood), drawable.sad_mood));
        moodList.add(new Mood(getString(string.ok_mood), drawable.ok_mood));
        moodList.add(new Mood(getString(string.good_mood), drawable.good_mood));
        moodList.add(new Mood(getString(string.awesome_mood), drawable.awesome_mood));
    }

    private void setupMoodListView() {
        WearableListView wearableListView = (WearableListView) findViewById(id.wearable_list);
        wearableListView.setAdapter(new MoodListAdapter(LayoutInflater.from(this), moodList));
        wearableListView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        TextView nameView = (TextView) viewHolder.itemView.findViewById(id.mood_message);
        new SendToDataLayerThread("/moods", nameView.getText().toString()).start();

        showSuccess();
        finish();
    }

    private void showSuccess() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                getString(string.mood_saved));
        startActivity(intent);
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    public void onDestroy() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onDestroy();
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
