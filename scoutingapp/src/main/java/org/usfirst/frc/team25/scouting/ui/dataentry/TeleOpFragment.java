package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

public class TeleOpFragment extends Fragment implements EntryFragment {

    private ScoutEntry entry;
    private RadioButton[] attemptHabClimbLevel;
    private RadioButton[] successHabClimbLevel;
    private RadioButton[] highestAssistedClimbLevel;
    private RadioGroup attemptHabClimbLevelGroup;
    private RadioGroup successHabClimbLevelGroup;
    private RadioGroup highestAssistedClimbLevelGroup;
    private ButtonIncDecSet cargoShipHatches;
    private ButtonIncDecSet cargoShipCargo;
    private ButtonIncDecSet rocketLevelOneHatches;
    private ButtonIncDecSet rocketLevelOneCargo;
    private ButtonIncDecSet rocketLevelTwoHatches;
    private ButtonIncDecSet rocketLevelTwoCargo;
    private ButtonIncDecSet rocketLevelThreeHatches;
    private ButtonIncDecSet rocketLevelThreeCargo;
    private ButtonIncDecView hatchesDropped;
    private ButtonIncDecView cargoDropped;
    private CheckBox attemptHabClimb;
    private CheckBox successHabClimb;
    private ButtonIncDecView partnerClimbsAssisted;
    private CheckBox climbAssistedByPartners;
    private EditText teamNumberThatAssistedClimb;
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
            rocketLevelOneHatches.setValue(tele.getCargoShipCargo());
            rocketLevelOneCargo.setValue(tele.getRocketLevelOneCargo());
            cargoShipCargo.setValue(tele.getRocketLevelOneHatches());
            rocketLevelTwoCargo.setValue(tele.getRocketLevelTwoCargo());
            rocketLevelTwoHatches.setValue(tele.getRocketLevelTwoHatches());
            rocketLevelThreeCargo.setValue(tele.getRocketLevelThreeCargo());
            rocketLevelThreeHatches.setValue(tele.getRocketLevelThreeHatches());

            climbAssistedByPartners.setChecked(tele.isClimbAssistedByPartners());
            partnerClimbsAssisted.setValue(tele.getNumberOfPartnerClimbsAssisted());
            hatchesDropped.setValue(tele.getHatchesDropped());
            cargoDropped.setValue(tele.getCargoDropped());
            attemptHabClimb.setChecked(tele.isAttemptHabClimb());
            successHabClimb.setChecked(tele.isSuccessHabClimb());

            if (tele.getAssistingClimbTeamNumber() != 0) {
                teamNumberThatAssistedClimb.setText(Integer.toString(tele.getAssistingClimbTeamNumber()));
            }

            for (int i = 2; i <= 3; i++) {
                if (i == tele.getHighestClimbAssisted()) {
                    highestAssistedClimbLevel[i - 2].setChecked(true);
                }
                }

            for (int i = 1; i <= successHabClimbLevel.length; i++) {
                if (i == tele.getSuccessHabClimbLevel()) {
                    successHabClimbLevel[i - 1].setChecked(true);
                    }
                }

            for (int i = 1; i <= attemptHabClimbLevel.length; i++) {
                if (i == tele.getAttemptHabClimbLevel()) {
                    attemptHabClimbLevel[i - 1].setChecked(true);
                    }
                }

