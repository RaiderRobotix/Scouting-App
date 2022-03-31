package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.thebluealliance.api.v3.models.Robot;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.Autonomous;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.UiHelper;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

import java.lang.reflect.Array;

public class AutoFragment extends Fragment implements EntryFragment {

    private ButtonIncDecView robotCargoScoredUpperHub,  robotCargoScoredLowerHub ,
             humanCargoScored ;

    private CheckBox robotPassTarmac, robotCommitedFoul;


    private ScoutEntry entry;


    public static AutoFragment getInstance(ScoutEntry entry) {
        AutoFragment autoFragment = new AutoFragment();
        autoFragment.setEntry(entry);
        return autoFragment;
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

        final View view = inflater.inflate(R.layout.fragment_auto, container, false);




        robotCargoScoredUpperHub = view.findViewById(R.id.Robot_balls_scored_upperHub);
        robotCargoScoredLowerHub = view.findViewById(R.id.Robot_Cargo_scored_lowerHub);
        humanCargoScored = view.findViewById(R.id.cargo_Human_scored);


        robotPassTarmac = view.findViewById(R.id.Robot_pass_tarmac);
        robotCommitedFoul = view.findViewById(R.id.Robot_got_foul);



        Button continueButton = view.findViewById(R.id.auto_continue);

        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            //FIX THE ROBOT PASS TARMAC CHECK BUTTON SO IF IT IS NOT CHECKED THEN IT GIVES AND ALERT


                saveState();
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, TeleOpFragment.getInstance(entry), "TELEOP")
                        .commit();
        });

        View[] items = {robotCargoScoredLowerHub,robotCargoScoredUpperHub};

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();

        }

        return view;
    }


    @Override
    public void autoPopulate() {
        if (entry.getAutonomous() != null) {

            Autonomous prevAuto = entry.getAutonomous();

            robotCargoScoredUpperHub.setValue(prevAuto.getRobotCargoScoredUpperHub());
            robotCargoScoredLowerHub.setValue(prevAuto.getRobotCargoScoredLowerHub());


            humanCargoScored.setValue(prevAuto.getHumanCargoScored());


            robotPassTarmac.setChecked(prevAuto.isRobotPassTarmac());
            robotCommitedFoul.setChecked(prevAuto.isRobotCommitFoul());
        }

    }

    @Override
    public void saveState() {
        entry.setAutonomous(new Autonomous(
                 robotCargoScoredUpperHub.getValue(),
                robotCargoScoredLowerHub.getValue(),
                humanCargoScored.getValue(),
                robotPassTarmac.isChecked(), robotCommitedFoul.isChecked()));
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Auton");
    }

}
