package com.shtaigaway.moodmuse.tracking;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shtaigaway.moodmuse.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Naughty Spirit
 * on 12/11/14.
 */
public class MoodTrackingService extends Service implements MoodTrackingScheduler {

    public static boolean isRunning = false;
    private ScheduledExecutorService backgroundService;

    private Random random = new Random();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isRunning) {
            backgroundService = Executors.newSingleThreadScheduledExecutor();
            scheduleNextMoodTracking();
            isRunning = true;
        }

        return START_STICKY;
    }

    public void scheduleNextMoodTracking() {

        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        long nextTrack = (random.nextInt(Constants.MAX_HOURS_TRACKING_INTERVAL) + 1 + currentHour) % 24;
        TimeUnit timeUnit = TimeUnit.HOURS;

        if (nextTrack > Constants.LATEST_TRACKING_TIME && nextTrack < Constants.EARLIEST_TRACKING_TIME) {
            nextTrack = nowAndEarliestTrackingTimeDifference();
            timeUnit = TimeUnit.MILLISECONDS;
        }

        backgroundService.schedule(new RequestMoodTracking(
                this, this), nextTrack, timeUnit);
    }

    private long nowAndEarliestTrackingTimeDifference() {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        int nextDay = calendar.get(Calendar.DAY_OF_YEAR) % 365;
        nextDay = nextDay == 0 ? 1 : nextDay;
        calendar.set(Calendar.DAY_OF_YEAR, nextDay);
        calendar.set(Calendar.HOUR_OF_DAY, Constants.EARLIEST_TRACKING_TIME);
        Date tomorrowTracking = calendar.getTime();
        return tomorrowTracking.getTime() - now.getTime();
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        backgroundService.shutdownNow();
        super.onDestroy();
    }

}
