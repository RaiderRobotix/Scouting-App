package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
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
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;

import java.util.ArrayList;


public class PostMatchFragment extends Fragment implements EntryFragment {


    private CheckBox ampFocused, speakerFocused, onstageFocused, defenseFocused, missed, tipped, commLost;
    private final RadioButton[] comparisonButtons = new RadioButton[3];
    private final RadioButton[] pickNumberButtons = new RadioButton[3];

    private ScoutEntry entry;
    private MaterialEditText robotComment;
    private RelativeLayout robotCommentView;
    private ArrayList<CheckBox> robotQuickComments;


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

    }

    public void saveState() {
        StringBuilder focus = new StringBuilder();

        String comparison = "";
        String[] comparisonValues = {">", "<", "="};
        for (int i = 0; i < comparisonButtons.length; i++) {
            if (comparisonButtons[i].isChecked()) {
                comparison = comparisonValues[i];
            }
        }

        int pickNumber = -1;
        for (int i = 0; i < pickNumberButtons.length; i++) {
            if (pickNumberButtons[i].isChecked()) {
                pickNumber = 2 - i;
            }
        }

        entry.setPostMatch(new PostMatch(robotComment.getText().toString(), missed.isChecked(), commLost.isChecked(), tipped.isChecked(), ampFocused.isChecked(), speakerFocused.isChecked(),
                defenseFocused.isChecked(), onstageFocused.isChecked(), entry.getPreMatch().getTeamNum(),
                FileManager.getPrevTeamNumber(getActivity()), comparison, pickNumber));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_post_match, container, false);

        robotComment = view.findViewById(R.id.robotDriverComment);

        robotCommentView = view.findViewById(R.id.robotDriverCommentView);

        Button finish = view.findViewById(R.id.post_finish);
        onstageFocused = view.findViewById(R.id.teleop_focus_onstage);
        ampFocused = view.findViewById(R.id.amp_focused);
        speakerFocused = view.findViewById(R.id.teleop_focus_score_speaker);
        defenseFocused = view.findViewById(R.id.teleop_focus_defense);

        missed = view.findViewById(R.id.missed_shots);
        commLost = view.findViewById(R.id.comms_lost);
        tipped = view.findViewById(R.id.tipped);

        comparisonButtons[0] = view.findViewById(R.id.current_team_comparison);
        comparisonButtons[1] = view.findViewById(R.id.prev_team_comparison);
        comparisonButtons[2] = view.findViewById(R.id.either_team_comparison);

        pickNumberButtons[0] = view.findViewById(R.id.first_pick);
        pickNumberButtons[1] = view.findViewById(R.id.second_pick);
        pickNumberButtons[2] = view.findViewById(R.id.dnp);


        int prevTeamNum = FileManager.getPrevTeamNumber(getActivity());

        String currentRobotCompareStr = "Team " + entry.getPreMatch().getTeamNum() + " (current " +
                "robot scouted)";

        String prevRobotCompareStr = "Team " + prevTeamNum + " (previous robot scouted)";

        comparisonButtons[0].setText(currentRobotCompareStr);
        comparisonButtons[1].setText(prevRobotCompareStr);

        // No previous data found, so remove it as an option
        if (prevTeamNum == 0 || !this.entry.getPreMatch().getScoutName().equals(FileManager.getPrevScoutName(getActivity()))) {
            comparisonButtons[2].setChecked(true);
            view.findViewById(R.id.prev_team_comparison_group).setVisibility(View.GONE);
        }

        robotQuickComments = new ArrayList<>();

        generateRobotQuickComments();

        autoPopulate();

        finish.setOnClickListener(view1 -> {
            boolean focusChecked = false;
            boolean comparisonSelected = false;
            boolean pickNumberSelected = false;


            for (RadioButton button : comparisonButtons) {
                if (button.isChecked()) {
                    comparisonSelected = true;
                }
            }
            for (RadioButton button : pickNumberButtons) {
                if (button.isChecked()) {
                    pickNumberSelected = true;
                }
            }




        });

        if (entry.getPreMatch().isRobotNoShow()) {
            comparisonButtons[1].setChecked(true);
            pickNumberButtons[2].setChecked(true);
            finish.callOnClick();
        }

        return view;
    }

    private void generateRobotQuickComments() {

        int prevId = -1;

        RelativeLayout.LayoutParams robotCommentParams =
                (RelativeLayout.LayoutParams) robotComment.getLayoutParams();
        robotCommentParams.addRule(RelativeLayout.BELOW, prevId);

        robotComment.setLayoutParams(robotCommentParams);
    }








    @Override
    public void onResume() {
        getActivity().setTitle("Add Entry - Post Match");
        super.onResume();

    }
}
