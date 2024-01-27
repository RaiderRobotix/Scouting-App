package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.UiHelper;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;


public class TeleOpFragment extends Fragment implements EntryFragment {

    private ScoutEntry entry;
    private ButtonIncDecSet notesPickedGround, notesPickedHps, ampNotesScoredTeleop, speakerNotesScoredTeleop, ampSpeakerNotesScored, notesDroppedTeleop;


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

        notesPickedGround = view.findViewById(R.id.notes_picked_ground);
        notesPickedHps = view.findViewById(R.id.notes_picked_teleop);
        ampNotesScoredTeleop = view.findViewById(R.id.amp_scored_teleop);
        speakerNotesScoredTeleop = view.findViewById(R.id.speaker_scored_teleop);
        ampSpeakerNotesScored = view.findViewById(R.id.speaker_amplified_teleop);
        notesDroppedTeleop = view.findViewById(R.id.notes_dropped_teleop);
        Button continueButton = view.findViewById(R.id.teleop_continue);


        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            hideKeyboard(getActivity());

            boolean proceed = true;


            if (proceed) {
                saveState();
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, PostMatchFragment.getInstance(entry), "POST")
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

            notesPickedGround.setValue(tele.getNotesPickedGround());
            notesPickedHps.setValue(tele.getNotesPickedHps());
            ampNotesScoredTeleop.setValue(tele.getAmpNotesScoredTelop());
            speakerNotesScoredTeleop.setValue(tele.getSpeakerNotesScoredTeleop());
            ampSpeakerNotesScored.setValue(tele.getAmpSpeakerNotesScored());
            notesDroppedTeleop.setValue(tele.getNotesDroppedTeleop());


        }
    }

    @Override
    public void saveState() {

        entry.setTeleOp(new TeleOp(notesPickedGround.getValue(), notesPickedHps.getValue(), ampNotesScoredTeleop.getValue(),
                speakerNotesScoredTeleop.getValue(), ampSpeakerNotesScored.getValue(), notesDroppedTeleop.getValue()
        ));


    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        hideKeyboard(getActivity());
    }

}