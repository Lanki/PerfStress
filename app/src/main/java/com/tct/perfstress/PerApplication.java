package com.tct.perfstress;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.tct.perfstress.service.LauncherService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

/**
 * Created by lqchen on 17-8-23.
 */

public class PerApplication extends Application {
    public static Context mContext;
    public static ArrayList<String> app_pkgs;
    public static HashMap<Integer, Boolean> isSelected;
    public static int appCount;
    public static int appSelected;
    public static Timer timerLaunch;
    public static Timer timerLogger;
    public static Intent launchServiceIntent;
    public static Long startTime;
    public static Long endTime;
    public static Boolean startLock;
    public static Boolean resultDeployed;

    @Override
    public void onCreate() {
        mContext = this;
        super.onCreate();
        isSelected = new HashMap<>();
        app_pkgs = Utility.queryPkgName(mContext);
        appCount = 0;
        appSelected = 0;
        startTime = 0L;
        endTime = 0L;
        startLock = false;
        resultDeployed = false;
        launchServiceIntent = new Intent(this, LauncherService.class);

    }
}
