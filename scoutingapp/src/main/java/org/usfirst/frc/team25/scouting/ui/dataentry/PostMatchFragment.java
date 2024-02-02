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
import org.usfirst.frc.team25.scouting.data.models.PreMatch;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;

import java.util.ArrayList;

import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;


public class PostMatchFragment extends Fragment implements EntryFragment {


    private CheckBox ampFocused, speakerFocused, onstageFocused, defenseFocused, missed, tipped, commLost, fouls, inaccurateData, slowIntake, aim, coopertition, hang, fastIntake;
    private final RadioButton[] comparisonButtons = new RadioButton[3];
    private final RadioButton[] pickNumberButtons = new RadioButton[3];

    private ScoutEntry entry;
    private MaterialEditText robotComment;


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

            robotComment.setText(postMatch.getRobotComment());
            missed.setChecked(postMatch.isMissed());
            commLost.setChecked(postMatch.isCommsLost());
            tipped.setChecked(postMatch.isTipped());
            fouls.setChecked(postMatch.isFouls());
            inaccurateData.setChecked(postMatch.isInaccurateData());
            slowIntake.setChecked(postMatch.isSlowIntake());
            aim.setChecked(postMatch.isAim());
            coopertition.setChecked(postMatch.isCoopertition());
            hang.setChecked(postMatch.isHanging());
            fastIntake.setChecked(postMatch.isFastIntake());
            ampFocused.setChecked(postMatch.isAmpFocused());
            speakerFocused.setChecked(postMatch.isSpeakerFocused());
            defenseFocused.setChecked(postMatch.isDefenseFocused());
            onstageFocused.setChecked(postMatch.isOnstageFocused());



        }
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

        entry.setPostMatch(new PostMatch(robotComment.getText().toString(), missed.isChecked(), commLost.isChecked(), tipped.isChecked(), fouls.isChecked(), inaccurateData.isChecked(), slowIntake.isChecked(), aim.isChecked(), coopertition.isChecked(), hang.isChecked(), fastIntake.isChecked(), ampFocused.isChecked(), speakerFocused.isChecked(),
                defenseFocused.isChecked(), onstageFocused.isChecked(), entry.getPreMatch().getTeamNum(),
                FileManager.getPrevTeamNumber(getActivity()), comparison, pickNumber));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_post_match, container, false);

        robotComment = view.findViewById(R.id.robotDriverComment);


        Button finish = view.findViewById(R.id.post_finish);
        onstageFocused = view.findViewById(R.id.teleop_focus_onstage);
        ampFocused = view.findViewById(R.id.amp_focused);
        speakerFocused = view.findViewById(R.id.teleop_focus_score_speaker);
        defenseFocused = view.findViewById(R.id.teleop_focus_defense);

        commLost = view.findViewById(R.id.comms_lost);
        tipped = view.findViewById(R.id.tipped);
        fouls = view.findViewById(R.id.fouls);
        inaccurateData = view.findViewById(R.id.innacurate);
        slowIntake = view.findViewById(R.id.slow);
        missed = view.findViewById(R.id.missed_shots);
        aim = view.findViewById(R.id.sharp_aim);
        coopertition = view.findViewById(R.id.coopertition);
        hang = view.findViewById(R.id.hanging);
        fastIntake = view.findViewById(R.id.quick_intake);


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



        generateRobotQuickComments();

        autoPopulate();

        finish.setOnClickListener(view1 -> {
            hideKeyboard(getActivity());

            boolean proceed = true;


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
