package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.Autonomous;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;

public class AutoFragment extends Fragment implements EntryFragment {

    private ButtonIncDecSet zoneOne, zoneTwo, zoneThree, zoneFour, ampAuto, missedAuto;


    private CheckBox crossComLine, foulAuto;


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

        zoneOne = view.findViewById(R.id.teleop_zone_1);
        zoneTwo = view.findViewById(R.id.auto_zone_2);
        zoneThree = view.findViewById(R.id.auto_zone_3);
        zoneFour = view.findViewById(R.id.teleop_zone_4);
        ampAuto = view.findViewById(R.id.auto_zone_amp);
        missedAuto = view.findViewById(R.id.auto_zone_missed);
        crossComLine = view.findViewById(R.id.cross_com_line);
        foulAuto = view.findViewById(R.id.foulTeleop);




        Button continueButton = view.findViewById(R.id.auto_continue);



        ButtonIncDecSet[] enablingCrossHabLineMetrics = new ButtonIncDecSet[]{ zoneOne, zoneTwo, zoneThree, zoneFour, ampAuto, missedAuto};
        /*
        for (ButtonIncDecSet set : enablingCrossHabLineMetrics) {
            set.incButton.setOnClickListener(view1 -> {
                set.increment();
                autoEnableCrossHabLine();
                if (set.equals()) {
                    autoEnableCargoShipHatchPlacementSet();
                }

            });
            set.decButton.setOnClickListener(view1 -> {
                set.decrement();
                autoEnableCrossHabLine();
                if (set.equals(cargoShipHatches)) {
                    autoEnableCargoShipHatchPlacementSet();
                }

            });
        }

        hatchesDropped.incButton.setOnClickListener(view1 -> {
            hatchesDropped.increment();
            autoEnableHatchesDroppedLocationSet();
        });

        hatchesDropped.decButton.setOnClickListener(view1 -> {
            hatchesDropped.decrement();
            autoEnableHatchesDroppedLocationSet();
        });

        cargoDropped.incButton.setOnClickListener(view1 -> {
            cargoDropped.increment();
            autoEnableCargoDroppedLocationSet();
        });

        cargoDropped.decButton.setOnClickListener(view1 -> {
            cargoDropped.decrement();
            autoEnableCargoDroppedLocationSet();
        });
        */

        autoPopulate();

        continueButton.setOnClickListener(view1 -> {

                saveState();
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, TeleOpFragment.getInstance(entry), "TELEOP")
                        .commit();


        });

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();

        }

        return view;
    }


    @Override
    public void autoPopulate() {
        if (entry.getAutonomous() != null) {

            Autonomous prevAuto = entry.getAutonomous();
            ampAuto.setValue(prevAuto.getAmpAuto());
            foulAuto.setChecked(prevAuto.isFoulAuto());
            crossComLine.setChecked(prevAuto.isCrossComLine());
            zoneOne.setValue(prevAuto.getZoneOne());
            zoneTwo.setValue(prevAuto.getZoneTwo());
            zoneThree.setValue(prevAuto.getZoneThree());
            zoneFour.setValue(prevAuto.getZoneFour());
            missedAuto.setValue(prevAuto.getMissedAuto());
        }

    }

    @Override
    public void saveState() {
        entry.setAutonomous(new Autonomous(ampAuto.getValue(), foulAuto.isChecked(), crossComLine.isChecked(), zoneOne.getValue(), zoneTwo.getValue(), zoneThree.getValue(), zoneFour.getValue(),
                missedAuto.getValue()
                ));
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Sandstorm");
    }

}
