package org.usfirst.frc.team25.scouting.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;

import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.usfirst.frc.team25.scouting.Constants;
import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.UpdateChecker;
import org.usfirst.frc.team25.scouting.data.models.Release;
import org.usfirst.frc.team25.scouting.ui.dataentry.AddEntryActivity;
import org.usfirst.frc.team25.scouting.ui.preferences.SettingsActivity;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/** The main activity of the application
 *
 */
public class MenuActivity extends NoBackgroundPortraitAppCompatActivity {

    private ImageButton addEntry, share, rules, settings;
    private TextView status;




    //Executes when application is first launched
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //Phone layout has fixed scaling of text and buttons
       if (!isTablet(getBaseContext()))
            setContentView(R.layout.activity_menu_phone);


        else setContentView(R.layout.activity_menu);


        addEntry = (ImageButton) findViewById(R.id.menu1_button);
        share = (ImageButton) findViewById(R.id.menu2_button);
        rules = (ImageButton) findViewById(R.id.menu3_button);
        settings = (ImageButton) findViewById(R.id.menu4_button);
        status = (TextView) findViewById(R.id.current_info_label);

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Good model on how to start a new activity
                Intent i = new Intent(MenuActivity.this, AddEntryActivity.class);
                startActivity(i);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                            FileManager.DIRECTORY_DATA);
                    if(!directory.exists())
                        directory.mkdir();
                    File file = new File(directory, FileManager.getDataFilename(getBaseContext()));
                    if(file.length()==0)
                        Toast.makeText(getBaseContext(), "Scouting data does not exist", Toast.LENGTH_SHORT).show();
                    else {
                        Intent share = new Intent();
                        share.setAction(Intent.ACTION_SEND);
                        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        share.setType("text/plain");
                        Intent chooser = Intent.createChooser(share, "Export scouting data");
                        startActivity(chooser);
                    }


                }catch(Exception e){
                    e.printStackTrace();

                }
            }
        });

        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, RulesActivity.class);
                startActivity(i);

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, SettingsActivity.class);

                startActivity(i);
            }
        });


        new UpdateChecker(MenuActivity.this).execute();
        verifyStoragePermissions(this);

    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }





    /**
     * Updates <code>TextView status</code> with scout name, match number, and position placed in preferences
     */
    void updateStatus() {
        Settings set = Settings.newInstance(getBaseContext());

        String info = set.getScoutName() + " - " +
                set.getScoutPos() + " - Match " + set.getMatchType() +
                set.getMatchNum();
        if(info.contains("DEFAULT"))
            info = "";
        status.setText(info);

    }

    /**
     * Method to update activity when it is resumed
     */
    @Override
    public void onResume() {
        Log.i("release", System.getProperty("user.home"));
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
     * Method that detects if the current device is a tablet based on predefined screen sizes
     * @param context - current context of the activity
     * @return isTablet - boolean value
     */
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }



}
