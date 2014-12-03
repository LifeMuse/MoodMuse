package com.shtaigaway.moodmuse.mood;

/**
 * Created by Naughty Spirit
 * on 12/3/14.
 */
public class Mood {

    private String message;
    private int imageResource;

    public Mood(String message, int imageResource) {
        this.message = message;
        this.imageResource = imageResource;
    }

    public String getMessage() {
        return message;
    }

    public int getImageResource() {
        return imageResource;
    }
}
