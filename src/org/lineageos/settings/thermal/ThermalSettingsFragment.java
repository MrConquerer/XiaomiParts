package org.lineageos.settings.thermal;

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

public class ThermalSettingsFragment extends PreferenceFragmentCompat implements
        OnPreferenceChangeListener {

    private SwitchPreference mThermalProfile;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.thermal_settings, rootKey);
        final ActionBar actionBar = (ActionBar) getActivity().findViewById(R.id.app_bar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mThermalProfile = (SwitchPreference) findPreference("thermal_profile");
        mThermalProfile.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mThermalProfile) {
            ThermalUtils.setThermalProfile((Boolean) newValue);
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
