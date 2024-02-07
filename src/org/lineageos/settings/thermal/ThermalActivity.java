package org.lineageos.settings.thermal;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class ThermalActivity extends AppCompatActivity {

    private static final String TAG_THERMAL = "thermal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermal); 
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ThermalSettingsFragment(), TAG_THERMAL).commit();
    }
}
