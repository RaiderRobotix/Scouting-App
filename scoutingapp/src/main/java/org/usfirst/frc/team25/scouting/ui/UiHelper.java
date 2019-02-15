package org.usfirst.frc.team25.scouting.ui;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.usfirst.frc.team25.scouting.R;

import java.util.HashMap;

public class UiHelper {

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken()
                    , InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

    public static int getHabLevelSelected(RadioButton[] habLevelArray, int shiftIndex) {
        for (int i = 0; i < habLevelArray.length; i++) {
            if (habLevelArray[i].isChecked()) {
                return i + shiftIndex;
            }
        }
        return 0;
    }

    public static int getIntegerFromTextBox(EditText numberEditText) {
        if (numberEditText.getText().toString().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(numberEditText.getText().toString());
        }
    }


    public static void radioButtonEnable(RadioGroup groupToEnableOrDisable, boolean modeSelected) {
        if (modeSelected) {
            for (int i = 0; i < groupToEnableOrDisable.getChildCount(); i++) {
                groupToEnableOrDisable.getChildAt(i).setEnabled(true);
            }
        } else {
            for (int i = 0; i < groupToEnableOrDisable.getChildCount(); i++) {
                groupToEnableOrDisable.getChildAt(i).setEnabled(false);
            }

            groupToEnableOrDisable.clearCheck();
        }
    }

    public static boolean checkIfButtonIsChecked(RadioButton[] groupToCheck) {
        for (RadioButton button : groupToCheck) {
            if (button.isChecked()) {
                return true;
            }
        }
        return false;
    }

    public static void autoSetTheme(Activity activity, int teamNum) {
        HashMap<Integer, Integer> teamNumThemePairs = new HashMap<>();

        int selectedTheme;

        teamNumThemePairs.put(2590, R.style.AppTheme_NoLauncher_Red);
        teamNumThemePairs.put(225, R.style.AppTheme_NoLauncher_Red);
        teamNumThemePairs.put(303, R.style.AppTheme_NoLauncher_Green);
        teamNumThemePairs.put(25, R.style.AppTheme_NoLauncher_Raider);
        teamNumThemePairs.put(1923, R.style.AppTheme_NoLauncher_Black);

        if (!teamNumThemePairs.containsKey(teamNum)) {
            selectedTheme = R.style.AppTheme_NoLauncher_Blue;
        } else {
            selectedTheme = teamNumThemePairs.get(teamNum);
        }

        activity.setTheme(selectedTheme);

    }
}

