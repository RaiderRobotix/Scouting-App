package org.usfirst.frc.team25.scouting.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.usfirst.frc.team25.scouting.R;

/**
 * Custom ViewGroup to increment or decrement a value with two buttons and a label, placed to the
 * left.
 * Minimum value is 0, with methods to set label text and initial value.
 */
public class ButtonIncDecSet extends RelativeLayout {

    public Button incButton, decButton;
    private int incDecAmount;
    private int minValue;
    private int maxValue;
    /** -ROBOTO-    I tried making private boolean trueValue and private boolean falseValue     */
    private TextView valueView;

    private OnClickListener listener;

    public ButtonIncDecSet(Context c, AttributeSet attrs) {
        super(c, attrs);
        initializeViews(c);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.ButtonIncDecSet);

        //XML attributes that can be set in layout files, rather than programmatically
        setValue(typedArray.getInteger(R.styleable.ButtonIncDecSet_initialValueSet, 0));
        setMinValue(typedArray.getInteger(R.styleable.ButtonIncDecSet_minValueSet, 0));
        setMaxValue(typedArray.getInteger(R.styleable.ButtonIncDecSet_maxValueSet,
                Integer.MAX_VALUE));
        setIncDecAmount(typedArray.getInteger(R.styleable.ButtonIncDecSet_incDecAmountSet, 1));

        typedArray.recycle();

    }

    private void initializeViews(Context c) {
        LayoutInflater inflater =
                (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_button_inc_dec_set, this);
    }

    private void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    private void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    private void setIncDecAmount(int incDecAmount) {
        this.incDecAmount = incDecAmount;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && listener != null) {
            listener.onClick(this);
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP && listener != null) {
            listener.onClick(this);
        }

        return super.dispatchTouchEvent(ev);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        valueView = findViewById(R.id.button_inc_dec_value);
        incButton = findViewById(R.id.inc_button);
        decButton = findViewById(R.id.dec_button);


        //Listeners to increment and decrement the value with a click
        incButton.setOnClickListener(view -> increment());
        decButton.setOnClickListener(view -> decrement());
    }

    public void increment() {
        setValue(getValue() + incDecAmount);
    }

    public void decrement() {
        setValue(getValue() - incDecAmount);
    }

    /**
     * @return Integer value of the displayed number
     */
    public int getValue() {
        valueView = findViewById(R.id.button_inc_dec_value);
        return Integer.parseInt(valueView.getText().toString());
    }

    /**
     * @param value - the initial value of the integer between the buttons. Cannot be less than 0.
     */

    /**   -ROBOTO-        I tried to switch (int value) to (boolean boolvalue). maxValue into boolValue, etc.*/

    public void setValue(int value) {
        if (value > maxValue) {
            value = maxValue;
        }

        if (value < minValue) {
            value = minValue;
        }

        /**  -ROBOTO-                                (*^(&% WEIRD BOOLEAN "setvalue" CODE I MADE UP ^&$%#$
        public void setValue(boolean boolvalue) {
            if (boolvalue = falseValue) {
                boolvalue = false;
            }


            if (boolvalue = minValue) {
                boolvalue = true;     */

        valueView = findViewById(R.id.button_inc_dec_value);
        valueView.setText(String.valueOf(value));
    }
}
