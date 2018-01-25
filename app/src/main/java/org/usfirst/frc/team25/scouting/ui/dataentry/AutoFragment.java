package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.Autonomous;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDec;

public class AutoFragment extends Fragment implements  EntryFragment{

    CheckBox baselineCrossed, useHoppers, shootsFromKey, attemptGear, successGear;
    ButtonIncDec highGoals, lowGoals, rotorsStarted, gearsDelivered;
    RadioButton[] pegButtons;
    final String[] pegButtonValues = {"Left", "Center", "Right"};
    Button continueButton;

    ScoutEntry entry;

    @Override
    public ScoutEntry getEntry() {
        return entry;
    }

    public static AutoFragment getInstance(ScoutEntry entry){
        AutoFragment af = new AutoFragment();
        af.setEntry(entry);
        return af;
    }

    public AutoFragment() {
        // Required empty public constructor

    }

    private void enablePegButtons(){
        for(RadioButton button : pegButtons)
            button.setEnabled(true);
    }

    private void disablePegButtons(){
        for(RadioButton button : pegButtons) {
            button.setEnabled(false);
            button.setChecked(false);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.fragment_auto, container, false);
        baselineCrossed = (CheckBox) view.findViewById(R.id.reach_auto_line);

        lowGoals = (ButtonIncDec) view.findViewById(R.id.own_scale_auto);
        continueButton = (Button) view.findViewById(R.id.auto_continue);



        Settings set = Settings.newInstance(getActivity());

        highGoals.setIncDecAmount(set.getHighGoalIncAuto());
        lowGoals.setIncDecAmount(set.getLowGoalIncAuto());


        highGoals.setMaxValue(60);
        lowGoals.setMaxValue(60);

        rotorsStarted.setMaxValue(2);
        gearsDelivered.setMaxValue(3);

        /*if(!entry.getPreMatch().isPilotPlaying()) {
            ((ViewGroup) rotorsStarted.getParent()).removeView(rotorsStarted);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) baselineCrossed.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.autoGearLocationGroup);
            baselineCrossed.setLayoutParams(params);
        }*/

        attemptGear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    successGear.setEnabled(true);
                    enablePegButtons();
                }
                else{
                    successGear.setChecked(false);
                    successGear.setEnabled(false);
                    disablePegButtons();
                }
            }
        });

        successGear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    baselineCrossed.setChecked(true);
                    baselineCrossed.setEnabled(false);
                }
                else if(!useHoppers.isChecked())
                    baselineCrossed.setEnabled(true);
            }
        });

        useHoppers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    baselineCrossed.setChecked(true);
                    baselineCrossed.setEnabled(false);
                }
                else if(!successGear.isChecked())
                    baselineCrossed.setEnabled(true);
            }
        });

        baselineCrossed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!baselineCrossed.isEnabled())
                    Toast.makeText(getActivity(), "If a gear is delivered or a hopper is triggered, the baseline is crossed", Toast.LENGTH_SHORT).show();
            }
        });



        autoPopulate();

       /* gearsDelivered.decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gearsDelivered.getValue()<=1)
                    disablePegButtons();
                else enablePegButtons();
                gearsDelivered.setValue(gearsDelivered.getValue()-1);
            }
        });

        gearsDelivered.incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enablePegButtons();
                gearsDelivered.setValue(gearsDelivered.getValue()+1);
            }
        });*/



        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean gearPegSelected = false;

                for(RadioButton button : pegButtons){
                    if(button.isChecked()){
                        gearPegSelected = true;
                        break;
                    }

                }

                if(attemptGear.isChecked()&&!gearPegSelected){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Select a peg for the auto gear")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

                else {
                    saveState();

                    getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, TeleOpFragment.getInstance(entry), "TELEOP")
                            .commit();
                }
            }
        });

        return view;
    }

    @Override
    public void saveState() {
        Settings set = Settings.newInstance(getActivity());
        /*Autonomous(boolean baselineCrossed, boolean useHoppers, int highGoals, int lowGoals, int rotorsStarted, int gearsDelivered) {*/

        String gearPeg = "";
        for(int i = 0; i < 3; i++){
            if(pegButtons[i].isChecked())
                gearPeg = pegButtonValues[i];
        }

       entry.setAuto(new Autonomous(baselineCrossed.isChecked(), useHoppers.isChecked(), highGoals.getValue(), lowGoals.getValue(),
               -1, gearsDelivered.getValue(), shootsFromKey.isChecked(), gearPeg,
               attemptGear.isChecked(), successGear.isChecked()));

    }

    @Override
    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Autonomous");

    }

    @Override
    public void autoPopulate() {
        if(entry.getAuto()!=null){
            Autonomous prevAuto = entry.getAuto();
            highGoals.setValue(prevAuto.getHighGoals());
            lowGoals.setValue(prevAuto.getLowGoals());
            baselineCrossed.setChecked(prevAuto.isBaselineCrossed());
            useHoppers.setChecked(prevAuto.isUseHoppers());
            rotorsStarted.setValue(prevAuto.getRotorsStarted());
            gearsDelivered.setValue(prevAuto.getGearsDelivered());
            attemptGear.setChecked(prevAuto.isAttemptGear());
            successGear.setChecked(prevAuto.isSuccessGear());

            if(rotorsStarted.getValue()==-1)
                rotorsStarted.setValue(0);

            if(attemptGear.isChecked()){
                enablePegButtons();
                for(int i = 0; i < 3; i++){
                    if(prevAuto.getGearPeg().equals(pegButtonValues[i])){
                        pegButtons[i].setChecked(true);
                        break;
                    }
                }
            }
            else disablePegButtons();

            shootsFromKey.setChecked(prevAuto.isShootsFromKey());

        }
        else disablePegButtons();
    }

}
