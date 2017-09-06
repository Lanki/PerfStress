package com.tct.perfstress;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.tct.perfstress.mode.ListItem;
import com.tct.perfstress.service.LauncherService;
import com.tct.perfstress.service.LoggerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static Intent loggerServiceIntent;
    public static Long startTime;
    public static Long endTime;
    public static Boolean startLock;
    public static Boolean resultDeployed;
    public static int latency;
    public static List<Integer> launchAppsNum;
    public static ArrayList<ListItem> listItem;
    public static ArrayList<ListItem> chosenList;
    public static ArrayList<Integer> latencies;
    public static boolean firstLaunch;

    @Override
    public void onCreate() {
        mContext = this;
        super.onCreate();
        isSelected = new HashMap<>();
        launchAppsNum = new ArrayList<>();
        chosenList = new ArrayList<>();
        latencies = new ArrayList<>();
        app_pkgs = Utility.queryPkgName(mContext);
        appCount = 0;
        appSelected = 0;
        latency = 0;
        startTime = 0L;
        endTime = 0L;
        firstLaunch = false;
        startLock = false;
        resultDeployed = false;
        listItem = new ArrayList<>();
        launchServiceIntent = new Intent(this, LauncherService.class);
        loggerServiceIntent = new Intent(this, LoggerService.class);

    }
}
