package com.shtaigaway.moodmuse.mood;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shtaigaway.moodmuse.R;

/**
 * Created by Naughty Spirit
 * on 12/1/14.
 */
public class MoodView extends LinearLayout
        implements WearableListView.OnCenterProximityListener {

    private ImageView moodImage;
    private TextView moodMessage;

    public MoodView(Context context) {
        this(context, null);
    }

    public MoodView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoodView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        moodImage = (ImageView) findViewById(R.id.mood_image);
        moodMessage = (TextView) findViewById(R.id.mood_message);
    }

    @Override
    public void onCenterPosition(boolean b) {
        float scale = 1.6f;
        moodImage.animate().scaleX(scale).scaleY(scale).alpha(1.0f);
        moodMessage.animate().alpha(1.0f);
    }

    @Override
    public void onNonCenterPosition(boolean b) {
        float scale = 1.0f;
        moodImage.animate().scaleX(scale).scaleY(scale).alpha(0.6f);
        moodMessage.animate().alpha(0.6f);
    }
}