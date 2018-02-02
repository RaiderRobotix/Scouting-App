package org.usfirst.frc.team25.scouting.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.usfirst.frc.team25.scouting.R;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.Timer;

/**
 * Created by sng on 1/31/2018.
 */

public class ButtonTimer extends RelativeLayout {

    public Button incButton, decButton, startStopButton;
    private TextView valueView;
    private TextView titleView;
    private float incDecAmount;
    private float minValue;
    private float maxValue;
    private boolean isTimerStart;
    private Clock timer;

    private OnClickListener listener;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_UP){
            if(listener!=null) listener.onClick(this);
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if(listener != null) listener.onClick(this);
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public ButtonTimer(Context c, AttributeSet attrs){
        super(c, attrs);
        initializeViews(c);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ButtonTimer);

        //XML attributes that can be set in layout files, rather than programmatically
        valueView = (TextView) findViewById(R.id.button_timer_value);
        incButton = (Button) findViewById(R.id.timer_inc_button);
        decButton = (Button) findViewById(R.id.timer_dec_button);
        startStopButton = (Button) findViewById(R.id.start_stop_button);
        titleView = (TextView) findViewById(R.id.button_timer_title);


        setValue(typedArray.getInteger(R.styleable.ButtonTimer_initialValueTimer, 0));
        setTitle(typedArray.getString(R.styleable.ButtonTimer_titlePromptTimer));
        setMinValue(typedArray.getInteger(R.styleable.ButtonTimer_minValueTimer, 0));
        setMaxValue(typedArray.getFloat(R.styleable.ButtonTimer_maxValueTimer, Float.MAX_VALUE));
        setIncDecAmount(typedArray.getFloat(R.styleable.ButtonTimer_buttonIncDecAmount, 0.5f));



        typedArray.recycle();

    }

    private void initializeViews(Context c){
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.button_timer_view, this);
    }

    /**
     * @param title - title of the left TextView
     */
    public void setTitle(CharSequence title){
        titleView.setText(title);
    }

    /**
     * @param value - the initial value of the integer between the buttons. Cannot be less than 0.
     */
    public void setValue(float value){
        if(value>maxValue)
            value = maxValue;

        if(value<minValue)
            value = minValue;

        BigDecimal bd = new BigDecimal(value);

        String displayText = bd.setScale(1, BigDecimal.ROUND_HALF_EVEN).toPlainString() + " sec";
        valueView = findViewById(R.id.button_timer_value);
        valueView.setText(displayText);
    }

    /**
     *
     * @return Float value of the displayed number
     */
    public float getValue(){
        valueView = (TextView) findViewById(R.id.button_timer_value);
        String value = valueView.getText().toString().split(" ")[0];
        return Float.parseFloat(value);
    }

    public void setIncDecAmount(float incDecAmount) {
        this.incDecAmount = incDecAmount;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        isTimerStart = false;



        //Listeners to increment and decrement the value with a click
        incButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) { increment(); }
        });
        decButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement();
            }
        });

        //Essentially acts like a toggle, starting and stopping the timer
        startStopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTimerStart){ //timer already started, need to stop
                    startStopButton.setText("Start");

                }
                else{ // starts timer
                    startStopButton.setText(("Stop"));
                    setValue(0f);
                    runTimer();
                }
                isTimerStart = !isTimerStart;
            }
        });
    }

    public void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(isTimerStart)
                    setValue(getValue()+0.1f);
                handler.postDelayed(this, 100);
            }
        });

    }

    public void increment(){
        setValue(getValue()+incDecAmount);
    }

    public void decrement(){
        setValue(getValue()-incDecAmount);
    }
}
