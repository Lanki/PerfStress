package com.tct.perfstress;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.tct.perfstress.mode.ListItem;
import com.tct.perfstress.ui.MainActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lqchen on 17-8-23.
 */

public class Utility {
    public static final String TAG = "Utility";

    public static String getActivityName(String pkgName_args, Context mContext) {
        String activityName = null;
        PackageManager pm = mContext.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm
                .queryIntentActivities(intent, 0);
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.packageName;
            activityName = resolveInfo.activityInfo.name;
//            Log.d(Utility.TAG, "pk = " + pkgName + "\n" +
//                                "cls = " + activityName + "\n" +
//                                "local = " + pkgName_args);
            if (pkgName.equals(pkgName_args)) {
//                appComponentName = new ComponentName(pkgName, activityName);
                Log.d(Utility.TAG, "activityName = " + activityName);
                return activityName;
            }

        }
        if (activityName == null) {
            throw new NullPointerException("没找到apps的MainActivityName");
        }

        return activityName;
    }

    public static ArrayList<ListItem> getAppInfo(PackageManager pm) {
        ArrayList<ListItem> mListItems = new ArrayList<>();
//        pm = context.getPackageManager();
        List<ApplicationInfo> listApplications = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listApplications, new ApplicationInfo.DisplayNameComparator(pm));
        for (ApplicationInfo app :
                listApplications) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                mListItems.add(queryAppInfo(app, pm));
            } else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                mListItems.add(queryAppInfo(app, pm));
            }
        }


        return mListItems;
    }

    private static ListItem queryAppInfo(ApplicationInfo app, PackageManager pm) {
//        Log.d(MainActivity.TAG, "appName = " + app.loadLabel(pm));
        ListItem listItem = new ListItem();
        listItem.setAppIcon(app.loadIcon(pm));
        listItem.setAppName((String) app.loadLabel(pm));
        listItem.setAppPkgName(app.packageName);
        return listItem;
    }


    public static Long getLaunchTime(ComponentName appName) {
        try {
            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
            Method getService = ServiceManager.getMethod("getService", String.class);
            Object oRemoteService = getService.invoke(null, "usagestats");
            Class<?> cStub = Class.forName("com.android.internal.app.IUsageStats$Stub");
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            Object oIUsageStats = asInterface.invoke(null, oRemoteService);
            Method getPkgUsageStats = oIUsageStats.getClass().getMethod("getPkgUsageStats", ComponentName.class);
            Object appStats = getPkgUsageStats.invoke(oIUsageStats, appName);

            Class<?> PkgUsageStats = Class.forName("com.android.internal.os.PkgUsageStats");
            Long appTime = PkgUsageStats.getDeclaredField("usageTime").getLong(appStats);

            return appTime;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    static ArrayList<String> queryPkgName(Context context) {
        PackageManager pm = context.getPackageManager();
        ArrayList<String> pkgNames = new ArrayList<>();
        List<ApplicationInfo> listApplications = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listApplications, new ApplicationInfo.DisplayNameComparator(pm));

        for (ApplicationInfo app :
                listApplications) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                pkgNames.add(app.packageName);
            } else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                pkgNames.add(app.packageName);
            }
        }
        return pkgNames;
    }

    @TargetApi(21)
    public static boolean isUsageStatsEnabled(Context mContext) {
        boolean bool = false;
        try {
            ApplicationInfo localApplicationInfo =  mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(),0);
            int i = ((AppOpsManager)mContext.getSystemService(Context.APP_OPS_SERVICE)).checkOpNoThrow("android:get_usage_stats", localApplicationInfo.uid, localApplicationInfo.packageName);

            if (i == 0) {
                bool = true;
            }
            return bool;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
