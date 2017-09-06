package com.tct.perfstress.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.tct.perfstress.Adapter.ResultListAdapter;
import com.tct.perfstress.PerApplication;
import com.tct.perfstress.R;

public class ResultActivity extends AppCompatActivity {
    private ListView mListView;
    private ResultListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PerApplication.startLock = false;
        stopService(PerApplication.loggerServiceIntent);
        PerApplication.timerLaunch.cancel();
        stopService(PerApplication.launchServiceIntent);
        setContentView(R.layout.activity_result);

        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new ResultListAdapter(PerApplication.mContext);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PerApplication.appCount = 0;
        PerApplication.launchAppsNum.clear();
        PerApplication.chosenList.clear();
        PerApplication.latencies.clear();
    }
}
