package org.usfirst.frc.team25.scouting.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.UpdateChecker;
import org.usfirst.frc.team25.scouting.ui.dataentry.AddEntryActivity;
import org.usfirst.frc.team25.scouting.ui.preferences.SettingsActivity;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity;

import java.io.File;

/**
 * The main activity of the application.
 * Responsible for displaying the info bar, game logo, and linking to the four main submenus
 */
public class MenuActivity extends NoBackgroundPortraitAppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private TextView status;

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    private static void verifyStoragePermissions(Activity activity) {
        // Check if the app havs write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // App doesn't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    //Executes when application is first launched
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Manages permissions
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //Phone layout has fixed scaling of text and buttons, no Raider Robotix Logo
        if (!isTablet(getBaseContext()))
            setContentView(R.layout.activity_menu_phone);

        else setContentView(R.layout.activity_menu);


        ImageButton addEntry = findViewById(R.id.add_entry_button);
        ImageButton share = findViewById(R.id.export_data_button);
        ImageButton rules = findViewById(R.id.rules_button);
        ImageButton settings = findViewById(R.id.settings_button);
        status = findViewById(R.id.current_info_label);

        addEntry.setOnClickListener(view -> {

            //Good model on how to start a new activity
            Intent i = new Intent(MenuActivity.this, AddEntryActivity.class);
            startActivity(i);
        });

        share.setOnClickListener(view -> {
            try {
                File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                        FileManager.DIRECTORY_DATA);
                if (!directory.exists())
                    directory.mkdir();
                File file = new File(directory, FileManager.getDataFilename(getBaseContext()));
                if (file.length() == 0)
                    Toast.makeText(getBaseContext(), "Scouting data does not exist", Toast.LENGTH_SHORT).show();
                else {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    shareIntent.setType("text/plain");
                    Intent chooser = Intent.createChooser(shareIntent, "Export scouting data");
                    startActivity(chooser);
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
        });

        rules.setOnClickListener(view -> {
            Intent i = new Intent(MenuActivity.this, RulesActivity.class);
            startActivity(i);

        });

        settings.setOnClickListener(view -> {
            Intent i = new Intent(MenuActivity.this, SettingsActivity.class);
            startActivity(i);
        });

        // Checks for updates from GitHub
        new UpdateChecker(MenuActivity.this).execute();
        verifyStoragePermissions(this);

    }

    private void updateStatus() {
        Settings set = Settings.newInstance(getBaseContext());

        String info = set.getScoutName() + " - " +
                set.getScoutPos() + " - Match " + set.getMatchType() +
                set.getMatchNum();
        if (info.contains("DEFAULT")) //App settings not yet changed
            info = "";
        status.setText(info);

    }

    /**
     * Sets the title and info bar upon resuming the activity
     */
    @Override
    public void onResume() {
        updateStatus();
        setTitle("Raider Robotix Scouting");
        super.onResume();
    }

    // Back button disabled to prevent accidental pressing
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back button currently disabled", Toast.LENGTH_LONG).show();
    }

    /**
     * Determines if the app is running on a tablet or a phone
     *
     * @param context Context of the current activity
     * @return True if the device is a tablet, false otherwise
     */
    private boolean isTablet(Context context) {
        //Note the use of the bitwise AND, rather than the logical AND.=
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

}