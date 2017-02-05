package org.usfirst.frc.team25.scouting.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.usfirst.frc.team25.scouting.R;

/**
 * Custom ViewGroup to increment or decrement a value with two buttons and a label, placed to the left.
 * Minimum value is 0, with methods to set label text and initial value.
 */
public class ButtonIncDec extends RelativeLayout {

    private Button incButton, decButton;
    private TextView valueView;
    private int incDecAmount;
    private int minValue;
    private int maxValue;


    public ButtonIncDec(Context c, AttributeSet attrs){
        super(c, attrs);
        initializeViews(c);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ButtonIncDec);

        //XML attributes that can be set in layout files, rather than programmatically
        setValue(typedArray.getInteger(R.styleable.ButtonIncDec_initialValue, 1));
        setTitle(typedArray.getString(R.styleable.ButtonIncDec_titlePrompt));
        setMinValue(typedArray.getInteger(R.styleable.ButtonIncDec_minValue, 0));
        setMaxValue(typedArray.getInteger(R.styleable.ButtonIncDec_maxValue, Integer.MAX_VALUE));
        setIncDecAmount(typedArray.getInteger(R.styleable.ButtonIncDec_incDecAmount, 1));


        typedArray.recycle();

    }

    private void initializeViews(Context c){
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.button_inc_view, this);
    }

    /**
     * @param title - title of the left TextView
     */
    public void setTitle(CharSequence title){
        TextView titleView = (TextView) findViewById(R.id.button_inc_dec_title);
        titleView.setText(title);
    }

    /**
     * @param value - the initial value of the integer between the buttons. Cannot be less than 0.
     */
    public void setValue(int value){
        if(value < 0)
            return; //Value cannot be set less than 0
        if(value>maxValue) {
            setValue(maxValue);
            return;
        }

        valueView = (TextView) findViewById(R.id.button_inc_dec_value);
        valueView.setText(String.valueOf(value));
    }

    /**
     *
     * @return Integer value of the displayed number
     */
    public int getValue(){
        valueView = (TextView) findViewById(R.id.button_inc_dec_value);
        return Integer.parseInt(valueView.getText().toString());
    }

    public void setIncDecAmount(int incDecAmount) {
        this.incDecAmount = incDecAmount;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        valueView = (TextView) findViewById(R.id.button_inc_dec_value);
        incButton = (Button) findViewById(R.id.inc_button);
        decButton = (Button) findViewById(R.id.dec_button);

        //Listeners to increment and decrement the value with a click
        incButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue(getValue()+incDecAmount);

            }
        });
        decButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue(getValue()-incDecAmount);
            }
        });
    }
}
