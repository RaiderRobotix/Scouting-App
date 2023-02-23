package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.Autonomous;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

public class AutoFragment extends Fragment implements EntryFragment {

    private ButtonIncDecSet coneTop, cubeTop, coneMid, cubeMid, coneBttm, cubeBttm, coneDropped, cubeDropped;


    private CheckBox robotExitCommunity, robotDockAttempt;

    private RadioButton[] robotDockButtons;
    private RadioGroup robotDockGroup;


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



        robotExitCommunity =  view.findViewById(R.id.robot_exit_community);

        coneTop = view.findViewById(R.id.cone_scored_top_row_auton);
        coneMid = view.findViewById(R.id.cone_scored_mid_row_auton);
        coneBttm = view.findViewById(R.id.cone_scored_btt_row_auton);

        cubeTop = view.findViewById(R.id.cube_scored_top_row_auton);
        cubeMid = view.findViewById(R.id.cube_scored_mid_row_auton);
        cubeBttm = view.findViewById(R.id.cube_scored_btt_row_auton);

        coneDropped = view.findViewById(R.id.cone_dropped_auton);
        cubeDropped = view.findViewById(R.id.cube_dropped_auton);

        robotDockButtons = new RadioButton[2];
        robotDockButtons[0] = view.findViewById(R.id.docked_button);
        robotDockButtons[1] = view.findViewById(R.id.docked_engaged_button);

        robotDockAttempt = view.findViewById(R.id.robot_attempt_dock);
        robotDockGroup = view.findViewById(R.id.robot_dock_group);


        Button continueButton = view.findViewById(R.id.auto_continue);


        //opponentCargoShipLineFoul.setOnCheckedChangeListener((compoundButton, b) -> autoEnableCrossHabLine());

        //ButtonIncDecSet[] enablingCrossHabLineMetrics = new ButtonIncDecSet[]{coneTop,cubeTop,coneMid,cubeMid,coneBttm,cubeBttm,coneDropped,cubeDropped};

        //        for (ButtonIncDecSet set : enablingCrossHabLineMetrics) {
        //            set.incButton.setOnClickListener(view1 -> {
        //                set.increment();
        //                autoEnableCrossHabLine();
        //                if (set.equals(cargoShipHatches)) {
        //                    autoEnableCargoShipHatchPlacementSet();
        //                }
        //
        //            });
        //            set.decButton.setOnClickListener(view1 -> {
        //                set.decrement();
        //                autoEnableCrossHabLine();
        //                if (set.equals(cargoShipHatches)) {
        //                    autoEnableCargoShipHatchPlacementSet();
        //                }
        //
        //            });
        //        }

        robotDockAttempt.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                for(RadioButton button : robotDockButtons) {
                    button.setEnabled(true);
                }

            } else {
                for(RadioButton button : robotDockButtons) {
                    button.setEnabled(false);
                    button.setChecked(false);
                }
            }
        });


        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            //            if (cargoShipHatches.getValue() > 0 && !frontCargoShipHatchCapable.isChecked() &&
            //                    !sideCargoShipHatchCapable.isChecked() || cargoDropped.getValue() > 0 && !cargoDroppedCargoShip.isChecked()
            //                    && !cargoDroppedRocket.isChecked() || hatchesDropped.getValue() > 0 && !hatchesDroppedCargoShip.isChecked() && !hatchesDroppedRocket.isChecked()) {
            //                new AlertDialog.Builder(getActivity())
            //                        .setTitle("Select where game pieces were placed/dropped")
            //                        .setPositiveButton("OK", (dialogInterface, i) -> {
            //
            //                        })
            //                        .show();
            //            } else {
            saveState();
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, TeleOpFragment.getInstance(entry), "TELEOP")
                    .commit();
            //}

        });

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();

        }

        return view;
    }

    //    private void autoEnableCrossHabLine() {
    //        if (rocketCargo.getValue() > 0 || cargoShipCargo.getValue() > 0
    //                || cargoShipHatches.getValue() > 0 || rocketHatches.getValue() > 0 || sideCargoShipHatchCapable.isChecked() ||
    //                frontCargoShipHatchCapable.isChecked() || opponentCargoShipLineFoul.isChecked()) {
    //            crossHabLine.setChecked(true);
    //            crossHabLine.setEnabled(false);
    //        } else {
    //            crossHabLine.setEnabled(true);
    //        }
    //    }
    //
    //    private void autoEnableCargoShipHatchPlacementSet() {
    //
    //        if (cargoShipHatches.getValue() > 0) {
    //            frontCargoShipHatchCapable.setEnabled(true);
    //            sideCargoShipHatchCapable.setEnabled(true);
    //        } else {
    //            frontCargoShipHatchCapable.setEnabled(false);
    //            sideCargoShipHatchCapable.setEnabled(false);
    //            frontCargoShipHatchCapable.setChecked(false);
    //            sideCargoShipHatchCapable.setChecked(false);
    //        }
    //    }
    //
    //    private void autoEnableHatchesDroppedLocationSet() {
    //        if (hatchesDropped.getValue() > 0) {
    //            hatchesDroppedCargoShip.setEnabled(true);
    //            hatchesDroppedRocket.setEnabled(true);
    //        } else {
    //            hatchesDroppedCargoShip.setEnabled(false);
    //            hatchesDroppedRocket.setEnabled(false);
    //            hatchesDroppedCargoShip.setChecked(false);
    //            hatchesDroppedRocket.setChecked(false);
    //        }
    //    }
    //
    //    private void autoEnableCargoDroppedLocationSet() {
    //        if (cargoDropped.getValue() > 0) {
    //            cargoDroppedRocket.setEnabled(true);
    //            cargoDroppedCargoShip.setEnabled(true);
    //        } else {
    //            cargoDroppedRocket.setEnabled(false);
    //            cargoDroppedCargoShip.setEnabled(false);
    //            cargoDroppedRocket.setChecked(false);
    //            cargoDroppedCargoShip.setChecked(false);
    //        }
    //    }

    @Override
    public void autoPopulate() {
        if (entry.getAutonomous() != null) {

            Autonomous prevAuto = entry.getAutonomous();

            robotDockAttempt.setChecked(prevAuto.isDockAttempt());
            robotExitCommunity.setChecked(prevAuto.isRobotExitCommunity());
            coneTop.setValue(prevAuto.getConeTop());
            cubeTop.setValue(prevAuto.getCubeTop());
            coneMid.setValue(prevAuto.getConeMid());
            cubeMid.setValue(prevAuto.getCubeMid());
            coneBttm.setValue(prevAuto.getConeBttm());
            cubeBttm.setValue(prevAuto.getCubeBttm());
            coneDropped.setValue(prevAuto.getConeDropped());
            cubeDropped.setValue(prevAuto.getCubeDropped());

            for (RadioButton button : robotDockButtons) {
                if (button.getText().toString().equals(prevAuto.getDockStatus())) {
                    button.setChecked(true);
                }
            }




        }

    }

    @Override
    public void saveState() {

        String dockStatus = "";
        for (RadioButton button : robotDockButtons) {
            if (button.isChecked()) {
                dockStatus = (String) button.getText();
                break;
            }
        }

        entry.setAutonomous(new Autonomous(coneTop.getValue(),cubeTop.getValue(),coneMid.getValue(),
                cubeMid.getValue(), coneBttm.getValue(), cubeBttm.getValue(), coneDropped.getValue(),
                cubeDropped.getValue(), robotExitCommunity.isChecked(),robotDockAttempt.isChecked(),dockStatus));
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Autonomous");
    }

}
