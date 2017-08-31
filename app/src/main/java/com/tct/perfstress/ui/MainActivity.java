package com.tct.perfstress.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tct.perfstress.Adapter.ListviewAdapter;
import com.tct.perfstress.PerApplication;
import com.tct.perfstress.R;
import com.tct.perfstress.Utility;
import com.tct.perfstress.mode.ListItem;
import com.tct.perfstress.service.LauncherService;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    //    private UsageStats mUsageStats;
    private ListView listView;
    private Context mContext;
    private ListviewAdapter adapter;
    private Button launch_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getBaseContext();
        if (!Utility.isUsageStatsEnabled(mContext)) {
            startActivity(new Intent("android.setting.USAGE_ACCESS_SETTING"));
        }
        setContentView(R.layout.activity_main);
        PackageManager pm = this.getPackageManager();
//        PerApplication.isSelected = new HashMap<>();
        listView = (ListView) findViewById(R.id.list_view);

        initListItem(pm);
        adapter = new ListviewAdapter(PerApplication.mContext, PerApplication.isSelected);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        launch_btn = (Button) findViewById(R.id.btn_launch);
        launch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PerApplication.startLock) {
                    PerApplication.startLock = true;
                    PerApplication.resultDeployed = false;
                    Intent intent = new Intent();
                    intent.setAction(LauncherService.LAUNCH_APP);
                    intent.setPackage("com.tct.perfstress");
                    MainActivity.this.startService(intent);
                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        PerApplication.endTime = System.currentTimeMillis();
    }

    private void initListItem(PackageManager pm) {

        new AsyncTask<PackageManager, Void, List<ListItem>>() {
            @Override
            protected List<ListItem> doInBackground(PackageManager... packageManagers) {
                List<ListItem> listItems = Utility.getAppInfo(packageManagers[0]);
                for (int i = 0; i < listItems.size(); i++) {
                    if (listItems.get(i).getAppPkgName().equals("com.tct.perfstress")) {
                        listItems.remove(i);
                    }
                }
                return listItems;
            }

            @Override
            protected void onPostExecute(List<ListItem> listItems) {
                super.onPostExecute(listItems);
                adapter.updateList(listItems);
                adapter.notifyDataSetChanged();
                Log.d(TAG, "call getAppInfo() the result = " + listItems.size());
            }
        }.execute(pm);
    }
}
