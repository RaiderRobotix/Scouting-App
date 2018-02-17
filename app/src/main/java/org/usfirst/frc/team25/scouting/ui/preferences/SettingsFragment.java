package org.usfirst.frc.team25.scouting.ui.preferences;


import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import org.usfirst.frc.team25.scouting.Constants;
import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.thebluealliance.DataDownloader;
import org.usfirst.frc.team25.scouting.ui.views.NumberPickerPreference;

import java.util.List;

/** List of preferences, as defined in res/xml/preferences.xml
 *
 */

public class SettingsFragment extends PreferenceFragment {

    ListPreference matchType, event, leftStation;
    Preference deleteFiles, changePass, year, downloadSchedule, game, version; // Buttons that hold a value, but do not prompt a dialogue
    NumberPickerPreference matchNum, shiftDur;
    EditTextPreference scoutNameInput;
    CheckBoxPreference useTeamList;
    Settings set;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        set = Settings.newInstance(getActivity());

        shiftDur = (NumberPickerPreference) findPreference("shift_dur");
        scoutNameInput = (EditTextPreference) findPreference("scout_name");
        matchType = (ListPreference) findPreference("match_type");
        matchNum = (NumberPickerPreference) findPreference("match_num");
        event = (ListPreference) findPreference("event");
        deleteFiles = findPreference("delete_data");
        changePass =  findPreference("change_pass");
        year = findPreference("year");
        downloadSchedule = findPreference("download_schedule");
        game = findPreference("game");
        version = findPreference("version");
        leftStation = (ListPreference) findPreference("leftStation");

        updateSummary();

        game.setSummary(Constants.GAME);
        version.setSummary("v"+Constants.VERSION);

        matchNum.setMaxValue(Settings.newInstance(getActivity()).getMaxMatchNum());
        shiftDur.setMaxValue(25);


        event.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                matchNum = (NumberPickerPreference) findPreference("match_num");
                matchNum.setValue(1); //Resets match number

                return true;// A false value means the preference change is not saved
            }
        });

        matchType.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                matchNum = (NumberPickerPreference) findPreference("match_num");
                matchNum.setValue(1);
                return true;
            }
        });


        deleteFiles.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(set.getHashedPass().equals("DEFAULT"))
                    Toast.makeText(getActivity(), "Password needs to be set before deleting data", Toast.LENGTH_SHORT ).show();

                else {
                    Intent i = new Intent(getActivity(), EnterPasswordActivity.class);
                    startActivity(i);
                }

                return true;
            }
        });


        changePass.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i;
                if(set.getHashedPass().equals("DEFAULT"))
                    i = new Intent(getActivity(), SetPasswordActivity.class);

                else i = new Intent(getActivity(), ConfirmPasswordActivity.class);

                startActivity(i);

                return true;
            }
        });

        year.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(), "Year automatically updated", Toast.LENGTH_SHORT).show();
                return true;
            }
        });




        downloadSchedule.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new DataDownloader(getActivity()).execute();

                return true;
            }
        });

    }



    /**
     * Updates preference summary text with their values
     */
    void updateSummary(){
        try {
            Settings.newInstance(getActivity()).setMaxMatchNum(FileManager.getMaxMatchNum(getActivity())); //Automates maximum match number based on current event
            shiftDur.setSummary(String.valueOf(set.getShiftDur()) + " matches");
            scoutNameInput.setSummary(set.getScoutName());
            matchNum.setSummary(String.valueOf(set.getMatchNum()));
            year.setSummary(set.getYear());
            set.setYear();
            leftStation.setSummary(leftStation.getValue());
        }catch(NullPointerException e){
            e.printStackTrace();
        }

    }


}
