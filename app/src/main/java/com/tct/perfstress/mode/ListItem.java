package com.tct.perfstress.mode;

import android.graphics.drawable.Drawable;

/**
 * Created by lqchen on 17-8-23.
 */

public class ListItem {
    private Drawable appIcon;
    private String appPkgName;
    private String appName;
    private int position;
    private boolean selected;

    public ListItem(Drawable appIcon, String appPkgName, String appName, int position, boolean selected) {
        this.appIcon = appIcon;
        this.appPkgName = appPkgName;
        this.appName = appName;
        this.position = position;
        this.selected = selected;
    }

    public ListItem() {

    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppPkgName() {
        return appPkgName;
    }

    public void setAppPkgName(String appPkgName) {
        this.appPkgName = appPkgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isselected() {
        return selected;
    }

    public void setselected(boolean selected) {
        this.selected = selected;
    }
}
