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
        String localLevel;
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            PerApplication.endTime = System.currentTimeMillis();
            PerApplication.latency = (int) (PerApplication.endTime - PerApplication.startTime);
            Toast.makeText(this, "第" + PerApplication.appCount + "个APP is launch, Latency: " +
                    PerApplication.latency, Toast.LENGTH_SHORT).show();
        }

        try {
            Thread.sleep(500L);
            Intent intent = new Intent(LoggerService.this, TestActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            if ((level >= ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE)
                    && (level != ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)) {
                localLevel = "Unknown TRIM_MEMORY Code";
                if (level == ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE) {
                    localLevel = "TRIM_MEMORY_RUNNING_MODERATE";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
