package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecInt;
import org.usfirst.frc.team25.scouting.ui.views.ButtonTimer;


public class TeleOpFragment extends Fragment implements EntryFragment {

    private RadioButton[] climbOtherRobotType;
    private RadioButton[] attemptHabClimbLevel;
    private RadioButton[] successHabClimbLevel;
    private ScoutEntry entry;
    private ButtonIncDecInt cargoShipHatches;
    private ButtonIncDecInt rocketHatches;
    private ButtonIncDecInt rocketCargo;
    private ButtonIncDecInt cargoShipCargo;
    private ButtonIncDecInt hatchesDropped;
    private ButtonIncDecInt climbsAssisted;
    private CheckBox attemptHabClimb;
    private CheckBox successHabClimb;
    private CheckBox climbsOtherRobots;
    private CheckBox hatchLevelThreeCapable;
    private CheckBox hatchLevelTwoCapable;
    private CheckBox hatchLevelOneCapable;
    private ButtonTimer timerIncAmount;
    private EditText climbOtherRobotTypeOtherField;


    private Settings set;


    public static TeleOpFragment getInstance(ScoutEntry entry) {
        TeleOpFragment tof = new TeleOpFragment();
        tof.setEntry(entry);
        return tof;
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
    public void autoPopulate() {
        if (entry.getTeleOp() != null) {
            TeleOp tele = entry.getTeleOp();
            cargoShipHatches.setValue(tele.getCargoShipHatches());
            rocketHatches.setValue(tele.getCargoShipCargo());
            rocketCargo.setValue(tele.getRocketCargo());
            cargoShipCargo.setValue(tele.getRocketHatches());
            hatchesDropped.setValue(tele.getHatchesDropped());
            climbsAssisted.setValue(tele.getClimbsAssisted());
            attemptHabClimb.setChecked(tele.isAttemptHabClimb());
            successHabClimb.setChecked(tele.isSuccessfulHabClimb());
            climbsOtherRobots.setChecked(tele.isOtherRobotClimb());

            if (tele.getOtherRobotClimbType().length() != 0) {
                boolean otherChecked = true;
                for (RadioButton button : climbOtherRobotType) {
                    if (button.getText().equals(tele.getOtherRobotClimbType())) {
                        button.setChecked(true);
                        otherChecked = false;
                    }
                }
                if (otherChecked) {
                    climbOtherRobotType[climbOtherRobotType.length - 1].setChecked(true);
                    climbOtherRobotTypeOtherField.setText(tele.getOtherRobotClimbType());
                }
            }


        }
    }

    public int getHabClimbLevel(RadioButton[] habLevelArray) {
        int i;
        for (i = 0; i > 3; i++) {
            if (habLevelArray[i].isChecked()) {
                return i + 1;
            }
            System.out.println("getHabLevelClimb method executed, " + i + 1 + " was the " +
                    "registered choice");
        }
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tele_op, container, false);

        cargoShipHatches = view.findViewById(R.id.cargo_ship_hatches);
        rocketHatches = view.findViewById(R.id.rocket_hatches);
        rocketCargo = view.findViewById(R.id.rocket_cargo);
        attemptHabClimb = view.findViewById(R.id.teleop_attempt_hab_climb_checkbox);
        successHabClimb = view.findViewById(R.id.teleop_success_hab_climb_checkbox);
        cargoShipCargo = view.findViewById(R.id.cargo_ship_cargo);
        hatchesDropped = view.findViewById(R.id.hatches_dropped);
        climbsAssisted = view.findViewById(R.id.climbs_assisted);
        climbsOtherRobots = view.findViewById(R.id.climb_other_robot);
        Button continueButton = view.findViewById(R.id.tele_continue);

        attemptHabClimbLevel = new RadioButton[3];
        attemptHabClimbLevel[0] = view.findViewById(R.id.attempt_hab_level_option_1);
        attemptHabClimbLevel[1] = view.findViewById(R.id.attempt_hab_level_option_2);
        attemptHabClimbLevel[2] = view.findViewById(R.id.attempt_hab_level_option_3);

        successHabClimbLevel = new RadioButton[3];
        successHabClimbLevel[0] = view.findViewById(R.id.success_hab_level_option_1);
        successHabClimbLevel[1] = view.findViewById(R.id.success_hab_level_option_2);
        successHabClimbLevel[2] = view.findViewById(R.id.success_hab_level_option_3);

        climbOtherRobotType = new RadioButton[5];
        climbOtherRobotType[0] = view.findViewById(R.id.ramp_bot_type);
        climbOtherRobotType[1] = view.findViewById(R.id.robot_rung_type);
        climbOtherRobotType[2] = view.findViewById(R.id.iron_cross_type);
        climbOtherRobotType[3] = view.findViewById(R.id.single_lift_type);
        climbOtherRobotType[4] = view.findViewById(R.id.other_type);


        climbOtherRobotTypeOtherField = view.findViewById(R.id.other_robot_type_text);
        timerIncAmount = view.findViewById(R.id.timer_manual_inc);

        timerIncAmount.startStopButton.setVisibility(View.GONE);


        set = Settings.newInstance(getActivity());



        timerIncAmount.setValue(set.getTimerManualInc());

        climbOtherRobotTypeOtherField.setEnabled(false);



        for (int i = 0; i < climbOtherRobotType.length - 1; i++) {
            climbOtherRobotType[i].setOnClickListener(view13 -> {
                climbOtherRobotType[climbOtherRobotType.length - 1].setChecked(false);
                climbOtherRobotTypeOtherField.setText("");
                climbOtherRobotTypeOtherField.setEnabled(false);
            });
        }

        // Special exception for the "other" radio button
        climbOtherRobotType[climbOtherRobotType.length - 1].setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                for (int i = 0; i < 4; i++) {
                    climbOtherRobotType[i].setChecked(false);
                }

                climbOtherRobotTypeOtherField.setEnabled(true);
            }
        });


        successHabClimb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                disableOtherRobotTypeGroup();
                climbsOtherRobots.setEnabled(false);
                climbsOtherRobots.setChecked(false);
                for (RadioButton button : successHabClimbLevel) {
                    button.setEnabled(true);
                }
            } else {
                climbsOtherRobots.setEnabled(true);
                for (RadioButton button : successHabClimbLevel) {
                    button.setEnabled(false);
                }
            }
        });

        attemptHabClimb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b && !climbsOtherRobots.isChecked()) {
                successHabClimb.setEnabled(true);
                for (RadioButton button : attemptHabClimbLevel) {
                    button.setEnabled(true);
                }
            } else {
                successHabClimb.setEnabled(false);
                successHabClimb.setChecked(false);
                for (RadioButton button : attemptHabClimbLevel) {
                    button.setEnabled(false);
                    button.setChecked(false);
                }
            }
        });

        climbsOtherRobots.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                enableOtherRobotTypeGroup();
                successHabClimb.setEnabled(false);
                successHabClimb.setChecked(false);
            } else {
                disableOtherRobotTypeGroup();
                if (attemptHabClimb.isChecked()) {
                    successHabClimb.setEnabled(true);
                }

            }
        });

        continueButton.setOnClickListener(view1 -> {
            hideKeyboard();
            if (climbsOtherRobots.isChecked() && !(climbOtherRobotType[0].isChecked() || climbOtherRobotType[1].isChecked()
                    || climbOtherRobotType[2].isChecked() || climbOtherRobotType[3].isChecked() ||
                    (climbOtherRobotType[4].isChecked() && !climbOtherRobotTypeOtherField.getText().toString().isEmpty()))) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select or fill in type of robot climbed on")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                            //do things
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                saveState();
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, PostmatchFragment.getInstance(entry), "POST")
                        .commit();

            }
        });


        return view;
    }

    @Override
    public void saveState() {
        String climbOtherRobotTypeStr = "";
        for (int i = 0; i < climbOtherRobotType.length - 1; i++) {
            if (climbOtherRobotType[i].isChecked()) {
                climbOtherRobotTypeStr = (String) climbOtherRobotType[i].getText();
            }
        }

        if (climbOtherRobotType[climbOtherRobotType.length - 1].isChecked()) {
            climbOtherRobotTypeStr = climbOtherRobotTypeOtherField.getText().toString();
        }



      /*  entry.setTeleOp(new TeleOp(cargoShipHatches.getValue(),
                cargoShipCargo.getValue(),
                rocketHatches.getValue(),
                rocketCargo.getValue(),
                cargoShipCargo.getValue(),
                hatchesDropped.getValue(),
                climbsAssisted.getValue(),
                getHabClimbLevel(attemptHabClimbLevel),
                getHabClimbLevel(successHabClimbLevel),
                attemptHabClimb.isChecked(),
                successHabClimb.isChecked(),
                climbsOtherRobots.isChecked())); */
    }

    private void disableOtherRobotTypeGroup() {
        for (RadioButton button : climbOtherRobotType) {
            button.setEnabled(false);
            button.setChecked(false);
        }
        climbOtherRobotTypeOtherField.setEnabled(false);
        climbOtherRobotTypeOtherField.setText("");
    }

    private void enableOtherRobotTypeGroup() {
        for (RadioButton button : climbOtherRobotType) {
            button.setEnabled(true);
        }
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputManager =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken()
                    , InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        autoPopulate();
    }

    @Override
    public void onStop() {
        set.setTimerManualInc(timerIncAmount.getValue());
        Log.i("tag", "stopped");
        super.onStop();
    }
}
