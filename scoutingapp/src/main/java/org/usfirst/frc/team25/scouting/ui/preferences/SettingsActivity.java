package org.usfirst.frc.team25.scouting.ui.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.ui.views.AppCompatPreferenceActivity;

/**
 * Activity that holds the fragment for settings/preferences
 */

public class SettingsActivity extends AppCompatPreferenceActivity implements OnSharedPreferenceChangeListener {

    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        settingsFragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, settingsFragment)
                .commit();

        setTheme(R.style.AppTheme_NoLauncher_Blue);

        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        preferences.registerOnSharedPreferenceChangeListener(this);
        setTitle("Settings");
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("prefs", "Settings successfully changed");

        settingsFragment.updateSummary();
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingsFragment.class.getName().equals(fragmentName);
    }

}
