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

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;


public class TeleOpFragment extends Fragment implements EntryFragment {

    private RadioButton[] attemptHabClimbLevel;
    private RadioButton[] successHabClimbLevel;
    private ScoutEntry entry;
    private ButtonIncDecSet cargoShipHatches;
    private ButtonIncDecSet cargoShipCargo;
    private ButtonIncDecSet rocketLevelOneHatches;
    private ButtonIncDecSet rocketLevelOneCargo;
    private ButtonIncDecSet rocketLevelTwoHatches;
    private ButtonIncDecSet rocketLevelTwoCargo;
    private ButtonIncDecSet rocketLevelThreeHatches;
    private ButtonIncDecSet rocketLevelThreeCargo;
    private ButtonIncDecView hatchesDropped;
    private CheckBox attemptHabClimb;
    private CheckBox successHabClimb;
    private ButtonIncDecView partnerClimbsAssisted;
    private CheckBox climbAssistedByPartners;
    private EditText teamNumberThatAssistedClimb;
    private RadioButton[] highestAssistedClimbLevel;
    private Settings set;


    public static TeleOpFragment getInstance(ScoutEntry entry) {
        TeleOpFragment tof = new TeleOpFragment();
        tof.setEntry(entry);
        return tof;
    }

    public static int getHabLevelSelected(RadioButton[] habLevelArray) {
        for (int i = 0; i < habLevelArray.length; i++) {
            if (habLevelArray[i].isChecked()) {
                return i + 1;
            }
        }
        return 0;
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
            hatchesDropped.setValue(tele.getHatchesDropped());
            teamNumberThatAssistedClimb.setText(tele.getAssistingClimbTeamNumber());
            attemptHabClimb.setChecked(tele.isAttemptHabClimb());
            successHabClimb.setChecked(tele.isSuccessHabClimb());
            teamNumberThatAssistedClimb.setEnabled(false);
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
        Button continueButton = view.findViewById(R.id.tele_continue);

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
                radioButtonEnable(successHabClimbLevel, true);
            } else {
                radioButtonEnable(successHabClimbLevel, false);
            }
        });

        attemptHabClimb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                successHabClimb.setEnabled(true);
                radioButtonEnable(attemptHabClimbLevel, true);
            } else {
                successHabClimb.setEnabled(false);
                successHabClimb.setChecked(false);
                radioButtonEnable(attemptHabClimbLevel, false);
                radioButtonEnable(successHabClimbLevel, false);
            }
        });

        continueButton.setOnClickListener(view1 -> {
            hideKeyboard();
            if (false/*otherRobotClimbsAssisted.isChecked() && teamNumberThatAssistedClimb
            .getText().toString().isEmpty()*/) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Fill in any empty fields")
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

    private void radioButtonEnable(RadioButton[] groupToEnableOrDisable, boolean modeSelected) {
        if (modeSelected) {
            for (RadioButton button : groupToEnableOrDisable) {
                button.setEnabled(true);
            }
        } else {
            for (RadioButton button : groupToEnableOrDisable) {
                button.setEnabled(false);
                button.setChecked(false);
            }
        }

    }

    @Override
    public void saveState() {


        //TODO reimplement this
     /*   entry.setTeleOp(new TeleOp(cargoShipHatches.getValue(),
                cargoShipCargo.getValue(),
                rocketLevelOneHatches.getValue(),
                rocketLevelOneCargo.getValue(),
                cargoShipCargo.getValue(),
                hatchesDropped.getValue(),
                climbsAssisted.getValue(),
                getHabLevelSelected(attemptHabClimbLevel),
                getHabLevelSelected(successHabClimbLevel),
                attemptHabClimb.isChecked(),
                successHabClimb.isChecked(),
                climbsOtherRobots.isChecked())); */
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