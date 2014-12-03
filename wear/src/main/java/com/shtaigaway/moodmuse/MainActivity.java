package com.shtaigaway.moodmuse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtaigaway.moodmuse.mood.Mood;

import java.util.ArrayList;
import java.util.List;

import static com.shtaigaway.moodmuse.R.drawable;
import static com.shtaigaway.moodmuse.R.id;
import static com.shtaigaway.moodmuse.R.layout;
import static com.shtaigaway.moodmuse.R.string;

public class MainActivity extends Activity implements WearableListView.ClickListener {

    private List<Mood> moodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMoodList();

        WearableListView wearableListView = (WearableListView) findViewById(R.id.wearable_list);
        wearableListView.setAdapter(new Adapter(this));
        wearableListView.setClickListener(this);
    }

    private void createMoodList() {
        moodList = new ArrayList<Mood>();
        moodList.add(new Mood(getString(string.angry_mood), drawable.angry_mood));
        moodList.add(new Mood(getString(string.bad_mood), drawable.sad_mood));
        moodList.add(new Mood(getString(string.ok_mood), drawable.ok_mood));
        moodList.add(new Mood(getString(string.good_mood), drawable.good_mood));
        moodList.add(new Mood(getString(string.awesome_mood), drawable.awesome_mood));
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        TextView nameView = (TextView) viewHolder.itemView.findViewById(id.mood_message);
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

    private final class Adapter extends WearableListView.Adapter {
        private final LayoutInflater inflater;

        public Adapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public class ItemViewHolder extends WearableListView.ViewHolder {
            private TextView textView;
            private ImageView imageView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(id.mood_message);
                imageView = (ImageView) itemView.findViewById(id.mood_image);
            }
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
            return new ItemViewHolder(inflater.inflate(layout.list_item, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder,
                                     int position) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            TextView view = itemHolder.textView;
            Mood mood = moodList.get(position);
            view.setText(mood.getMessage());
            itemHolder.imageView.setImageResource(mood.getImageResource());
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return moodList.size();
        }
    }
}
