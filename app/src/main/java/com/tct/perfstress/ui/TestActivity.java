package com.tct.perfstress.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.tct.perfstress.PerApplication;
import com.tct.perfstress.R;

public class TestActivity extends AppCompatActivity {
    public static final String TAG = "TestActivity";
    private String appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        try {
            appName = PerApplication.listItem.get(PerApplication.launchAppsNum.get(PerApplication.appCount - 1)).getAppName();
        } catch (NullPointerException e) {
            Log.e(TestActivity.TAG, " listItems haven't acquire");
        }

        TextView nextApp = (TextView) findViewById(R.id.tv);

        if (PerApplication.appCount < PerApplication.appSelected) {
            nextApp.setText("Launching " + appName + " next...\n\n" + PerApplication.appCount + " of "
                    + PerApplication.appSelected);
        } else {
            nextApp.setText("Launching Result screen...");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
