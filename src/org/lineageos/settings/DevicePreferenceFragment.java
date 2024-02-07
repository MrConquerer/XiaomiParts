package org.lineageos.settings.device;

import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;

import org.lineageos.settings.utils.RefreshRateUtils;

/** This class displays the device-specific preferences related to refresh rate. */
public class DevicePreferenceFragment extends PreferenceFragment {
    private static final String keyMinRefreshRate = "pref_min_refresh_rate";

    private ListPreference minRefreshRatePreference;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.device_prefs);
        minRefreshRatePreference = (ListPreference) findPreference(keyMinRefreshRate);
        minRefreshRatePreference.setOnPreferenceChangeListener(refreshRateChangeListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        minRefreshRatePreference.setValue(Integer.toString(RefreshRateUtils.getRefreshRate(getActivity())));
        minRefreshRatePreference.setSummary(minRefreshRatePreference.getEntry());
    }

    private final Preference.OnPreferenceChangeListener refreshRateChangeListener =
            new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    final String key = preference.getKey();

                    if (keyMinRefreshRate.equals(key)) {
                        RefreshRateUtils.setRefreshRate(getActivity(), Integer.valueOf((String) value));
                        RefreshRateUtils.setFPS(Integer.valueOf((String) value));
                        int minRefreshRateIndex = minRefreshRatePreference.findIndexOfValue((String) value);
                        preference.setSummary(minRefreshRatePreference.getEntries()[minRefreshRateIndex]);
                    }
                    return true;
                }
            };
}
