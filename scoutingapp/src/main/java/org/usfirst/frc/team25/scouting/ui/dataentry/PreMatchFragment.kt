package org.usfirst.frc.team25.scouting.ui.dataentry

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView
import com.rengwuxian.materialedittext.MaterialEditText
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import org.usfirst.frc.team25.scouting.R
import org.usfirst.frc.team25.scouting.data.FileManager.addToTeamList
import org.usfirst.frc.team25.scouting.data.FileManager.getMaxMatchNum
import org.usfirst.frc.team25.scouting.data.FileManager.getTeamPlaying
import org.usfirst.frc.team25.scouting.data.FileManager.isOnTeamlist
import org.usfirst.frc.team25.scouting.data.Settings.Companion.newInstance
import org.usfirst.frc.team25.scouting.data.models.PreMatch
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry
import org.usfirst.frc.team25.scouting.ui.UiHelper
import java.io.IOException

class PreMatchFragment : EntryFragment() {
	private var startingPositionButtons: Array<RadioButton?>
	private var startingLevelButtons: Array<RadioButton?>
	private var startingGamePieceButtons: Array<RadioButton?>
	private var startingGamePieceGroup: RadioGroup? = null
	private var startingLevelButtonsGroup: RadioGroup? = null
	private var startingPositionButtonsGroup: RadioGroup? = null
	private var nameField: MaterialEditText? = null
	private var matchNumField: MaterialEditText? = null
	private var teamNumField: MaterialEditText? = null
	private var scoutPosSpinner: MaterialBetterSpinner? = null
	private override var entry: ScoutEntry? = null
	private var robotNoShow: CheckBox? = null
	public override fun getEntry(): ScoutEntry? {
		return entry
	}

