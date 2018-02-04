package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDec;
import org.usfirst.frc.team25.scouting.ui.views.ButtonTimer;


public class TeleOpFragment extends Fragment implements EntryFragment{

    ScoutEntry entry;
    ImageView fieldImage;
    Button continueButton;
    ButtonIncDec ownSwitchCubes, scaleCubes, opponentSwtichCubes, exchangeCubes,
        cubesDropped, climbsAssisted;
    CheckBox attemptRumgClimb, successRungClimb, parked, climbsOtherRobots;
    ButtonTimer firstCubeTime, cycleTime;
    EditText climbOtherRobotTypeOtherField;
    RadioButton[] climbOtherRobotType = new RadioButton[4];
    int fieldConfigIndex = 0;
    final int[] redLeftFieldConfig = {R.drawable.red_left_lll, R.drawable.red_left_lrl,
            R.drawable.red_left_rlr, R.drawable.red_left_rrr};
    final int[] blueLeftFieldConfig = {R.drawable.blue_left_rrr, R.drawable.blue_left_rlr,
            R.drawable.blue_left_lrl, R.drawable.blue_left_lll};
    RadioGroup otherRobotTypeGroup;


    Settings set;


    public static TeleOpFragment getInstance(ScoutEntry entry){
        TeleOpFragment tof = new TeleOpFragment();
        tof.setEntry(entry);
        return tof;
    }

