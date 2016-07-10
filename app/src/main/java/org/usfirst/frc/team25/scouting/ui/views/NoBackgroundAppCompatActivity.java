package org.usfirst.frc.team25.scouting.ui.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.usfirst.frc.team25.scouting.R;

/**
 * Default activity that all activities extend from, removes splash screen background
 */
public class NoBackgroundAppCompatActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoLauncher);
        super.onCreate(savedInstanceState);
    }
}
