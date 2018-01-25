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

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDec;


public class TeleOpFragment extends Fragment implements EntryFragment{

    //TODO add drag and drop ListView for robot actions during cycle

    ScoutEntry entry;
    Button continueButton;
    ButtonIncDec high, low, gears, rotors, hoppers, cycles, highInc, lowInc, gearsDropped;
    CheckBox returnLoading, overflowLoading, attemptTakeoff, readyTakeoff;
    CheckBox[] gearDropLocCheckset = new CheckBox[3];
    final String[] gearDropLocValues = {"Retrieval zone", "Peg", "Other"};


    Settings set;

    void enableGearDropLocCheckset(){
        for(CheckBox cb : gearDropLocCheckset)
            cb.setEnabled(true);
    }

    void disableGearDropLocCheckset(){
        for(CheckBox cb : gearDropLocCheckset) {
            cb.setEnabled(false);
            cb.setChecked(false);
        }
    }

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

        high = (ButtonIncDec) view.findViewById(R.id.own_switch_tele);
        low = (ButtonIncDec) view.findViewById(R.id.scale_tele);
        gears = (ButtonIncDec) view.findViewById(R.id.opponent_switch_tele);
        attemptTakeoff = (CheckBox) view.findViewById(R.id.attempt_rung_climb);
        readyTakeoff = (CheckBox) view.findViewById(R.id.success_rung_climb);
        continueButton = (Button) view.findViewById(R.id.tele_continue);



        gearsDropped.incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableGearDropLocCheckset();
                gearsDropped.increment();
                Log.i("tag", new Integer(gearsDropped.getValue()).toString());
            }
        });

        gearsDropped.decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gearsDropped.getValue()<=1)
                    disableGearDropLocCheckset();
                gearsDropped.decrement();
            }
        });



        set = Settings.newInstance(getActivity());

        high.setIncDecAmount(set.getHighGoalIncTele());
        low.setIncDecAmount(set.getLowGoalIncTele());

        highInc.setValue(set.getHighGoalIncTele());
        lowInc.setValue(set.getLowGoalIncTele());


        /*if(!entry.getPreMatch().isPilotPlaying()) {
            ((ViewGroup) rotors.getParent()).removeView(rotors);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) hoppers.getLayoutParams();
          params.addRule(RelativeLayout.BELOW, R.id.gearsTele);
            hoppers.setLayoutParams(params);
        }

        else rotors.setMaxValue(4-entry.getAuto().getRotorsStarted());*/

        hoppers.setMaxValue(5 - (entry.getAuto().isUseHoppers() ? 1 : 0));


        readyTakeoff.setEnabled(false);



        attemptTakeoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    readyTakeoff.setEnabled(true);
                else{
                    readyTakeoff.setEnabled(false);
                    readyTakeoff.setChecked(false);
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean gearDropLocSelected = false;

                for(CheckBox cb : gearDropLocCheckset){
                    if(cb.isChecked()){
                        gearDropLocSelected = true;
                        break;
                    }

                }

                if(gearsDropped.getValue()>=1 && !gearDropLocSelected){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Select location(s) for dropped gear(s)")
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
                    getFragmentManager()
                            .beginTransaction()
                            .replace(android.R.id.content, PostMatchFragment.getInstance(entry), "POST")
                            .commit();
                }
            }
        });

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high.setIncDecAmount(highInc.getValue());
            }
        });

        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low.setIncDecAmount(lowInc.getValue());
            }
        });



        return view;
    }

    @Override
    public void onStop() {
        set.setLowGoalIncTele(lowInc.getValue());
        set.setHighGoalIncTele(highInc.getValue());
        Log.i("tag", "stopped");
        super.onStop();
    }

    @Override
    public void saveState() {
        String gearDropLoc = "";
        for(int i = 0; i < gearDropLocCheckset.length; i++)
            if(gearDropLocCheckset[i].isChecked())
                gearDropLoc+= gearDropLocValues[i] + "; ";

        entry.setTeleOp(new TeleOp(low.getValue(), high.getValue(), gears.getValue(), hoppers.getValue(),
                 -1, attemptTakeoff.isChecked(), readyTakeoff.isChecked(),
                returnLoading.isChecked(), overflowLoading.isChecked(), cycles.getValue(), gearsDropped.getValue(), gearDropLoc));
    }

    @Override
    public void autoPopulate() {
        if(entry.getTeleOp()!=null){
            TeleOp tele = entry.getTeleOp();
            low.setValue(tele.getLowGoals());
            high.setValue(tele.getHighGoals());
            gears.setValue(tele.getGearsDelivered());
            hoppers.setValue(tele.getHopppersUsed());
            rotors.setValue(tele.getRotorsStarted());
            cycles.setValue(tele.getNumCycles());
            attemptTakeoff.setChecked(tele.isAttemptTakeoff());
            gearsDropped.setValue(tele.getGearsDropped());

            returnLoading.setChecked(tele.isUseReturnLoading());
            overflowLoading.setChecked(tele.isUseOverflowLoading());

            if(tele.getGearsDropped()>=1){
                enableGearDropLocCheckset();
                for(int i = 0; i < gearDropLocCheckset.length; i++)
                    if(tele.getGearsDroppedLoc().contains(gearDropLocValues[i]))
                        gearDropLocCheckset[i].setChecked(true);
            }
            else disableGearDropLocCheckset();

            if(tele.isAttemptTakeoff())
                readyTakeoff.setEnabled(true);
            readyTakeoff.setChecked(tele.isReadyTakeoff());
        }
        else cycles.setValue(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        autoPopulate();
    }
}
