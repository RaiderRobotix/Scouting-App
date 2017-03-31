package org.usfirst.frc.team25.scouting.data.thebluealliance;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.adithyasairam.tba4j.Events;
import com.adithyasairam.tba4j.models.Match;
import com.adithyasairam.tba4j.models.Team;

import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.Sorters;



public class DataDownloader  extends AsyncTask<Void, Void, String>{

        Context c;
        Settings set;
    boolean teamListExists, matchScheduleExists;
    File teamListFilePath, matchListFilePath;

        public DataDownloader(Context c){
            this.c = c;
            set = Settings.newInstance(c);
            teamListExists = FileManager.teamListExists(c);
            teamListFilePath = FileManager.getTeamListFilePath(c);
            matchScheduleExists = FileManager.matchScheduleExists(c);
            matchListFilePath = FileManager.getMatchListFilePath(c);
        }

        private String getTeamList(String eventCode) throws UnknownHostException{
            String teamList = "";
            ArrayList<Team> teams = Sorters.sortByTeamNum(new ArrayList<Team>(Arrays.asList(Events.getEventTeamsList(eventCode))));
            for (Team team : teams)
                teamList += team.team_number + ",";
            StringBuilder output = new StringBuilder(teamList);
            output.setCharAt(output.length() - 1, ' ');
            return teamList;
        }

    /** Generates a file with list of teams playing in each match
     * Each line contains comma delimited match number, then team numbers for red alliance, then blue alliance.
     * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatsboro-Horsham in 2016
     */
    private String getMatchList(String eventCode) throws FileNotFoundException, UnknownHostException {
        String matchList = "";
        for(Match match : Sorters.sortByMatchNum(Sorters.filterQualification(new ArrayList<Match>(Arrays.asList(Events.getEventMatches(eventCode)))))){

            matchList+=match.match_number+",";
            for(int i = 0; i < 2; i++) //iterate through two alliances
                for(int j = 0; j < 3; j++) //iterate through teams in alliance
                    //A ternary operator is used here for convenience. TODO fix this unreadable mess
                    matchList+= i==0 ? match.alliances.red.teams[j].split("frc")[1]+",": match.alliances.blue.teams[j].split("frc")[1]+",";
            matchList+=",\n";


        }

        return matchList;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("download", "Executing download task");
    }

    /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(Void... voids) {
            try {



                //TODO store these values in an array
                String eventCode = "";

                String currentEvent = set.getCurrentEvent();
                String currentYear = set.getYear();

                if(currentEvent.equals("Mount Olive"))
                    eventCode = currentYear+"njfla";
                if(currentEvent.equals("Montgomery"))
                    eventCode = currentYear+"njski";
                if(currentEvent.equals("Mid-Atlantic"))
                    eventCode = currentYear+"mrcmp";


                if(!eventCode.equals("")) {
                    if(!teamListExists) {
                        String teamList = getTeamList(eventCode);
                        if (teamList.equals(""))
                            return "Event data not found on The Blue Alliance";
                        else FileManager.saveFile(teamListFilePath, teamList, c);
                    }
                    if(!matchScheduleExists) {
                        String matchList = getMatchList(eventCode);
                        if (matchList.equals(""))
                            return "Only team list downloaded";
                        else {
                            FileManager.saveFile(matchListFilePath, matchList, c);
                            return "Match schedule downloaded";
                        }
                    }

                    else return "Match schedule already exists";



                }
                return "Event data not found on The Blue Alliance";


            }catch (UnknownHostException e){
                return "No internet connection";
            }
            catch (Exception e) {
                e.printStackTrace();
                return "Match data failed to download";
            }


        }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);

        if(!message.equals(""))
            Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }



}


