package org.lineageos.settings.thermal;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import java.util.List;

public final class ThermalUtils {

    private static final String TAG = "ThermalUtils";
    private static final boolean DEBUG = false;

    private static final String THERMAL_CONTROL = "/sys/devices/virtual/thermal/thermal_message/sconfig";
    private static final String THERMAL_DEFAULT = "0";
    private static final String THERMAL_GAME = "10";

    private static IActivityTaskManager mIatm;

    public static void setThermalProfile(boolean isGameMode) {
        if (DEBUG) Log.d(TAG, "Setting profile: " + isGameMode);
        String profile = isGameMode ? THERMAL_GAME : THERMAL_DEFAULT;
        FileUtils.writeLine(THERMAL_CONTROL, profile);
    }

    public static void setDefaultThermalProfile() {
        if (DEBUG) Log.d(TAG, "Setting default profile");
        FileUtils.writeLine(THERMAL_CONTROL, THERMAL_DEFAULT);
    }

    public static void setThermalProfile(Context context) {
        if (mIatm == null) {
            mIatm = ActivityTaskManager.getService();
        }
        try {
            // Get the list of tasks for the current user
            List<ActivityManager.RunningTaskInfo> tasks = mIatm.getTasksForUser(UserHandle.myUserId());
            if (tasks != null && !tasks.isEmpty()) {
                // Get the foreground task
                ActivityManager.RunningTaskInfo foregroundTask = tasks.get(0);
                if (foregroundTask != null) {
                    // Get the name of the top activity in the foreground task
                    String foregroundApp = foregroundTask.topActivityName;
                    if (DEBUG) Log.d(TAG, "Foreground app: " + foregroundApp);
                    // Check if the foreground app is a game
                    boolean isGameMode = context.getPackageManager().isGame(foregroundApp);
                    // Set the thermal profile accordingly
                    setThermalProfile(isGameMode);
                }
            }
        } catch (RemoteException e) {
            // Do nothing
        }
    }
}
