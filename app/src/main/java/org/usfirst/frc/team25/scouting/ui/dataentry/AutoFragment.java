package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Autonomous;
import org.usfirst.frc.team25.scouting.data.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDec;

public class AutoFragment extends Fragment implements  EntryFragment{

    CheckBox reached, crossed;
    ButtonIncDec high, low;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_auto, container, false);

        reached = (CheckBox)  view.findViewById(R.id.reach_defense);
        crossed = (CheckBox) view.findViewById(R.id.cross_defense);
        high = (ButtonIncDec) view.findViewById(R.id.high_shots_auto);
        low = (ButtonIncDec) view.findViewById(R.id.low_shots_auto);
        continueButton = (Button) view.findViewById(R.id.auto_continue);

        crossed.setEnabled(false);

        autoPopulate();

        reached.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    crossed.setEnabled(true);
                else{
                    crossed.setEnabled(false);
                    crossed.setChecked(false);
                }
            }
        });

        crossed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!crossed.isEnabled())
                    Toast.makeText(getActivity(), "Robot must reach defense first", Toast.LENGTH_SHORT).show();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveState();
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, TeleOpFragment.getInstance(entry), "TELEOP")
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void saveState() {
        entry.setAuto(new Autonomous(high.getValue(), low.getValue(), reached.isChecked(), crossed.isChecked()));
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
            high.setValue(prevAuto.getHighShots());
            low.setValue(prevAuto.getLowShots());
            reached.setChecked(prevAuto.isReached());
            if(prevAuto.isReached())
                crossed.setEnabled(true);
            crossed.setChecked(prevAuto.isCrossed());
        }
    }

}
