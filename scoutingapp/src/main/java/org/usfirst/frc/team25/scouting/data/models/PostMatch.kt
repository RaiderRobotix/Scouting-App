package org.usfirst.frc.team25.scouting.data.models

import android.widget.CheckBox
import java.util.*

/**
 * Qualitative reflection on the robot's performance after a match
 * Not to be used for end game actions
 */
data class PostMatch(var robotComment: String, @field:Transient var robotQuickComments: ArrayList<CheckBox>,
                @field:Transient val robotQuickCommentValues: Array<String>, var focus: String, val teamOneCompare: Int,
                val teamTwoCompare: Int, val comparison: String, val pickNumber: Int) {
	private val robotQuickCommentSelections: HashMap<String, Boolean> = HashMap()
	
	fun finalizeComment() {
		for (value in robotQuickCommentValues) {
			for (cb in robotQuickComments) {
				if (cb.text.toString() == value) {
					robotQuickCommentSelections[value] = cb.isChecked
				}
			}
		}
		
		robotComment = robotComment.replace("[,\n]", ";") //prevent csv
// problems
	}
	
}