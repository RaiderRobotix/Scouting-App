package org.usfirst.frc.team25.scouting.data.thebluealliance;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thebluealliance.api.v3.TBA;
import com.thebluealliance.api.v3.models.Match;
import com.thebluealliance.api.v3.models.Team;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.Sorters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class DataDownloader extends AsyncTask<Void, Void, String> {

    private final Context c;
    private final Settings set;
    private final String apiKey;

    private final File teamListFilePath;
    private final File matchListFilePath;
    private final File scoreBreakdownFilePath;

    public DataDownloader(Context c) {
        this.c = c;
        set = Settings.newInstance(c);
        apiKey = c.getResources().getString(R.string.tba_api_key);
        teamListFilePath = FileManager.getTeamListFilePath(c);
        matchListFilePath = FileManager.getMatchListFilePath(c);
        scoreBreakdownFilePath = FileManager.getScoreBreakdownFilePath(c);
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected String doInBackground(Void... voids) {
        try {

            //TODO store these values in an array
            String eventCode;

            String currentEvent = set.getCurrentEvent();
            String currentYear = set.getYear();

            eventCode = currentYear + currentEvent;

            if (currentEvent.equals("Mount Olive")) {
                eventCode = currentYear + "njfla";
            }
            if (currentEvent.equals("Montgomery")) {
                eventCode = currentYear + "njski";
            }
            if (currentEvent.equals("Mid-Atlantic")) {
                eventCode = currentYear + "mrcmp";
            }
            if (currentEvent.equals("Carson")) {
                eventCode = currentYear + "cars";
            }
            if (currentEvent.equals("Week 0")) {
                eventCode = currentYear + "week0";
            }
            if (currentEvent.equals("Brunswick Eruption")) {
                eventCode = currentYear + "njbe";
            }

            try {
                TBA tba = new TBA(apiKey);
                if (tba.dataRequest.getDataTBA("/status").getResponseCode() == 401) {
                    return "Invalid Blue Alliance API key";
                }
            } catch (NullPointerException e) {
                return "Invalid Blue Alliance API key";
            }

            if (!eventCode.isEmpty()) {

                try {
                    FileManager.saveFile(scoreBreakdownFilePath, getEventMatchData(eventCode), c);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                String teamList = getTeamList(eventCode);
                if (teamList.isEmpty()) {
                    return "Event data not found on The Blue Alliance";
                } else {
                    FileManager.saveFile(teamListFilePath, teamList, c);
                }


                String matchList = getMatchList(eventCode);
                if (matchList.isEmpty()) {
                    return "Only team list downloaded";
                } else {
                    FileManager.saveFile(matchListFilePath, matchList, c);
                    return "Match schedule downloaded";
                }


            }
            return "Event data not found on The Blue Alliance";

        } catch (Exception e) {
            e.printStackTrace();
            return "Match data failed to download";
        }


    }

    private String getEventMatchData(String eventCode) throws IOException {
        return new Gson().toJson(Sorters.sortByMatchNum(Sorters.filterQualification(new ArrayList<>(Arrays.asList(new TBA(apiKey).eventRequest.getMatches(eventCode))))));
    }

    private String getTeamList(String eventCode) throws IOException {
        StringBuilder teamList = new StringBuilder();
        ArrayList<Team> teams =
                Sorters.sortByTeamNum(new ArrayList<>(Arrays.asList(new TBA(apiKey).eventRequest.getTeams(eventCode))));

        for (Team team : teams) {
            teamList.append(team.getTeamNumber()).append(",");
        }

        teamList.setCharAt(teamList.length() - 1, ' ');
        return teamList.toString();
    }

    /**
     * Generates CSV file content with list of teams playing in each match
     * Each line contains comma delimited match number, then team numbers for red alliance, then
     * blue alliance.
     *
     * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatboro-Horsham in 2016
     */
    private String getMatchList(String eventCode) throws IOException {
        StringBuilder matchList = new StringBuilder();
        for (Match match :
                Sorters.sortByMatchNum(Sorters.filterQualification(new ArrayList<>(Arrays.asList(new TBA(apiKey).eventRequest.getMatches(eventCode)))))) {

            matchList.append(match.getMatchNumber()).append(",");

            //iterate through two alliances
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) { //iterate through teams in alliance
                    if (i == 0) {
                        matchList.append(match.getRedAlliance().getTeamKeys()[j].split("frc")[1]).append(",");
                    } else {
                        matchList.append(match.getBlueAlliance().getTeamKeys()[j].split("frc")[1]).append(",");
                    }
                }

            }

            matchList.append("\n");


        }

        return matchList.toString();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("download", "Executing download task");
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);

        // Display response message, if any
        if (!message.isEmpty()) {
            Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
        }
    }


}