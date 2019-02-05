package org.usfirst.frc.team25.scouting.ui.preferences;


import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.thebluealliance.DataDownloader;
import org.usfirst.frc.team25.scouting.ui.views.DecimalPickerPreference;
import org.usfirst.frc.team25.scouting.ui.views.NumberPickerPreference;

/**
 * List of preferences, as defined in res/xml/preferences.xml
 */

public class SettingsFragment extends PreferenceFragment {

    private ListPreference leftStation;
    private Preference year;
    private NumberPickerPreference matchNum, shiftDur;
    private DecimalPickerPreference timerManualInc;
    private EditTextPreference scoutNameInput;
    private Settings settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        settings = Settings.newInstance(getActivity());

        shiftDur = (NumberPickerPreference) findPreference("shift_dur");
        scoutNameInput = (EditTextPreference) findPreference("scout_name");
        ListPreference matchType = (ListPreference) findPreference("match_type");
        matchNum = (NumberPickerPreference) findPreference("match_num");
        ListPreference event = (ListPreference) findPreference("event");
        Preference deleteFiles = findPreference("delete_data");
        Preference changePass = findPreference("change_pass");
        year = findPreference("year");

        Preference downloadSchedule = findPreference("download_schedule");
        Preference game = findPreference("game");
        // Buttons that hold a value, but do not prompt a dialogue
        Preference version = findPreference("version");
        leftStation = (ListPreference) findPreference("leftStation");

        timerManualInc = (DecimalPickerPreference) findPreference("timer_manual_inc");

        updateSummary();

        game.setSummary(getString(R.string.game_name));
        version.setSummary(getString(R.string.version_number));

        matchNum.setMaxValue(Settings.newInstance(getActivity()).getMaxMatchNum());
        shiftDur.setMaxValue(25);


        event.setOnPreferenceChangeListener((preference, o) -> {
            matchNum = (NumberPickerPreference) findPreference("match_num");
            matchNum.setValue(1); //Resets match number

            return true;
            // A false value means the preference change is not saved
        });


        matchType.setOnPreferenceChangeListener((preference, o) -> {
            matchNum = (NumberPickerPreference) findPreference("match_num");
            matchNum.setValue(1);
            return true;
        });


        deleteFiles.setOnPreferenceClickListener(preference -> {
            if (settings.getHashedPass().equals("DEFAULT")) {
                Toast.makeText(getActivity(), "Password needs to be settings before deleting data",
                        Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(getActivity(), EnterPasswordActivity.class);
                startActivity(i);
            }

            return true;
        });


        changePass.setOnPreferenceClickListener(preference -> {
            Intent i;
            if (settings.getHashedPass().equals("DEFAULT")) {
                i = new Intent(getActivity(), SetPasswordActivity.class);
            } else {
                i = new Intent(getActivity(), ConfirmPasswordActivity.class);
            }
            startActivity(i);

            return true;
        });

        year.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "Year automatically updated", Toast.LENGTH_SHORT).show();
            return true;
        });


        downloadSchedule.setOnPreferenceClickListener(preference -> {
            new DataDownloader(getActivity()).execute();

            return true;
        });

    }


    /**
     * Updates preference summary text with their values
     */
    void updateSummary() {
        try {

            //Automates maximum match number based on current event
            Settings.newInstance(getActivity()).setMaxMatchNum(FileManager.getMaxMatchNum(getActivity()));

            shiftDur.setSummary(String.valueOf(settings.getShiftDur()) + " matches");
            scoutNameInput.setSummary(settings.getScoutName());
            matchNum.setSummary(String.valueOf(settings.getMatchNum()));
            year.setSummary(settings.getYear());
            settings.setYear();
            leftStation.setSummary(leftStation.getValue());
            timerManualInc.setSummary(settings.getTimerManualInc() + " sec");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


}
