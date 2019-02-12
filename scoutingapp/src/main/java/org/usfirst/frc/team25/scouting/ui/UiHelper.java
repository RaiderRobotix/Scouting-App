package org.usfirst.frc.team25.scouting.ui;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

    public static int getIntegerFromTextBox(EditText numberEditText) {
        if (numberEditText.getText().toString().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(numberEditText.getText().toString());
        }
    }

    public static int getHighestHabLevelSelected(RadioButton[] highestHabLevelArray) {
        for (int i = 0; i < 2; i++) {
            if (highestHabLevelArray[i].isChecked()) {
                return i + 2;
            }
        }
        return 0;
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
}