            if (partnerClimbsAssisted.getValue() >= 1) {
                radioButtonEnable(highestAssistedClimbLevelGroup, true);
            }

        }
    }

    @Override
    public void saveState() {

        entry.setTeleOp(new TeleOp(cargoShipHatches.getValue(),
                cargoShipCargo.getValue(),
                rocketLevelOneCargo.getValue(),
                rocketLevelOneHatches.getValue(),
                rocketLevelTwoCargo.getValue(),
                rocketLevelTwoHatches.getValue(),
                rocketLevelThreeCargo.getValue(),
                rocketLevelThreeHatches.getValue(),
                hatchesDropped.getValue(),
                cargoDropped.getValue(),
                climbAssistedByPartners.isChecked(),
                getHabLevelSelected(attemptHabClimbLevel),
                getHabLevelSelected(successHabClimbLevel),
                attemptHabClimb.isChecked(),
                successHabClimb.isChecked(),
                getIntegerFromTextBox(teamNumberThatAssistedClimb),
                partnerClimbsAssisted.getValue(),
                getHighestHabLevelSelected(highestAssistedClimbLevel)));

    }

    //get child count in regular for loop
    private void radioButtonEnable(RadioGroup groupToEnableOrDisable, boolean modeSelected) {
        if (modeSelected) {
            for (int i = 0; i < groupToEnableOrDisable.getChildCount(); i++) {
                groupToEnableOrDisable.getChildAt(i).setEnabled(true);
            }
        } else {
            for (int i = 0; i < groupToEnableOrDisable.getChildCount(); i++) {
                groupToEnableOrDisable.getChildAt(i).setEnabled(false);
                groupToEnableOrDisable.clearCheck();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tele_op, container, false);

        cargoShipHatches = view.findViewById(R.id.cargo_ship_hatches_teleop);
        rocketLevelOneHatches = view.findViewById(R.id.rocket_level_one_hatches_teleop);
        rocketLevelOneCargo = view.findViewById(R.id.rocket_level_one_cargo_teleop);
        rocketLevelTwoHatches = view.findViewById(R.id.rocket_level_two_hatches_teleop);
        rocketLevelTwoCargo = view.findViewById(R.id.rocket_level_two_cargo_teleop);
        rocketLevelThreeHatches = view.findViewById(R.id.rocket_level_three_hatches_teleop);
        rocketLevelThreeCargo = view.findViewById(R.id.rocket_level_three_cargo_teleop);

        teamNumberThatAssistedClimb = view.findViewById(R.id.team_number_text_input);
        climbAssistedByPartners =
                view.findViewById(R.id.climb_assisted_by_alliance_partner_checkbox);
        partnerClimbsAssisted = view.findViewById(R.id.alliance_partner_climbs_assisted);
        attemptHabClimb = view.findViewById(R.id.attempt_hab_climb);
        successHabClimb = view.findViewById(R.id.success_hab_climb);
        cargoShipCargo = view.findViewById(R.id.cargo_ship_cargo_teleop);
        hatchesDropped = view.findViewById(R.id.hatches_dropped_teleop);
        cargoDropped = view.findViewById(R.id.cargo_dropped_teleop);
        Button continueButton = view.findViewById(R.id.tele_continue);
        attemptHabClimbLevelGroup = view.findViewById(R.id.attempt_hab_climb_level);
        successHabClimbLevelGroup = view.findViewById(R.id.success_hab_climb_level);
        highestAssistedClimbLevelGroup = view.findViewById(R.id.highest_climb_assist_radio_group);

        attemptHabClimbLevel = new RadioButton[3];
        attemptHabClimbLevel[0] = view.findViewById(R.id.attempt_hab_level_1);
        attemptHabClimbLevel[1] = view.findViewById(R.id.attempt_hab_level_2);
        attemptHabClimbLevel[2] = view.findViewById(R.id.attempt_hab_level_3);

        successHabClimbLevel = new RadioButton[3];
        successHabClimbLevel[0] = view.findViewById(R.id.success_hab_level_1);
        successHabClimbLevel[1] = view.findViewById(R.id.success_hab_level_2);
        successHabClimbLevel[2] = view.findViewById(R.id.success_hab_level_3);

        highestAssistedClimbLevel = new RadioButton[2];
        highestAssistedClimbLevel[0] = view.findViewById(R.id.highest_assisted_climb_one);
        highestAssistedClimbLevel[1] = view.findViewById(R.id.highest_assisted_climb_two);

        set = Settings.newInstance(getActivity());

        successHabClimb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                radioButtonEnable(successHabClimbLevelGroup, true);
            } else {
                radioButtonEnable(successHabClimbLevelGroup, false);
            }
        });

        attemptHabClimb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                successHabClimb.setEnabled(true);
                radioButtonEnable(attemptHabClimbLevelGroup, true);
            } else {
                successHabClimb.setEnabled(false);
                successHabClimb.setChecked(false);
                radioButtonEnable(attemptHabClimbLevelGroup, false);
                radioButtonEnable(successHabClimbLevelGroup, false);
            }
        });

        climbAssistedByPartners.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                teamNumberThatAssistedClimb.setEnabled(true);
            } else {
                teamNumberThatAssistedClimb.setEnabled(false);
                teamNumberThatAssistedClimb.setText("0");
            }
        });


        partnerClimbsAssisted.decButton.setOnClickListener(view1 -> {
            if (partnerClimbsAssisted.getValue() == 1) {
                radioButtonEnable(highestAssistedClimbLevelGroup, false);
            }
            partnerClimbsAssisted.decrement();
        });

        partnerClimbsAssisted.incButton.setOnClickListener(view2 -> {
            if (partnerClimbsAssisted.getValue() >= 0) {
                radioButtonEnable(highestAssistedClimbLevelGroup, true);
            }
            partnerClimbsAssisted.increment();

        });



        continueButton.setOnClickListener(view1 -> {
            hideKeyboard();
            if (climbAssistedByPartners.isChecked() && teamNumberThatAssistedClimb
                    .getText().toString().isEmpty() || partnerClimbsAssisted.getValue() >= 1 && !checkIfButtonIsChecked(highestAssistedClimbLevel)
                    || attemptHabClimb.isChecked() && !checkIfButtonIsChecked(attemptHabClimbLevel) || successHabClimb.isChecked()
                    && !checkIfButtonIsChecked(successHabClimbLevel)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Please fill in any empty fields")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
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

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();
        }

        return view;
    }

    private static boolean checkIfButtonIsChecked(RadioButton[] groupToCheck) {
        for (RadioButton button : groupToCheck) {
            if (button.isChecked()) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method that receives a RadioButton array and returns an integer corresponding to the hab
     * level selected
     *
     * @param habLevelArray Provide a RadioButtonGroup with three hab level options
     * @return integer Returns integer value corresponding to hab levels 1,2, and 3
     */
    public static int getHabLevelSelected(RadioButton[] habLevelArray) {
        for (int i = 0; i < habLevelArray.length; i++) {
            if (habLevelArray[i].isChecked()) {
                return i + 1;
            }
        }
        return 0;
    }

    int getIntegerFromTextBox(EditText numberEditText) {
        if (numberEditText.getText().toString().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(numberEditText.getText().toString());
        }
    }

    public static int getHighestHabLevelSelected(RadioButton[] highestHabLevelArray) {
        for (int i = 0; i < 2; i++) {
            if (highestHabLevelArray[i].isChecked()) {
                return i + 2;
            }
        }
        return 0;
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

}