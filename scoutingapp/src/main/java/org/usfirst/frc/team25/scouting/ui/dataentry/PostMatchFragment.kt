package org.usfirst.frc.team25.scouting.ui.dataentry

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.rengwuxian.materialedittext.MaterialEditText
import org.usfirst.frc.team25.scouting.R
import org.usfirst.frc.team25.scouting.data.FileManager.getPrevScoutName
import org.usfirst.frc.team25.scouting.data.FileManager.getPrevTeamNumber
import org.usfirst.frc.team25.scouting.data.FileManager.saveData
import org.usfirst.frc.team25.scouting.data.Settings.Companion.newInstance
import org.usfirst.frc.team25.scouting.data.models.PostMatch
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry
import java.util.*

class PostMatchFragment : EntryFragment() {
	private val focusButtons = arrayOfNulls<CheckBox>(5)
	private val comparisonButtons = arrayOfNulls<RadioButton>(3)
	private val pickNumberButtons = arrayOfNulls<RadioButton>(3)
	private val ROBOT_COMMENT_VALUES = arrayOf(
			"Low objectives only",
			"Scoring potential reached",
			"Hatch panel floor intake",
			"Cargo floor intake",
			"Fast HAB climb",
			"Shoots cargo",
			"Effective defense",
			"Higher rocket levels significantly slower",
			"Struggles to place in far rocket side",
			"Poorly-secured hatches",
			"Places extra cargo in bays",
			"Slow cargo intake",
			"Slow hatch panel intake",
			"Lost communications",
			"Tipped over",
			"Commits (tech) fouls (explain)",
			"Do not pick (explain)",
			"Possible inaccurate data (specify)")
	private override var entry: ScoutEntry? = null
	private var robotComment: MaterialEditText? = null
	private var robotCommentView: RelativeLayout? = null
	private var robotQuickComments: ArrayList<CheckBox>? = null
	public override fun getEntry(): ScoutEntry? {
		return entry
	}

	public override fun setEntry(entry: ScoutEntry?) {
		this.entry = entry
	}

