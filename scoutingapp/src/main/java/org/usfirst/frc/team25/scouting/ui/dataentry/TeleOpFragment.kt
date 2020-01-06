package org.usfirst.frc.team25.scouting.ui.dataentry

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.usfirst.frc.team25.scouting.R
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry
import org.usfirst.frc.team25.scouting.data.models.TeleOp
import org.usfirst.frc.team25.scouting.ui.UiHelper
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView

class TeleOpFragment : EntryFragment() {
	private override var entry: ScoutEntry? = null
	private var attemptHabClimbLevel: Array<RadioButton?>
	private var successHabClimbLevel: Array<RadioButton?>
	private var highestAssistedClimbLevel: Array<RadioButton?>
	private var climbAssistStartingLevel: Array<RadioButton?>
	private var attemptHabClimbLevelGroup: RadioGroup? = null
	private var successHabClimbLevelGroup: RadioGroup? = null
	private var highestAssistedClimbLevelGroup: RadioGroup? = null
	private var climbAssistStartingLevelGroup: RadioGroup? = null
	private var cargoShipHatches: ButtonIncDecSet? = null
	private var cargoShipCargo: ButtonIncDecSet? = null
	private var rocketLevelOneHatches: ButtonIncDecSet? = null
	private var rocketLevelOneCargo: ButtonIncDecSet? = null
	private var rocketLevelTwoHatches: ButtonIncDecSet? = null
	private var rocketLevelTwoCargo: ButtonIncDecSet? = null
	private var rocketLevelThreeHatches: ButtonIncDecSet? = null
	private var rocketLevelThreeCargo: ButtonIncDecSet? = null
	private var attemptHabClimb: CheckBox? = null
	private var successHabClimb: CheckBox? = null
	private var partnerClimbsAssisted: ButtonIncDecView? = null
	private var hatchesDropped: ButtonIncDecView? = null
	private var cargoDropped: ButtonIncDecView? = null
	private var climbAssistedByPartners: CheckBox? = null
	private var assistingClimbTeamNum: EditText? = null
	public override fun getEntry(): ScoutEntry? {
		return entry
	}

