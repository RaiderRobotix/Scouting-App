package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;


import com.rengwuxian.materialedittext.MaterialEditText;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.models.EndGame;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;

import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;


public class EndGameFragment extends Fragment implements EntryFragment {

    private CheckBox park, deepBarge, shallowBarge;
    private ScoutEntry entry;


    public static EndGameFragment getInstance(ScoutEntry entry) {
        EndGameFragment endgameFragment = new EndGameFragment();
        endgameFragment.setEntry(entry);
        return endgameFragment;
    }

    public ScoutEntry getEntry() {
        return entry;
    }

    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }

    @Override
    public void autoPopulate() {
        if (entry.getEndgame() != null) {
            EndGame endgame = entry.getEndgame();

            park.setChecked(endgame.isPark());
            deepBarge.setChecked(endgame.isDeepBarge());
            shallowBarge.setChecked(endgame.isShallowBarge());


        }
    }

    public void saveState() {

        entry.setEndgame(new EndGame(park.isChecked(), deepBarge.isChecked(), shallowBarge.isChecked()));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_endgame, container, false);

        Button continueButton = view.findViewById(R.id.endgame_continue);
        park = view.findViewById(R.id.endgame_park);
        deepBarge = view.findViewById(R.id.endgame_deepBarge);
        shallowBarge = view.findViewById(R.id.endgame_shallowBarge);

        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            hideKeyboard(getActivity());

            boolean proceed = true;


            if (proceed) {
                saveState();
                getActivity().setTheme(R.style.AppTheme_NoLauncher_Blue);
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, PostMatchFragment.getInstance(entry), "POST")
                        .commit();
            }
        });




        return view;
    }










    @Override
    public void onResume() {
        getActivity().setTitle("Add Entry - End Game");
        super.onResume();

    }
}
