package org.lineageos.settings.display;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import androidx.preference.PreferenceManager;

import org.lineageos.settings.R;
import org.lineageos.settings.utils.FileUtils;

public class DcDimmingTileService extends TileService {

    private static final String DC_DIMMING_ENABLE_KEY = "dc_dimming_enable";
    private static final String DC_DIMMING_NODE = "/sys/devices/platform/soc/soc:qcom,dsi-display/msm_fb_ea_enable";

    private void updateUI(boolean enabled) {
        final Tile tile = getQsTile();
        tile.setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();
    }

    @Override
    @TargetApi(29)
    public void onStartListening() {
        super.onStartListening();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        updateUI(sharedPrefs.getBoolean(DC_DIMMING_ENABLE_KEY, false));
    }

    @Override
    @TargetApi(29)
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean enabled = !(sharedPrefs.getBoolean(DC_DIMMING_ENABLE_KEY, false));
        FileUtils.writeLine(DC_DIMMING_NODE, enabled ? "1" : "0");
        sharedPrefs.edit().putBoolean(DC_DIMMING_ENABLE_KEY, enabled).apply();
        updateUI(enabled);
    }
}
