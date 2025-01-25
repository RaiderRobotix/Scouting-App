package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;

import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;


public class TeleOpFragment extends Fragment implements EntryFragment {

    private ScoutEntry entry;
    private ButtonIncDecSet levelOneTeleop, levelTwoTeleop, levelThreeTeleop, levelFourTeleop, coralCount, netTeleop, processorTeleop, missedTeleop, minFoulTeleop, majFoulTeleop;



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

        levelOneTeleop = view.findViewById(R.id.teleop_level_one_text);
        levelTwoTeleop = view.findViewById(R.id.teleop_level_two_text);
        levelThreeTeleop = view.findViewById(R.id.teleop_level_three_text);
        levelFourTeleop = view.findViewById(R.id.teleop_level_four_text);
        coralCount = view.findViewById(R.id.teleop_coral);
        netTeleop = view.findViewById(R.id.teleop_net);
        processorTeleop = view.findViewById(R.id.teleop_processor);
        missedTeleop = view.findViewById(R.id.teleop_zone_missed);
        minFoulTeleop = view.findViewById(R.id.minor_foul_teleop_text);
        majFoulTeleop = view.findViewById(R.id.major_foul_teleop_text);

        Button continueButton = view.findViewById(R.id.teleop_continue);


        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            hideKeyboard(getActivity());

            boolean proceed = true;


            if (proceed) {
                saveState();
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, EndGameFragment.getInstance(entry), "ENDGAME")
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

            netTeleop.setValue(tele.getNetTeleop());
            minFoulTeleop.setValue(tele.getMinFoulTeleop());
            majFoulTeleop.setValue(tele.getMajFoulTeleop());
            levelOneTeleop.setValue(tele.getLevelOneTeleop());
            levelTwoTeleop.setValue(tele.getLevelTwoTeleop());
            levelThreeTeleop.setValue(tele.getLevelThreeTeleop());
            levelFourTeleop.setValue(tele.getLevelFourTeleop());
            coralCount.setValue(tele.getCoralCount());
            processorTeleop.setValue(tele.getProcessorTeleop());
            missedTeleop.setValue(tele.getMissedTeleop());

        }
    }

    @Override
    public void saveState() {

        entry.setTeleOp(new TeleOp(netTeleop.getValue(), minFoulTeleop.getValue(), majFoulTeleop.getValue(), levelOneTeleop.getValue(), levelTwoTeleop.getValue(), levelThreeTeleop.getValue(), levelFourTeleop.getValue(), coralCount.getValue(), processorTeleop.getValue(), missedTeleop.getValue()
        ));


    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        hideKeyboard(getActivity());
    }

}