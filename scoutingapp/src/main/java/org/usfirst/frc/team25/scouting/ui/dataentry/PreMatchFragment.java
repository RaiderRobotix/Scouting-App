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
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

    private RadioButton[] startingPositionButtons, startingLevelButtons, startingGamePieceButtons;
    private RadioGroup startingGamePieceGroup, startingLevelButtonsGroup,
            startingPositionButtonsGroup;
    private MaterialEditText nameField, matchNumField, teamNumField;
    private MaterialBetterSpinner scoutPosSpinner;
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


        Button continueButton = view.findViewById(R.id.prematch_continue);

        scoutPosSpinner = view.findViewById(R.id.scout_pos_spin);
        scoutPosSpinner.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.position_options)));
        scoutPosSpinner.setFloatingLabel(MaterialAutoCompleteTextView.FLOATING_LABEL_NORMAL);

        robotNoShow = view.findViewById(R.id.robot_no_show_checkbox);

        startingLevelButtons = new RadioButton[2];
        startingLevelButtons[0] = view.findViewById(R.id.hab_level_1);
        startingLevelButtons[1] = view.findViewById(R.id.hab_level_2);

        nameField = view.findViewById(R.id.scout_name_field);
        matchNumField = view.findViewById(R.id.match_num_field);
        teamNumField = view.findViewById(R.id.team_num_field);

        startingPositionButtons = new RadioButton[3];
        startingPositionButtons[0] = view.findViewById(R.id.leftStart);
        startingPositionButtons[1] = view.findViewById(R.id.centerStart);
        startingPositionButtons[2] = view.findViewById(R.id.rightStart);

        startingGamePieceButtons = new RadioButton[3];
        startingGamePieceButtons[0] = view.findViewById(R.id.cargo_button);
        startingGamePieceButtons[1] = view.findViewById(R.id.hatch_panel_button);
        startingGamePieceButtons[2] = view.findViewById(R.id.none_button);

        startingLevelButtonsGroup = view.findViewById(R.id.starting_level);
        startingPositionButtonsGroup = view.findViewById(R.id.starting_position);
        startingGamePieceGroup = view.findViewById(R.id.robot_starting_game_piece);

        autoPopulate();

        // Nudges the user to fill in the next unfilled text field
        EditText[] fields = new EditText[]{nameField, matchNumField, teamNumField};

        for (EditText field : fields) {
            if (field.getText().toString().isEmpty()) {
                field.requestFocus();
            }
        }

        startingLevelButtons[1].setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                startingPositionButtons[1].setEnabled(false);
                startingPositionButtons[1].setChecked(false);
            } else {
                startingPositionButtons[1].setEnabled(true);
            }

        });

        robotNoShow.setOnCheckedChangeListener((compoundButton, becameChecked) -> {

            UiHelper.radioButtonEnable(startingLevelButtonsGroup, !becameChecked);
            UiHelper.radioButtonEnable(startingPositionButtonsGroup, !becameChecked);
            UiHelper.radioButtonEnable(startingGamePieceGroup, !becameChecked);

        });


        continueButton.setOnClickListener(view1 -> {

            boolean proceed = true;
            Settings set = Settings.newInstance(getActivity());

            set.setMaxMatchNum(FileManager.getMaxMatchNum(getActivity()));

            // Sequentially verifies that user inputted a value
            if (nameField.getText().toString().isEmpty()) {
                nameField.setError("Scout name required");
                proceed = false;
            }

            if (scoutPosSpinner.getText().toString().isEmpty()) {
                scoutPosSpinner.setError("Scout position required");
                proceed = false;
            }

            if (matchNumField.getText().toString().isEmpty()) {
                matchNumField.setError("Match number required");
                proceed = false;

            } else if (Integer.parseInt(matchNumField.getText().toString()) < 1 ||
                    Integer.parseInt(matchNumField.getText().toString()) > Settings.newInstance(getActivity()).getMaxMatchNum()) {
                matchNumField.setError("Invalid match number value");
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


            RadioButton[][] startingValueButtons = new RadioButton[][]{startingPositionButtons,
                    startingLevelButtons, startingGamePieceButtons};

            for (RadioButton[] startingValueSet : startingValueButtons) {
                boolean buttonSelectedInSet = false;
                for (RadioButton button : startingValueSet) {
                    if (button.isChecked()) {
                        buttonSelectedInSet = true;
                        break;
                    }
                }
                if (!buttonSelectedInSet) {
                    startingValuesSelected = false;
                    break;
                }
            }


            if (proceed && !robotNoShow.isChecked() && !startingValuesSelected) {
                proceed = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select robot starting level/position/game piece")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {

                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

            if (proceed && robotNoShow.isChecked()) {
                proceed = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setNegativeButton("No", ((dialogInterface, i) -> {
                }))
                        .setPositiveButton("Yes", ((dialogInterface, i) -> continueToAuto()));

                View dialogBox = inflater.inflate(R.layout.view_robot_no_show, null);
                builder.setView(dialogBox);
                AlertDialog alertDialog = builder.show();

                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setEnabled(false);

                button.setTextColor(Color.parseColor("#c3c3c3"));

                EditText yesConfirmation = dialogBox.findViewById(R.id.yes_text_input);
                yesConfirmation.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                                  int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1,
                                              int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.toString().equals("YES")) {
                            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                                    .setEnabled(true);
                            button.setTextColor(Color.parseColor("#000000"));
                        } else {
                            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                                    .setEnabled(false);
                            button.setTextColor(Color.parseColor("#c3c3c3"));
                        }

                    }

                });


            }

            // If all normal checks pass, verify against team lists or match schedule
            if (Settings.newInstance(getActivity()).useTeamList() && proceed) {
                boolean checkTeamList = false;
                try {

                    // Non-quals matches don't get checked against match schedule
                    if (!Settings.newInstance(getActivity()).getMatchType().equals("Q")) {
                        checkTeamList = true;
                    } else if (!FileManager.getTeamPlaying(getActivity(),
                            scoutPosSpinner.getText().toString(),
                            Integer.parseInt(matchNumField.getText().toString())).equals(teamNumField.getText().toString())) {
                        proceed = false;


                        new AlertDialog.Builder(getActivity())
                                .setTitle("Confirm team number against the field, " + nameField.getText().toString() + "!!")
                                .setMessage("Are you sure that team " + teamNumField.getText().toString() + " is " +
                                        scoutPosSpinner.getText().toString() + " in match " + matchNumField.getText().toString() + "?")
                                .setPositiveButton("Yes", (dialog, which) -> continueToAuto())
                                .setNegativeButton("No", (dialog, which) -> {

                                })
                                .create()
                                .show();
                        continueToAuto();

                    }

                } catch (IOException e) {
                    //Match list does not exist; looking for team list
                    checkTeamList = true;
                }
                if (checkTeamList && !FileManager.isOnTeamlist(teamNumField.getText().toString(),
                        getActivity())) {
                    proceed = false;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Confirm team number against the field, " + nameField.getText().toString() + "!!")
                            .setMessage("Are you sure that team " + teamNumField.getText().toString() + " is playing at " + set.getCurrentEvent() + "?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                FileManager.addToTeamList(teamNumField.getText().toString(),
                                        getActivity());

                                continueToAuto();
                            })
                            .setNegativeButton("No", (dialog, which) -> {

                            })
                            .create()
                            .show();
                }
            }


            if (proceed) {
                continueToAuto();

            }
        });

        return view;
    }

    public void autoPopulate() {

        //Manually filled data overrides preferences
        if (entry.getPreMatch() != null) {
            PreMatch prevPreMatch = entry.getPreMatch();

            nameField.setText(prevPreMatch.getScoutName());
            scoutPosSpinner.setText(prevPreMatch.getScoutPos());
            matchNumField.setText(String.valueOf(prevPreMatch.getMatchNum()));
            teamNumField.setText(String.valueOf(prevPreMatch.getTeamNum()));


            for (RadioButton button : startingPositionButtons) {
                if (button.getText().equals(prevPreMatch.getStartingPos())) {
                    button.setChecked(true);
                }
            }
            for (RadioButton button : startingGamePieceButtons) {
                if (button.getText().toString().equals(prevPreMatch.getStartingGamePiece())) {
                    button.setChecked(true);
                }
            }


            for (RadioButton button : startingLevelButtons) {
                if (button.getText().toString().contains(Integer.toString(prevPreMatch.getStartingLevel()))) {
                    button.setChecked(true);
                }
            }

            robotNoShow.setChecked(robotNoShow.isChecked());

            startingPositionButtons[1].setEnabled(prevPreMatch.getStartingLevel() == 1);


        } else {
            Settings set = Settings.newInstance(getActivity());

            if (!set.getScoutPos().equals("DEFAULT")) {
                scoutPosSpinner.setText(set.getScoutPos());

                if (set.useTeamList() && set.getMatchType().equals("Q")) {
                    try {
                        teamNumField.setText(FileManager.getTeamPlaying(getActivity(),
                                set.getScoutPos(), set.getMatchNum()));
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }

            //Scout nameField is prompted for after a shift ends, but not during the first match
            if ((!set.getScoutName().equals("DEFAULT") && !((set.getMatchNum() - 1) % set.getShiftDur() == 0)) || set.getMatchNum() == 1) {
                nameField.setText(set.getScoutName());

            }

            matchNumField.setText(String.valueOf(set.getMatchNum()));
        }
    }

    @Override
    public void saveState() {
        String startPos = "";
        for (RadioButton button : startingPositionButtons) {
            if (button.isChecked()) {
                startPos = (String) button.getText();
                break;
            }
        }

        String startPiece = "";
        for (RadioButton button : startingGamePieceButtons) {
            if (button.isChecked()) {
                startPiece = (String) button.getText();
                break;
            }
        }

        entry.setPreMatch(new PreMatch(nameField.getText().toString(),
                scoutPosSpinner.getText().toString(),
                startPos,
                Integer.parseInt(matchNumField.getText().toString()),
                Integer.parseInt(teamNumField.getText().toString()),
                UiHelper.getHabLevelSelected(startingLevelButtons),
                robotNoShow.isChecked(), startPiece
        ));
    }

    private void continueToAuto() {


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