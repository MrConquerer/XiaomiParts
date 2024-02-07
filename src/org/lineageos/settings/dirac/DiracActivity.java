package org.lineageos.settings.dirac;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;

public class DiracActivity extends AppCompatActivity {

    private static final String TAG_DIRAC = "dirac";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dirac); 
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DiracSettingsFragment(), TAG_DIRAC).commit();
    }
}
