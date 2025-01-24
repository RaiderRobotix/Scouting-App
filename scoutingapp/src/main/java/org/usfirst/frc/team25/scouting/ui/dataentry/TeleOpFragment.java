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
    private ButtonIncDecSet levelOneTeleop, levelTwoTeleop, levelThreeTeleop, levelFourTeleop, levelFiveTeleop, netTeleop, missedTeleop, minFoulTeleop, majFoulTeleop;



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

        levelOneTeleop = view.findViewById(R.id.teleop_zone_1);
        levelTwoTeleop = view.findViewById(R.id.teleop_zone_2);
        levelThreeTeleop = view.findViewById(R.id.teleop_zone_3);
        levelFourTeleop = view.findViewById(R.id.teleop_zone_4);
        levelFiveTeleop = view.findViewById(R.id.teleop_zone_amp);
        netTeleop = view.findViewById(R.id.teleop_zone_net);
        missedTeleop = view.findViewById(R.id.teleop_zone_missed);
        minFoulTeleop = view.findViewById(R.id.foul_teleop);
        majFoulTeleop = view.findViewById(R.id.foul_teleop);

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
            levelFiveTeleop.setValue(tele.getLevelFiveTeleop());
            missedTeleop.setValue(tele.getMissedTeleop());

        }
    }

    @Override
    public void saveState() {

        entry.setTeleOp(new TeleOp(netTeleop.getValue(), minFoulTeleop.getValue(), majFoulTeleop.getValue(), levelOneTeleop.getValue(), levelTwoTeleop.getValue(), levelThreeTeleop.getValue(), levelFourTeleop.getValue(), levelFiveTeleop.getValue(), missedTeleop.getValue()
        ));


    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        hideKeyboard(getActivity());
    }

}