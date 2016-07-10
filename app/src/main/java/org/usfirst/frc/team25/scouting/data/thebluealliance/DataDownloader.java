package org.usfirst.frc.team25.scouting.data.thebluealliance;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.usfirst.frc.team25.scouting.data.thebluealliance.models.Event;
import org.usfirst.frc.team25.scouting.data.thebluealliance.models.Match;


public class DataDownloader  extends AsyncTask<String, Void, String>{
	public static final String BLUE_ALLIANCE_API_URL = "https://www.thebluealliance.com/api/v2/";
	public static final Gson gson = new Gson();
	public static final String 
		QUALIFIER = "qm",
		QUARTER_FINAL="qf",
		SEMI_FINAL="sf",
		FINAL="f";

	public static DataDownloader getInstance(){
        return new DataDownloader();
    }
	
	public static String getJsonData(String directory) throws IOException{

		URLConnection con = new URL(BLUE_ALLIANCE_API_URL + directory).openConnection();
		con.setRequestProperty("X-TBA-App-Id", "frc25:scouting:v0.1");
		con.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");


		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		return br.readLine();

	}
	
	public static Event[] getTeamEvents(int teamNumber, int year) throws IOException{
		String jsonData = getJsonData("team/frc"+String.valueOf(teamNumber)+"/"+String.valueOf(year)+"/events");
		
		Type listType = new TypeToken<List<Event>>(){}.getType();
		List<Event> teamEventList = gson.fromJson(jsonData, listType);
		return teamEventList.toArray(new Event[teamEventList.size()]);
		
	}
	
	public static Match[] getEventMatches(String eventKey) throws IOException{
		String jsonData = getJsonData("event/"+eventKey+"/matches");
		Type listType = new TypeToken<List<Match>>(){}.getType();
		List<Match> eventMatchList = gson.fromJson(jsonData, listType);
		return eventMatchList.toArray(new Match[eventMatchList.size()]);
	}
	
	public static Match[] filterMatches(Match[] matchArray, String compLevel){
		
		List<Match> eventMatchList = new LinkedList<Match>(Arrays.asList(matchArray));
		
		Iterator<Match> iter = eventMatchList.iterator();

		while (iter.hasNext()) {
		    Match match = iter.next();

		    if (!match.getCompLevel().equals(compLevel))
		        iter.remove();
		}
		
		return eventMatchList.toArray(matchArray);
	}


    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
