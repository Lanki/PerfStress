package com.tct.perfstress.mode;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lqchen on 17-8-23.
 */

public class ListItem implements Parcelable {
    private Drawable appIcon;
    private String appPkgName;
    private String appName;
    private int position;
    private boolean selected;


    public ListItem() {

    }

    protected ListItem(Parcel in) {
        appPkgName = in.readString();
        appName = in.readString();
        position = in.readInt();
        selected = in.readByte() != 0;
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(appPkgName);
        parcel.writeString(appName);
        parcel.writeInt(position);
        parcel.writeByte((byte) (selected ? 1 : 0));
    }
}
