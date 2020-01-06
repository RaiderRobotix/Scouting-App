package org.usfirst.frc.team25.scouting.data.thebluealliance

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.thebluealliance.api.v3.TBA
import org.usfirst.frc.team25.scouting.R
import org.usfirst.frc.team25.scouting.data.FileManager
import org.usfirst.frc.team25.scouting.data.Settings
import org.usfirst.frc.team25.scouting.data.Sorters
import java.io.File
import java.io.IOException
import java.util.*

class DataDownloader(private val context: Context) : AsyncTask<Void?, Void?, String>() {
	private val settings: Settings = Settings.newInstance(context)
	private val apiKey: String = context.resources.getString(R.string.tba_api_key)
	private val teamListFilePath: File = FileManager.getTeamListFilePath(context)
	private val matchListFilePath: File = FileManager.getMatchListFilePath(context)
	private val scoreBreakdownFilePath: File = FileManager.getScoreBreakdownFilePath(context)
	/**
	 * Downloading file in background thread
	 */
	override fun doInBackground(vararg params: Void?): String? {
		return try { //TODO store these values in an array
			var eventCode: String
			val currentEvent = settings.currentEvent
			val currentYear = settings.year
			eventCode = currentYear + currentEvent
			if (currentEvent == "Mount Olive") {
				eventCode = currentYear + "njfla"
			}
			if (currentEvent == "Montgomery") {
				eventCode = currentYear + "njski"
			}
			if (currentEvent == "Mid-Atlantic") {
				eventCode = currentYear + "mrcmp"
			}
			if (currentEvent == "Carson") {
				eventCode = currentYear + "cars"
			}
			if (currentEvent == "Week 0") {
				eventCode = currentYear + "week0"
			}
			if (currentEvent == "Brunswick Eruption") {
				eventCode = currentYear + "njbe"
			}
			try {
				val tba = TBA(apiKey)
				if (tba.dataRequest.getDataTBA("/status").responseCode == 401) {
					return "Invalid Blue Alliance API key"
				}
			} catch (e: NullPointerException) {
				return "Invalid Blue Alliance API key"
			}
			if (!eventCode.isEmpty()) {
				try {
					FileManager.saveFile(scoreBreakdownFilePath, getEventMatchData(eventCode), context)
				} catch (e: IOException) {
					e.printStackTrace()
				}
				val teamList = getTeamList(eventCode)
				if (teamList.isEmpty()) {
					return "Event data not found on The Blue Alliance"
				} else {
					FileManager.saveFile(teamListFilePath, teamList, context)
				}
				val matchList = getMatchList(eventCode)
				return if (matchList.isEmpty()) {
					"Only team list downloaded"
				} else {
					FileManager.saveFile(matchListFilePath, matchList, context)
					"Match schedule downloaded"
				}
			}
			"Event data not found on The Blue Alliance"
		} catch (e: Exception) {
			e.printStackTrace()
			"Match data failed to download"
		}
	}

	@Throws(IOException::class)
	private fun getEventMatchData(eventCode: String): String {
		return Gson().toJson(Sorters.sortByMatchNum(Sorters.filterQualification(ArrayList(Arrays.asList(*TBA(apiKey).eventRequest.getMatches(eventCode))))))
	}

	@Throws(IOException::class)
	private fun getTeamList(eventCode: String): String {
		val teamList = StringBuilder()
		val teams = Sorters.sortByTeamNum(ArrayList(Arrays.asList(*TBA(apiKey).eventRequest.getTeams(eventCode))))
		for (team in teams) {
			teamList.append(team.teamNumber).append(",")
		}
		teamList.setCharAt(teamList.length - 1, ' ')
		return teamList.toString()
	}

	/**
	 * Generates CSV file content with list of teams playing in each match
	 * Each line contains comma delimited match number, then team numbers for red alliance, then
	 * blue alliance.
	 *
	 * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatboro-Horsham in 2016
	 */
	@Throws(IOException::class)
	private fun getMatchList(eventCode: String): String {
		val matchList = StringBuilder()
		for (match in Sorters.sortByMatchNum(Sorters.filterQualification(ArrayList(Arrays.asList(*TBA(apiKey).eventRequest.getMatches(eventCode)))))) {
			matchList.append(match.matchNumber).append(",")
			//iterate through two alliances
			for (i in 0..1) {
				for (j in 0..2) { //iterate through teams in alliance
					if (i == 0) {
						matchList.append(match.redAlliance.teamKeys[j].split("frc").toTypedArray()[1]).append(",")
					} else {
						matchList.append(match.blueAlliance.teamKeys[j].split("frc").toTypedArray()[1]).append(",")
					}
				}
			}
			matchList.append("\n")
		}
		return matchList.toString()
	}

	override fun onPreExecute() {
		super.onPreExecute()
		Log.i("download", "Executing download task")
	}

	override fun onPostExecute(message: String) {
		super.onPostExecute(message)
		// Display response message, if any
		if (!message.isEmpty()) {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
		}
	}
	
}