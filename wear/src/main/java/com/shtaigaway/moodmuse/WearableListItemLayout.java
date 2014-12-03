package com.shtaigaway.moodmuse;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Naughty Spirit
 * on 12/1/14.
 */
public class WearableListItemLayout extends LinearLayout
        implements WearableListView.Item {

    private ImageView moodImage;
    private float scale;

    public WearableListItemLayout(Context context) {
        this(context, null);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        moodImage = (ImageView) findViewById(R.id.mood_image);
    }

    @Override
    public float getProximityMinValue() {
        return 1f;
    }

    @Override
    public float getProximityMaxValue() {
        return 1.6f;
    }

    @Override
    public float getCurrentProximityValue() {
        return scale;
    }

    @Override
    public void setScalingAnimatorValue(float scale) {
        this.scale = scale;
        moodImage.setScaleX(scale);
        moodImage.setScaleY(scale);
    }

    @Override
    public void onScaleUpStart() {
    }

    @Override
    public void onScaleDownStart() {
    }
}