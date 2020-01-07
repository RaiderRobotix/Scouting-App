package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.UiHelper;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;


public class TeleOpFragment extends Fragment implements EntryFragment {

    private ScoutEntry entry;
    private RadioButton[] attemptHabClimbLevel, successHabClimbLevel, highestAssistedClimbLevel,
            climbAssistStartingLevel;
    private RadioGroup attemptHabClimbLevelGroup, successHabClimbLevelGroup,
            highestAssistedClimbLevelGroup, climbAssistStartingLevelGroup;
    private ButtonIncDecSet cargoShipHatches, cargoShipCargo, rocketLevelOneHatches,
            rocketLevelOneCargo, rocketLevelTwoHatches, rocketLevelTwoCargo,
            rocketLevelThreeHatches, rocketLevelThreeCargo;
    private CheckBox attemptHabClimb, successHabClimb;
    private ButtonIncDecView partnerClimbsAssisted, hatchesDropped, cargoDropped;
    private CheckBox climbAssistedByPartners;
    private EditText assistingClimbTeamNum;


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

        assistingClimbTeamNum = view.findViewById(R.id.team_number_text_input);
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
        climbAssistStartingLevelGroup = view.findViewById(R.id.climb_assist_starting_level_group);

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

        climbAssistStartingLevel = new RadioButton[3];
        climbAssistStartingLevel[0] = view.findViewById(R.id.climb_assist_starting_zero);
        climbAssistStartingLevel[1] = view.findViewById(R.id.climb_assist_starting_one);
        climbAssistStartingLevel[2] = view.findViewById(R.id.climb_assist_starting_two);


        for (RadioButton anAttemptHabClimbLevel : attemptHabClimbLevel) {
            anAttemptHabClimbLevel.setOnClickListener(view1 -> autoDisableSuccessGroup());
        }

        for (RadioButton button : highestAssistedClimbLevel) {
            button.setOnClickListener((view1 -> {
                autoDisableClimbAssistStartingLevelGroup();
            }));
        }

