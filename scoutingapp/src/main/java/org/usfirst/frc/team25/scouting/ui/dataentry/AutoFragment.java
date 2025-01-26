package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

//
//
// import com.rengwuxian.materialedittext.R;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.Autonomous;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;

public class AutoFragment extends Fragment implements EntryFragment {

    private ButtonIncDecSet levelOne, levelTwo, levelThree, levelFour, coral, processorAuto, missedAuto, madeAuto, minorFoulAuto, majorFoulAuto;


    private CheckBox crossComLine;


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

        levelOne = view.findViewById(R.id.auto_zone_one_text);
        coral = view.findViewById(R.id.auto_zone_text_coral);
        levelTwo = view.findViewById(R.id.auto_zone_two_text);
        levelThree = view.findViewById(R.id.auto_zone_three_text);
        levelFour = view.findViewById(R.id.auto_zone_four_text);
        processorAuto = view.findViewById(R.id.auto_zone_text_processor);
        madeAuto = view.findViewById(R.id.auto_zone_text_made);
        missedAuto = view.findViewById(R.id.auto_zone_text_missed);
        crossComLine = view.findViewById(R.id.cross_com_line);
        minorFoulAuto = view.findViewById(R.id.minor_foul_auto_text);
        majorFoulAuto = view.findViewById(R.id.major_foul_auto_text);




        Button continueButton = view.findViewById(R.id.auto_continue);



        ButtonIncDecSet[] enablingCrossHabLineMetrics = new ButtonIncDecSet[]{ levelOne, levelTwo, levelThree, levelFour, coral, madeAuto, missedAuto};
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
            processorAuto.setValue(prevAuto.getProcessorAuto());
            majorFoulAuto.setValue(prevAuto.getMajorFoulAuto());
            minorFoulAuto.setValue(prevAuto.getMinorFoulAuto());
            crossComLine.setChecked(prevAuto.isCrossComLine());
            levelOne.setValue(prevAuto.getLevelOne());
            levelTwo.setValue(prevAuto.getLevelTwo());
            levelThree.setValue(prevAuto.getLevelThree());
            levelFour.setValue(prevAuto.getLevelFour());
            coral.setValue(prevAuto.getCoral());
            madeAuto.setValue(prevAuto.getMadeAuto());
            missedAuto.setValue(prevAuto.getMissedAuto());
        }

    }

    @Override
    public void saveState() {
        entry.setAutonomous(new Autonomous(processorAuto.getValue(), majorFoulAuto.getValue(), minorFoulAuto.getValue(), crossComLine.isChecked(), levelOne.getValue(), levelTwo.getValue(), levelThree.getValue(), levelFour.getValue(), coral.getValue(),
                madeAuto.getValue(), missedAuto.getValue()
                ));
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Sandstorm");
    }

}
