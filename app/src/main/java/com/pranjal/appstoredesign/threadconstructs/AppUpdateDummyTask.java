package com.pranjal.appstoredesign.threadconstructs;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import com.pranjal.appstoredesign.App;

/**
 * Created by Pranjal on 01-04-2017.
 * AsyncTask to simulate the updating of an app.
 */

public class AppUpdateDummyTask extends AsyncTask<Void, Integer, Void> {

    // The approximate running time of the AsyncTask.
    private static final int RUN_TIME_MILLIS = 20000;

    // AppItem position for which this task shall work.
    private int position;

    public AppUpdateDummyTask (int appItemPosition) {
        this.position = appItemPosition;
    }

    @Override
    protected void onPreExecute () {
        Intent intent = new Intent(App.PROGRESS_BROADCAST_NAME);
        // Setting the progress to 0 initially.
        intent.putExtra("progress", 0);
        intent.putExtra("position", position);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int progress = 0;
        // wait and update progress for RUN_TIME_MILLIS milliseconds (approx).
        while (!isCancelled() && progress <= 100) {
            try {
                Thread.sleep(RUN_TIME_MILLIS/100);
                publishProgress(progress);
            } catch (InterruptedException ie) {
                break;
            }
            progress++;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate (Integer... integers) {
        Intent intent = new Intent(App.PROGRESS_BROADCAST_NAME);
        // Adding data to the intent for local broadcast.
        intent.putExtra("progress", integers[0]);
        intent.putExtra("position", position);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }

    // For assuring that the last broadcast received sets a progress of -1 on the appItem.
    @Override
    protected void onCancelled () {
        Intent intent = new Intent(App.PROGRESS_BROADCAST_NAME);
        // Adding data to the intent for local broadcast.
        intent.putExtra("progress", -1);
        intent.putExtra("position", position);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }

}
