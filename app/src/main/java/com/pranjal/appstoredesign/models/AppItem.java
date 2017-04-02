package com.pranjal.appstoredesign.models;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pranjal.appstoredesign.App;
import com.pranjal.appstoredesign.R;
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
    // Boolean to tell if the app has been updated successfully.
    private boolean isUpdated;
    // Boolean to tell the state of the "what's new" drop down updateInfo.
    private boolean isActive;
    // The AsyncTask associated with this app's update.
    private AppUpdateDummyTask task;

    // The height of the dropdown menu.
    private float updateInfoHeight;

    public AppItem (String appTitle, String versionString, String updateInfo, int imageResId) {
        this.appTitle = appTitle;
        this.versionString = versionString;
        this.updateInfo = updateInfo;
        this.imageResId = imageResId;
        this.progress = -1;
        this.task = null;
        this.isUpdated = false;
        this.isActive = false;

        // Code to measure the height of the wrap_content height TextView.
        // Constructing a dummy textView and loading the text into it to get the final height.
        TextView textView = new TextView(App.getContext());
        int padding =
                (int) App.getContext().getResources()
                        .getDimension(R.dimen.app_item_update_info_paddingTop);
        textView.setPadding(0, padding, 0, 0);
        textView.setTypeface(Typeface.DEFAULT);
        textView.setText(updateInfo);
        textView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                App.getContext().getResources()
                        .getDimension(R.dimen.app_item_update_info_text_size)
        );
        Point size = new Point();
        ((WindowManager) textView.getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(size);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(size.x - 2*padding,
                View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        // Setting the updateInfo textView height.
        updateInfoHeight = textView.getMeasuredHeight();
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
        progress = 0;
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

    public boolean isActive () {
        return isActive;
    }

    public void setActive (boolean isActive) {
        this.isActive = isActive;
    }

    public float getUpdateInfoHeight () {
        return updateInfoHeight;
    }
}
