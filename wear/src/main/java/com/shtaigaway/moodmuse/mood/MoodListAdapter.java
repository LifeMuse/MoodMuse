package com.shtaigaway.moodmuse.mood;

import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtaigaway.moodmuse.MainActivity;
import com.shtaigaway.moodmuse.R;

import java.util.List;

/**
 * Created by Naughty Spirit
 * on 12/3/14.
 */
public final class MoodListAdapter extends WearableListView.Adapter {
    private final List<Mood> moodList;
    private final LayoutInflater inflater;

    public MoodListAdapter(LayoutInflater inflater, List<Mood> moodList) {
        this.moodList = moodList;
        this.inflater = inflater;
    }

    public class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.mood_message);
            imageView = (ImageView) itemView.findViewById(R.id.mood_image);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.list_item, null));
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
