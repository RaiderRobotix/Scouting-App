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

import org.usfirst.frc.team25.scouting.Constants;
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
 */


public class UpdateChecker extends AsyncTask<String, Integer, Boolean> {

    private final Context context;

    private Release release;

    public UpdateChecker(Context c) {
        this.context = c;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if (result)
            new AlertDialog.Builder(context)
                    .setTitle("App update available")
                    .setMessage("A new version of the app is available. Would you like to update it now?")
                    .setPositiveButton("Get update", (dialog, which) -> new AppDownloader(context, release.getAssets()[0]).execute())
                    .setNegativeButton("Remind me later", (dialog, which) -> Toast.makeText(context, "Reminder set for next app launch", Toast.LENGTH_SHORT).show())
                    .create()
                    .show();
    }

    @Override
    protected java.lang.Boolean doInBackground(java.lang.String... arg0) {

        try {
            URL url = new URL("https://api.github.com/repos/" + Constants.REPOSITORY_NAME + "/releases/latest");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

            String respStr = response.toString();


            this.release = new Gson().fromJson(respStr, Release.class);


            if (!release.getTagName().equals("v" + Constants.VERSION_NUMBER))
                return true;
            else
                new File(Environment.getExternalStorageDirectory() + "/Download/" + release.getAssets()[0].getName()).delete();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}


class AppDownloader extends AsyncTask<String, Integer, Boolean> {
    private final Context context;
    private final Release.Asset asset;
    private ProgressDialog bar;
    private String location;

    AppDownloader(Context c, Release.Asset asset) {
        this.context = c;
        this.asset = asset;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bar = new ProgressDialog(context);
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
        String msg;
        if (progress[0] > 99) {

            msg = "Finishing... ";

        } else {

            msg = "Downloading... " + progress[0] + "%";
        }
        bar.setMessage(msg);

    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            URL url = new URL(asset.getBrowserDownloadUrl());

            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");

            c.connect();


            java.lang.String PATH = Environment.getExternalStorageDirectory() + "/Download/";
            File file = new File(PATH);
            file.mkdirs();

            java.lang.String fileName = asset.getName();

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

            int total_size = 1431692;//size of apk

            byte[] buffer = new byte[1024];

            int len1;
            int per = 0;
            int downloaded = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
                downloaded += len1;
            }
            fos.close();
            is.close();

            openNewVersion(PATH, fileName);
            this.location = PATH + fileName;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openNewVersion(java.lang.String location, java.lang.String fileName) {
        Uri uri = Uri.parse("file://" + location + fileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");

        context.startActivity(intent);


    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        bar.dismiss();
        //new File(location).delete();
    }
}










