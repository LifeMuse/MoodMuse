package com.shtaigaway.moodmuse.mood;

import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtaigaway.moodmuse.R;

import java.util.List;

/**
 * Created by Naughty Spirit
 * on 12/3/14.
 */
public final class MoodListAdapter extends WearableListView.Adapter {
    private final List<Mood> moodList;
    private LayoutInflater inflater;

    public MoodListAdapter(LayoutInflater inflater, List<Mood> moodList) {
        this.inflater = inflater;
        this.moodList = moodList;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new WearableListView.ViewHolder(inflater.inflate(R.layout.mood_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder,
                                 int position) {
        MoodView moodView = (MoodView) viewHolder.itemView;
        TextView view = (TextView) moodView.findViewById(R.id.mood_message);
        ImageView imageView = (ImageView) moodView.findViewById(R.id.mood_image);
        Mood mood = moodList.get(position);
        view.setText(mood.getMessage());
        imageView.setImageResource(mood.getImageResource());
    }

    @Override
    public int getItemCount() {
        return moodList.size();
    }
}
