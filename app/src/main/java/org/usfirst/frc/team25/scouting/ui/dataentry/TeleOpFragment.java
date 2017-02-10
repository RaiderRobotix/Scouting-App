package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.SimpleDragSortCursorAdapter;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class TeleOpFragment extends Fragment implements EntryFragment{

    //TODO add drag and drop ListView for robot actions during cycle

    ScoutEntry entry;
    Button continueButton;
    ButtonIncDec high, low, gears, rotors, hoppers, cycles, highInc, lowInc;
    CheckBox returnLoading, overflowLoading, attemptTakeoff, readyTakeoff;
    DragSortListView lv;
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

       high = (ButtonIncDec) view.findViewById(R.id.highGoalsTele);
        low = (ButtonIncDec) view.findViewById(R.id.lowGoalsTele);
        gears = (ButtonIncDec) view.findViewById(R.id.gearsTele);
        rotors = (ButtonIncDec) view.findViewById(R.id.rotorsTele);
        hoppers=(ButtonIncDec) view.findViewById(R.id.hoppersTele);
        returnLoading = (CheckBox) view.findViewById(R.id.useReturnStation);
        overflowLoading = (CheckBox) view.findViewById(R.id.useOverflow);
        attemptTakeoff = (CheckBox) view.findViewById(R.id.attemptTakeoff);
        readyTakeoff = (CheckBox) view.findViewById(R.id.readyForTakeoff);
        continueButton = (Button) view.findViewById(R.id.tele_continue);
        cycles = (ButtonIncDec) view.findViewById(R.id.numCycles);
        highInc = (ButtonIncDec) view.findViewById(R.id.highGoalInc);
        lowInc = (ButtonIncDec) view.findViewById(R.id.lowGoalInc);



        set = Settings.newInstance(getActivity());

        high.setIncDecAmount(set.getHighGoalIncTele());
        low.setIncDecAmount(set.getLowGoalIncTele());

        highInc.setValue(set.getHighGoalIncTele());
        lowInc.setValue(set.getLowGoalIncTele());

        if(!entry.getPreMatch().isPilotPlaying()) {
            ((ViewGroup) rotors.getParent()).removeView(rotors);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) hoppers.getLayoutParams();
          params.addRule(RelativeLayout.BELOW, R.id.gearsTele);
            hoppers.setLayoutParams(params);
        }


        else rotors.setMaxValue(4-entry.getAuto().getRotorsStarted());

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

                saveState();
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, PostMatchFragment.getInstance(entry), "POST")
                        .commit();
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
        entry.setTeleOp(new TeleOp(low.getValue(), high.getValue(), gears.getValue(), hoppers.getValue(),
                entry.getPreMatch().isPilotPlaying() ? rotors.getValue() : -1, attemptTakeoff.isChecked(), readyTakeoff.isChecked(),
                returnLoading.isChecked(), overflowLoading.isChecked(), cycles.getValue()));
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
            attemptTakeoff.setChecked(tele.isAttemptTakeoff());

            returnLoading.setChecked(tele.isUseReturnLoading());
            overflowLoading.setChecked(tele.isUseOverflowLoading());

            if(tele.isAttemptTakeoff())
                readyTakeoff.setEnabled(true);
            readyTakeoff.setChecked(tele.isReadyTakeoff());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        autoPopulate();
    }
}
