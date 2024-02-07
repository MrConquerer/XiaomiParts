package org.lineageos.settings.refresh;

import android.content.Context;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import org.lineageos.settings.R;
import org.lineageos.settings.utils.RefreshRateUtils;

import java.util.Arrays;

/** This class provides a quick settings tile to toggle the refresh rate. */
public class RefreshRateTileService extends TileService {

    private Context context;
    private Tile tile;

    private String[] refreshRates;
    private String[] refreshRateValues;
    private int currentRefreshRate;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        refreshRates = context.getResources().getStringArray(R.array.device_refresh_rates);
        refreshRateValues = context.getResources().getStringArray(R.array.device_refresh_rates_val);
    }

    private void updateCurrentRefreshRate() {
        currentRefreshRate = Arrays.asList(refreshRateValues).indexOf(Integer.toString(RefreshRateUtils.getRefreshRate(context)));
    }

    private void updateTileState() {
        if (currentRefreshRate == 1) {
            tile.setState(Tile.STATE_ACTIVE);
        } else {
            tile.setState(Tile.STATE_INACTIVE);
        }
        tile.setLabel(refreshRates[currentRefreshRate]);
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        tile = getQsTile();
        updateCurrentRefreshRate();
        updateTileState();
    }

    private int getRefreshRateVal() {
        return Integer.valueOf(refreshRateValues[currentRefreshRate]);
    }

    @Override
    public void onClick() {
        super.onClick();
        updateCurrentRefreshRate();
        if (currentRefreshRate == 1) {
            currentRefreshRate = 0;
        } else {
            currentRefreshRate = 1;
        }
        RefreshRateUtils.setRefreshRate(context, getRefreshRateVal());
        RefreshRateUtils.setFPS(getRefreshRateVal());
        updateTileState();
    }
}
