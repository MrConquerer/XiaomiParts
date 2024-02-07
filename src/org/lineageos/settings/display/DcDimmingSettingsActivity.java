package org.lineageos.settings.display;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class DcDimmingSettingsActivity extends AppCompatActivity {

    private static final String TAG_DCDIMMING = "dcdimming";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcdimming);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DcDimmingSettingsFragment(), TAG_DCDIMMING).commit();
    }
}
