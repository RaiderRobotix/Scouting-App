package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.UiHelper;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;


public class TeleOpFragment extends Fragment implements EntryFragment {

    private ScoutEntry entry;
    private RadioButton[] successRungClimbLevel, attemptRungClimbLevel;
    private RadioGroup successRungClimbLevelGroup , attemptRungClimbLevelGroup;
    private CheckBox attemptRungClimb ;
    private ButtonIncDecView RobotCargoScoredUpperHub, RobotCargoPickedUp,
            RobotCargoScoredLowerHub, RobotCargoDropped;
    private long startTime = 0;
    private long stopTime = 0;
    private int reaction = 0;
    private ArrayList<Integer> Time= new ArrayList<Integer>();
    private Timer timer = new Timer();



    public static TeleOpFragment getInstance(ScoutEntry entry) {
        TeleOpFragment tof = new TeleOpFragment();
        tof.setEntry(entry);
        return tof;
    }

    @Override
    public ScoutEntry getEntry() {
        return entry;
    }

    @Override
    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tele_op, container, false);

        Button continueButton = view.findViewById(R.id.tele_continue);


        RobotCargoScoredUpperHub = view.findViewById(R.id.Robot_Cargo_scored_upperHub);
        RobotCargoPickedUp = view.findViewById(R.id.Robot_cargo_pick_up);
        RobotCargoScoredLowerHub = view.findViewById(R.id.Robot_cargo_scored_lowerHub);
        RobotCargoDropped = view.findViewById(R.id.Robot_Cargo_dropped);


        attemptRungClimbLevelGroup = view.findViewById(R.id.attempt_rung_climb_level);
        attemptRungClimb = view.findViewById(R.id.attempt_rung_climb);
        attemptRungClimbLevel = new RadioButton[3];
        attemptRungClimbLevel[0] = view.findViewById(R.id.attempt_low_rung);
        attemptRungClimbLevel[1] = view.findViewById(R.id.attempt_mid_rung);
        attemptRungClimbLevel[2] = view.findViewById(R.id.not_success_in_rung);


        successRungClimbLevelGroup = view.findViewById(R.id.success_rung_climb_level);
        successRungClimbLevel = new RadioButton[5];
        successRungClimbLevel[0] = view.findViewById(R.id.Low_Rung);
        successRungClimbLevel[1] = view.findViewById(R.id.Mid_Rung);
        successRungClimbLevel[2] = view.findViewById(R.id.High_Rung);
        successRungClimbLevel[3] = view.findViewById(R.id.Traversal_Rung);
        successRungClimbLevel[4] = view.findViewById(R.id.robot_fell_off);


        attemptRungClimb.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                for(RadioButton button : attemptRungClimbLevel) {
                    button.setEnabled(true);
                }
            } else {
                for(RadioButton button : attemptRungClimbLevel) {
                    button.setEnabled(false);
                    button.setChecked(false);
                }
                for(RadioButton button : successRungClimbLevel) {
                    button.setEnabled(false);
                    button.setChecked(false);
                }
            }
        });

        for(RadioButton button:attemptRungClimbLevel) {
            button.setOnClickListener((view1 -> {
                for (RadioButton bttn : attemptRungClimbLevel){
                    // Save the current time. This will get called
                    // 3 times as it gets called per button but it
                    // shouldn't be an issue

                    if (bttn == button) {
                        startTime = System.currentTimeMillis();
                        bttn.setChecked(true);
                    }
                    else {
                        bttn.setChecked(false);
                    }

                    if(attemptRungClimbLevel[0].isChecked() || attemptRungClimbLevel[1].isChecked()){
                        for(RadioButton bttn1 : successRungClimbLevel) {
                            bttn1.setEnabled(true);


                        }
                    }
                    else{
                        for(RadioButton bttn1 : successRungClimbLevel) {
                            bttn1.setEnabled(false);
                            bttn1.setChecked(false);
                        }

                    }
                }
            }));
        }

        for(RadioButton button:successRungClimbLevel) {
            button.setOnClickListener((view1 -> {
                for (RadioButton bttn : successRungClimbLevel){
                    bttn.setChecked(bttn == button);
                }
                Time.add(0);
                if((attemptRungClimbLevel[0].isChecked() && successRungClimbLevel[0].isChecked()) ||
                        (attemptRungClimbLevel[1].isChecked() && successRungClimbLevel[1].isChecked())
                        ||(successRungClimbLevel[4].isChecked())){
                    Time.add(0);
                    System.out.println("1Time Robot Took is " + Time.get(Time.size()-1));
                }
                else{
                    stopTime = System.currentTimeMillis();
                    reaction = (int)((stopTime-startTime)/1000);
                    Time.add(reaction);
                    if(reaction > 30){
                        Time.add(0);
                        System.out.println("Time Robot Took is " + Time.get(Time.size()-1));
                    }
                    else {
                        System.out.println("Time Robot Took is " + Time.get(Time.size()-1));
                    }
                }
            }));
        }




        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            hideKeyboard(getActivity());

            boolean proceed = true;

            if (proceed) {
                saveState();
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, PostMatchFragment.getInstance(entry), "POST")
                        .commit();
            }
        });

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();
        }

        return view;
    }

    @Override
    public void autoPopulate() {
        if (entry.getTeleOp() != null) {
            TeleOp tele = entry.getTeleOp();

            RobotCargoPickedUp.setValue(tele.getRobotCargoPickedUp());
            RobotCargoScoredUpperHub.setValue(tele.getRobotCargoScoredUpperHub());
            RobotCargoScoredLowerHub.setValue(tele.getRobotCargoScoredLowerHub());
            RobotCargoDropped.setValue(tele.getRobotCargoDropped());


            attemptRungClimb.setChecked(tele.isAttemptRungClimb());

            for (int i = 1; i <= successRungClimbLevel.length; i++) {
                if (i == tele.getSuccessRungClimbLevel()) {
                    successRungClimbLevel[i-1].setChecked(true);
                }
            }

            for (int i = 1; i <= attemptRungClimbLevel.length; i++) {
                if (i == tele.getAttemptRungClimbLevel()) {
                    attemptRungClimbLevel[i - 1].setChecked(true);
                }

            }
            if(attemptRungClimbLevel[0].isChecked()||attemptRungClimbLevel[1].isChecked()){
                for(RadioButton button:successRungClimbLevel) {
                    button.setEnabled(true);}
            }

        }
    }

    @Override
    public void saveState() {

        entry.setTeleOp(new TeleOp(RobotCargoPickedUp.getValue(), RobotCargoScoredUpperHub.getValue() ,
                RobotCargoScoredLowerHub.getValue() , RobotCargoDropped.getValue() , UiHelper.getHabLevelSelected(successRungClimbLevel) ,
                attemptRungClimb.isChecked()  , UiHelper.getHabLevelSelected(attemptRungClimbLevel) , Time.get(Time.size()-1)));



    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        hideKeyboard(getActivity());
    }

}