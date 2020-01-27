package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.StringProcessing;
import org.usfirst.frc.team25.scouting.data.models.PostMatch;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;

import java.util.ArrayList;

import lombok.val;


public class PostMatchFragment extends Fragment implements EntryFragment {


    private final CheckBox[] focusButtons = new CheckBox[5];
    private final RadioButton[] comparisonButtons = new RadioButton[3];
    private final RadioButton[] pickNumberButtons = new RadioButton[3];
    private final String[] ROBOT_COMMENT_VALUES = {
            "Low objectives only",
            "Scoring potential reached",
            "Hatch panel floor intake",
            "Fast intake",
            "Fast climb",
            "Struggles to play under defense",
            "Effective defense",
            "Higher port levels significantly slower",
            "Struggles to level",
            "Slow climb",
            "Fast drive",
            "Assists in climbs",
            "Fast control panel",
            "Middle Hang",
            "Every-bot",
            "Lost communications",
            "Tipped over",
            "Commits (tech) fouls (explain)",
            "Do not pick (explain)",
            "Possible inaccurate data (specify)",

    };
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_post_match, container, false);

        robotComment = view.findViewById(R.id.robotDriverComment);

        robotCommentView = view.findViewById(R.id.robotDriverCommentView);

        Button finish = view.findViewById(R.id.post_finish);
        focusButtons[0] = view.findViewById(R.id.teleop_focus_bottom_port);
        focusButtons[1] = view.findViewById(R.id.teleop_focus_top_port);
        focusButtons[2] = view.findViewById(R.id.teleop_focus_collecting_cells);
        focusButtons[3] = view.findViewById(R.id.teleop_focus_control_panel);
        focusButtons[4] = view.findViewById(R.id.teleop_focus_defense);

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

            for (CheckBox cb : focusButtons) {
                if (cb.isChecked()) {
                    focusChecked = true;
                }
            }
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

            if (!focusChecked && !entry.getPreMatch().getNoShow()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select the robot's focus for this match")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                        });
                AlertDialog alert = builder.create();
                alert.show();

            } else if (!comparisonSelected) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Compare the robot to the previous one")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                        });
                AlertDialog alert = builder.create();
                alert.show();

            } else if (!pickNumberSelected) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select which pick this robot would be")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                        });
                AlertDialog alert = builder.create();
                alert.show();

            } else {
                saveState();

                final PostMatch unsaFePM = entry.getPostMatch();
                val pm = unsaFePM.copy(
                        unsaFePM.getComparison(),
                        unsaFePM.getPickNumber(),
                        // Escape user input for csv
                        StringProcessing.removeCommasBreaks(unsaFePM.getRobotComment()),
                        unsaFePM.getAutoFocus(),
                        unsaFePM.getAutoFocus(),
                        unsaFePM.getClimbType()
                );
                entry.setPostMatch(pm);

                Settings set = Settings.newInstance(getActivity());

                if (set.getMatchType().equals("P")) {
                    Toast.makeText(getActivity().getBaseContext(), "Practice match data NOT " +
                            "saved", Toast.LENGTH_LONG).show();
                } else {
                    FileManager.saveData(entry, getActivity());
                    Toast.makeText(getActivity().getBaseContext(), "Match data saved",
                            Toast.LENGTH_LONG).show();
                }


                getActivity().setTheme(R.style.AppTheme_NoLauncher_Blue);
                set.setMatchNum(set.getMatchNum() + 1);
                getActivity().finish();
            }


        });

        if (entry.getPreMatch().getNoShow()) {
            comparisonButtons[1].setChecked(true);
            pickNumberButtons[2].setChecked(true);
            finish.callOnClick();
        }

        return view;
    }

    @Override
    public void autoPopulate() {
        if (entry.getPostMatch() != null) {
            val robotCheckedComments = entry.getPostMatch().getRobotQuickCommentSelections();

            for (int i = 0; i < robotCheckedComments.size(); i++) {
                if (robotCheckedComments.get(i)) {
                    robotQuickComments.get(i).setChecked(true);
                }
            }

            robotComment.setText(entry.getPostMatch().getRobotComment());

            // Parse focus string
            for (CheckBox cb : focusButtons) {
                for (String item : entry.getPostMatch().getTeleopFocus().split("; ")) {
                    if (cb.getText().equals(item)) {
                        cb.setChecked(true);
                    }
                }
            }

            String[] comparisonValues = {">", "<", "="};
            for (int i = 0; i < comparisonValues.length; i++) {
                if (entry.getPostMatch().getComparison().equals(comparisonValues[i])) {
                    comparisonButtons[i].setChecked(true);
                }
            }

            try {
                pickNumberButtons[2 - entry.getPostMatch().getPickNumber()].setChecked(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveState() {
        StringBuilder focus = new StringBuilder();
        for (CheckBox cb : focusButtons) {
            if (cb.isChecked()) {
                if (!focus.toString().equals("")) {
                    focus.append("; ");
                }
                focus.append((String) cb.getText());
            }
        }

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

        entry.setPostMatch(new PostMatch(
                        null, // TODO Create comparison
                        pickNumber,
                        robotComment.getText().toString(),
                        focus.toString(),
                        focus.toString(),
                        null // TODO Input ClimbType
                )
        );
        val pm = entry.getPostMatch();
        for (String comment : ROBOT_COMMENT_VALUES) {
            pm.getRobotQuickCommentSelections().putIfAbsent(comment, false);
        }
    }

    private void generateRobotQuickComments() {

        int prevId = -1;

        for (int i = 0; i < Math.ceil(ROBOT_COMMENT_VALUES.length / 2.0); i++) {
            ArrayList<String> checkSetValues = new ArrayList<>();
            checkSetValues.add(ROBOT_COMMENT_VALUES[i * 2]);
            try {
                checkSetValues.add(ROBOT_COMMENT_VALUES[i * 2 + 1]);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            LinearLayout checkSet = new LinearLayout(getActivity());
            CheckBox leftComment = new CheckBox(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1);

            leftComment.setLayoutParams(params);
            leftComment.setText(checkSetValues.get(0));
            leftComment.setPadding(5, 3, 3, 5);

            robotQuickComments.add(leftComment);
            checkSet.addView(leftComment);

            if (checkSetValues.size() != 1) {
                CheckBox rightComment = new CheckBox(getActivity());

                rightComment.setLayoutParams(params);
                rightComment.setText(checkSetValues.get(1));
                rightComment.setPadding(3, 3, 5, 5);

                robotQuickComments.add(rightComment);
                checkSet.addView(rightComment);

            }

            int currentId = ViewGroup.generateViewId();

            checkSet.setId(currentId);


            RelativeLayout.LayoutParams rlp =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(10, 10, 10, 10);


            if (i == 0) {
                rlp.addRule(RelativeLayout.BELOW, R.id.robotDriverQuickCommentHint);
            } else {
                rlp.addRule(RelativeLayout.BELOW, prevId);
            }

            prevId = currentId;

            robotCommentView.addView(checkSet, rlp);


        }

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