	public override fun setEntry(entry: ScoutEntry?) {
		this.entry = entry
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
	                          savedInstanceState: Bundle): View {
		val view = inflater.inflate(R.layout.fragment_tele_op, container, false)
		cargoShipHatches = view.findViewById(R.id.cargo_ship_hatches_teleop)
		rocketLevelOneHatches = view.findViewById(R.id.rocket_level_one_hatches_teleop)
		rocketLevelOneCargo = view.findViewById(R.id.rocket_level_one_cargo_teleop)
		rocketLevelTwoHatches = view.findViewById(R.id.rocket_level_two_hatches_teleop)
		rocketLevelTwoCargo = view.findViewById(R.id.rocket_level_two_cargo_teleop)
		rocketLevelThreeHatches = view.findViewById(R.id.rocket_level_three_hatches_teleop)
		rocketLevelThreeCargo = view.findViewById(R.id.rocket_level_three_cargo_teleop)
		assistingClimbTeamNum = view.findViewById(R.id.team_number_text_input)
		climbAssistedByPartners = view.findViewById(R.id.climb_assisted_by_alliance_partner_checkbox)
		partnerClimbsAssisted = view.findViewById(R.id.alliance_partner_climbs_assisted)
		attemptHabClimb = view.findViewById(R.id.attempt_hab_climb)
		successHabClimb = view.findViewById(R.id.success_hab_climb)
		cargoShipCargo = view.findViewById(R.id.cargo_ship_cargo_teleop)
		hatchesDropped = view.findViewById(R.id.hatches_dropped_teleop)
		cargoDropped = view.findViewById(R.id.cargo_dropped_teleop)
		val continueButton = view.findViewById<Button>(R.id.tele_continue)
		attemptHabClimbLevelGroup = view.findViewById(R.id.attempt_hab_climb_level)
		successHabClimbLevelGroup = view.findViewById(R.id.success_hab_climb_level)
		highestAssistedClimbLevelGroup = view.findViewById(R.id.highest_climb_assist_radio_group)
		climbAssistStartingLevelGroup = view.findViewById(R.id.climb_assist_starting_level_group)
		attemptHabClimbLevel = arrayOfNulls(3)
		attemptHabClimbLevel[0] = view.findViewById(R.id.attempt_hab_level_1)
		attemptHabClimbLevel[1] = view.findViewById(R.id.attempt_hab_level_2)
		attemptHabClimbLevel[2] = view.findViewById(R.id.attempt_hab_level_3)
		successHabClimbLevel = arrayOfNulls(3)
		successHabClimbLevel[0] = view.findViewById(R.id.success_hab_level_1)
		successHabClimbLevel[1] = view.findViewById(R.id.success_hab_level_2)
		successHabClimbLevel[2] = view.findViewById(R.id.success_hab_level_3)
		highestAssistedClimbLevel = arrayOfNulls(2)
		highestAssistedClimbLevel[0] = view.findViewById(R.id.highest_assisted_climb_one)
		highestAssistedClimbLevel[1] = view.findViewById(R.id.highest_assisted_climb_two)
		climbAssistStartingLevel = arrayOfNulls(3)
		climbAssistStartingLevel[0] = view.findViewById(R.id.climb_assist_starting_zero)
		climbAssistStartingLevel[1] = view.findViewById(R.id.climb_assist_starting_one)
		climbAssistStartingLevel[2] = view.findViewById(R.id.climb_assist_starting_two)
		for (anAttemptHabClimbLevel in attemptHabClimbLevel) {
			anAttemptHabClimbLevel!!.setOnClickListener { view1: View? -> autoDisableSuccessGroup() }
		}
		for (button in highestAssistedClimbLevel) {
			button!!.setOnClickListener { view1: View? -> autoDisableClimbAssistStartingLevelGroup() }
		}
		successHabClimb.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
			if (b) {
				UiHelper.radioButtonEnable(successHabClimbLevelGroup, true)
				autoDisableSuccessGroup()
			} else {
				UiHelper.radioButtonEnable(successHabClimbLevelGroup, false)
			}
		})
		attemptHabClimb.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
			if (b) {
				successHabClimb.setEnabled(true)
				UiHelper.radioButtonEnable(attemptHabClimbLevelGroup, true)
			} else {
				successHabClimb.setEnabled(false)
				successHabClimb.setChecked(false)
				UiHelper.radioButtonEnable(attemptHabClimbLevelGroup, false)
				UiHelper.radioButtonEnable(successHabClimbLevelGroup, false)
			}
		})
		climbAssistedByPartners.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
			if (b) {
				assistingClimbTeamNum.setEnabled(true)
			} else {
				assistingClimbTeamNum.setEnabled(false)
				assistingClimbTeamNum.setText("")
			}
		})
		partnerClimbsAssisted.decButton.setOnClickListener { view1: View? ->
			partnerClimbsAssisted.decrement()
			if (partnerClimbsAssisted.getValue() == 0) {
				UiHelper.radioButtonEnable(highestAssistedClimbLevelGroup, false)
				UiHelper.radioButtonEnable(climbAssistStartingLevelGroup, false)
			}
		}
		partnerClimbsAssisted.incButton.setOnClickListener { view1: View? ->
			partnerClimbsAssisted.increment()
			if (partnerClimbsAssisted.getValue() > 0) {
				UiHelper.radioButtonEnable(highestAssistedClimbLevelGroup, true)
				UiHelper.radioButtonEnable(climbAssistStartingLevelGroup, true)
			}
		}
		autoPopulate()
		continueButton.setOnClickListener { view1: View? ->
			UiHelper.hideKeyboard(activity)
			var proceed = true
			if (climbAssistedByPartners.isChecked()) {
				if (assistingClimbTeamNum.getText().toString().isEmpty()) {
					assistingClimbTeamNum.setError("Enter the assisting team's number")
					proceed = false
				} else {
					val inputTeamNum = assistingClimbTeamNum.getText().toString().toInt()
					if (inputTeamNum <= 0 || inputTeamNum > 9999) {
						assistingClimbTeamNum.setError("Enter a valid team number")
						proceed = false
					}
				}
			}
			if (proceed && partnerClimbsAssisted.getValue() >= 1 &&
					!(UiHelper.checkIfButtonIsChecked(highestAssistedClimbLevel) && UiHelper.checkIfButtonIsChecked(climbAssistStartingLevel))
					|| attemptHabClimb.isChecked() && !UiHelper.checkIfButtonIsChecked(attemptHabClimbLevel) || (successHabClimb.isChecked()
							&& !UiHelper.checkIfButtonIsChecked(successHabClimbLevel))) {
				proceed = false
				val builder = AlertDialog.Builder(activity)
				builder.setTitle("Please fill in any empty HAB climb fields")
						.setCancelable(false)
						.setPositiveButton("OK") { dialog: DialogInterface?, id: Int -> }
				val alert = builder.create()
				alert.show()
			}
			if (proceed) {
				saveState()
				fragmentManager
						.beginTransaction()
						.replace(android.R.id.content, PostMatchFragment.Companion.getInstance(entry), "POST")
						.commit()
			}
		}
		if (entry!!.preMatch.isRobotNoShow) {
			continueButton.callOnClick()
		}
		return view
	}

	private fun autoDisableClimbAssistStartingLevelGroup() {
		if (highestAssistedClimbLevel[0]!!.isChecked) {
			if (climbAssistStartingLevel[2]!!.isChecked) {
				climbAssistStartingLevelGroup!!.clearCheck()
			}
			climbAssistStartingLevel[2]!!.isEnabled = false
		} else if (partnerClimbsAssisted!!.value > 0) {
			climbAssistStartingLevel[2]!!.isEnabled = true
		}
	}

	public override fun autoPopulate() {
		if (entry!!.teleOp != null) {
			val (cargoShipHatches1, rocketLevelOneHatches1, rocketLevelTwoHatches1, rocketLevelThreeHatches1, cargoShipCargo1, rocketLevelOneCargo1, rocketLevelTwoCargo1, rocketLevelThreeCargo1, hatchesDropped1, cargoDropped1, isAttemptHabClimb, attemptHabClimbLevel1, isSuccessHabClimb, successHabClimbLevel1, isClimbAssistedByPartner, assistingClimbTeamNum1, numPartnerClimbAssists, partnerClimbAssistEndLevel, partnerClimbAssistStartLevel) = entry!!.teleOp
			cargoShipHatches!!.value = cargoShipHatches1
			cargoShipCargo!!.value = cargoShipCargo1
			rocketLevelOneCargo!!.value = rocketLevelOneCargo1
			rocketLevelOneHatches!!.value = rocketLevelOneHatches1
			rocketLevelTwoCargo!!.value = rocketLevelTwoCargo1
			rocketLevelTwoHatches!!.value = rocketLevelTwoHatches1
			rocketLevelThreeCargo!!.value = rocketLevelThreeCargo1
			rocketLevelThreeHatches!!.value = rocketLevelThreeHatches1
			climbAssistedByPartners!!.isChecked = isClimbAssistedByPartner
			partnerClimbsAssisted!!.value = numPartnerClimbAssists
			hatchesDropped!!.value = hatchesDropped1
			cargoDropped!!.value = cargoDropped1
			attemptHabClimb!!.isChecked = isAttemptHabClimb
			successHabClimb!!.isChecked = isSuccessHabClimb
			if (assistingClimbTeamNum1 != 0) {
				assistingClimbTeamNum!!.setText(Integer.toString(assistingClimbTeamNum1))
			}
			for (i in 2..3) {
				if (i == partnerClimbAssistEndLevel) {
					highestAssistedClimbLevel[i - 2]!!.isChecked = true
				}
			}
			for (i in 0..2) {
				if (i == partnerClimbAssistStartLevel) {
					climbAssistStartingLevel[i]!!.isChecked = true
				}
			}
			for (i in 1..successHabClimbLevel.size) {
				if (i == successHabClimbLevel1) {
					successHabClimbLevel[i - 1]!!.isChecked = true
				}
			}
			for (i in 1..attemptHabClimbLevel.size) {
				if (i == attemptHabClimbLevel1) {
					attemptHabClimbLevel[i - 1]!!.isChecked = true
				}
			}
			if (partnerClimbsAssisted!!.value >= 1) {
				UiHelper.radioButtonEnable(highestAssistedClimbLevelGroup, true)
				UiHelper.radioButtonEnable(climbAssistStartingLevelGroup, true)
			}
			autoDisableClimbAssistStartingLevelGroup()
			autoDisableSuccessGroup()
		}
	}

	public override fun saveState() {
		entry!!.teleOp = TeleOp(cargoShipHatches!!.value, rocketLevelOneHatches!!.value,
				rocketLevelTwoHatches!!.value, rocketLevelThreeHatches!!.value,
				cargoShipCargo!!.value, rocketLevelOneCargo!!.value,
				rocketLevelTwoCargo!!.value, rocketLevelThreeCargo!!.value,
				hatchesDropped!!.value, cargoDropped!!.value, attemptHabClimb!!.isChecked,
				UiHelper.getHabLevelSelected(attemptHabClimbLevel), successHabClimb!!.isChecked,
				UiHelper.getHabLevelSelected(successHabClimbLevel),
				climbAssistedByPartners!!.isChecked,
				UiHelper.getIntegerFromTextBox(assistingClimbTeamNum),
				partnerClimbsAssisted!!.value,
				UiHelper.getHabLevelSelected(highestAssistedClimbLevel, 2),
				UiHelper.getHabLevelSelected(climbAssistStartingLevel, 0)
		)
	}

	private fun autoDisableSuccessGroup() {
		var attemptLevel = -1
		for (i in attemptHabClimbLevel.indices) {
			if (attemptHabClimbLevel[i]!!.isChecked) {
				attemptLevel = i
			}
		}
		if (successHabClimb!!.isChecked) {
			for (j in successHabClimbLevel.indices) {
				if (j > attemptLevel) {
					successHabClimbLevel[j]!!.isEnabled = false
					if (successHabClimbLevel[j]!!.isChecked) {
						successHabClimbLevelGroup!!.clearCheck()
					}
				} else {
					successHabClimbLevel[j]!!.isEnabled = true
				}
			}
		}
	}

	override fun onResume() {
		super.onResume()
		activity.title = "Add Entry - Tele-Op"
		UiHelper.hideKeyboard(activity)
	}

	companion object {
		fun getInstance(entry: ScoutEntry?): TeleOpFragment {
			val tof = TeleOpFragment()
			tof.setEntry(entry)
			return tof
		}
	}
}