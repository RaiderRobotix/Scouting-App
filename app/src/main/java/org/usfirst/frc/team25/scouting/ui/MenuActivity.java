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
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.usfirst.frc.team25.scouting.Constants;
import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.ui.dataentry.AddEntryActivity;
import org.usfirst.frc.team25.scouting.ui.preferences.SettingsActivity;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/** The main activity of the application
 *
 */
public class MenuActivity extends NoBackgroundPortraitAppCompatActivity {

    private ImageButton addEntry, share, rules, settings;
    private TextView status;
    ProgressDialog bar;



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
        checkUpdate();
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

    public void checkUpdate(){
       try {

           GitHub gh = GitHub.connect("team25scoutingsystem@gmail.com", "password");

            GHRelease latestRelease = gh.getRepository(Constants.REPOSITORY_NAME).getLatestRelease();
            Toast.makeText(getApplicationContext(), latestRelease.getUrl().toString(), Toast.LENGTH_LONG);
            if(!latestRelease.getTagName().equals("v"+Constants.VERSION))
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("App update available")
                        .setMessage("A new version of the app is available. Would you like to update it now?")
                        .setPositiveButton("Get update", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new DownloadNewVersion().execute();
                            }
                        })
                        .setNegativeButton("Remind me later", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    class DownloadNewVersion extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bar = new ProgressDialog(MenuActivity.this);
            bar.setCancelable(false);

            bar.setMessage("Downloading...");

            bar.setIndeterminate(true);
            bar.setCanceledOnTouchOutside(false);
            bar.show();

        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            bar.setIndeterminate(false);
            bar.setMax(100);
            bar.setProgress(progress[0]);
            String msg = "";
            if(progress[0]>99){

                msg="Finishing... ";

            }else {

                msg="Downloading... "+progress[0]+"%";
            }
            bar.setMessage(msg);

        }
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            bar.dismiss();

            if(result){

                Toast.makeText(getApplicationContext(),"Update Done",
                        Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(getApplicationContext(),"Error: Try Again",
                        Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean flag = false;

            try {
                GitHub gh = GitHub.connect();
                GHRelease latestRelease = gh.getRepository(Constants.REPOSITORY_NAME).getLatestRelease();
                URL url = latestRelease.getAssets().get(0).getUrl();

                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();


                String PATH = Environment.getExternalStorageDirectory()+"/Download/";
                File file = new File(PATH);
                file.mkdirs();

                String fileName = latestRelease.getAssets().get(0).getName();

                File outputFile = new File(file,fileName);

                if(outputFile.exists()){
                    outputFile.delete();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();

                int total_size = 1431692;//size of apk

                byte[] buffer = new byte[1024];

                int len1 = 0;
                int per = 0;
                int downloaded=0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    downloaded +=len1;
                    per = (int) (downloaded * 100 / total_size);
                }
                fos.close();
                is.close();

                openNewVersion(PATH, fileName);

                flag = true;
            } catch (Exception e) {
                flag = false;
            }
            return flag;

        }

    }

    void openNewVersion(String location, String fileName) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(location + fileName)),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

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
