package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.models.PreMatch;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.Settings;

import java.io.IOException;

public class PrematchFragment extends Fragment implements  EntryFragment{

    private Button continueButton;
    private MaterialEditText name;
    private MaterialEditText matchNum;
    private MaterialEditText teamNum;
    private MaterialBetterSpinner scoutPos;
    private ScoutEntry entry;
    private final RadioButton[] startingPositions = new RadioButton[3];


    @Override
    public ScoutEntry getEntry() {
        return entry;
    }

    public PrematchFragment() {
        // Required empty public constructor
    }


    public static PrematchFragment getInstance(ScoutEntry entry){
        PrematchFragment pf = new PrematchFragment();
        pf.setEntry(entry);
        return pf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.fragment_prematch, container, false);
        continueButton = view.findViewById(R.id.prematch_continue);

        scoutPos = view.findViewById(R.id.scout_pos_spin);
        scoutPos.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.position_options)));
        scoutPos.setFloatingLabel(MaterialAutoCompleteTextView.FLOATING_LABEL_NORMAL);

        name = view.findViewById(R.id.scout_name_field);
        matchNum = view.findViewById(R.id.match_num_field);
        teamNum = view.findViewById(R.id.team_num_field);
        startingPositions[0] = view.findViewById(R.id.leftStart);
        startingPositions[1] = view.findViewById(R.id.centerStart);
        startingPositions[2] = view.findViewById(R.id.rightStart);

        autoPopulate();

        if(name.getText().toString().equals(""))
            name.requestFocus();
        else if(matchNum.getText().toString().equals(""))
            matchNum.requestFocus();
        else if(teamNum.getText().toString().equals(""))
            teamNum.requestFocus();



        /*event.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Settings.newInstance(getActivity()).setCurrentEvent(event.getText().toString());
            }
        });*/

        continueButton.setOnClickListener(view1 -> {
           boolean proceed = true;
            Settings set = Settings.newInstance(getActivity());
           // set.setCurrentEvent(event.getText().toString());
            set.setMaxMatchNum(FileManager.getMaxMatchNum(getActivity()));
            if(name.getText().toString().equals("")){
                name.setError("Scout name required");
                proceed = false;
            }

            if(scoutPos.getText().toString().equals("")){
                scoutPos.setError("Scout position required");
                proceed = false;
            }

            if(matchNum.getText().toString().equals("")) {
                matchNum.setError("Match number required");
                proceed = false;
            }

            else if(Integer.parseInt(matchNum.getText().toString()) < 1 || Integer.parseInt(matchNum.getText().toString()) > Settings.newInstance(getActivity()).getMaxMatchNum()){
                matchNum.setError("Invalid match number value");
                proceed=false;
            }

           /* if(event.getText().toString().equals("")){
                event.setError("Event required");
                proceed=false;
            }*/


            if(teamNum.getText().toString().equals("") || Integer.parseInt(teamNum.getText().toString()) < 1 || Integer.parseInt(teamNum.getText().toString()) > 9999) {
                if (teamNum.getText().toString().equals(""))
                    teamNum.setError("Team number required");
                else teamNum.setError("Invalid team number");
                proceed = false;
            }
            boolean aButtonSelected = false;
            for(RadioButton button : startingPositions){
                if(button.isChecked())
                    aButtonSelected = true;
            }

            if(proceed&&!aButtonSelected){
                proceed = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select robot starting position")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                            //do things
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }



            if(Settings.newInstance(getActivity()).useTeamList()&&proceed){
                    boolean checkTeamList = false;
                    try {
                        if(!Settings.newInstance(getActivity()).getMatchType().equals("Q")) //Non-quals matches don't get checked against match schedule
                            checkTeamList = true;
                        else if (!FileManager.getTeamPlaying(getActivity(), scoutPos.getText().toString(), Integer.parseInt(matchNum.getText().toString())).equals(teamNum.getText().toString())) {

                            proceed = false;
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Confirm team number")
                                    .setMessage("Are you sure that team " + teamNum.getText().toString() + " is " + scoutPos.getText().toString() + " in match " + matchNum.getText().toString() + "?")
                                    .setPositiveButton("Yes", (dialog, which) -> {
                                        saveState();
                                        Settings.newInstance(getActivity()).autoSetPreferences(entry.getPreMatch());

                                        hideKeyboard();
                                        getFragmentManager().beginTransaction()
                                                .replace(android.R.id.content, AutoFragment.getInstance(entry), "AUTO")
                                                .commit();
                                    })
                                    .setNegativeButton("No", (dialog, which) -> {

                                    })
                                    .create()
                                    .show();

                        }

                    } catch (IOException e) {
                        //Match list does not exist; looking for team list
                        checkTeamList = true;
                    }
                    if(checkTeamList&&!FileManager.isOnTeamlist(teamNum.getText().toString(),getActivity())) {
                        proceed = false;
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Confirm team number")
                                .setMessage("Are you sure that team " + teamNum.getText().toString() + " is playing at " + set.getCurrentEvent() + "?")
                                .setPositiveButton("Yes", (dialog, which) -> {
                                    FileManager.addToTeamList(teamNum.getText().toString(), getActivity());
                                    continueButton.callOnClick();
                                })
                                .setNegativeButton("No", (dialog, which) -> {

                                })
                                .create()
                                .show();
                    }
            }



            if(proceed) {
                saveState();

                set.autoSetPreferences(entry.getPreMatch());
                if(entry.getPreMatch().getTeamNum()==2590)
                    getActivity().setTheme(R.style.AppTheme_NoLauncher_Red);
                else if(entry.getPreMatch().getTeamNum()==303)
                    getActivity().setTheme(R.style.AppTheme_NoLauncher_Green);
                else if(entry.getPreMatch().getTeamNum()==25)
                    getActivity().setTheme(R.style.AppTheme_NoLauncher_Raider);
                else getActivity().setTheme(R.style.AppTheme_NoLauncher_Blue);
                hideKeyboard();
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, AutoFragment.getInstance(entry), "AUTO")
                        .commit();

            }
        });


        return view;
    }

    @Override
    public void saveState() {
        String startPos = "";
        for(RadioButton button : startingPositions)
            if(button.isChecked())
                startPos = (String) button.getText();
        entry.setPreMatch(new PreMatch(name.getText().toString(), scoutPos.getText().toString(),
                Integer.parseInt(matchNum.getText().toString()), Integer.parseInt(teamNum.getText().toString()), startPos));
    }

    public void autoPopulate(){
        //Manually filled data overrides preferences
        if(entry.getPreMatch()!=null){
            PreMatch prevPreMatch = entry.getPreMatch();

            name.setText(prevPreMatch.getScoutName());
            scoutPos.setText(prevPreMatch.getScoutPos());
            matchNum.setText(String.valueOf(prevPreMatch.getMatchNum()));
            teamNum.setText(String.valueOf(prevPreMatch.getTeamNum()));
            for(RadioButton button : startingPositions)
                if(button.getText().equals(prevPreMatch.getStartingPos()))
                    button.setChecked(true);
        }

        // Pulls data values from preferences to automatically fill fields
        else {
            Settings set = Settings.newInstance(getActivity());

            if (!set.getScoutPos().equals("DEFAULT")){
                scoutPos.setText(set.getScoutPos());

                if(set.useTeamList()&&set.getMatchType().equals("Q")){
                    try {
                        teamNum.setText(FileManager.getTeamPlaying(getActivity(), set.getScoutPos(), set.getMatchNum()));
                    }catch (IOException e){

                        e.printStackTrace();
                    }
                }
            }

            //Scout name is prompted for after a shift ends, but not during the first match
            if ((!set.getScoutName().equals("DEFAULT") && !((set.getMatchNum() - 1) % set.getShiftDur() == 0)) || set.getMatchNum() == 1) {
                name.setText(set.getScoutName());

            }


           /* if (!set.getCurrentEvent().equals("DEFAULT"))
                event.setText(set.getCurrentEvent());*/


            matchNum.setText(String.valueOf(set.getMatchNum()));
        }
    }

    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Pre-Match");
    }

    /** Hides the keyboard in the next fragment
     *
     */
    public void hideKeyboard(){
        try {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


}
