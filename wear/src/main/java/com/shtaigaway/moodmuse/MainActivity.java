package com.shtaigaway.moodmuse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.shtaigaway.moodmuse.mood.Mood;
import com.shtaigaway.moodmuse.mood.MoodListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.shtaigaway.moodmuse.R.drawable;
import static com.shtaigaway.moodmuse.R.id;
import static com.shtaigaway.moodmuse.R.string;

public class MainActivity extends Activity implements WearableListView.ClickListener {

    private List<Mood> moodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMoodList();
        setupMoodListView();
    }

    private void createMoodList() {
        moodList = new ArrayList<Mood>();
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

}
