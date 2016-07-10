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

    public ButtonIncDec(Context c, AttributeSet attrs){
        super(c, attrs);
        initializeViews(c);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ButtonIncDec);

        //XML attributes that can be set in layout files, rather than programmatically
        setValue(typedArray.getInteger(R.styleable.ButtonIncDec_initialValue, 1));
        setTitle(typedArray.getString(R.styleable.ButtonIncDec_titlePrompt));

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
            return;

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
                setValue(getValue()+1);
            }
        });
        decButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue(getValue()-1);
            }
        });
    }
}
