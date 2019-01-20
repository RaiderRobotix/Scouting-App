package org.usfirst.frc.team25.scouting.ui.views;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.usfirst.frc.team25.scouting.R;

/**
 * Default activity that all activities extend from, removes splash screen background; forces
 * portrait mode
 */
public class NoBackgroundPortraitAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoLauncher_Blue);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
    }
}
