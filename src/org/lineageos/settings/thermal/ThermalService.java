package org.lineageos.settings.thermal;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskStackListener;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

public class ThermalService extends Service {

    private static final String TAG = "ThermalService";
    private static final boolean DEBUG = false;

    private String mPreviousApp;
    private ThermalUtils mThermalUtils;

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPreviousApp = "";
            mThermalUtils.setDefaultThermalProfile();
        }
    };

    @Override
    public void onCreate() {
        if (DEBUG) Log.d(TAG, "Creating service");
        try {
            ActivityTaskManager.getInstance().registerTaskStackListener(mTaskListener);
        } catch (RemoteException e) {
            // Do nothing
        }
        mThermalUtils = new ThermalUtils(this);
        registerReceiver();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) Log.d(TAG, "Starting service");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        this.registerReceiver(mIntentReceiver, filter);
    }

    private final TaskStackListener mTaskListener = new TaskStackListener() {
        @Override
        public void onTaskStackChanged() {
            try {
                // Get the list of running tasks
                List<ActivityManager.RunningTaskInfo> tasks = ActivityManager.getRunningTasks(1);
                if (tasks != null && !tasks.isEmpty()) {
                    // Get the foreground task
                    ActivityManager.RunningTaskInfo foregroundTask = tasks.get(0);
                    if (foregroundTask != null && foregroundTask.topActivity != null) {
                        ComponentName taskComponentName = foregroundTask.topActivity;
                        String foregroundApp = taskComponentName.getPackageName();
                        if (!foregroundApp.equals(mPreviousApp)) {
                            mThermalUtils.setThermalProfile(foregroundApp);
                            mPreviousApp = foregroundApp;
                        }
                    }
                }
            } catch (Exception e) {}
        }
    };
}
