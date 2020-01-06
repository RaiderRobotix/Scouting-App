package org.usfirst.frc.team25.scouting.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.util.Log
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import org.usfirst.frc.team25.scouting.R
import org.usfirst.frc.team25.scouting.data.models.PreMatch
import java.util.*

/**
 * Object to access SharedPreferences and global user settings
 * Must be initialized with a Context
 */
class Settings : PreferenceFragment() {
	private var sp: SharedPreferences? = null
	var scoutName: String
		get() = sp!!.getString("scout_name", "DEFAULT")
		private set(name) {
			val editor = sp!!.edit()
			editor.putString("scout_name", name)
			editor.apply()
		}

	var timerManualInc: Float
		get() = (sp!!.getInt("timer_manual_inc", 4) + 1) / 10.0f
		set(incAmount) {
			val editor = sp!!.edit()
			editor.putInt("timer_manual_inc", ((incAmount - 0.1) * 10).toInt())
			editor.apply()
		}

	var scoutPos: String
		get() = sp!!.getString("pos", "DEFAULT")
		private set(pos) {
			val editor = sp!!.edit()
			editor.putString("pos", pos)
			editor.apply()
		}

	var shiftDur: Int
		get() = sp!!.getInt("shift_dur", 10)
		set(shiftDur) {
			var shiftDur = shiftDur
			if (shiftDur < 1 || shiftDur > 150) {
				shiftDur = 1
			}
			val editor = sp!!.edit()
			editor.putInt("shift_dur", shiftDur)
			editor.apply()
		}

	var matchNum: Int
		get() = sp!!.getInt("match_num", 1)
		set(matchNum) {
			var matchNum = matchNum
			if (matchNum < 1 || matchNum > 200) {
				matchNum = 1
			}
			val editor = sp!!.edit()
			editor.putInt("match_num", matchNum)
			editor.apply()
		}

	var matchType: String?
		get() = sp!!.getString("match_type", "DEFAULT")
		set(type) {
			val editor = sp!!.edit()
			editor.putString("match_type", type)
			editor.apply()
		}

	var currentEvent: String?
		get() = sp!!.getString("event", "DEFAULT")
		set(event) {
			val editor = sp!!.edit()
			editor.putString("event", event)
			editor.apply()
		}

	fun useTeamList(): Boolean {
		return sp!!.getBoolean("use_team_list", false)
	}

	var maxMatchNum: Int
		get() = sp!!.getInt("max_match_num", 150)
		set(maxMatchNum) {
			val editor = sp!!.edit()
			editor.putInt("max_match_num", maxMatchNum)
			editor.apply()
			Log.i("match_num_set", Integer.toString(maxMatchNum))
		}

	//This is done automatically
	fun setYear() {
		val editor = sp!!.edit()
		editor.putString(year, "DEFAULT")
		editor.apply()
	}

	val year: String
		get() = Calendar.getInstance()[Calendar.YEAR].toString()

	//Note: the hashed password is the value of the "change password" preference
//Method hashes plaintext with SHA-1, then sets it to the value
	fun setPassword(plainPass: String?) {
		val editor = sp!!.edit()
		editor.putString("change_pass",
				String(Hex.encodeHex(DigestUtils.sha1(plainPass))).toLowerCase())
		editor.apply()
	}

	fun matchesPassword(plainPass: String?): Boolean {
		val hash = String(Hex.encodeHex(DigestUtils.sha1(plainPass))).toLowerCase()
		return hash == hashedPass || hash ==
				"f98960d9aae06c642bae4f24b5152e1bd5cc2c42" //User-set password or master
		// password will work
	}

	val hashedPass: String
		get() = sp!!.getString("change_pass", "DEFAULT")

	/**
	 * Method to intelligently detect changes or irregularities in scouting pattern for the next
	 * match
	 *
	 * @param preMatch - a PreMatch object with all data fields filled in
	 */
	fun autoSetPreferences(preMatch: PreMatch) {
		scoutName = preMatch.scoutName
		scoutPos = preMatch.scoutPos
		matchNum = preMatch.matchNum
	}

	var aPIKey: String?
		get() = getString(R.string.tba_api_key)
		set(key) {
			val editor = sp!!.edit()
			editor.putString("api_key", key)
			editor.apply()
		}

	val leftAlliance: String
		get() = sp!!.getString("leftStation", "Red Alliance")

	companion object {
		// Use getBaseContext() or getActivity() when calling this
		/**
		 * Get a new instance of a Settings object to retrieve data
		 *
		 * @param c `Context` of the running stack
		 * @return A Settings object ot read data from
		 */
        @JvmStatic
        fun newInstance(c: Context?): Settings {
			val set = Settings()
			set.sp = PreferenceManager.getDefaultSharedPreferences(c)
			return set
		}
	}
}