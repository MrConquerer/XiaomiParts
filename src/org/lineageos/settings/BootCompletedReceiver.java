package org.lineageos.settings.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemProperties;
import androidx.preference.PreferenceManager;

import org.lineageos.settings.dirac.DiracUtils;
import org.lineageos.settings.utils.RefreshRateUtils;
import org.lineageos.settings.utils.FileUtils;
import org.lineageos.settings.thermal.ThermalUtils;

/** This class handles the actions to be performed when the device boots up. */
public class BootCompletedReceiver extends BroadcastReceiver {

    private static final String dcDimmingEnableKey = "dc_dimming_enable";
    private static final String dcDimmingNode = "/sys/devices/platform/soc/soc:qcom,dsi-display/msm_fb_ea_enable";

    @Override
    public void onReceive(final Context context, Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        // Set the refresh rate according to the user preference
        RefreshRateUtils.setFPS(RefreshRateUtils.getRefreshRate(context));

        // Initialize the Dirac sound enhancer
        DiracUtils.initialize(context);

        // Enable or disable DC dimming feature
        boolean dcDimmingEnabled = sharedPreferences.getBoolean(dcDimmingEnableKey, false);
        FileUtils.writeLine(dcDimmingNode, dcDimmingEnabled ? "1" : "0");

        // Start the thermal profile service
        ThermalUtils.startService(context);
    }

}
