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

import java.util.Timer;
import java.util.TimerTask;


import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;


public class TeleOpFragment extends Fragment implements EntryFragment {

    private ScoutEntry entry;
    /*private RadioButton[] attemptHabClimbLevel, successRungClimbLevel, highestAssistedClimbLevel,
            climbAssistStartingLevel;*/
    private RadioButton[] successRungClimbLevel, attemptRungClimbLevel;
    private RadioGroup successRungClimbLevelGroup , attemptRungClimbLevelGroup;

    /*private RadioGroup attemptHabClimbLevelGroup, successRungClimbLevelGroup,
            highestAssistedClimbLevelGroup, climbAssistStartingLevelGroup;
    private ButtonIncDecSet cargoShipHatches, cargoShipCargo, rocketLevelOneHatches,
            rocketLevelOneCargo, rocketLevelTwoHatches, rocketLevelTwoCargo,
            rocketLevelThreeHatches, rocketLevelThreeCargo;*/
    private CheckBox attemptRungClimb ;
    private ButtonIncDecView HumanCargoScored, RobotCargoScoredUpperHub, RobotCargoPickedUp,
            RobotCargoScoredLowerHub, RobotCargoDropped , HumanCargoMissed;
    //private CheckBox climbAssistedByPartners;
    //private EditText assistingClimbTeamNum;
   private long startTime = 0;
    private long stopTime = 0;
    private long reaction = 0;
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private double time = 0.0;



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

        /*cargoShipHatches = view.findViewById(R.id.cargo_ship_hatches_teleop);
        rocketLevelOneHatches = view.findViewById(R.id.rocket_level_one_hatches_teleop);
        rocketLevelOneCargo = view.findViewById(R.id.rocket_level_one_cargo_teleop);
        rocketLevelTwoHatches = view.findViewById(R.id.rocket_level_two_hatches_teleop);
        rocketLevelTwoCargo = view.findViewById(R.id.rocket_level_two_cargo_teleop);
        rocketLevelThreeHatches = view.findViewById(R.id.rocket_level_three_hatches_teleop);
        rocketLevelThreeCargo = view.findViewById(R.id.rocket_level_three_cargo_teleop);

        assistingClimbTeamNum = view.findViewById(R.id.team_number_text_input);
        climbAssistedByPartners =
                view.findViewById(R.id.climb_assisted_by_alliance_partner_checkbox);*/
        //HumanCargoScored = view.findViewById(R.id.Human_Cargo_Scored);
        //HumanCargoMissed = view.findViewById(R.id.Human_Cargo_Missed);
        attemptRungClimb = view.findViewById(R.id.attempt_rung_climb);
        //successRungClimb = view.findViewById(R.id.success_rung_climb);
        //cargoShipCargo = view.findViewById(R.id.cargo_ship_cargo_teleop);
        RobotCargoScoredUpperHub = view.findViewById(R.id.Robot_Cargo_scored_upperHub);
        RobotCargoPickedUp = view.findViewById(R.id.Robot_cargo_pick_up);
        RobotCargoScoredLowerHub = view.findViewById(R.id.Robot_cargo_scored_lowerHub);
        RobotCargoDropped = view.findViewById(R.id.Robot_Cargo_dropped);
        Button continueButton = view.findViewById(R.id.tele_continue);
        attemptRungClimbLevelGroup = view.findViewById(R.id.attempt_rung_climb_level);
        successRungClimbLevelGroup = view.findViewById(R.id.success_rung_climb_level);
        /*highestAssistedClimbLevelGroup = view.findViewById(R.id.highest_climb_assist_radio_group);
        climbAssistStartingLevelGroup = view.findViewById(R.id.climb_assist_starting_level_group);*/

        attemptRungClimbLevel = new RadioButton[3];
        attemptRungClimbLevel[0] = view.findViewById(R.id.attempt_low_rung);
        attemptRungClimbLevel[1] = view.findViewById(R.id.attempt_mid_rung);
        attemptRungClimbLevel[2] = view.findViewById(R.id.not_success_in_rung);


