package com.pranjal.appstoredesign;

import android.app.Application;
import android.content.Context;

/**
 * Created by Pranjal on 02-04-2017.
 * Application class for getting context outside of activities.
 */

public class App extends Application {

    // The static context that shall be accessed from other classes.
    private static Context context;

    // The String that distinguishes the broadcast as a progress change.
    public static final String PROGRESS_BROADCAST_NAME = "com.pranjal.appstoredesign.progress";

    @Override
    public void onCreate() {
        super.onCreate();
        // Store the application context on application load.
        context = this;
    }

    public static Context getContext () {
        return context;
    }

}
