package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.PostMatch;
import org.usfirst.frc.team25.scouting.data.models.PreMatch;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;

import java.util.ArrayList;

import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;


public class PostMatchFragment extends Fragment implements EntryFragment {


    private ButtonIncDecSet defensive, speed, manuver, hpEfficiency;
    private CheckBox winRanking, melody, ensemble;
    private TextInputEditText allianceScore;
    private ScoutEntry entry;
    private int ranking, score;


    public static PostMatchFragment getInstance(ScoutEntry entry) {
        PostMatchFragment postmatchFragment = new PostMatchFragment();
        postmatchFragment.setEntry(entry);
        return postmatchFragment;
    }

    public ScoutEntry getEntry() {
        return entry;
    }

    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }

    @Override
    public void autoPopulate() {
        if (entry.getPostMatch() != null) {
            PostMatch postMatch = entry.getPostMatch();

            score = postMatch.getAllianceScore();
            winRanking.setChecked(postMatch.isWinRanking());
            melody.setChecked(postMatch.isMelody());
            ensemble.setChecked(postMatch.isEnsemble());
            defensive.setValue(postMatch.getDefensive());
            speed.setValue(postMatch.getSpeed());
            manuver.setValue(postMatch.getManuver());
            hpEfficiency.setValue(postMatch.getHpEfficiency());
            ranking = postMatch.getRanking();

        }
    }

    public void saveState() {
        entry.setPostMatch(new PostMatch(Integer.parseInt(String.valueOf(allianceScore.getText())), winRanking.isChecked(), melody.isChecked(), ensemble.isChecked(), defensive.getValue(), speed.getValue(), manuver.getValue(), hpEfficiency.getValue(), ranking));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_post_match, container, false);


        allianceScore = view.findViewById(R.id.alliance_score);
        score = Integer.parseInt(String.valueOf(allianceScore.getText()));
        winRanking = view.findViewById(R.id.win_rp);
        melody = view.findViewById(R.id.melody_bonus);
        ensemble = view.findViewById(R.id.ensemble_bonus);
        defensive = view.findViewById(R.id.defensive);
        speed = view.findViewById(R.id.speed);
        manuver = view.findViewById(R.id.manuver);
        hpEfficiency = view.findViewById(R.id.hp_efficiency);




        Button finish = view.findViewById(R.id.post_finish);


        autoPopulate();

        finish.setOnClickListener(view1 -> {
            hideKeyboard(getActivity());

            boolean proceed = true;
            if(winRanking.isChecked()){
                ranking = ranking+2;
            }
            if(melody.isChecked()){
                ranking++;
            }
            if(ensemble.isChecked()){
                ranking++;
            }

            if (proceed) {
                saveState();
                getActivity().setTheme(R.style.AppTheme_NoLauncher_Blue);
                FileManager.saveData(entry, getActivity());
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, PostMatchFragment.getInstance(entry), "POST")
                        .commit();
                getActivity().finish();
            }
        });



        return view;
    }










    @Override
    public void onResume() {
        getActivity().setTitle("Add Entry - Post Match");
        super.onResume();

    }
}
