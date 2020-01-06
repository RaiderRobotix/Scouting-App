package org.usfirst.frc.team25.scouting.ui

import android.app.Activity
import android.content.Context
import android.util.SparseIntArray
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import org.usfirst.frc.team25.scouting.R

object UiHelper {
	fun hideKeyboard(activity: Activity) {
		try {
			val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
			inputManager.hideSoftInputFromWindow(activity.currentFocus.windowToken
					, InputMethodManager.HIDE_NOT_ALWAYS)
		} catch (e: NullPointerException) {
			e.printStackTrace()
		}
	}

	/**
	 * A method that receives a RadioButton array and returns an integer corresponding to the hab
	 * level selected
	 *
	 * @param habLevelArray Provide a RadioButtonGroup with three hab level options
	 * @return integer Returns integer value corresponding to hab levels 1,2, and 3
	 */
	fun getHabLevelSelected(habLevelArray: Array<RadioButton>): Int {
		for (i in habLevelArray.indices) {
			if (habLevelArray[i].isChecked) {
				return i + 1
			}
		}
		return 0
	}

	fun getHabLevelSelected(habLevelArray: Array<RadioButton>, shiftIndex: Int): Int {
		for (i in habLevelArray.indices) {
			if (habLevelArray[i].isChecked) {
				return i + shiftIndex
			}
		}
		return 0
	}

	fun getIntegerFromTextBox(numberEditText: EditText): Int {
		return if (numberEditText.text.toString().isEmpty()) {
			0
		} else {
			numberEditText.text.toString().toInt()
		}
	}

	fun radioButtonEnable(groupToEnableOrDisable: RadioGroup, modeSelected: Boolean) {
		if (modeSelected) {
			for (i in 0 until groupToEnableOrDisable.childCount) {
				groupToEnableOrDisable.getChildAt(i).isEnabled = true
			}
		} else {
			for (i in 0 until groupToEnableOrDisable.childCount) {
				groupToEnableOrDisable.getChildAt(i).isEnabled = false
			}
			groupToEnableOrDisable.clearCheck()
		}
	}

	fun checkIfButtonIsChecked(groupToCheck: Array<RadioButton>): Boolean {
		for (button in groupToCheck) {
			if (button.isChecked) {
				return true
			}
		}
		return false
	}

	fun autoSetTheme(activity: Activity, teamNum: Int) {
		val teamNumThemePairs = SparseIntArray()
		teamNumThemePairs[2590] = R.style.AppTheme_NoLauncher_Red
		teamNumThemePairs[225] = R.style.AppTheme_NoLauncher_Red
		teamNumThemePairs[303] = R.style.AppTheme_NoLauncher_Green
		teamNumThemePairs[25] = R.style.AppTheme_NoLauncher_Raider
		teamNumThemePairs[1923] = R.style.AppTheme_NoLauncher_Black
		
		activity.setTheme(
				teamNumThemePairs.get(teamNum, R.style.AppTheme_NoLauncher_Blue)
		)
	}
	
	operator fun SparseIntArray.set(index:Int,item:Int) {
		put(index,item)
	}
}