        successRungClimbLevel = new RadioButton[5];
        successRungClimbLevel[0] = view.findViewById(R.id.Low_Rung);
        successRungClimbLevel[1] = view.findViewById(R.id.Mid_Rung);
        successRungClimbLevel[2] = view.findViewById(R.id.High_Rung);
        successRungClimbLevel[3] = view.findViewById(R.id.Traversal_Rung);
        successRungClimbLevel[4] = view.findViewById(R.id.robot_fell_off);
        //successRungClimbLevel[4] = view.findViewById(R.id.robot_fell);

       /* highestAssistedClimbLevel = new RadioButton[2];
        highestAssistedClimbLevel[0] = view.findViewById(R.id.highest_assisted_climb_one);
        highestAssistedClimbLevel[1] = view.findViewById(R.id.highest_assisted_climb_two);

        climbAssistStartingLevel = new RadioButton[3];
        climbAssistStartingLevel[0] = view.findViewById(R.id.climb_assist_starting_zero);
        climbAssistStartingLevel[1] = view.findViewById(R.id.climb_assist_starting_one);
        climbAssistStartingLevel[2] = view.findViewById(R.id.climb_assist_starting_two);*/


        /*for (RadioButton anAttemptHabClimbLevel : attemptHabClimbLevel) {
            anAttemptHabClimbLevel.setOnClickListener(view1 -> autoDisableSuccessGroup());
        }

        for (RadioButton button : highestAssistedClimbLevel) {
            button.setOnClickListener((view1 -> {
                autoDisableClimbAssistStartingLevelGroup();
            }));
        }*/

        /*successRungClimb.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
               for(RadioButton button : successRungClimbLevel) {
                   button.setEnabled(true);
                   attemptRungClimb.setChecked(true);
                   attemptRungClimb.setEnabled(false);
               }
            } else {
                for(RadioButton button : successRungClimbLevel) {
                    button.setEnabled(false);
                    button.setChecked(false);
                    attemptRungClimb.setEnabled(true);
                }
            }
        });*/




