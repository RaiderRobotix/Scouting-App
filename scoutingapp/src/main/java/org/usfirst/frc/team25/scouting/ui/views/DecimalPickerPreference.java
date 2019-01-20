package org.usfirst.frc.team25.scouting.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

/**
 * A {@link android.preference.Preference} that displays a number picker as a dialog.
 */
public class DecimalPickerPreference extends DialogPreference {
    private NumberPicker mPicker;

    private Integer mNumber;

    public DecimalPickerPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DecimalPickerPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
        mNumber = 0;
    }

    @Override
    protected View onCreateDialogView() {
        mPicker = new NumberPicker(getContext());
        String[] values = {"0.1 sec", "0.2 sec", "0.3 sec", "0.4 sec", "0.5 sec", "0.6 sec", "0.7" +
                " sec", "0.8 sec", "0.9 sec", "1 sec", "1.1 sec", "1.2 sec", "1.3 sec", "1.4 sec"
                , "1.5 sec", "1.6 sec", "1.7 sec",
                "1.8 sec", "1.9 sec", "2 sec"};
        mPicker.setMinValue(0);
        mPicker.setMaxValue(values.length - 1); //Automates maximum match number from match list
        mPicker.setValue(mNumber);
        mPicker.setDisplayedValues(values);
        mPicker.setWrapSelectorWheel(false);
        mPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        return mPicker;
    }


    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            // needed when user edits the text field and clicks OK
            mPicker.clearFocus();
            setValue(mPicker.getValue());

        }
    }

    private void setValue(int value) {
        if (shouldPersist()) {
            persistInt(value);
        }

        if (value != mNumber) {
            mNumber = value;
            notifyChanged();
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setValue(restoreValue ? getPersistedInt(mNumber) : (Integer) defaultValue);
    }
}