package com.tct.perfstress.service;

import android.app.Service;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tct.perfstress.PerApplication;

public class LoggerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        String localLevel;
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            PerApplication.endTime = System.currentTimeMillis();
        }
    }
}
