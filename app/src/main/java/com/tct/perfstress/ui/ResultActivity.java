package com.tct.perfstress.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tct.perfstress.PerApplication;
import com.tct.perfstress.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PerApplication.startLock = false;
        stopService(PerApplication.loggerServiceIntent);
        PerApplication.timerLaunch.cancel();
        stopService(PerApplication.launchServiceIntent);
        setContentView(R.layout.activity_result);


    }

    @Override
    protected void onPause() {
        super.onPause();
        PerApplication.appCount = 0;
        PerApplication.launchAppsNum.clear();
        finish();
    }
}
