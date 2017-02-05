package org.usfirst.frc.team25.scouting.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.thebluealliance.DataDownloader;
import org.usfirst.frc.team25.scouting.data.thebluealliance.models.Match;
import org.usfirst.frc.team25.scouting.ui.dataentry.AddEntryActivity;
import org.usfirst.frc.team25.scouting.ui.preferences.SettingsActivity;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundAppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/** The main activity of the application
 *
 */
public class MenuActivity extends NoBackgroundAppCompatActivity {

    private ImageButton addEntry, share, rules, settings;
    private TextView status;



    //Executes when application is first launched
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Phone layout has fixed scaling of text and buttons
        if (!isTablet(getBaseContext()))
            setContentView(R.layout.activity_menu_phone);


        else setContentView(R.layout.activity_menu);


        addEntry = (ImageButton) findViewById(R.id.menu1_button);
        share = (ImageButton) findViewById(R.id.menu2_button);
        rules = (ImageButton) findViewById(R.id.menu3_button);
        settings = (ImageButton) findViewById(R.id.menu4_button);
        status = (TextView) findViewById(R.id.current_info_label);

        //Methods that are OnClickListeners for each icon
        entryListen();
        shareListen();
        rulesListen();
        settingsListen();

    }

    void entryListen() {
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Good model on how to start a new activity
                Intent i = new Intent(MenuActivity.this, AddEntryActivity.class);
                startActivity(i);
            }
        });
    }

    void shareListen() {
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                            FileManager.getDirectory());
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
    }

    void rulesListen() {
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, RulesActivity.class);
                startActivity(i);

            }
        });
    }

    void settingsListen() {
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, SettingsActivity.class);

                startActivity(i);
            }
        });
    }

    /**
     * Updates <code>TextView status</code> with scout name, match number, and position placed in preferences
     */
    void updateStatus() {
        Settings set = Settings.newInstance(getBaseContext());

        String info = set.getScoutName() + " - " +
                set.getScoutPos() + " - Match " + set.getMatchType() +
                set.getMatchNum();
        status.setText(info);

    }

    /**
     * Method to update activity when it is resumed
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
