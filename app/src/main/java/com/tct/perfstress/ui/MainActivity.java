package com.tct.perfstress.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.tct.perfstress.Adapter.ListviewAdapter;
import com.tct.perfstress.PerApplication;
import com.tct.perfstress.R;
import com.tct.perfstress.Utility;
import com.tct.perfstress.mode.ListItem;
import com.tct.perfstress.service.LauncherService;
import com.tct.perfstress.service.LoggerService;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    //    private UsageStats mUsageStats;
    private ListView listView;
    private Context mContext;
    private ListviewAdapter adapter;
    private Button launch_btn;
    private Button install_btn;

    private ArrayList<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getBaseContext();
        if (!Utility.isUsageStatsEnabled(mContext)) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
        setContentView(R.layout.activity_main);
        PackageManager pm = this.getPackageManager();
//        PerApplication.isSelected = new HashMap<>();
        listView = (ListView) findViewById(R.id.list_view);

        initListItem(pm);
        adapter = new ListviewAdapter(PerApplication.mContext, PerApplication.isSelected);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        install_btn = (Button) findViewById(R.id.btn_install);
        install_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InstallActivity.class);
                startActivity(intent);
            }
        });
        launch_btn = (Button) findViewById(R.id.btn_launch);
        launch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PerApplication.startLock) {
                    PerApplication.startLock = true;
                    PerApplication.resultDeployed = false;
                    Intent launchIntent = new Intent();
                    launchIntent.setAction(LauncherService.LAUNCH_APP);
                    launchIntent.setPackage("com.tct.perfstress");
                    startService(launchIntent);
                    Intent loggerIntent = new Intent();
                    loggerIntent.setAction(LoggerService.STARTLOG);
                    loggerIntent.setPackage("com.tct.perfstress");
                    startService(loggerIntent);
                    Toast.makeText(MainActivity.this,"开始启动选中的App",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initListItem(PackageManager pm) {

        new AsyncTask<PackageManager, Void, ArrayList<ListItem>>() {
            @Override
            protected ArrayList<ListItem> doInBackground(PackageManager... packageManagers) {
                listItems = Utility.getAppInfo(packageManagers[0]);
                PerApplication.listItem = listItems;
                for (int i = 0; i < listItems.size(); i++) {
                    Log.d(MainActivity.TAG, "appName = " + listItems.get(i).getAppName());
                    if (listItems.get(i).getAppPkgName().equals("com.tct.perfstress")) {
                        Log.d(MainActivity.TAG, "size = " + listItems.size());
                        listItems.remove(i);
                        Log.d(MainActivity.TAG, "filter size = " + listItems.size());
                        return listItems;
                    }
                }
                return listItems;
            }

            @Override
            protected void onPostExecute(ArrayList<ListItem> listItems) {
                super.onPostExecute(listItems);
                adapter.updateList(listItems);
                adapter.notifyDataSetChanged();
                Log.d(TAG, "call getAppInfo() the result = " + listItems.size());
            }
        }.execute(pm);
    }
}
