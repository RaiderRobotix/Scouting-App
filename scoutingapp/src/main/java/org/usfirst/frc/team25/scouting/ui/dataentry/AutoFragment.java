package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
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
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

public class AutoFragment extends Fragment implements EntryFragment {

    private ButtonIncDecSet notesPickedAuto, ampNotesAuto, speakerNotesAuto, notesDroppedAuto;



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


        notesPickedAuto=view.findViewById(R.id.notes_picked_auton);
        ampNotesAuto=view.findViewById(R.id.amp_scored_auton);
        speakerNotesAuto=view.findViewById(R.id.speaker_scored_auton);
        notesDroppedAuto=view.findViewById(R.id.notes_dropped_auton);
        crossComLine=view.findViewById(R.id.cross_com_line);


        Button continueButton = view.findViewById(R.id.auto_continue);



        ButtonIncDecSet[] enablingCrossHabLineMetrics = new ButtonIncDecSet[]{notesPickedAuto, ampNotesAuto, speakerNotesAuto, notesDroppedAuto};
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
            crossComLine.setChecked(prevAuto.isCrossComLine());
            notesPickedAuto.setValue(prevAuto.getNotesPickedAuto());
            ampNotesAuto.setValue(prevAuto.getAmpScoredAuto());
            speakerNotesAuto.setValue(prevAuto.getSpeakerScoredAuto());
            notesDroppedAuto.setValue(prevAuto.getNotesDroppedAuto());

        }

    }

    @Override
    public void saveState() {
        entry.setAutonomous(new Autonomous(crossComLine.isChecked(), notesPickedAuto.getValue(),
                ampNotesAuto.getValue(),
                speakerNotesAuto.getValue(),
                notesDroppedAuto.getValue()
                ));
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Sandstorm");
    }

}
