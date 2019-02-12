package org.usfirst.frc.team25.scouting.data;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;

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
 * Class of static methods used for interaction with the file system
 * TODO make this more generalized
 */
public class FileManager {

    public static final String DIRECTORY_DATA = "Raider Robotix Scouting";

    /**
     * Deletes all scouting data from the data directory
     * Executes a backup as well
     * Should not be used until the end of the season
     *
     * @param c <code>Context</code> of the running stack
     */
    public static void deleteData(Context c) {
        backup(c);
        try {
            File[] directoryFiles = getDirectory().listFiles();
            for (File file : directoryFiles) {
                if (file.getName().contains("Data")) {
                    if (file.delete()) {
                        Log.i("delete", file.getName() + " deleted");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Backs up the contents of the main data directory to Documents
     *
     * @param c <code>Context</code> of the running stack
     */
    private static void backup(Context c) {
        try {
            File sourceDir = getDirectory();
            File destDir =
                    new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), DIRECTORY_DATA + " - Backup");

            MediaScannerConnection.scanFile(c, new String[]{destDir.toString()}, null, null);
            FileUtils.copyDirectory(sourceDir, destDir);
            Log.i("backup", "files written to internal storage for backup");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static File getDirectory() {
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                DIRECTORY_DATA);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    public static File getScoreBreakdownFilePath(Context c) {
        return new File(getDirectory(), getScoreBreakdownFilename(c));
    }

    private static String getScoreBreakdownFilename(Context c) {
        Settings set = Settings.newInstance(c);
        return "ScoreBreakdown - " + set.getYear() + set.getCurrentEvent() + ".json";
    }

    /**
     * Finds the largest match number for the event, given the match list
     *
     * @param c <code>Context</code> of the running stack
     * @return Number of the last qualification match if a match list is present
     * 150 otherwise
     */
    public static int getMaxMatchNum(Context c) {

        File file = getMatchListFilePath(c);

        if (!file.exists()) {
            return 150;
        }

        int maxMatches = 0;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));

            while ((reader.readLine()) != null) {
                maxMatches++; //Incremented for each new line; each line contains match info
            }

        } catch (IOException e) {
            Log.i("file import", "no matchlist available");
        }

        return maxMatches;
    }

    public static File getMatchListFilePath(Context c) {
        return new File(getDirectory(), getMatchListFilename(c));
    }

    /**
     * Generates the filename for the match list, based on the current event
     * Should be consistent with the desktop client
     *
     * @param c <code>Context</code> of the running stack
     * @return The filename of the team list file
     */
    private static String getMatchListFilename(Context c) {
        Settings set = Settings.newInstance(c);
        return "Matches - " + set.getYear() + set.getCurrentEvent() + ".csv";
    }

    /**
     * Finds the current team playing in the match, for the given scouting position
     *
     * @param c        <code>Context</code> of the running stack
     * @param scoutPos Current scouting position (Red/Blue, 1-3)
     * @param matchNum Current match number
     * @return Team number of the team playing in the current match, if available
     * @throws IOException when the match list does not exist
     */
    public static String getTeamPlaying(Context c, String scoutPos, int matchNum) throws IOException {

        File file = getMatchListFilePath(c);

        if (!file.exists()) {
            throw new IOException("Match file does not exist");
        }

        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));

        String row;
        ArrayList<String> rows = new ArrayList<>();
        while ((row = reader.readLine()) != null) {
            rows.add(row);
        }


        for (String r : rows) {
            String[] dataEntries = r.split(",");

            //Each row in match list formatted as [match number], [red 1 team number], [red 2],
            // [red 3], [blue 1], [blue 2], [blue 3]
            if (Integer.parseInt(dataEntries[0]) == matchNum) {
                if (scoutPos.equals("Red 1")) {
                    return dataEntries[1];
                }
                if (scoutPos.equals("Red 2")) {
                    return dataEntries[2];
                }
                if (scoutPos.equals("Red 3")) {
                    return dataEntries[3];
                }
                if (scoutPos.equals("Blue 1")) {
                    return dataEntries[4];
                }
                if (scoutPos.equals("Blue 2")) {
                    return dataEntries[5];
                }
                if (scoutPos.equals("Blue 3")) {
                    return dataEntries[6];
                }
            }

        }
        reader.close();


        return ""; //When the correct match number is not found, for some reason; shouldn't happen
    }

    public static boolean teamListExists(Context c) {

        File file = getTeamListFilePath(c);
        return file.exists();
    }

    public static File getTeamListFilePath(Context c) {
        return new File(getDirectory(), getTeamListFilename(c));
    }

    /**
     * Generates the filename for the general team list, based on the current event
     * Should be consistent with the desktop client
     *
     * @param c <code>Context</code> of the running stack
     * @return The filename of the team list file
     */
    private static String getTeamListFilename(Context c) {
        Settings set = Settings.newInstance(c);
        return "Teams - " + set.getYear() + set.getCurrentEvent() + ".csv";
    }

    public static boolean matchScheduleExists(Context c) {

        File file = getMatchListFilePath(c);
        return file.exists();
    }

