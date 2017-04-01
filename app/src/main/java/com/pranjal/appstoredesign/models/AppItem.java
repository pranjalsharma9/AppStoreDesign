package com.pranjal.appstoredesign.models;

/**
 * Created by Pranjal on 01-04-2017.
 * The class representing an app's information.
 */

public class AppItem {

    private String appTitle;
    private String versionString;
    private String updateInfo;
    private int imageResId;

    public AppItem (String appTitle, String versionString, String updateInfo, int imageResId) {
        this.appTitle = appTitle;
        this.versionString = versionString;
        this.updateInfo = updateInfo;
        this.imageResId = imageResId;
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

}