        successHabClimb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                UiHelper.radioButtonEnable(successHabClimbLevelGroup, true);
                autoDisableSuccessGroup();
            } else {
                UiHelper.radioButtonEnable(successHabClimbLevelGroup, false);
            }
        });


        attemptHabClimb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                successHabClimb.setEnabled(true);
                UiHelper.radioButtonEnable(attemptHabClimbLevelGroup, true);
            } else {
                successHabClimb.setEnabled(false);
                successHabClimb.setChecked(false);
                UiHelper.radioButtonEnable(attemptHabClimbLevelGroup, false);
                UiHelper.radioButtonEnable(successHabClimbLevelGroup, false);
            }
        });


        climbAssistedByPartners.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                assistingClimbTeamNum.setEnabled(true);
            } else {
                assistingClimbTeamNum.setEnabled(false);
                assistingClimbTeamNum.setText("");
            }
        });


        partnerClimbsAssisted.decButton.setOnClickListener(view1 -> {
            partnerClimbsAssisted.decrement();
            if (partnerClimbsAssisted.getValue() == 0) {
                UiHelper.radioButtonEnable(highestAssistedClimbLevelGroup, false);
                UiHelper.radioButtonEnable(climbAssistStartingLevelGroup, false);
            }
        });

        partnerClimbsAssisted.incButton.setOnClickListener(view1 -> {
            partnerClimbsAssisted.increment();
            if (partnerClimbsAssisted.getValue() > 0) {
                UiHelper.radioButtonEnable(highestAssistedClimbLevelGroup, true);
                UiHelper.radioButtonEnable(climbAssistStartingLevelGroup, true);
            }
        });

        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            hideKeyboard(getActivity());

            boolean proceed = true;

            if (climbAssistedByPartners.isChecked()) {
                if (assistingClimbTeamNum.getText().toString().isEmpty()) {
                    assistingClimbTeamNum.setError("Enter the assisting team's number");
                    proceed = false;

                } else {
                    int inputTeamNum =
                            Integer.parseInt(assistingClimbTeamNum.getText().toString());
                    if (inputTeamNum <= 0 || inputTeamNum > 9999) {
                        assistingClimbTeamNum.setError("Enter a valid team number");
                        proceed = false;
                    }
                }


            }

            if (proceed && partnerClimbsAssisted.getValue() >= 1 &&
                    !(UiHelper.checkIfButtonIsChecked(highestAssistedClimbLevel) && UiHelper.checkIfButtonIsChecked(climbAssistStartingLevel))
                    || attemptHabClimb.isChecked() && !UiHelper.checkIfButtonIsChecked(attemptHabClimbLevel) || successHabClimb.isChecked()
                    && !UiHelper.checkIfButtonIsChecked(successHabClimbLevel)) {

                proceed = false;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Please fill in any empty HAB climb fields")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

            if (proceed) {
                saveState();
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, PostMatchFragment.getInstance(entry), "POST")
                        .commit();
            }
        });

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();
        }

        return view;
    }

    private void autoDisableClimbAssistStartingLevelGroup() {
        if (highestAssistedClimbLevel[0].isChecked()) {
            if (climbAssistStartingLevel[2].isChecked()) {
                climbAssistStartingLevelGroup.clearCheck();

            }
            climbAssistStartingLevel[2].setEnabled(false);
        } else if (partnerClimbsAssisted.getValue() > 0) {
            climbAssistStartingLevel[2].setEnabled(true);
        }

    }

    @Override
    public void autoPopulate() {
        if (entry.getTeleOp() != null) {
            TeleOp tele = entry.getTeleOp();

            cargoShipHatches.setValue(tele.getCargoShipHatches());
            cargoShipCargo.setValue(tele.getCargoShipCargo());
            rocketLevelOneCargo.setValue(tele.getRocketLevelOneCargo());
            rocketLevelOneHatches.setValue(tele.getRocketLevelOneHatches());
            rocketLevelTwoCargo.setValue(tele.getRocketLevelTwoCargo());
            rocketLevelTwoHatches.setValue(tele.getRocketLevelTwoHatches());
            rocketLevelThreeCargo.setValue(tele.getRocketLevelThreeCargo());
            rocketLevelThreeHatches.setValue(tele.getRocketLevelThreeHatches());

            climbAssistedByPartners.setChecked(tele.isClimbAssistedByPartner());
            partnerClimbsAssisted.setValue(tele.getNumPartnerClimbAssists());
            hatchesDropped.setValue(tele.getHatchesDropped());
            cargoDropped.setValue(tele.getCargoDropped());
            attemptHabClimb.setChecked(tele.isAttemptHabClimb());
            successHabClimb.setChecked(tele.isSuccessHabClimb());

            if (tele.getAssistingClimbTeamNum() != 0) {
                assistingClimbTeamNum.setText(Integer.toString(tele.getAssistingClimbTeamNum()));
            }

            for (int i = 2; i <= 3; i++) {
                if (i == tele.getPartnerClimbAssistEndLevel()) {
                    highestAssistedClimbLevel[i - 2].setChecked(true);
                }
            }

            for (int i = 0; i < 3; i++) {
                if (i == tele.getPartnerClimbAssistStartLevel()) {
                    climbAssistStartingLevel[i].setChecked(true);
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
                UiHelper.radioButtonEnable(highestAssistedClimbLevelGroup, true);
                UiHelper.radioButtonEnable(climbAssistStartingLevelGroup, true);
            }

            autoDisableClimbAssistStartingLevelGroup();

            autoDisableSuccessGroup();

        }
    }

    @Override
    public void saveState() {

        entry.setTeleOp(new TeleOp(cargoShipHatches.getValue(), rocketLevelOneHatches.getValue(),
                rocketLevelTwoHatches.getValue(), rocketLevelThreeHatches.getValue(),
                cargoShipCargo.getValue(), rocketLevelOneCargo.getValue(),
                rocketLevelTwoCargo.getValue(), rocketLevelThreeCargo.getValue(),
                hatchesDropped.getValue(), cargoDropped.getValue(), attemptHabClimb.isChecked(),
                UiHelper.getHabLevelSelected(attemptHabClimbLevel), successHabClimb.isChecked(),
                UiHelper.getHabLevelSelected(successHabClimbLevel),
                climbAssistedByPartners.isChecked(),
                UiHelper.getIntegerFromTextBox(assistingClimbTeamNum),
                partnerClimbsAssisted.getValue(),
                UiHelper.getHabLevelSelected(highestAssistedClimbLevel, 2),
                UiHelper.getHabLevelSelected(climbAssistStartingLevel, 0)
        ));


    }

    private void autoDisableSuccessGroup() {
        int attemptLevel = -1;
        for (int i = 0; i < attemptHabClimbLevel.length; i++) {
            if (attemptHabClimbLevel[i].isChecked()) {
                attemptLevel = i;
            }

        }
        if (successHabClimb.isChecked()) {
            for (int j = 0; j < successHabClimbLevel.length; j++) {
                if (j > attemptLevel) {
                    successHabClimbLevel[j].setEnabled(false);
                    if (successHabClimbLevel[j].isChecked()) {
                        successHabClimbLevelGroup.clearCheck();
                    }
                } else {
                    successHabClimbLevel[j].setEnabled(true);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        hideKeyboard(getActivity());
    }

}