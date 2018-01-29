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
        baselineCrossed = (CheckBox) view.findViewById(R.id.reach_auto_line);

        lowGoals = (ButtonIncDec) view.findViewById(R.id.own_scale_auto);
        continueButton = (Button) view.findViewById(R.id.auto_continue);








        autoPopulate();


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean gearPegSelected = false;


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
        Settings set = Settings.newInstance(getActivity());
        /*Autonomous(boolean baselineCrossed, boolean useHoppers, int highGoals, int lowGoals, int rotorsStarted, int gearsDelivered) {*/



    /*   entry.setAuto(new Autonomous(baselineCrossed.isChecked(), useHoppers.isChecked(), 0, 0,
               -1, gearsDelivered.getValue(), shootsFromKey.isChecked(), "1",
               attemptGear.isChecked(), successGear.isChecked()));*/

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



            shootsFromKey.setChecked(prevAuto.isShootsFromKey());

        }

    }

}