        attemptRungClimb.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                for(RadioButton button : attemptRungClimbLevel) {
                    button.setEnabled(true);
                }
                //successRungClimb.setEnabled(true);
                //UiHelper.radioButtonEnable(attemptRungClimbLevelGroup, true);
            } else {
                for(RadioButton button : attemptRungClimbLevel) {
                    button.setEnabled(false);
                }
                //successRungClimb.setEnabled(false);
                //successRungClimb.setChecked(false);
                //UiHelper.radioButtonEnable(attemptRungClimbLevelGroup, false);
                //UiHelper.radioButtonEnable(successRungClimbLevelGroup, false);
            }


        });

        for(RadioButton button:attemptRungClimbLevel) {
            button.setOnClickListener((view1 -> {
                for (RadioButton bttn : attemptRungClimbLevel){
                    // Save the current time. This will get called
                    // 3 times as it gets called per button but it
                    // shouldn't be an issue
                    // kavinraj does not want this code :(
                    /*System.out.println("sjfgknkjngsk");
                    startTime = System.currentTimeMillis();

                    // Is bttn the only button that should be checked?
                    bttn.setChecked(bttn == button);*/

                    // but kavin wants this code
                    if (bttn == button) {
                        System.out.println("sjfgknkjngsk");
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

        /*for (RadioButton bttn : attemptRungClimbLevel) {
            bttn.setOnClickListener((compoundButton) -> {
                System.out.println("sjfgknkjngsk");
                startTime = System.currentTimeMillis();
                // startTimer();
                if(attemptRungClimbLevel[0].isChecked() || attemptRungClimbLevel[1].isChecked()){
                    for(RadioButton button : successRungClimbLevel) {
                        button.setEnabled(true);


                    }
                }
                else{
                    for(RadioButton button : successRungClimbLevel) {
                        button.setEnabled(false);
                        button.setChecked(false);
                    }

                }
            });
        }*/

        /*attemptRungClimbLevelGroup.setOnCheckedChangeListener((compoundButton, b) -> {
                System.out.println("sjfgknkjngsk");
                startTime = System.currentTimeMillis();

                if(attemptRungClimbLevel[0].isChecked() || attemptRungClimbLevel[1].isChecked()){
                    for(RadioButton button : successRungClimbLevel) {
                        button.setEnabled(true);
                    }
                }
                else{
                    for(RadioButton button : successRungClimbLevel) {
                        button.setEnabled(false);
                    }

                }

            });*/

        for(RadioButton button:successRungClimbLevel) {
            button.setOnClickListener((view1 -> {
                stopTime = System.currentTimeMillis();
                reaction = (stopTime-startTime)/1000;
                System.out.println("Time Robot Took is " + reaction);
                for (RadioButton bttn : successRungClimbLevel){
                    if (bttn == button) {
                        bttn.setChecked(true);
                    }

                    else {
                        bttn.setChecked(false);
                    }
                }
            }));
        }




/*
        if ((attemptRungClimbLevel[0].isChecked() || attemptRungClimbLevel[1].isChecked())) {
            startTime = System.currentTimeMillis();
            System.out.println("*********************************************HELLO*******");
            if (successRungClimbLevel[0].isChecked() || successRungClimbLevel[1].isChecked() || successRungClimbLevel[2].isChecked() || successRungClimbLevel[3].isChecked()){
                stopTime = System.currentTimeMillis();
                reaction = (stopTime - startTime)/1000;
                System.out.println("******************************" + reaction);
            }
        }*/




        /*long startTime = System.currentTimeMillis();
        scanner.next();

        long stopTime = System.currentTimeMillis();

        long reaction = stopTime - startTime;

        System.out.println(reaction/1000);
        System.out.println(reaction);*/

        /*attemptRungClimb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b && (attemptRungClimbLevel[0].isChecked() || attemptRungClimbLevel[1].isChecked())) {
                startTime = System.currentTimeMillis();
               if(successRungClimbLevel[0].isChecked() || successRungClimbLevel[1].isChecked() || successRungClimbLevel[2].isChecked() || successRungClimbLevel[3].isChecked()){
                   stopTime = System.currentTimeMillis();
               }
            }

        });*/


        /*climbAssistedByPartners.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                assistingClimbTeamNum.setEnabled(true);
            } else {
                assistingClimbTeamNum.setEnabled(false);
                assistingClimbTeamNum.setText("");
            }
        });*/






        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            hideKeyboard(getActivity());

            boolean proceed = true;

            /*if (climbAssistedByPartners.isChecked()) {
                if (assistingClimbTeamNum.getText().toString().isEmpty()) {
                    assistingClimbTeamNum.setError("Enter the assisting team's number");
                    proceed = false;

                } else {
                    int inputTeamNum =
                            Integer.parseInt(assistingClimbTeamNum.getText().toString());
                    if (inputTeamNum <= 0 || inputTeamNum > 9999) {
                        assistingClimbTeamNum.setError("Enter a valid team number");
                        proceed = false;
                    }
                }


            }*/

            /*if (proceed && HumanCargoScored.getValue() >= 1 &&
                    !(UiHelper.checkIfButtonIsChecked(highestAssistedClimbLevel) && UiHelper.checkIfButtonIsChecked(climbAssistStartingLevel))
                    || attemptRungClimb.isChecked() && !UiHelper.checkIfButtonIsChecked(attemptHabClimbLevel) || successRungClimb.isChecked()
                    && !UiHelper.checkIfButtonIsChecked(successRungClimbLevel)) {

                proceed = false;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Please fill in any empty HAB climb fields")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }*/

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

    /*private void autoDisableClimbAssistStartingLevelGroup() {
        if (highestAssistedClimbLevel[0].isChecked()) {
            if (climbAssistStartingLevel[2].isChecked()) {
                climbAssistStartingLevelGroup.clearCheck();

            }
            climbAssistStartingLevel[2].setEnabled(false);
        } else if (HumanCargoScored.getValue() > 0) {
            climbAssistStartingLevel[2].setEnabled(true);
        }

    }*/

    @Override
    public void autoPopulate() {
        if (entry.getTeleOp() != null) {
            TeleOp tele = entry.getTeleOp();

            RobotCargoPickedUp.setValue(tele.getRobotCargoPickedUp());
            RobotCargoScoredUpperHub.setValue(tele.getRobotCargoScoredUpperHub());
            RobotCargoScoredLowerHub.setValue(tele.getRobotCargoScoredLowerHub());
            RobotCargoDropped.setValue(tele.getRobotCargoDropped());

            attemptRungClimb.setChecked(tele.isAttemptRungClimb());
            //successRungClimb.setChecked(tele.isSuccessRungClimb());

            /*cargoShipHatches.setValue(tele.getCargoShipHatches());
            cargoShipCargo.setValue(tele.getCargoShipCargo());
            rocketLevelOneCargo.setValue(tele.getRocketLevelOneCargo());
            rocketLevelOneHatches.setValue(tele.getRocketLevelOneHatches());
            rocketLevelTwoCargo.setValue(tele.getRocketLevelTwoCargo());
            rocketLevelTwoHatches.setValue(tele.getRocketLevelTwoHatches());
            rocketLevelThreeCargo.setValue(tele.getRocketLevelThreeCargo());
            rocketLevelThreeHatches.setValue(tele.getRocketLevelThreeHatches());

            climbAssistedByPartners.setChecked(tele.isClimbAssistedByPartner());
            HumanCargoScored.setValue(tele.getNumPartnerClimbAssists());
            RobotCargoScoredUpperHub.setValue(tele.getHatchesDropped());
            RobotCargoPickedUp.setValue(tele.getCargoDropped());
            attemptRungClimb.setChecked(tele.isAttemptHabClimb());
            successRungClimb.setChecked(tele.isSuccessHabClimb());*/

            /*if (tele.getAssistingClimbTeamNum() != 0) {
                assistingClimbTeamNum.setText(Integer.toString(tele.getAssistingClimbTeamNum()));
            }

            for (int i = 2; i <= 3; i++) {
                if (i == tele.getPartnerClimbAssistEndLevel()) {
                    highestAssistedClimbLevel[i - 2].setChecked(true);
                }
            }

            for (int i = 0; i < 3; i++) {
                if (i == tele.getPartnerClimbAssistStartLevel()) {
                    climbAssistStartingLevel[i].setChecked(true);
                }
            }*/

            for (int i = 1; i <= successRungClimbLevel.length; i++) {
                if (i == tele.getSuccessRungClimbLevel()) {
                    successRungClimbLevel[i - 1].setChecked(true);
                }
            }

            for (int i = 1; i <= attemptRungClimbLevel.length; i++) {
                if (i == tele.getAttemptRungClimbLevel()) {
                    attemptRungClimbLevel[i - 1].setChecked(true);
                }
            }


            //autoDisableClimbAssistStartingLevelGroup();

            //autoDisableSuccessGroup();

        }
    }

    @Override
    public void saveState() {

        entry.setTeleOp(new TeleOp(RobotCargoPickedUp.getValue(), RobotCargoScoredUpperHub.getValue() ,
                RobotCargoScoredLowerHub.getValue() , RobotCargoDropped.getValue() , UiHelper.getHabLevelSelected(successRungClimbLevel) ,
                attemptRungClimb.isChecked()  , UiHelper.getHabLevelSelected(attemptRungClimbLevel)));


    }


   public void startTimer(){
       //Timer timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.e("Timer Task", String.valueOf(time));
                time++;

                if(time>=30){
                    timer.cancel();
                }
                else if(attemptRungClimbLevel[0].isChecked() && successRungClimbLevel[0].isChecked()){
                    timer.cancel();
                }
                else if(attemptRungClimbLevel[1].isChecked() && successRungClimbLevel[1].isChecked()){
                    timer.cancel();
                }
                else {
                    for (RadioButton bttn : successRungClimbLevel) {
                        bttn.setOnCheckedChangeListener((compoundButton, b) -> {
                            timer.cancel();
                        });
                    }
                }
            }
        };
       timer.schedule(timerTask,0,1000);


   }

   /*public void stopTimer(){
        timer.cancel();
   }*/

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        hideKeyboard(getActivity());
    }

}