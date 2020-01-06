package org.usfirst.frc.team25.scouting.ui.dataentry

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import org.usfirst.frc.team25.scouting.R
import org.usfirst.frc.team25.scouting.data.models.Autonomous
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView

class AutoFragment : EntryFragment() {
	private var rocketCargo: ButtonIncDecSet? = null
	private var rocketHatches: ButtonIncDecSet? = null
	private var cargoShipCargo: ButtonIncDecSet? = null
	private var cargoShipHatches: ButtonIncDecSet? = null
	private var hatchesDropped: ButtonIncDecView? = null
	private var cargoDropped: ButtonIncDecView? = null
	private var crossHabLine: CheckBox? = null
	private var opponentCargoShipLineFoul: CheckBox? = null
	private var sideCargoShipHatchCapable: CheckBox? = null
	private var frontCargoShipHatchCapable: CheckBox? = null
	private var cargoDroppedCargoShip: CheckBox? = null
	private var cargoDroppedRocket: CheckBox? = null
	private var hatchesDroppedCargoShip: CheckBox? = null
	private var hatchesDroppedRocket: CheckBox? = null
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
	                          savedInstanceState: Bundle): View {
		val view = inflater.inflate(R.layout.fragment_auto, container, false)
		rocketCargo = view.findViewById(R.id.rocket_cargo_auto)
		rocketHatches = view.findViewById(R.id.rocket_hatches_auto)
		cargoShipCargo = view.findViewById(R.id.cargo_ship_cargo_auto)
		cargoDropped = view.findViewById(R.id.cargo_dropped_auto)
		crossHabLine = view.findViewById(R.id.cross_hab_line)
		hatchesDropped = view.findViewById(R.id.hatches_dropped_auto)
		opponentCargoShipLineFoul = view.findViewById(R.id.opponent_cargo_ship_line)
		frontCargoShipHatchCapable = view.findViewById(R.id.hatches_front_cargo_auto)
		sideCargoShipHatchCapable = view.findViewById(R.id.hatches_side_cargo_auto)
		cargoShipHatches = view.findViewById(R.id.cargo_ship_hatches_auto)
		cargoDroppedCargoShip = view.findViewById(R.id.cargo_dropped_cargo_ship)
		cargoDroppedRocket = view.findViewById(R.id.cargo_dropped_rocket)
		hatchesDroppedCargoShip = view.findViewById(R.id.hatches_dropped_cargo_ship)
		hatchesDroppedRocket = view.findViewById(R.id.hatches_dropped_rocket)
		val continueButton = view.findViewById<Button>(R.id.auto_continue)
		opponentCargoShipLineFoul.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean -> autoEnableCrossHabLine() })
		val enablingCrossHabLineMetrics = arrayOf(rocketHatches,
				cargoShipHatches, rocketCargo, cargoShipCargo)
		for (set in enablingCrossHabLineMetrics) {
			set!!.incButton.setOnClickListener { view1: View? ->
				set.increment()
				autoEnableCrossHabLine()
				if (set == cargoShipHatches) {
					autoEnableCargoShipHatchPlacementSet()
				}
			}
			set.decButton.setOnClickListener { view1: View? ->
				set.decrement()
				autoEnableCrossHabLine()
				if (set == cargoShipHatches) {
					autoEnableCargoShipHatchPlacementSet()
				}
			}
		}
		hatchesDropped.incButton.setOnClickListener { view1: View? ->
			hatchesDropped.increment()
			autoEnableHatchesDroppedLocationSet()
		}
		hatchesDropped.decButton.setOnClickListener { view1: View? ->
			hatchesDropped.decrement()
			autoEnableHatchesDroppedLocationSet()
		}
		cargoDropped.incButton.setOnClickListener { view1: View? ->
			cargoDropped.increment()
			autoEnableCargoDroppedLocationSet()
		}
		cargoDropped.decButton.setOnClickListener { view1: View? ->
			cargoDropped.decrement()
			autoEnableCargoDroppedLocationSet()
		}
		autoPopulate()
		continueButton.setOnClickListener { view1: View? ->
			if (cargoShipHatches.getValue() > 0 && !frontCargoShipHatchCapable.isChecked() &&
					!sideCargoShipHatchCapable.isChecked() || (cargoDropped.getValue() > 0 && !cargoDroppedCargoShip.isChecked()
							&& !cargoDroppedRocket.isChecked()) || hatchesDropped.getValue() > 0 && !hatchesDroppedCargoShip.isChecked() && !hatchesDroppedRocket.isChecked()) {
				AlertDialog.Builder(activity)
						.setTitle("Select where game pieces were placed/dropped")
						.setPositiveButton("OK") { dialogInterface: DialogInterface?, i: Int -> }
						.show()
			} else {
				saveState()
				fragmentManager.beginTransaction()
						.replace(android.R.id.content, TeleOpFragment.Companion.getInstance(entry), "TELEOP")
						.commit()
			}
		}
		if (entry!!.preMatch.isRobotNoShow) {
			continueButton.callOnClick()
		}
		return view
	}

	private fun autoEnableCrossHabLine() {
		if (rocketCargo!!.value > 0 || cargoShipCargo!!.value > 0 || cargoShipHatches!!.value > 0 || rocketHatches!!.value > 0 || sideCargoShipHatchCapable!!.isChecked ||
				frontCargoShipHatchCapable!!.isChecked || opponentCargoShipLineFoul!!.isChecked) {
			crossHabLine!!.isChecked = true
			crossHabLine!!.isEnabled = false
		} else {
			crossHabLine!!.isEnabled = true
		}
	}

	private fun autoEnableCargoShipHatchPlacementSet() {
		if (cargoShipHatches!!.value > 0) {
			frontCargoShipHatchCapable!!.isEnabled = true
			sideCargoShipHatchCapable!!.isEnabled = true
		} else {
			frontCargoShipHatchCapable!!.isEnabled = false
			sideCargoShipHatchCapable!!.isEnabled = false
			frontCargoShipHatchCapable!!.isChecked = false
			sideCargoShipHatchCapable!!.isChecked = false
		}
	}

	private fun autoEnableHatchesDroppedLocationSet() {
		if (hatchesDropped!!.value > 0) {
			hatchesDroppedCargoShip!!.isEnabled = true
			hatchesDroppedRocket!!.isEnabled = true
		} else {
			hatchesDroppedCargoShip!!.isEnabled = false
			hatchesDroppedRocket!!.isEnabled = false
			hatchesDroppedCargoShip!!.isChecked = false
			hatchesDroppedRocket!!.isChecked = false
		}
	}

	private fun autoEnableCargoDroppedLocationSet() {
		if (cargoDropped!!.value > 0) {
			cargoDroppedRocket!!.isEnabled = true
			cargoDroppedCargoShip!!.isEnabled = true
		} else {
			cargoDroppedRocket!!.isEnabled = false
			cargoDroppedCargoShip!!.isEnabled = false
			cargoDroppedRocket!!.isChecked = false
			cargoDroppedCargoShip!!.isChecked = false
		}
	}

	public override fun autoPopulate() {
		if (entry!!.autonomous != null) {
			val (rocketCargo1, rocketHatches1, cargoShipHatches1, cargoShipCargo1, hatchesDropped1, cargoDropped1, crossHabLine1, opponentCargoShipLineFoul1, sideCargoShipHatchCapable1, frontCargoShipHatchCapable1, cargoDroppedCargoShip1, cargoDroppedRocket1, hatchesDroppedRocket1, hatchesDroppedCargoShip1) = entry!!.autonomous
			cargoShipCargo!!.value = cargoShipCargo1
			rocketCargo!!.value = rocketCargo1
			rocketHatches!!.value = rocketHatches1
			cargoShipHatches!!.value = cargoShipHatches1
			hatchesDropped!!.value = hatchesDropped1
			cargoDropped!!.value = cargoDropped1
			sideCargoShipHatchCapable!!.isChecked = sideCargoShipHatchCapable1
			frontCargoShipHatchCapable!!.isChecked = frontCargoShipHatchCapable1
			crossHabLine!!.isChecked = crossHabLine1
			opponentCargoShipLineFoul!!.isChecked = opponentCargoShipLineFoul1
			cargoDroppedCargoShip!!.isChecked = cargoDroppedCargoShip1
			cargoDroppedRocket!!.isChecked = cargoDroppedRocket1
			hatchesDroppedCargoShip!!.isChecked = hatchesDroppedCargoShip1
			hatchesDroppedRocket!!.isChecked = hatchesDroppedRocket1
			autoEnableCargoShipHatchPlacementSet()
			autoEnableCargoDroppedLocationSet()
			autoEnableHatchesDroppedLocationSet()
			autoEnableCrossHabLine()
		}
	}

	public override fun saveState() {
		entry!!.autonomous = Autonomous(rocketCargo!!.value, rocketHatches!!.value,
				cargoShipHatches!!.value,
				cargoShipCargo!!.value,
				hatchesDropped!!.value,
				cargoDropped!!.value, crossHabLine!!.isChecked,
				opponentCargoShipLineFoul!!.isChecked, sideCargoShipHatchCapable!!.isChecked,
				frontCargoShipHatchCapable!!.isChecked, cargoDroppedCargoShip!!.isChecked,
				cargoDroppedRocket!!.isChecked, hatchesDroppedRocket!!.isChecked,
				hatchesDroppedCargoShip!!.isChecked)
	}

	override fun onResume() {
		super.onResume()
		activity.title = "Add Entry - Sandstorm"
	}

	companion object {
		fun getInstance(entry: ScoutEntry?): AutoFragment {
			val autoFragment = AutoFragment()
			autoFragment.setEntry(entry)
			return autoFragment
		}
	}
}