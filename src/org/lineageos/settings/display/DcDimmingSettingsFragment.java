package org.lineageos.settings.display;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import org.lineageos.settings.R;
import org.lineageos.settings.utils.FileUtils;

public class DcDimmingSettingsFragment extends PreferenceFragmentCompat implements
        OnPreferenceChangeListener {

    private SwitchPreference mDcDimmingPreference;
    private static final String DC_DIMMING_ENABLE_KEY = "dc_dimming_enable";
    private static final String DC_DIMMING_NODE = "/sys/devices/platform/soc/soc:qcom,dsi-display/msm_fb_ea_enable";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.dcdimming_settings, rootKey);
        final ActionBar actionBar = (ActionBar) getActivity().findViewById(R.id.app_bar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDcDimmingPreference = (SwitchPreference) findPreference(DC_DIMMING_ENABLE_KEY);
        if (FileUtils.fileExists(DC_DIMMING_NODE)) {
            mDcDimmingPreference.setEnabled(true);
            mDcDimmingPreference.setOnPreferenceChangeListener(this);
        } else {
            mDcDimmingPreference.setSummary(R.string.dc_dimming_enable_summary_not_supported);
            mDcDimmingPreference.setEnabled(false);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (DC_DIMMING_ENABLE_KEY.equals(preference.getKey())) {
            FileUtils.writeLine(DC_DIMMING_NODE, (Boolean) newValue ? "1":"0");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return false;
    }

}
