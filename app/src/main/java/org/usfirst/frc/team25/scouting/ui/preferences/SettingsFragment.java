package org.usfirst.frc.team25.scouting.ui.preferences;


import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.ui.views.NumberPickerPreference;

//List of preferences, as defined in res/xml/preferences.xml
public class SettingsFragment extends PreferenceFragment {

    ListPreference matchType, event;
    Preference deleteFiles, changePass, year, importData; // Buttons that hold a value, but do not prompt a dialogue
    NumberPickerPreference npp, shiftDur;
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
        npp = (NumberPickerPreference) findPreference("match_num");
        event = (ListPreference) findPreference("event");
        deleteFiles = findPreference("delete_data");
        changePass =  findPreference("change_pass");
        year = findPreference("year");
        importData = findPreference("import_data");

        updateSummary();


        event.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                npp = (NumberPickerPreference) findPreference("match_num");
                npp.setValue(1);
                return true;
            }
        });

        matchType.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                npp = (NumberPickerPreference) findPreference("match_num");
                npp.setValue(1);
                return true; // A false value means the preference change is not saved
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


        importData.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //TODO: Create import match data functionality
                Toast.makeText(getActivity(), "Feature not available", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    /**
     * Updates preference summary text with their values
     */
    void updateSummary(){
        try {
            shiftDur.setSummary(String.valueOf(set.getShiftDur()) + " matches");
            scoutNameInput.setSummary(set.getScoutName());
            npp.setSummary(String.valueOf(set.getMatchNum()));
            year.setSummary(set.getYear());
            set.setYear();
        }catch(NullPointerException e){
            e.printStackTrace();
        }

    }


}