	public override fun setEntry(entry: ScoutEntry?) {
		this.entry = entry
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
	                          savedInstanceState: Bundle): View {
		val view = inflater.inflate(R.layout.fragment_prematch, container, false)
		val continueButton = view.findViewById<Button>(R.id.prematch_continue)
		scoutPosSpinner = view.findViewById(R.id.scout_pos_spin)
		scoutPosSpinner.setAdapter(ArrayAdapter(activity,
				android.R.layout.simple_dropdown_item_1line,
				resources.getStringArray(R.array.position_options)))
		scoutPosSpinner.setFloatingLabel(MaterialAutoCompleteTextView.FLOATING_LABEL_NORMAL)
		robotNoShow = view.findViewById(R.id.robot_no_show_checkbox)
		startingLevelButtons = arrayOfNulls(2)
		startingLevelButtons[0] = view.findViewById(R.id.hab_level_1)
		startingLevelButtons[1] = view.findViewById(R.id.hab_level_2)
		nameField = view.findViewById(R.id.scout_name_field)
		matchNumField = view.findViewById(R.id.match_num_field)
		teamNumField = view.findViewById(R.id.team_num_field)
		startingPositionButtons = arrayOfNulls(3)
		startingPositionButtons[0] = view.findViewById(R.id.leftStart)
		startingPositionButtons[1] = view.findViewById(R.id.centerStart)
		startingPositionButtons[2] = view.findViewById(R.id.rightStart)
		startingGamePieceButtons = arrayOfNulls(3)
		startingGamePieceButtons[0] = view.findViewById(R.id.cargo_button)
		startingGamePieceButtons[1] = view.findViewById(R.id.hatch_panel_button)
		startingGamePieceButtons[2] = view.findViewById(R.id.none_button)
		startingLevelButtonsGroup = view.findViewById(R.id.starting_level)
		startingPositionButtonsGroup = view.findViewById(R.id.starting_position)
		startingGamePieceGroup = view.findViewById(R.id.robot_starting_game_piece)
		autoPopulate()
		// Nudges the user to fill in the next unfilled text field
		val fields = arrayOf<EditText?>(nameField, matchNumField, teamNumField)
		for (field in fields) {
			if (field!!.text.toString().isEmpty()) {
				field.requestFocus()
			}
		}
		startingLevelButtons[1].setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
			if (b) {
				startingPositionButtons[1].setEnabled(false)
				startingPositionButtons[1].setChecked(false)
			} else {
				startingPositionButtons[1].setEnabled(true)
			}
		})
		robotNoShow.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton: CompoundButton?, becameChecked: Boolean ->
			UiHelper.radioButtonEnable(startingLevelButtonsGroup, !becameChecked)
			UiHelper.radioButtonEnable(startingPositionButtonsGroup, !becameChecked)
			UiHelper.radioButtonEnable(startingGamePieceGroup, !becameChecked)
		})
		continueButton.setOnClickListener { view1: View? ->
			var proceed = true
			val set = newInstance(activity)
			set.maxMatchNum = getMaxMatchNum(activity)
			// Sequentially verifies that user inputted a value
			if (nameField.getText().toString().isEmpty()) {
				nameField.setError("Scout name required")
				proceed = false
			}
			if (scoutPosSpinner.getText().toString().isEmpty()) {
				scoutPosSpinner.setError("Scout position required")
				proceed = false
			}
			if (matchNumField.getText().toString().isEmpty()) {
				matchNumField.setError("Match number required")
				proceed = false
			} else if (matchNumField.getText().toString().toInt() < 1 ||
					matchNumField.getText().toString().toInt() > newInstance(activity).maxMatchNum) {
				matchNumField.setError("Invalid match number value")
				proceed = false
			}
			if (teamNumField.getText().toString().length == 0 || teamNumField.getText().toString().toInt() < 1 || teamNumField.getText().toString().toInt() > 9999) {
				if (teamNumField.getText().length == 0) {
					teamNumField.setError("Team number required")
				} else {
					teamNumField.setError("Invalid team number")
				}
				proceed = false
			}
			var startingValuesSelected = true
			val startingValueButtons = arrayOf(startingPositionButtons,
					startingLevelButtons, startingGamePieceButtons)
			for (startingValueSet in startingValueButtons) {
				var buttonSelectedInSet = false
				for (button in startingValueSet) {
					if (button!!.isChecked) {
						buttonSelectedInSet = true
						break
					}
				}
				if (!buttonSelectedInSet) {
					startingValuesSelected = false
					break
				}
			}
			if (proceed && !robotNoShow.isChecked() && !startingValuesSelected) {
				proceed = false
				val builder = AlertDialog.Builder(activity)
				builder.setTitle("Select robot starting level/position/game piece")
						.setCancelable(false)
						.setPositiveButton("OK") { dialog: DialogInterface?, id: Int -> }
				val alert = builder.create()
				alert.show()
			}
			if (proceed && robotNoShow.isChecked()) {
				proceed = false
				val builder = AlertDialog.Builder(activity)
				builder.setNegativeButton("No") { dialogInterface: DialogInterface?, i: Int -> }
						.setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int -> continueToAuto() }
				val dialogBox = inflater.inflate(R.layout.view_robot_no_show, null)
				builder.setView(dialogBox)
				val alertDialog = builder.show()
				val button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
				button.isEnabled = false
				button.setTextColor(Color.parseColor("#c3c3c3"))
				val yesConfirmation = dialogBox.findViewById<EditText>(R.id.yes_text_input)
				yesConfirmation.addTextChangedListener(object : TextWatcher {
					override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int,
					                               i2: Int) {
					}

					override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int,
					                           i2: Int) {
					}

					override fun afterTextChanged(editable: Editable) {
						if (editable.toString() == "YES") {
							alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).isEnabled = true
							button.setTextColor(Color.parseColor("#000000"))
						} else {
							alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).isEnabled = false
							button.setTextColor(Color.parseColor("#c3c3c3"))
						}
					}
				})
			}
			// If all normal checks pass, verify against team lists or match schedule
			if (newInstance(activity).useTeamList() && proceed) {
				var checkTeamList = false
				try { // Non-quals matches don't get checked against match schedule
					if (newInstance(activity).matchType != "Q") {
						checkTeamList = true
					} else if (getTeamPlaying(activity,
									scoutPosSpinner.getText().toString(), matchNumField.getText().toString().toInt()) != teamNumField.getText().toString()) {
						proceed = false
						AlertDialog.Builder(activity)
								.setTitle("Confirm team number against the field, " + nameField.getText().toString() + "!!")
								.setMessage("Are you sure that team " + teamNumField.getText().toString() + " is " +
										scoutPosSpinner.getText().toString() + " in match " + matchNumField.getText().toString() + "?")
								.setPositiveButton("Yes") { dialog: DialogInterface?, which: Int -> continueToAuto() }
								.setNegativeButton("No") { dialog: DialogInterface?, which: Int -> }
								.create()
								.show()
						continueToAuto()
					}
				} catch (e: IOException) { //Match list does not exist; looking for team list
					checkTeamList = true
				}
				if (checkTeamList && !isOnTeamlist(teamNumField.getText().toString(),
								activity)) {
					proceed = false
					AlertDialog.Builder(activity)
							.setTitle("Confirm team number against the field, " + nameField.getText().toString() + "!!")
							.setMessage("Are you sure that team " + teamNumField.getText().toString() + " is playing at " + set.currentEvent + "?")
							.setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
								addToTeamList(teamNumField.getText().toString(),
										activity)
								continueToAuto()
							}
							.setNegativeButton("No") { dialog: DialogInterface?, which: Int -> }
							.create()
							.show()
				}
			}
			if (proceed) {
				continueToAuto()
			}
		}
		return view
	}

	public override fun autoPopulate() { //Manually filled data overrides preferences
		if (entry!!.preMatch != null) {
			val (scoutName, scoutPos, startingPos, matchNum, teamNum, startingLevel, _, startingGamePiece) = entry!!.preMatch
			nameField!!.setText(scoutName)
			scoutPosSpinner!!.setText(scoutPos)
			matchNumField!!.setText(matchNum.toString())
			teamNumField!!.setText(teamNum.toString())
			for (button in startingPositionButtons) {
				if (button!!.text == startingPos) {
					button.isChecked = true
				}
			}
			for (button in startingGamePieceButtons) {
				if (button!!.text.toString() == startingGamePiece) {
					button.isChecked = true
				}
			}
			for (button in startingLevelButtons) {
				if (button!!.text.toString().contains(Integer.toString(startingLevel))) {
					button.isChecked = true
				}
			}
			robotNoShow!!.isChecked = robotNoShow!!.isChecked
			startingPositionButtons[1]!!.isEnabled = startingLevel == 1
		} else {
			val set = newInstance(activity)
			if (set.scoutPos != "DEFAULT") {
				scoutPosSpinner!!.setText(set.scoutPos)
				if (set.useTeamList() && set.matchType == "Q") {
					try {
						teamNumField!!.setText(getTeamPlaying(activity,
								set.scoutPos, set.matchNum))
					} catch (e: IOException) {
						e.printStackTrace()
					}
				}
			}
			//Scout nameField is prompted for after a shift ends, but not during the first match
			if (set.scoutName != "DEFAULT" && (set.matchNum - 1) % set.shiftDur != 0 || set.matchNum == 1) {
				nameField!!.setText(set.scoutName)
			}
			matchNumField!!.setText(set.matchNum.toString())
		}
	}

	public override fun saveState() {
		var startPos = ""
		for (button in startingPositionButtons) {
			if (button!!.isChecked) {
				startPos = button.text as String
				break
			}
		}
		var startPiece = ""
		for (button in startingGamePieceButtons) {
			if (button!!.isChecked) {
				startPiece = button.text as String
				break
			}
		}
		entry!!.preMatch = PreMatch(nameField!!.text.toString(),
				scoutPosSpinner!!.text.toString(),
				startPos, matchNumField!!.text.toString().toInt(), teamNumField!!.text.toString().toInt(),
				UiHelper.getHabLevelSelected(startingLevelButtons),
				robotNoShow!!.isChecked, startPiece
		)
	}

	private fun continueToAuto() {
		saveState()
		newInstance(activity).autoSetPreferences(entry!!.preMatch)
		UiHelper.autoSetTheme(activity, entry!!.preMatch.teamNum)
		UiHelper.hideKeyboard(activity)
		fragmentManager.beginTransaction()
				.replace(android.R.id.content, AutoFragment.Companion.getInstance(entry), "AUTO")
				.commit()
	}

	override fun onResume() {
		super.onResume()
		activity.title = "Add Entry - Pre-Match"
	}

	companion object {
		fun getInstance(entry: ScoutEntry?): PreMatchFragment {
			val prematchFragment = PreMatchFragment()
			prematchFragment.setEntry(entry)
			return prematchFragment
		}
	}
}