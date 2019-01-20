package org.usfirst.frc.team25.scouting.data;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.Release;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sng on 2/18/2018.
 * Checks for scouting app updates via GitHub releases
 */


public class UpdateChecker extends AsyncTask<String, Integer, Boolean> {

    private final Context context;

    private Release release;

    public UpdateChecker(Context c) {
        this.context = c;
    }

    @Override
    protected java.lang.Boolean doInBackground(java.lang.String... arg0) {

        try {
            URL url = new URL("https://api.github.com/repos/"
                    + context.getString(R.string.repository_name) + "/releases/latest");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            String respStr = response.toString();


            this.release = new Gson().fromJson(respStr, Release.class);


            if (!release.getTagName().equals(context.getString(R.string.version_number))) {
                return true;
            } else {
                new File(Environment.getExternalStorageDirectory() + "/Download/" +
                        release.getAssets()[0].getName()).delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Returns if download was successful or not
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if (result) {
            new AlertDialog.Builder(context)
                    .setTitle("App update available")
                    .setMessage("A new version of the app is available. Would you like to update " +
                            "it now?")
                    .setPositiveButton("Get update", (dialog, which) ->
                            new AppDownloader(context, release.getAssets()[0]).execute())
                    .setNegativeButton("Remind me later", (dialog, which) ->
                            Toast.makeText(context, "Reminder set for next app launch",
                                    Toast.LENGTH_SHORT).show())
                    .create()
                    .show();
        }
    }


}


class AppDownloader extends AsyncTask<String, Integer, Boolean> {
    private final Context context;
    private final Release.Asset asset;
    private ProgressDialog progressBar;

    AppDownloader(Context c, Release.Asset asset) {
        this.context = c;
        this.asset = asset;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);

        progressBar.setMessage("Downloading...");

        progressBar.setIndeterminate(true);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();
    }

    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);

        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.setProgress(progress[0]);
        String msg;
        if (progress[0] > 99) {

            msg = "Finishing... ";

        } else {

            msg = "Downloading... " + progress[0] + "%";
        }
        progressBar.setMessage(msg);

    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            URL url = new URL(asset.getBrowserDownloadUrl());

            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");

            c.connect();


            String downloadPath = Environment.getExternalStorageDirectory() + "/Download/";
            File file = new File(downloadPath);
            file.mkdirs();

            String fileName = asset.getName();

            File outputFile = new File(file, fileName);

            if (outputFile.exists()) {
                outputFile.delete();
            }

            FileOutputStream fos = new FileOutputStream(outputFile);

            if (c.getResponseCode() >= 400 && c.getResponseCode() <= 499) {
                Log.i("tag", " " + c.getResponseCode());
                Log.i("tag", " " + c.getURL());
                throw new Exception("Bad authorization");
            }
            InputStream is = c.getInputStream();


            byte[] buffer = new byte[1024];

            int len1;
            int downloaded = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
                downloaded += len1;
            }
            fos.close();
            is.close();

            openNewVersion(downloadPath, fileName);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openNewVersion(String location, String fileName) {
        Uri uri = Uri.parse("file://" + location + fileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");

        context.startActivity(intent);
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressBar.dismiss();
    }
}










