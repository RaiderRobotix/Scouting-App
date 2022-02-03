package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.Autonomous;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.UiHelper;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

public class AutoFragment extends Fragment implements EntryFragment {

    private ButtonIncDecSet rocketCargo, rocketHatches, cargoShipCargo, cargoShipHatches;

    private ButtonIncDecView robotCargoScoredUpperHub,  robotcargoPickedup, robotCargoScoredLowerHub ,
            robotCargoDropped , humanCargoScored , humanCargoMissed;

    private CheckBox crossHabLine, robotPassTarmac,robotCommitedFoul, sideCargoShipHatchCapable,
            frontCargoShipHatchCapable, cargoDroppedCargoShip, cargoDroppedRocket,
            hatchesDroppedCargoShip, hatchesDroppedRocket;


    private ScoutEntry entry;


    public static AutoFragment getInstance(ScoutEntry entry) {
        AutoFragment autoFragment = new AutoFragment();
        autoFragment.setEntry(entry);
        return autoFragment;
    }

    @Override
    public ScoutEntry getEntry() {
        return entry;
    }

    @Override
    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_auto, container, false);


       // rocketCargo = view.findViewById(R.id.rocket_cargo_auto);
       // rocketHatches = view.findViewById(R.id.rocket_hatches_auto);
        //cargoShipCargo = view.findViewById(R.id.cargo_ship_cargo_auto);
        robotcargoPickedup = view.findViewById(R.id.Cargo_robot_picked_up);
        //crossHabLine = view.findViewById(R.id.cross_hab_line);
        robotCargoScoredUpperHub = view.findViewById(R.id.Robot_balls_scored_upperHub);
        robotCargoScoredLowerHub = view.findViewById(R.id.Robot_Cargo_scored_lowerHub);
        robotCargoDropped = view.findViewById(R.id.Cargo_robot_dropped);
        humanCargoScored = view.findViewById(R.id.cargo_Human_scored);
        humanCargoMissed = view.findViewById(R.id.cargo_Human_missed);


        robotPassTarmac = view.findViewById(R.id.Robot_pass_tarmac);
        robotCommitedFoul = view.findViewById(R.id.Robot_got_foul);
        //frontCargoShipHatchCapable = view.findViewById(R.id.hatches_front_cargo_auto);
        //sideCargoShipHatchCapable = view.findViewById(R.id.hatches_side_cargo_auto);
        //cargoShipHatches = view.findViewById(R.id.cargo_ship_hatches_auto);
        //cargoDroppedCargoShip = view.findViewById(R.id.cargo_dropped_cargo_ship);
        //cargoDroppedRocket = view.findViewById(R.id.cargo_dropped_rocket);
       // hatchesDroppedCargoShip = view.findViewById(R.id.hatches_dropped_cargo_ship);
       // hatchesDroppedRocket = view.findViewById(R.id.hatches_dropped_rocket);


        Button continueButton = view.findViewById(R.id.auto_continue);



        //opponentCargoShipLineFoul.setOnCheckedChangeListener((compoundButton, b) -> autoEnableCrossHabLine());

       /* ButtonIncDecSet[] enablingCrossHabLineMetrics = new ButtonIncDecSet[]{rocketHatches,
                cargoShipHatches, rocketCargo, cargoShipCargo};

        for (ButtonIncDecSet set : enablingCrossHabLineMetrics) {
            set.incButton.setOnClickListener(view1 -> {
                set.increment();
                autoEnableCrossHabLine();
                if (set.equals(cargoShipHatches)) {
                    autoEnableCargoShipHatchPlacementSet();
                }

            });
            set.decButton.setOnClickListener(view1 -> {
                set.decrement();
                autoEnableCrossHabLine();
                if (set.equals(cargoShipHatches)) {
                    autoEnableCargoShipHatchPlacementSet();
                }

            });
        }*/




        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            //FIX THE ROBOT PASS TARMAC CHECK BUTTON SO IF IT IS NOT CHECKED THEN IT GIVES AND ALERT
            if (robotcargoPickedup.getValue() == 0 && (robotCargoScoredLowerHub.getValue() > 1 ||
                    robotCargoScoredUpperHub.getValue() > 1) || (robotPassTarmac.isChecked()==false &&
                    (robotCargoScoredLowerHub.getValue()>0 || robotCargoScoredUpperHub.getValue() > 0 ||
                            robotcargoPickedup.getValue() > 0))) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select How many Balls the robot picked up / Select if the Robot Crossed the Tarmac")
                        .setPositiveButton("OK", (dialogInterface, i) -> {

                        })
                        .show();


            } else {
                saveState();
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, TeleOpFragment.getInstance(entry), "TELEOP")
                        .commit();
            }

        });

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();

        }

        return view;
    }

   /* private void autoEnableCrossHabLine() {
        if (rocketCargo.getValue() > 0 || cargoShipCargo.getValue() > 0
                || cargoShipHatches.getValue() > 0 || rocketHatches.getValue() > 0 || sideCargoShipHatchCapable.isChecked() ||
                frontCargoShipHatchCapable.isChecked() || robotPassTarmac.isChecked()) {
            crossHabLine.setChecked(true);
            crossHabLine.setEnabled(false);
        } else {
            crossHabLine.setEnabled(true);
        }
    }

    private void autoEnableCargoShipHatchPlacementSet() {

        if (cargoShipHatches.getValue() > 0) {
            frontCargoShipHatchCapable.setEnabled(true);
            sideCargoShipHatchCapable.setEnabled(true);
        } else {
            frontCargoShipHatchCapable.setEnabled(false);
            sideCargoShipHatchCapable.setEnabled(false);
            frontCargoShipHatchCapable.setChecked(false);
            sideCargoShipHatchCapable.setChecked(false);
        }
    }

    private void autoEnableHatchesDroppedLocationSet() {
        if (hatchesDropped.getValue() > 0) {
            hatchesDroppedCargoShip.setEnabled(true);
            hatchesDroppedRocket.setEnabled(true);
        } else {
            hatchesDroppedCargoShip.setEnabled(false);
            hatchesDroppedRocket.setEnabled(false);
            hatchesDroppedCargoShip.setChecked(false);
            hatchesDroppedRocket.setChecked(false);
        }
    }

    private void autoEnableCargoDroppedLocationSet() {
        if (cargoDropped.getValue() > 0) {
            cargoDroppedRocket.setEnabled(true);
            cargoDroppedCargoShip.setEnabled(true);
        } else {
            cargoDroppedRocket.setEnabled(false);
            cargoDroppedCargoShip.setEnabled(false);
            cargoDroppedRocket.setChecked(false);
            cargoDroppedCargoShip.setChecked(false);
        }
    }*/

    @Override
    public void autoPopulate() {
        if (entry.getAutonomous() != null) {

            Autonomous prevAuto = entry.getAutonomous();
            /*cargoShipCargo.setValue(prevAuto.getCargoShipCargo());
            rocketCargo.setValue(prevAuto.getRocketCargo());
            rocketHatches.setValue(prevAuto.getRocketHatches());
            cargoShipHatches.setValue(prevAuto.getCargoShipHatches());*/
            robotcargoPickedup.setValue(prevAuto.getRobotCargoPickedup());
            robotCargoScoredUpperHub.setValue(prevAuto.getRobotCargoScoredUpperHub());
            robotCargoScoredLowerHub.setValue(prevAuto.getRobotCargoScoredLowerHub());
            robotCargoDropped.setValue(prevAuto.getRobotCargoDropped());
            humanCargoScored.setValue(prevAuto.getHumanCargoScored());
            humanCargoMissed.setValue(prevAuto.getHumanCargoMissed());
            /*sideCargoShipHatchCapable.setChecked(prevAuto.isSideCargoShipHatchCapable());
            frontCargoShipHatchCapable.setChecked(prevAuto.isFrontCargoShipHatchCapable());
            crossHabLine.setChecked(prevAuto.isCrossHabLine());*/
            robotPassTarmac.setChecked(prevAuto.isRobotPassTarmac());
            /*cargoDroppedCargoShip.setChecked(prevAuto.isCargoDroppedCargoShip());
            cargoDroppedRocket.setChecked(prevAuto.isCargoDroppedRocket());
            hatchesDroppedCargoShip.setChecked(prevAuto.isHatchesDroppedCargoShip());
            hatchesDroppedRocket.setChecked(prevAuto.isHatchesDroppedRocket());*/
            robotCommitedFoul.setChecked(prevAuto.isRobotCommitFoul());

            //autoEnableCargoShipHatchPlacementSet();
            //autoEnableCargoDroppedLocationSet();
            //autoEnableHatchesDroppedLocationSet();

            //autoEnableCrossHabLine();

        }

    }

    @Override
    public void saveState() {
        entry.setAutonomous(new Autonomous(
                robotcargoPickedup.getValue(), robotCargoScoredUpperHub.getValue(),
                robotCargoScoredLowerHub.getValue(), robotCargoDropped.getValue(),
                humanCargoScored.getValue(), humanCargoMissed.getValue(),
                robotPassTarmac.isChecked(), robotCommitedFoul.isChecked()));
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Auton");
    }

}
