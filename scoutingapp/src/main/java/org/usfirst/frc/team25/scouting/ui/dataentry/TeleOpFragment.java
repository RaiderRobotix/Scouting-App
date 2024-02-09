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
    private ButtonIncDecSet zoneOneTeleop, zoneTwoTeleop, zoneThreeTeleop, zoneFourTeleop, ampZoneTeleop, missedTeleop, foulTeleop;



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

        zoneOneTeleop = view.findViewById(R.id.teleop_zone_1);
        zoneTwoTeleop = view.findViewById(R.id.teleop_zone_2);
        zoneThreeTeleop = view.findViewById(R.id.teleop_zone_3);
        zoneFourTeleop = view.findViewById(R.id.teleop_zone_4);
        ampZoneTeleop = view.findViewById(R.id.teleop_zone_amp);
        missedTeleop = view.findViewById(R.id.teleop_zone_missed);
        foulTeleop = view.findViewById(R.id.foul_teleop);

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

            ampZoneTeleop.setValue(tele.getAmpTeleop());
            foulTeleop.setValue(tele.getFoulTeleop());
            zoneOneTeleop.setValue(tele.getZoneOneTeleop());
            zoneTwoTeleop.setValue(tele.getZoneTwoTeleop());
            zoneThreeTeleop.setValue(tele.getZoneThreeTeleop());
            zoneFourTeleop.setValue(tele.getZoneFourTeleop());
            missedTeleop.setValue(tele.getMissedTeleop());

        }
    }

    @Override
    public void saveState() {

        entry.setTeleOp(new TeleOp(ampZoneTeleop.getValue(), foulTeleop.getValue(), zoneOneTeleop.getValue(), zoneTwoTeleop.getValue(), zoneThreeTeleop.getValue(), zoneFourTeleop.getValue(), missedTeleop.getValue()
        ));


    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        hideKeyboard(getActivity());
    }

}