	public override fun autoPopulate() {
		if (entry!!.postMatch != null) {
			val robotCheckedComments: ArrayList<CheckBox> = entry!!.postMatch.robotQuickComments
			for (i in robotCheckedComments.indices) {
				if (robotCheckedComments[i].isChecked) {
					robotQuickComments!![i].isChecked = true
				}
			}
			robotComment!!.setText(entry!!.postMatch.robotComment)
			// Parse focus string
			for (cb in focusButtons) {
				for (item in entry!!.postMatch.focus.split("; ").toTypedArray()) {
					if (cb!!.text == item) {
						cb.isChecked = true
					}
				}
			}
			val comparisonValues = arrayOf(">", "<", "=")
			for (i in comparisonValues.indices) {
				if (entry!!.postMatch.comparison == comparisonValues[i]) {
					comparisonButtons[i]!!.isChecked = true
				}
			}
			try {
				pickNumberButtons[2 - entry!!.postMatch.pickNumber]!!.isChecked = true
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	public override fun saveState() {
		val focus = StringBuilder()
		for (cb in focusButtons) {
			if (cb!!.isChecked) {
				if (focus.toString() != "") {
					focus.append("; ")
				}
				focus.append(cb.text as String)
			}
		}
		var comparison = ""
		val comparisonValues = arrayOf(">", "<", "=")
		for (i in comparisonButtons.indices) {
			if (comparisonButtons[i]!!.isChecked) {
				comparison = comparisonValues[i]
			}
		}
		var pickNumber = -1
		for (i in pickNumberButtons.indices) {
			if (pickNumberButtons[i]!!.isChecked) {
				pickNumber = 2 - i
			}
		}
		entry!!.postMatch = PostMatch(robotComment!!.text.toString(), robotQuickComments!!,
				ROBOT_COMMENT_VALUES, focus.toString(), entry!!.preMatch.teamNum,
				getPrevTeamNumber(activity), comparison, pickNumber)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
	                          savedInstanceState: Bundle): View {
		val view = inflater.inflate(R.layout.fragment_post_match, container, false)
		robotComment = view.findViewById(R.id.robotDriverComment)
		robotCommentView = view.findViewById(R.id.robotDriverCommentView)
		val finish = view.findViewById<Button>(R.id.post_finish)
		focusButtons[0] = view.findViewById(R.id.teleop_focus_hatches)
		focusButtons[1] = view.findViewById(R.id.teleop_focus_cargo)
		focusButtons[2] = view.findViewById(R.id.teleop_focus_cargo_ship)
		focusButtons[3] = view.findViewById(R.id.teleop_focus_rocket)
		focusButtons[4] = view.findViewById(R.id.teleop_focus_defense)
		comparisonButtons[0] = view.findViewById(R.id.current_team_comparison)
		comparisonButtons[1] = view.findViewById(R.id.prev_team_comparison)
		comparisonButtons[2] = view.findViewById(R.id.either_team_comparison)
		pickNumberButtons[0] = view.findViewById(R.id.first_pick)
		pickNumberButtons[1] = view.findViewById(R.id.second_pick)
		pickNumberButtons[2] = view.findViewById(R.id.dnp)
		val prevTeamNum = getPrevTeamNumber(activity)
		val currentRobotCompareStr = "Team " + entry!!.preMatch.teamNum + " (current " +
				"robot scouted)"
		val prevRobotCompareStr = "Team $prevTeamNum (previous robot scouted)"
		comparisonButtons[0].setText(currentRobotCompareStr)
		comparisonButtons[1].setText(prevRobotCompareStr)
		// No previous data found, so remove it as an option
		if (prevTeamNum == 0 || this.entry!!.preMatch.scoutName != getPrevScoutName(activity)) {
			comparisonButtons[2].setChecked(true)
			view.findViewById<View>(R.id.prev_team_comparison_group).visibility = View.GONE
		}
		robotQuickComments = ArrayList()
		generateRobotQuickComments()
		autoPopulate()
		finish.setOnClickListener { view1: View? ->
			var focusChecked = false
			var comparisonSelected = false
			var pickNumberSelected = false
			for (cb in focusButtons) {
				if (cb!!.isChecked) {
					focusChecked = true
				}
			}
			for (button in comparisonButtons) {
				if (button!!.isChecked) {
					comparisonSelected = true
				}
			}
			for (button in pickNumberButtons) {
				if (button!!.isChecked) {
					pickNumberSelected = true
				}
			}
			if (!focusChecked && !entry!!.preMatch.isRobotNoShow) {
				val builder = AlertDialog.Builder(activity)
				builder.setTitle("Select the robot's focus for this match")
						.setCancelable(false)
						.setPositiveButton("OK") { dialog: DialogInterface?, id: Int -> }
				val alert = builder.create()
				alert.show()
			} else if (!comparisonSelected) {
				val builder = AlertDialog.Builder(activity)
				builder.setTitle("Compare the robot to the previous one")
						.setCancelable(false)
						.setPositiveButton("OK") { dialog: DialogInterface?, id: Int -> }
				val alert = builder.create()
				alert.show()
			} else if (!pickNumberSelected) {
				val builder = AlertDialog.Builder(activity)
				builder.setTitle("Select which pick this robot would be")
						.setCancelable(false)
						.setPositiveButton("OK") { dialog: DialogInterface?, id: Int -> }
				val alert = builder.create()
				alert.show()
			} else {
				saveState()
				entry!!.postMatch.finalizeComment()
				val set = newInstance(activity)
				if (set.matchType == "P") {
					Toast.makeText(activity.baseContext, "Practice match data NOT " +
							"saved", Toast.LENGTH_LONG).show()
				} else {
					saveData(entry, activity)
					Toast.makeText(activity.baseContext, "Match data saved",
							Toast.LENGTH_LONG).show()
				}
				activity.setTheme(R.style.AppTheme_NoLauncher_Blue)
				set.matchNum = set.matchNum + 1
				activity.finish()
			}
		}
		if (entry!!.preMatch.isRobotNoShow) {
			comparisonButtons[1].setChecked(true)
			pickNumberButtons[2].setChecked(true)
			finish.callOnClick()
		}
		return view
	}

	private fun generateRobotQuickComments() {
		var prevId = -1
		var i = 0
		while (i < Math.ceil(ROBOT_COMMENT_VALUES.size / 2.0)) {
			val checkSetValues = ArrayList<String>()
			checkSetValues.add(ROBOT_COMMENT_VALUES[i * 2])
			try {
				checkSetValues.add(ROBOT_COMMENT_VALUES[i * 2 + 1])
			} catch (e: IndexOutOfBoundsException) {
				e.printStackTrace()
			}
			val checkSet = LinearLayout(activity)
			val leftComment = CheckBox(activity)
			val params = LinearLayout.LayoutParams(1,
					ViewGroup.LayoutParams.WRAP_CONTENT, 1)
			leftComment.layoutParams = params
			leftComment.text = checkSetValues[0]
			leftComment.setPadding(5, 3, 3, 5)
			robotQuickComments!!.add(leftComment)
			checkSet.addView(leftComment)
			if (checkSetValues.size != 1) {
				val rightComment = CheckBox(activity)
				rightComment.layoutParams = params
				rightComment.text = checkSetValues[1]
				rightComment.setPadding(3, 3, 5, 5)
				robotQuickComments!!.add(rightComment)
				checkSet.addView(rightComment)
			}
			val currentId = ViewGroup.generateViewId()
			checkSet.id = currentId
			val rlp = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT)
			rlp.setMargins(10, 10, 10, 10)
			if (i == 0) {
				rlp.addRule(RelativeLayout.BELOW, R.id.robotDriverQuickCommentHint)
			} else {
				rlp.addRule(RelativeLayout.BELOW, prevId)
			}
			prevId = currentId
			robotCommentView!!.addView(checkSet, rlp)
			i++
		}
		val robotCommentParams = robotComment!!.layoutParams as RelativeLayout.LayoutParams
		robotCommentParams.addRule(RelativeLayout.BELOW, prevId)
		robotComment!!.layoutParams = robotCommentParams
	}

	override fun onResume() {
		activity.title = "Add Entry - Post Match"
		super.onResume()
	}

	companion object {
		fun getInstance(entry: ScoutEntry?): PostMatchFragment {
			val postmatchFragment = PostMatchFragment()
			postmatchFragment.setEntry(entry)
			return postmatchFragment
		}
	}
}