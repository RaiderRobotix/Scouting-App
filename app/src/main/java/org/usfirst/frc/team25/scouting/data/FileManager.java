package org.usfirst.frc.team25.scouting.data;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
        import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sng on 6/29/2016.
 */
public class FileManager {

    public static final String DIRECTORY = "Raider Robotix Scouting";

    public static String getDirectory(){
        return DIRECTORY;
    }

    public static void backup(Context c){
        try{
            File sourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), getDirectory());
            File destDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), getDirectory()+" - Backup");
            MediaScannerConnection.scanFile(c, new String[] {destDir.toString()}, null, null);
            FileUtils.copyDirectory(sourceDir, destDir);
            Log.i("backup", "files written to internal storage for backup");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void deleteData(Context c){
        backup(c);
        try {
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), getDirectory());
            FileUtils.cleanDirectory(directory);
            Log.i("delete", "files deleted from external storage");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String getFilename(Context c){
        Settings set = Settings.newInstance(c);
        final String fileName = "Scouting - " + set.getScoutPos() + " - " +set.getCurrentEvent() + ".json";
        return fileName;
    }

    public static void saveData(ScoutEntry entry, Context c){
        Gson gson = new Gson();
        List<ScoutEntry> entries = new ArrayList<>();
        Type listEntries = new TypeToken<List<ScoutEntry>>(){}.getType();

        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), getDirectory());
            if(!directory.exists())
                directory.mkdir();
            String existingData = "";

            File file = new File(directory, getFilename(c));

            try {
                FileInputStream inputStream = new FileInputStream(file);
                BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
                String row = "";


                while ((row = reader.readLine()) != null)
                    existingData += row;
                reader.close();
            }catch (IOException e){
                Log.i("file export", "no previously existing data");
            }

            if(!existingData.equals("")) {
                Log.i("file export", "Attempting to parse data");
                entries = gson.fromJson(existingData, listEntries);
            }

            entries.add(entry);

            String output = gson.toJson(entries);
            Log.i("file export", "File converted to JSON");

            file.createNewFile();
            Log.i("file export", "File created");

            FileWriter writer = new FileWriter(file, false); // False overwrites, true appends
            writer.write(output);
            writer.flush();
            writer.close();
            Log.i("file export", "File written successfully");

            MediaScannerConnection.scanFile(c, new String[]{file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String s, Uri uri) {
                    Log.i("scan", "media scan completed");
                }
            }); // Refresh PC connection to view file

        }catch(Exception e){
            e.printStackTrace();

        }

    }

}
