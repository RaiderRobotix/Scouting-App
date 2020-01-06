package org.usfirst.frc.team25.scouting.data

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.commons.io.FileUtils
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry
import java.io.*
import java.util.*

/**
 * Class of static methods used for interaction with the file system
 * TODO make this more generalized
 */
object FileManager {
	const val DIRECTORY_DATA = "Raider Robotix Scouting"
	/**
	 * Deletes all scouting data from the data directory
	 * Executes a backup as well
	 * Should not be used until the end of the season
	 *
	 * @param c `Context` of the running stack
	 */
    @JvmStatic
    fun deleteData(c: Context) {
		backup(c)
		try {
			val directoryFiles = directory.listFiles()
			for (file in directoryFiles) {
				if (file.name.contains("Data")) {
					if (file.delete()) {
						Log.i("delete", file.name + " deleted")
					}
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	/**
	 * Backs up the contents of the main data directory to Documents
	 *
	 * @param c `Context` of the running stack
	 */
	private fun backup(c: Context) {
		try {
			val sourceDir = directory
			val destDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath, "$DIRECTORY_DATA - Backup")
			MediaScannerConnection.scanFile(c, arrayOf(destDir.toString()), null, null)
			FileUtils.copyDirectory(sourceDir, destDir)
			Log.i("backup", "files written to internal storage for backup")
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	private val directory: File
		get() {
			val directory = File(Environment.getExternalStorageDirectory().absolutePath,
					DIRECTORY_DATA)
			if (!directory.exists()) {
				directory.mkdir()
			}
			return directory
		}

	fun getScoreBreakdownFilePath(c: Context): File {
		return File(directory, getScoreBreakdownFilename(c))
	}

	private fun getScoreBreakdownFilename(c: Context): String {
		val set: Settings = Settings.newInstance(c)
		return "ScoreBreakdown - " + set.year + set.currentEvent + ".json"
	}

	/**
	 * Finds the largest match number for the event, given the match list
	 *
	 * @param c `Context` of the running stack
	 * @return Number of the last qualification match if a match list is present
	 * 150 otherwise
	 */
    @JvmStatic
    fun getMaxMatchNum(c: Context?): Int {
		val file = getMatchListFilePath(c)
		if (!file.exists()) {
			return 150
		}
		var maxMatches = 0
		try {
			val inputStream = FileInputStream(file)
			val reader = BufferedReader(InputStreamReader(inputStream))
			while (reader.readLine() != null) {
				maxMatches++ //Incremented for each new line; each line contains match info
			}
		} catch (e: IOException) {
			Log.i("file import", "no matchlist available")
		}
		return maxMatches
	}

	fun getMatchListFilePath(c: Context?): File {
		return File(directory, getMatchListFilename(c))
	}

	/**
	 * Generates the filename for the match list, based on the current event
	 * Should be consistent with the desktop client
	 *
	 * @param c `Context` of the running stack
	 * @return The filename of the team list file
	 */
	private fun getMatchListFilename(c: Context?): String {
		val set: Settings = Settings.newInstance(c)
		return "Matches - " + set.year + set.currentEvent + ".csv"
	}

	/**
	 * Finds the current team playing in the match, for the given scouting position
	 *
	 * @param c        `Context` of the running stack
	 * @param scoutPos Current scouting position (Red/Blue, 1-3)
	 * @param matchNum Current match number
	 * @return Team number of the team playing in the current match, if available
	 * @throws IOException when the match list does not exist
	 */
	@JvmStatic
    @Throws(IOException::class)
	fun getTeamPlaying(c: Context?, scoutPos: String, matchNum: Int): String {
		val file = getMatchListFilePath(c)
		if (!file.exists()) {
			throw IOException("Match file does not exist")
		}
		val inputStream = FileInputStream(file)
		val reader = BufferedReader(InputStreamReader(inputStream))
		var row: String
		val rows = ArrayList<String>()
		while (reader.readLine().also { row = it } != null) {
			rows.add(row)
		}
		for (r in rows) {
			val dataEntries = r.split(",").toTypedArray()
			//Each row in match list formatted as [match number], [red 1 team number], [red 2],
// [red 3], [blue 1], [blue 2], [blue 3]
			if (dataEntries[0].toInt() == matchNum) {
				if (scoutPos == "Red 1") {
					return dataEntries[1]
				}
				if (scoutPos == "Red 2") {
					return dataEntries[2]
				}
				if (scoutPos == "Red 3") {
					return dataEntries[3]
				}
				if (scoutPos == "Blue 1") {
					return dataEntries[4]
				}
				if (scoutPos == "Blue 2") {
					return dataEntries[5]
				}
				if (scoutPos == "Blue 3") {
					return dataEntries[6]
				}
			}
		}
		reader.close()
		return "" //When the correct match number is not found, for some reason; shouldn't happen
	}

	fun teamListExists(c: Context?): Boolean {
		val file = getTeamListFilePath(c)
		return file.exists()
	}

	fun getTeamListFilePath(c: Context?): File {
		return File(directory, getTeamListFilename(c))
	}

	/**
	 * Generates the filename for the general team list, based on the current event
	 * Should be consistent with the desktop client
	 *
	 * @param c `Context` of the running stack
	 * @return The filename of the team list file
	 */
	private fun getTeamListFilename(c: Context?): String {
		val set: Settings = Settings.newInstance(c)
		return "Teams - " + set.year + set.currentEvent + ".csv"
	}

	fun matchScheduleExists(c: Context?): Boolean {
		val file = getMatchListFilePath(c)
		return file.exists()
	}

	/**
	 * Checks a team list file to see if a team is playing at the event
	 *
	 * @param teamNum Team number to be checked
	 * @param c       `Context` of the running stack
	 * @return `true` if `teamNum` is in the list of teams for the current
	 * event, `false ` otherwise
	 */
    @JvmStatic
    fun isOnTeamlist(teamNum: String, c: Context?): Boolean {
		try {
			val existingData = StringBuilder()
			val file = getTeamListFilePath(c)
			if (!file.exists()) {
				return true //default case to prevent user from being stuck if team list does
			}
			// not exist
			try {
				val inputStream = FileInputStream(file)
				val reader = BufferedReader(InputStreamReader(inputStream))
				var row: String?
				while (reader.readLine().also { row = it } != null) {
					existingData.append(row)
				}
				reader.close()
			} catch (e: IOException) {
				Log.i("file export", "no teamlist available")
			}
			//Team list is a single row, comma separated file of team numbers
			val teamList = existingData.toString().split(",").toTypedArray()
			for (team in teamList) {
				if (team == teamNum) {
					return true
				}
			}
			return false
		} catch (e: Exception) {
			e.printStackTrace()
		}
		return true
	}

	/**
	 * Adds a team number to the list of teams for the current event
	 * Appends the number to the team list file
	 * Creates the file if it does not exist
	 *
	 * @param teamNum Team number to be added
	 * @param c       `Context` of the running stack
	 */
    @JvmStatic
    fun addToTeamList(teamNum: String, c: Context?) {
		try {
			val file = getTeamListFilePath(c)
			val writer = FileWriter(file, true)
			writer.write(",$teamNum")
			writer.flush()
			writer.close()
			Log.i("file export", "Team list updated successfully")
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	/**
	 * Saves a new entry to the JSON data file
	 *
	 * @param entry `ScoutEntry` object to be saved to data file
	 * @param c     Context of current activity
	 */
    @JvmStatic
    fun saveData(entry: ScoutEntry?, c: Context) {
		val gson = Gson()
		var entries: MutableList<ScoutEntry?> = ArrayList()
		val listEntries = object : TypeToken<List<ScoutEntry?>?>() {}.type //Example for deserializing an ArrayList of objects
		val existingData = StringBuilder()
		val file = getDataFilePath(c)
		//Reads the raw data
		try {
			val inputStream = FileInputStream(file)
			val reader = BufferedReader(InputStreamReader(inputStream))
			var row: String?
			while (reader.readLine().also { row = it } != null) {
				existingData.append(row)
			}
			reader.close()
		} catch (e: IOException) {
			Log.i("file export", "no previously existing data")
		}
		if (existingData.isNotEmpty()) {
			Log.i("file export", "Attempting to parse data")
			entries = gson.fromJson(existingData.toString(), listEntries)
		}
		entries.add(entry)
		val output = gson.toJson(entries)
		Log.i("file export", "File converted to JSON")
		saveFile(file, output, c)
	}

	private fun getDataFilePath(c: Context): File {
		return File(directory, getDataFilename(c))
	}

	fun saveFile(filePath: File, contents: String, c: Context?) {
		try {
			filePath.createNewFile()
			Log.i("file export", "File created")
			val writer = FileWriter(filePath, false) // False overwrites, true appends
			writer.write(contents)
			writer.flush()
			writer.close()
			Log.i("file export", "File written successfully")
			MediaScannerConnection.scanFile(c, arrayOf(filePath.toString()), null
			) { _: String?, _: Uri? -> Log.i("scan", "media scan completed") } // Refresh PC connection
			// to view file
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	/**
	 * Generates the filename for collected scouting data,
	 * based on the current event and scouting position
	 * Should be consistent with the desktop client
	 *
	 * @param c `Context` of the running stack
	 * @return The filename of the data file for the current event
	 */
    @JvmStatic
    fun getDataFilename(c: Context?): String {
		val set: Settings = Settings.newInstance(c)
		return "Data - " + set.scoutPos + " - " + set.year + set.currentEvent +
				".json"
	}

	@JvmStatic
    fun getPrevTeamNumber(c: Context): Int {
		val gson = Gson()
		val entries: List<ScoutEntry>
		val listEntries = object : TypeToken<List<ScoutEntry?>?>() {}.type //Example for deserializing an ArrayList of objects
		val existingData = StringBuilder()
		val file = getDataFilePath(c)
		//Reads the raw data
		try {
			val inputStream = FileInputStream(file)
			val reader = BufferedReader(InputStreamReader(inputStream))
			var row: String?
			while (reader.readLine().also { row = it } != null) {
				existingData.append(row)
			}
			reader.close()
		} catch (e: IOException) {
			return 0
		}
		return if (existingData.toString() != "") {
			Log.i("file export", "Attempting to parse data")
			entries = gson.fromJson(existingData.toString(), listEntries)
			entries[entries.size - 1].preMatch.teamNum
		} else {
			0
		}
	}

	@JvmStatic
    fun getPrevScoutName(c: Context): String = getPrevScoutEntry(c)?.preMatch?.scoutName ?: ""

	private fun getPrevScoutEntry(c: Context): ScoutEntry? {
		val gson = Gson()
		val entries: List<ScoutEntry>
		val listEntries = object : TypeToken<List<ScoutEntry?>?>() {}.type //Example for deserializing an ArrayList of objects
		val existingData = StringBuilder()
		val file = getDataFilePath(c)
		//Reads the raw data
		try {
			val inputStream = FileInputStream(file)
			val reader = BufferedReader(InputStreamReader(inputStream))
			var row: String?
			while (reader.readLine().also { row = it } != null) {
				existingData.append(row)
			}
			reader.close()
		} catch (e: IOException) {
			return null
		}
		return if (existingData.toString() != "") {
			Log.i("file export", "Attempting to parse data")
			entries = gson.fromJson(existingData.toString(), listEntries)
			entries[entries.size - 1]
		} else {
			null
		}
	}
}