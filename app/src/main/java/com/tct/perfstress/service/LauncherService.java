package com.tct.perfstress.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tct.perfstress.PerApplication;
import com.tct.perfstress.Utility;
import com.tct.perfstress.ui.ResultActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lqchen on 17-8-23.
 */

public class LauncherService extends Service {
    public static final String TAG = "LauncherService";
    public static final String LAUNCH_APP = "com.lqchen.perfapp.launchapp";

    private Context mContext;
    private List<Integer> launchAppsNum;
    private String command;
//    private Long appLaunchTime;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service onCreate()");
        mContext = this;
//        appLaunchTime = 0L;
        launchAppsNum = new ArrayList<>();
        PerApplication.timerLaunch = new Timer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service onStartCommand()");
        String action = null;
        try {
            action = intent.getAction();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (action == null) {
            return super.onStartCommand(intent, flags, startId);
        }

        switch (action) {
            case LAUNCH_APP:
                try {
                    startLaunchApp();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startLaunchApp() {
        PerApplication.timerLaunch.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (launchAppsNum.size() == 0) {
                    setLaunchAppNum();
                }
                Log.d(LauncherService.TAG, "appSelected = " + PerApplication.appCount);
                if (PerApplication.appCount != PerApplication.appSelected) {
                    Log.d(LauncherService.TAG, "called run()");
                    try {
                        String pkgName = PerApplication.app_pkgs.get(launchAppsNum.get(PerApplication.appCount));
                        Intent intent = mContext.getPackageManager()
                                .getLaunchIntentForPackage(pkgName);
                        mContext.startActivity(intent);
                        PerApplication.appCount++;
                        Log.d(LauncherService.TAG, "开始启动第" + PerApplication.appCount + "个App, Name = " + pkgName);
                        PerApplication.startTime = System.currentTimeMillis();
//                        runShellCommand(mergeCommand(pkgName, Utility.getActivityName(pkgName, mContext)));
//                        Log.d(Utility.TAG,"shell command = " + mergeCommand(pkgName,Utility.getActivityName(pkgName,mContext)));
                        Log.d(LauncherService.TAG, "启动时间" + (PerApplication.endTime - PerApplication.startTime));
                        return;
                    } catch (Exception e) {
                        Log.e(LauncherService.TAG, "PS - StarterService----Unable to start " +
                                PerApplication.app_pkgs.get(launchAppsNum.get(PerApplication.appCount)) + e);
                    }
                }else {
                    if (!PerApplication.resultDeployed) {
                        PerApplication.resultDeployed = true;
                        Log.d(LauncherService.TAG, "PS - StarterService----Launching Results Activity.");
                        Intent intent = new Intent(mContext, ResultActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }

            }
        }, 4000L, 10000L);
//        Toast.makeText(mContext, "appLaunchTime = " + appLaunchTime, Toast.LENGTH_SHORT).show();
        PerApplication.appCount = 0;
        launchAppsNum.clear();
    }


    private void setLaunchAppNum() {
        for (Map.Entry e :
                PerApplication.isSelected.entrySet()) {
            Object key = e.getKey();
            Object val = e.getValue();
            if (val.equals(true)) {
                launchAppsNum.add(Integer.valueOf(key.toString()));
//                Log.d(LauncherService.TAG, "key = " + key);
            }
        }
    }

    private void test(String pkgName, Context mContext) {
        String activityName = Utility.getActivityName(pkgName, mContext);
    }

    private void runShellCommand(String command) {
        Process process = null;
        BufferedReader bufferedReader = null;
        StringBuilder mShellCommandSB = new StringBuilder();
//        Log.d(LauncherService.TAG, "shell command" + command);

        mShellCommandSB.delete(0, mShellCommandSB.length());
        String[] cmd = new String[]{"/system/bin/sh", "-c", command}; //调用bin文件
        try {
//            byte b[] = new byte[1024];
            process = Runtime.getRuntime().exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                mShellCommandSB.append(line);
            }
            Log.d(LauncherService.TAG, "runShellCommand result : " + mShellCommandSB.toString());
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (process != null) {
                process.destroy();
            }
        }
    }

    private String mergeCommand(String pkgName, String activityName) {
        return "am start -W -n " + pkgName + "/" + activityName;
    }
}
