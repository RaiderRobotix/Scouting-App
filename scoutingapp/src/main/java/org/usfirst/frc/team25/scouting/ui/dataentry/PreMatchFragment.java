package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.PreMatch;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.UiHelper;

import java.io.IOException;

public class PreMatchFragment extends Fragment implements EntryFragment {

    private RadioButton[] typeTank, typeSwerve, speedMeterSecond, speedNone;
//    private RadioGroup startingGamePieceGroup, startingLevelButtonsGroup, startingPositionButtonsGroup;

    private MaterialEditText nameField,nameField2, nameField3, teamNumField;
//    private MaterialBetterSpinner scoutPosSpinner;
    private ScoutEntry entry;
    private CheckBox robotNoShow;

    public static PreMatchFragment getInstance(ScoutEntry entry) {
        PreMatchFragment prematchFragment = new PreMatchFragment();
        prematchFragment.setEntry(entry);
        return prematchFragment;
    }

    @Override
    public ScoutEntry getEntry() {
        return entry;
    }

    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_prematch, container, false);

        Button finishButton = view.findViewById(R.id.post_finish);


        nameField = view.findViewById(R.id.scout_name_field);
        nameField2 = view.findViewById(R.id.scout_name_field_2);
        nameField3 = view.findViewById(R.id.scout_name_field_3);

        teamNumField = view.findViewById(R.id.team_num_field);

      //  speedMeterSecond = view.findViewById(R.id.speed_meter_second);
      //  speedNone = view.findViewById(R.id.speed_none);


        autoPopulate();

        // Nudges the user to fill in the next unfilled text field
        EditText[] fields = new EditText[]{nameField, nameField2, nameField3, teamNumField};

        for (EditText field : fields) {
            if (field.getText().toString().isEmpty()) {
                field.requestFocus();
            }
        }


        robotNoShow.setOnCheckedChangeListener((compoundButton, becameChecked) -> {



        });




        finishButton.setOnClickListener(view1 -> {

            boolean proceed = true;

            // Sequentially verifies that user inputted a value
            if (nameField.getText().toString().isEmpty()) {
                nameField.setError("Scout name required");
                proceed = false;
            }

            if (teamNumField.getText().toString().length() == 0 || Integer.parseInt(teamNumField.getText().toString()) < 1
                    || Integer.parseInt(teamNumField.getText().toString()) > 9999) {
                if (teamNumField.getText().length() == 0) {
                    teamNumField.setError("Team number required");
                } else {
                    teamNumField.setError("Invalid team number");
                }
                proceed = false;
            }

            boolean startingValuesSelected = true;

//            if (proceed && robotNoShow.isChecked()) {
//                proceed = false;
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setNegativeButton("No", ((dialogInterface, i) -> {
//                        }))
//                        .setPositiveButton("Yes", ((dialogInterface, i) -> continueToAuto()));

//                View dialogBox = inflater.inflate(R.layout.view_robot_no_show, null);
//                builder.setView(dialogBox);
//                AlertDialog alertDialog = builder.show();

//                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//                button.setEnabled(false);
//
//                button.setTextColor(Color.parseColor("#c3c3c3"));
//
//                EditText yesConfirmation = dialogBox.findViewById(R.id.yes_text_input);

//                yesConfirmation.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1,
//                                                  int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1,
//                                              int i2) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//                        if (editable.toString().equals("YES")) {
//                            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
//                                    .setEnabled(true);
//                            button.setTextColor(Color.parseColor("#000000"));
//                        } else {
//                            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
//                                    .setEnabled(false);
//                            button.setTextColor(Color.parseColor("#c3c3c3"));
//                        }
//
//                    }
//
//                });

            if (proceed) {
                continueToFinish();
            }
        });

        return view;
    }

    public void autoPopulate() {

        //Manually filled data overrides preferences
        if (entry.getPreMatch() != null) {
            PreMatch prevPreMatch = entry.getPreMatch();

            nameField.setText(prevPreMatch.getScoutName());
            nameField2.setText(prevPreMatch.getScoutName());
            nameField3.setText(prevPreMatch.getScoutName());

            teamNumField.setText(String.valueOf(prevPreMatch.getTeamNum()));

            



        } else {
//            Settings set = Settings.newInstance(getActivity());
//
//            if (!set.getScoutPos().equals("DEFAULT")) {
//                scoutPosSpinner.setText(set.getScoutPos());
//
//                if (set.useTeamList() && set.getMatchType().equals("Q")) {
//                    try {
//                        teamNumField.setText(FileManager.getTeamPlaying(getActivity(),
//                                set.getScoutPos(), set.getMatchNum()));
//                    } catch (IOException e) {
//
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            //Scout nameField is prompted for after a shift ends, but not during the first match
//            if ((!set.getScoutName().equals("DEFAULT") && !((set.getMatchNum() - 1) % set.getShiftDur() == 0)) || set.getMatchNum() == 1) {
//                nameField.setText(set.getScoutName());
//
//            }
//
//            matchNumField.setText(String.valueOf(set.getMatchNum()));
        }
    }

    @Override
    public void saveState() {
        String startPos = "";




        entry.setPreMatch(new PreMatch(nameField.getText().toString(),
//                scoutPosSpinner.getText().toString(),
                Integer.parseInt(matchNumField.getText().toString()),
                Integer.parseInt(teamNumField.getText().toString()),
                robotNoShow.isChecked()
        ));
    }

    private void continueToFinish() {


        saveState();

        Settings.newInstance(getActivity()).autoSetPreferences(entry.getPreMatch());

        UiHelper.autoSetTheme(getActivity(), entry.getPreMatch().getTeamNum());

        UiHelper.hideKeyboard(getActivity());
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, AutoFragment.getInstance(entry), "AUTO")
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Pre-Match");
    }
}