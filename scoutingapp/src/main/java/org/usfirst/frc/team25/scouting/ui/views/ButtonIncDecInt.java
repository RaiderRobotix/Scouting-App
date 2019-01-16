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
public class ButtonIncDecInt extends RelativeLayout {

    public Button incButton, decButton;
    private TextView valueView;
    private int incDecAmount, minValue, maxValue;

    private OnClickListener listener;

    public ButtonIncDecInt(Context c, AttributeSet attrs) {
        super(c, attrs);
        initializeViews(c);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.ButtonIncDecInt);

        //XML attributes that can be set in layout files, rather than programmatically
        setValue(typedArray.getInteger(R.styleable.ButtonIncDecInt_initialValue, 0));
        setTitle(typedArray.getString(R.styleable.ButtonIncDecInt_titlePrompt));
        setMinValue(typedArray.getInteger(R.styleable.ButtonIncDecInt_minValue, 0));
        setMaxValue(typedArray.getInteger(R.styleable.ButtonIncDecInt_maxValue, Integer.MAX_VALUE));
        setIncDecAmount(typedArray.getInteger(R.styleable.ButtonIncDecInt_incDecAmount, 1));

        typedArray.recycle();

    }

    private void initializeViews(Context c) {
        LayoutInflater inflater =
                (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_button_inc_dec, this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && listener != null) {
            listener.onClick(this);
        }

        return super.dispatchKeyEvent(event);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP && listener != null) {
            listener.onClick(this);
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param title - title of the left TextView
     */
    private void setTitle(CharSequence title) {
        TextView titleView = findViewById(R.id.button_inc_dec_title);
        titleView.setText(title);
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
    public void setValue(int value) {
        if (value > maxValue)
            value = maxValue;

        if (value < minValue)
            value = minValue;

        valueView = findViewById(R.id.button_inc_dec_value);
        valueView.setText(String.valueOf(value));
    }

    private void setIncDecAmount(int incDecAmount) {
        this.incDecAmount = incDecAmount;
    }

    private void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    private void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
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
}
