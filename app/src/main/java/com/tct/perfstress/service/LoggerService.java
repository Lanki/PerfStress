package com.tct.perfstress.service;

import android.app.Service;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.tct.perfstress.PerApplication;
import com.tct.perfstress.mode.ListItem;
import com.tct.perfstress.ui.TestActivity;

import java.util.ArrayList;

public class LoggerService extends Service {
    public static final String TAG = "LoggerService";
    public static final String STARTLOG = "com.lqchen.perfapp.startlog";
    private ArrayList<ListItem> listItems;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
            case LoggerService.STARTLOG:
                break;
            default:
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(LoggerService.TAG, "call onTrimMemory");
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Log.d(LoggerService.TAG, "level = 20");
            PerApplication.endTime = System.currentTimeMillis();
            PerApplication.latency = (int) (PerApplication.endTime - PerApplication.startTime);
            String appName = PerApplication.listItem
                    .get(PerApplication.launchAppsNum.get(PerApplication.appCount - 1)).getAppName();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.e(LoggerService.TAG, " I can't sleep!!!");
                e.printStackTrace();
            }
            Toast.makeText(this, appName + " is launch, Latency: " +
                    PerApplication.latency + " ms", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoggerService.this, TestActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        if ((level >= TRIM_MEMORY_RUNNING_MODERATE)
                && (level != TRIM_MEMORY_UI_HIDDEN)) {
            String trim = "Unknown TRIM_MEMORY Code";
            try {
                if (level == TRIM_MEMORY_RUNNING_MODERATE) {
                    trim = "TRIM_MEMORY_RUNNING_MODERATE";
                    // TODO: 17-9-6
                }
                if (level == TRIM_MEMORY_RUNNING_LOW) {
                    trim = "TRIM_MEMORY_RUNNING_LOW";
                }
                if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
                    trim = "TRIM_MEMORY_RUNNING_CRITICAL";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
