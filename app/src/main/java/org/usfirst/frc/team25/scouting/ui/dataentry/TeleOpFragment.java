package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.TeleOp;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDec;


public class TeleOpFragment extends Fragment implements EntryFragment{

    ScoutEntry entry;
    Button continueButton;
    ButtonIncDec high, low;
    CheckBox outer, courtyard, batter, breached, scaled;

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

        high = (ButtonIncDec) view.findViewById(R.id.high_shots_tele);
        low = (ButtonIncDec) view.findViewById(R.id.low_shots_tele);
        outer = (CheckBox) view.findViewById(R.id.score_outer);
        courtyard = (CheckBox) view.findViewById(R.id.score_courtyard);
        batter = (CheckBox) view.findViewById(R.id.score_batter);
        breached = (CheckBox) view.findViewById(R.id.breach_tower);
        scaled = (CheckBox) view.findViewById(R.id.scale_tower);
        continueButton = (Button) view.findViewById(R.id.tele_continue);
        scaled.setEnabled(false);

        breached.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    scaled.setEnabled(true);
                else{
                    scaled.setEnabled(false);
                    scaled.setChecked(false);
                }
            }
        });

        scaled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!scaled.isEnabled())
                    Toast.makeText(getActivity(), "Robot must breach tower first", Toast.LENGTH_SHORT).show();
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

        return view;
    }

    @Override
    public void saveState() {
        entry.setTeleOp(new TeleOp(high.getValue(), low.getValue(), outer.isChecked(),
                courtyard.isChecked(), batter.isChecked(), breached.isChecked(), scaled.isChecked()));
    }

    @Override
    public void autoPopulate() {
        if(entry.getTeleOp()!=null){
            TeleOp tele = entry.getTeleOp();
            high.setValue(tele.getHighShots());
            low.setValue(tele.getLowShots());
            outer.setChecked(tele.isScoreOuterWorks());
            batter.setChecked(tele.isScoreBatter());
            courtyard.setChecked(tele.isScoreCourtyard());
            breached.setChecked(tele.isTowerBreached());
            if(tele.isTowerBreached())
                scaled.setEnabled(true);
            scaled.setChecked(tele.isTowerScaled());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        autoPopulate();
    }
}
