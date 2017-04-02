package com.pranjal.appstoredesign.models;

import android.os.AsyncTask;

import com.pranjal.appstoredesign.threadconstructs.AppUpdateDummyTask;

/**
 * Created by Pranjal on 01-04-2017.
 * The class representing an app's information.
 */

public class AppItem {

    private String appTitle;
    private String versionString;
    private String updateInfo;
    private int imageResId;
    // progress == -1, when the update is is not ongoing or completed.
    private int progress;
    private boolean isUpdated;
    private AppUpdateDummyTask task;

    public AppItem (String appTitle, String versionString, String updateInfo, int imageResId) {
        this.appTitle = appTitle;
        this.versionString = versionString;
        this.updateInfo = updateInfo;
        this.imageResId = imageResId;
        this.progress = -1;
        this.task = null;
        this.isUpdated = false;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public String getVersionString() {
        return versionString;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public int getImageResId () {
        return imageResId;
    }

    public void setProgress (int progress) {
        this.progress = progress;
        if (progress == 100) {
            isUpdated = true;
            task = null;
        }
    }

    public int getProgress () {
        return progress;
    }

    public void startUpdate (int position) {
        task = new AppUpdateDummyTask(position);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void stopUpdate () {
        // Set the progress to -1 so that the update state of this appItem is seen as not updating.
        progress = -1;
        if (task != null) {
            task.cancel(true);
        }
        task = null;
    }

    public boolean isUpdated () {
        return isUpdated;
    }

}
