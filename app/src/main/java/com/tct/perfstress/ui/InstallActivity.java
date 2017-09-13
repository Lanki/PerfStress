package com.tct.perfstress.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.tct.perfstress.R;
import com.tct.perfstress.Utility;

import java.io.File;
import java.io.FileNotFoundException;

public class InstallActivity extends AppCompatActivity {
    public static final String TAG = "InstallActivity";

    private TextView textView;

    private String apkPath;
    private String apkName;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);

        textView = (TextView) findViewById(R.id.tv);

        initUrl();
//        installApk(url);
        textView.setText(apkPath);


    }

    public void initUrl() {
        apkPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "Download"
                + File.separator + "application";
        try {
            apkName = Utility.getFileName(apkPath).get(0);
            Log.d(TAG, "initUrl-- apkName = " + apkName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        url = apkPath + File.separator + apkName;
    }


    public void installApk(String url) {
        Log.d(TAG, "installApk-- url = " + url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(url)),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


}
