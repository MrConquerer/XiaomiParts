package org.lineageos.settings.device;

import android.os.Bundle;
import androidx.preference.PreferenceActivity;
import androidx.preference.PreferenceFragmentCompat;

/** This class displays the device-specific preferences. */
public class DevicePreferenceActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new DevicePreferenceFragment())
                .commit();
    }

    public static class DevicePreferenceFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.device_preferences, rootKey);
        }
    }
}