    public TeleOpFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tele_op, container, false);

        ownSwitchCubes = (ButtonIncDec) view.findViewById(R.id.own_switch_tele);
        scaleCubes = (ButtonIncDec) view.findViewById(R.id.scale_tele);
        opponentSwtichCubes = (ButtonIncDec) view.findViewById(R.id.opponent_switch_tele);
        attemptRumgClimb = (CheckBox) view.findViewById(R.id.attempt_rung_climb);
        successRungClimb = (CheckBox) view.findViewById(R.id.success_rung_climb);
        continueButton = (Button) view.findViewById(R.id.tele_continue);
        fieldImage = view.findViewById(R.id.field_config_image);
        firstCubeTime = view.findViewById(R.id.first_cube_time);
        exchangeCubes = view.findViewById(R.id.exchange_tele);
        cubesDropped = view.findViewById(R.id.cubes_dropped_tele);
        cycleTime = view.findViewById(R.id.cycle_time);
        parked = view.findViewById(R.id.park_platform);
        climbsAssisted = view.findViewById(R.id.climbs_assisted);
        climbsOtherRobots = view.findViewById(R.id.climb_other_robot);
        climbOtherRobotType[0] = view.findViewById(R.id.ramp_bot_type);
        climbOtherRobotType[1]=view.findViewById(R.id.robot_rung_type);
        climbOtherRobotType[2]=view.findViewById(R.id.iron_cross_type);
        climbOtherRobotType[3]=view.findViewById(R.id.other_type);
        otherRobotTypeGroup = view.findViewById(R.id.climb_other_robot_type_group);
        climbOtherRobotTypeOtherField = view.findViewById(R.id.other_robot_type_text);


        Settings set = Settings.newInstance(getActivity());

        climbOtherRobotTypeOtherField.setEnabled(false);

        climbOtherRobotType[3].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    for(int i = 0; i < 3; i++)
                        climbOtherRobotType[i].setChecked(false);
                    climbOtherRobotTypeOtherField.setEnabled(true);
                }
            }
        });



        for(int i = 0; i < 3; i++)
            climbOtherRobotType[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    climbOtherRobotType[3].setChecked(false);
                    climbOtherRobotTypeOtherField.setText("");
                    climbOtherRobotTypeOtherField.setEnabled(false);
                }
            });

        if(set.getLeftAlliance().equals("Red Alliance"))
            fieldImage.setImageResource(redLeftFieldConfig[0]);
        else fieldImage.setImageResource(blueLeftFieldConfig[0]);

        fieldImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(set.getLeftAlliance().equals("Red Alliance"))
                    fieldImage.setImageResource(redLeftFieldConfig[++fieldConfigIndex%4]);
                else fieldImage.setImageResource(blueLeftFieldConfig[++fieldConfigIndex%4]);
            }
        });

        successRungClimb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    parked.setChecked(false);
                    parked.setEnabled(false);
                    disableOtherRobotTypeGroup();
                    climbsOtherRobots.setEnabled(false);
                    climbsOtherRobots.setChecked(false);
                }
                else{
                    parked.setEnabled(true);
                    climbsOtherRobots.setEnabled(true);
                }
            }
        });

        attemptRumgClimb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b&&!climbsOtherRobots.isChecked())
                    successRungClimb.setEnabled(true);
                else{
                    successRungClimb.setEnabled(false);
                    successRungClimb.setChecked(false);
                }
            }
        });

        climbsOtherRobots.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    enableOtherRobotTypeGroup();
                    successRungClimb.setEnabled(false);
                    successRungClimb.setChecked(false);
                    parked.setChecked(false);
                    parked.setEnabled(false);
                }
                else {
                    disableOtherRobotTypeGroup();
                    parked.setEnabled(true);
                    if(attemptRumgClimb.isChecked())
                        successRungClimb.setEnabled(true);
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(climbsOtherRobots.isChecked()&&!(climbOtherRobotType[0].isChecked() ||climbOtherRobotType[1].isChecked()
                        ||climbOtherRobotType[2].isChecked() ||
                        (climbOtherRobotType[3].isChecked()&&!climbOtherRobotTypeOtherField.getText().toString().isEmpty()))){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Select or fill in type of robot climbed on")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else{
                    saveState();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(android.R.id.content, PostMatchFragment.getInstance(entry), "POST")
                            .commit();
                }
            }
            });






        return view;
    }

    public void enableOtherRobotTypeGroup(){
        for(RadioButton button : climbOtherRobotType)
            button.setEnabled(true);
    }

    public void disableOtherRobotTypeGroup(){
        for(RadioButton button : climbOtherRobotType){
            button.setEnabled(false);
            button.setChecked(false);
        }
        climbOtherRobotTypeOtherField.setEnabled(false);
        climbOtherRobotTypeOtherField.setText("");
    }

    @Override
    public void onStop() {
/*        set.setLowGoalIncTele(lowInc.getValue());
        set.setHighGoalIncTele(highInc.getValue());*/
        Log.i("tag", "stopped");
        super.onStop();
    }

    @Override
    public void saveState() {
        String climbOtherRobotTypeStr = "";
        for(int i = 0; i < 3; i++)
            if(climbOtherRobotType[i].isChecked())
                climbOtherRobotTypeStr = (String) climbOtherRobotType[i].getText();
        if(climbOtherRobotType[3].isChecked())
            climbOtherRobotTypeStr = climbOtherRobotTypeOtherField.getText().toString();

        //Always from red driver POV, depicting red plates
        String[] fieldLayoutValues = {"lll", "lrl", "rlr", "rrr"};

        entry.setTeleOp(new TeleOp(firstCubeTime.getValue(), cycleTime.getValue(), ownSwitchCubes.getValue(),
               scaleCubes.getValue(), opponentSwtichCubes.getValue(), exchangeCubes.getValue(), cubesDropped.getValue(),
                climbsAssisted.getValue(), parked.isChecked(), attemptRumgClimb.isChecked(), successRungClimb.isChecked(),
                climbsOtherRobots.isChecked(), climbOtherRobotTypeStr, fieldLayoutValues[fieldConfigIndex%4]));

    }

    @Override
    public void autoPopulate() {
        if(entry.getTeleOp()!=null){
            TeleOp tele = entry.getTeleOp();
            firstCubeTime.setValue(tele.getFirstCubeTime());
            cycleTime.setValue(tele.getCycleTime());
            ownSwitchCubes.setValue(tele.getOwnSwitchCubes());
            scaleCubes.setValue(tele.getScaleCubes());
            opponentSwtichCubes.setValue(tele.getOpponentSwitchCubes());
            exchangeCubes.setValue(tele.getExchangeCubes());
            cubesDropped.setValue(tele.getCubesDropped());
            climbsAssisted.setValue(tele.getClimbsAssisted());
            parked.setChecked(tele.isParked());
            attemptRumgClimb.setChecked(tele.isAttemptRungClimb());
            successRungClimb.setChecked(tele.isSuccessfulRungClimb());
            climbsOtherRobots.setChecked(tele.isOtherRobotClimb());

            if(!tele.getOtherRobotClimbType().equals("")){
                boolean otherChecked = true;
                for(RadioButton button : climbOtherRobotType)
                    if(button.getText().equals(tele.getOtherRobotClimbType())) {
                        button.setChecked(true);
                        otherChecked = false;
                    }
                if(otherChecked) {
                    climbOtherRobotType[3].setChecked(true);
                    Log.i("tag", tele.getOtherRobotClimbType());
                    climbOtherRobotTypeOtherField.setText(tele.getOtherRobotClimbType());
                }
            }


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        autoPopulate();
    }
}
