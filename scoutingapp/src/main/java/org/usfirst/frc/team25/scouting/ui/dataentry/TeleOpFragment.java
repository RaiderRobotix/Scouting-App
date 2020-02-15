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

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.UiHelper;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

import lombok.val;

import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;


public class TeleOpFragment extends Fragment implements EntryFragment {

    private ScoutEntry entry;
    private CheckBox rotationControl, rotationOverspun, positionControl,rungLevel, attemptedClimb, successClimb, park;
    private ButtonIncDecView cellsScoredBottom, cellsScoredInner, cellsScoredOuter, cellsDropped,climbAssistedByPartners;
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

        rotationControl = view.findViewById(R.id.rotation_control_teleop);
        rotationOverspun = view.findViewById(R.id.rotation_control_overspin_teleop);
        positionControl = view.findViewById(R.id.position_control_teleop);
        rungLevel = view.findViewById(R.id.rung_is_level_teleop);
        attemptedClimb = view.findViewById(R.id.attempts_climb_teleop);
        successClimb = view.findViewById(R.id.climb_success_teleop);
        cellsScoredBottom = view.findViewById(R.id.bottom_power_port_teleop);
        cellsScoredInner = view.findViewById(R.id.inner_power_port_teleop);
        cellsScoredOuter = view.findViewById(R.id.outer_power_port_teleop);
        cellsDropped = view.findViewById(R.id.power_cells_dropped_teleop);
        climbAssistedByPartners = view.findViewById(R.id.assisted_climbs_teleop);
        assistingClimbTeamNum = view.findViewById(R.id.team_number_text_input);
        park = view.findViewById(R.id.parks_teleop);
        Button continueButton = view.findViewById(R.id.teleop_continue);

        autoPopulate();

        climbAssistedByPartners.incButton.setOnClickListener(view1 -> {
            climbAssistedByPartners.increment();
            assistingClimbTeamNum.setEnabled(true);
        });
        climbAssistedByPartners.decButton.setOnClickListener(view1 -> {
            climbAssistedByPartners.decrement();
            if (climbAssistedByPartners.getValue() != 0) {
                assistingClimbTeamNum.setEnabled(true);
            } else if (climbAssistedByPartners.getValue() == 0) {
                assistingClimbTeamNum.setEnabled(false);
            }
        });

        continueButton.setOnClickListener(view1 -> {
            hideKeyboard(getActivity());

            boolean proceed = true;

            if (climbAssistedByPartners.getValue() > 0) {
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

            if (proceed && successClimb.isChecked() && rungLevel.isChecked() && !(attemptedClimb.isChecked())) {

                proceed = false;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Please fill in any empty hang fields")
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

        if (entry.getPreMatch().getNoShow()) {
            continueButton.callOnClick();
        }

        return view;
    }
    @Override
    public void autoPopulate() {
        entry.getTeleOp();
        TeleOp tele = entry.getTeleOp();

        if(tele != null) {

            cellsScoredOuter.setValue(tele.getCellsScoredOuter());
            cellsScoredInner.setValue(tele.getCellsScoredInner());
            cellsScoredBottom.setValue(tele.getCellsScoredBottom());
            cellsDropped.setValue(tele.getCellsDropped());
            climbAssistedByPartners.setValue(tele.getAssistedClimbs());
            rungLevel.setChecked(tele.getRungLevel());
            attemptedClimb.setChecked(tele.getAttemptHang());
            successClimb.setChecked(tele.getSuccessHang());
            rotationControl.setChecked(tele.getRotationControl());
            rotationOverspun.setChecked(tele.getRotationOverspun());
            positionControl.setChecked(tele.getPositionControl());
            park.setChecked(tele.getParked());

            if (tele.getAssistingClimbTeamNum() != 0) {
                assistingClimbTeamNum.setText(Integer.toString(tele.getAssistingClimbTeamNum()));
            }
        }

    }

    @Override
    public void saveState() {
        val entry = getEntry();
        setEntry(
                new ScoutEntry(
                        entry.getPreMatch(),
                        entry.getAutonomous(),
                        new TeleOp(
                                cellsScoredBottom.getValue(),
                                cellsScoredInner.getValue(),
                                cellsScoredOuter.getValue(),
                                cellsDropped.getValue(),
                                rotationControl.isChecked(),
                                rotationOverspun.isChecked(),
                                positionControl.isChecked(),
                                attemptedClimb.isChecked(),
                                successClimb.isChecked(),
                                rungLevel.isChecked(),
                                climbAssistedByPartners.getValue(),
                                UiHelper.getIntegerFromTextBox(assistingClimbTeamNum),
                                park.isChecked()
                        ),
                        entry.getPostMatch()
                )
        );
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        hideKeyboard(getActivity());
    }

}