    /**
     * Checks a team list file to see if a team is playing at the event
     *
     * @param teamNum Team number to be checked
     * @param c       <code>Context</code> of the running stack
     * @return <code>true</code> if <code>teamNum</code> is in the list of teams for the current
     * event, <code>false </code> otherwise
     */
    public static boolean isOnTeamlist(String teamNum, Context c) {
        try {

            StringBuilder existingData = new StringBuilder();

            File file = getTeamListFilePath(c);

            if (!file.exists()) {
                return true; //default case to prevent user from being stuck if team list does
            }
            // not exist


            try {
                FileInputStream inputStream = new FileInputStream(file);
                BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
                String row;

                while ((row = reader.readLine()) != null) {
                    existingData.append(row);
                }
                reader.close();
            } catch (IOException e) {
                Log.i("file export", "no teamlist available");
            }

            //Team list is a single row, comma separated file of team numbers
            String[] teamList = existingData.toString().split(",");
            for (String team : teamList) {
                if (team.equals(teamNum)) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Adds a team number to the list of teams for the current event
     * Appends the number to the team list file
     * Creates the file if it does not exist
     *
     * @param teamNum Team number to be added
     * @param c       <code>Context</code> of the running stack
     */
    public static void addToTeamList(String teamNum, Context c) {
        try {

            File file = getTeamListFilePath(c);

            FileWriter writer = new FileWriter(file, true);
            writer.write("," + teamNum);
            writer.flush();
            writer.close();
            Log.i("file export", "Team list updated successfully");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Saves a new entry to the JSON data file
     *
     * @param entry <code>ScoutEntry</code> object to be saved to data file
     * @param c     Context of current activity
     */
    public static void saveData(ScoutEntry entry, Context c) {
        Gson gson = new Gson();
        List<ScoutEntry> entries = new ArrayList<>();
        Type listEntries = new TypeToken<List<ScoutEntry>>() {
        }.getType(); //Example for deserializing an ArrayList of objects


        StringBuilder existingData = new StringBuilder();

        File file = getDataFilePath(c);

        //Reads the raw data
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
            String row;


            while ((row = reader.readLine()) != null) {
                existingData.append(row);
            }
            reader.close();
        } catch (IOException e) {
            Log.i("file export", "no previously existing data");
        }

        if (existingData.length() != 0) {
            Log.i("file export", "Attempting to parse data");
            entries = gson.fromJson(existingData.toString(), listEntries);
        }

        entries.add(entry);

        String output = gson.toJson(entries);
        Log.i("file export", "File converted to JSON");

        saveFile(file, output, c);


    }

    private static File getDataFilePath(Context c) {
        return new File(getDirectory(), getDataFilename(c));
    }

    public static void saveFile(File filePath, String contents, Context c) {
        try {
            filePath.createNewFile();
            Log.i("file export", "File created");

            FileWriter writer = new FileWriter(filePath, false); // False overwrites, true appends
            writer.write(contents);
            writer.flush();
            writer.close();
            Log.i("file export", "File written successfully");

            MediaScannerConnection.scanFile(c, new String[]{filePath.toString()}, null,
                    (s, uri) -> Log.i("scan", "media scan completed")); // Refresh PC connection
            // to view file
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Generates the filename for collected scouting data,
     * based on the current event and scouting position
     * Should be consistent with the desktop client
     *
     * @param c <code>Context</code> of the running stack
     * @return The filename of the data file for the current event
     */
    public static String getDataFilename(Context c) {
        Settings set = Settings.newInstance(c);
        return "Data - " + set.getScoutPos() + " - " + set.getYear() + set.getCurrentEvent() +
                ".json";
    }

    public static int getPrevTeamNumber(Context c) {
        Gson gson = new Gson();
        List<ScoutEntry> entries;
        Type listEntries = new TypeToken<List<ScoutEntry>>() {
        }.getType(); //Example for deserializing an ArrayList of objects


        StringBuilder existingData = new StringBuilder();

        File file = getDataFilePath(c);

        //Reads the raw data
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
            String row;

            while ((row = reader.readLine()) != null) {
                existingData.append(row);
            }
            reader.close();
        } catch (IOException e) {
            return 0;
        }

        if (!existingData.toString().equals("")) {
            Log.i("file export", "Attempting to parse data");
            entries = gson.fromJson(existingData.toString(), listEntries);
            return entries.get(entries.size() - 1).getPreMatch().getTeamNum();
        } else {
            return 0;
        }


    }

    public static String getPrevScoutName(Context c) {
        ScoutEntry prevScoutEntry = getPrevScoutEntry(c);

        if (prevScoutEntry == null) {
            return "";
        } else {
            return prevScoutEntry.getPreMatch().getScoutName();
        }
    }


    public static ScoutEntry getPrevScoutEntry(Context c) {
        Gson gson = new Gson();
        List<ScoutEntry> entries;
        Type listEntries = new TypeToken<List<ScoutEntry>>() {
        }.getType(); //Example for deserializing an ArrayList of objects


        StringBuilder existingData = new StringBuilder();

        File file = getDataFilePath(c);

        //Reads the raw data
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
            String row;

            while ((row = reader.readLine()) != null) {
                existingData.append(row);
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }

        if (!existingData.toString().equals("")) {
            Log.i("file export", "Attempting to parse data");
            entries = gson.fromJson(existingData.toString(), listEntries);
            return entries.get(entries.size() - 1);
        } else {
            return null;
        }


    }

}
