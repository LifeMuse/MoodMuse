package com.shtaigaway.moodmuse.tracking;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
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
public class MoodTrackingService extends Service implements MoodTrackingScheduler, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static boolean isRunning = false;
    private ScheduledExecutorService backgroundService;

    private Random random = new Random();

    GoogleApiClient googleApiClient;


    @Override
    public void onCreate() {
        super.onCreate();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
    }

    @Override
    public void onDestroy() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        isRunning = false;
        backgroundService.shutdownNow();
        super.onDestroy();
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
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isRunning) {
            googleApiClient.connect();
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
        backgroundService.schedule(new RequestMoodTracking(googleApiClient, this), nextTrack, timeUnit);
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

}
