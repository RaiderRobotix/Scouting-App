package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Fragment;
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



    private ScoutEntry entry;
    private MaterialEditText robotComment;
    private RelativeLayout robotCommentView;
    private ArrayList<CheckBox> robotQuickComments;
    private Button finish;
    private final CheckBox[] focusButtons = new CheckBox[5];
    private final RadioButton[] comparisonButtons = new RadioButton[3];
    private final RadioButton[] pickNumberButtons = new RadioButton[3];

    private final String[] ROBOT_COMMENT_VALUES = {
            "No cube intake",
            "Shoots cubes",
            "Slow to climb",
            "Ramp bot",
            "Has rung on robot",
            "Can lift other robots",
            "Plays strategically",
            "More cubes unneeded",
            "Climb/park unneeded (levitate used and others climbed)",
            "Played defense effectively",
            "Lost communication",
            "Tipped over",
            "Caused (tech) fouls (explain below)",
            "Possible inaccurate data (specify below)"
    };


    public static PostMatchFragment getInstance(ScoutEntry entry){
        PostMatchFragment pmf = new PostMatchFragment();
        pmf.setEntry(entry);
        return pmf;
    }

    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }
    public ScoutEntry getEntry() {
        return entry;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_post_match, container, false);

        robotComment = view.findViewById(R.id.robotDriverComment);

        robotCommentView = view.findViewById(R.id.robotDriverCommentView);

        finish = view.findViewById(R.id.post_finish);
        focusButtons[0] =  view.findViewById(R.id.own_switch_focus);
        focusButtons[1] = view.findViewById(R.id.opponent_switch_focus);
        focusButtons[2] = view.findViewById(R.id.scale_focus);
        focusButtons[3] = view.findViewById(R.id.vault_focus);
        focusButtons[4] = view.findViewById(R.id.defense_focus);
        comparisonButtons[0] = view.findViewById(R.id.current_team_comparison);
        comparisonButtons[1] = view.findViewById(R.id.prev_team_comparison);
        comparisonButtons[2] = view.findViewById(R.id.either_team_comparison);
        pickNumberButtons[0] = view.findViewById(R.id.first_pick);
        pickNumberButtons[1] = view.findViewById(R.id.second_pick);
        pickNumberButtons[2] = view.findViewById(R.id.dnp);

        int prevTeamNum = FileManager.getPrevTeamNumber(getActivity());

        comparisonButtons[0].setText("Team " + entry.getPreMatch().getTeamNum() + " (current robot scouted)");
        comparisonButtons[1].setText("Team " + prevTeamNum + " (previous robot scouted)");

        if(prevTeamNum==0){
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

            for(CheckBox cb : focusButtons)
                if(cb.isChecked())
                    focusChecked = true;
            for(RadioButton button : comparisonButtons)
                if(button.isChecked())
                    comparisonSelected = true;
            for(RadioButton button : pickNumberButtons)
                if(button.isChecked())
                    pickNumberSelected = true;

            if(!focusChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select the robot's focus for this match")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                            //do things
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

            else if(!comparisonSelected){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Compare the robot to the previous one or select no opinion")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                            //do things
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

            else if(!pickNumberSelected) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select which pick this robot would be")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                            //do things
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

            else{
                saveState();
                final PostMatch pm = entry.getPostMatch();
                pm.finalizeComment();
                entry.setPostMatch(pm);

                Settings set = Settings.newInstance(getActivity());

                if (set.getMatchType().equals("P"))
                    Toast.makeText(getActivity().getBaseContext(), "Practice match data NOT saved", Toast.LENGTH_LONG).show();
                else {
                    FileManager.saveData(entry, getActivity());
                    Toast.makeText(getActivity().getBaseContext(), "Match data saved", Toast.LENGTH_LONG).show();
                }

                getActivity().setTheme(R.style.AppTheme_NoLauncher_Blue);
                set.setMatchNum(set.getMatchNum() + 1);
                getActivity().finish();
            }


        });

        return view;
    }

    private void generateRobotQuickComments(){

        int prevId = -1;

        for(int i = 0; i < Math.ceil(ROBOT_COMMENT_VALUES.length/2.0); i++){
            ArrayList<String> checkSetValues = new ArrayList<>();
            checkSetValues.add(ROBOT_COMMENT_VALUES[i*2]);
            try{
                checkSetValues.add(ROBOT_COMMENT_VALUES[i*2+1]);
            }catch(IndexOutOfBoundsException e){

            }

            LinearLayout checkSet = new LinearLayout(getActivity());
            CheckBox leftComment = new CheckBox(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

            leftComment.setLayoutParams(params);
            leftComment.setText(checkSetValues.get(0));
            leftComment.setPadding(5,3,3,5);

            robotQuickComments.add(leftComment);
            checkSet.addView(leftComment);

            if(checkSetValues.size()!=1){
                CheckBox rightComment = new CheckBox(getActivity());

                rightComment.setLayoutParams(params);
                rightComment.setText(checkSetValues.get(1));
                rightComment.setPadding(3,3,5,5);

                robotQuickComments.add(rightComment);
                checkSet.addView(rightComment);

            }

            int currentId = ViewGroup.generateViewId();

            checkSet.setId(currentId);


            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(10,10,10,10);


            if(i==0)
                rlp.addRule(RelativeLayout.BELOW, R.id.robotDriverQuickCommentHint);
            else rlp.addRule(RelativeLayout.BELOW, prevId);

            prevId = currentId;

            robotCommentView.addView(checkSet, rlp);



        }

        RelativeLayout.LayoutParams robotCommentParams = (RelativeLayout.LayoutParams) robotComment.getLayoutParams();
        robotCommentParams.addRule(RelativeLayout.BELOW, prevId);

        robotComment.setLayoutParams(robotCommentParams);
    }





    public void saveState(){
        StringBuilder focus = new StringBuilder();
        for(CheckBox cb : focusButtons)
            if(cb.isChecked()){
                if(!focus.toString().equals(""))
                    focus.append("; ");
                focus.append((String) cb.getText());
            }
        String comparison = "";
        String[] comparisonValues = {">", "<", "="};
        for(int i = 0; i < comparisonButtons.length; i++)
            if(comparisonButtons[i].isChecked())
                comparison = comparisonValues[i];

        int pickNumber = -1;
        for(int i = 0; i<pickNumberButtons.length; i++)
            if(pickNumberButtons[i].isChecked())
                pickNumber=2-i;

       entry.setPostMatch(new PostMatch(robotComment.getText().toString(), robotQuickComments,
               ROBOT_COMMENT_VALUES, focus.toString(), entry.getPreMatch().getTeamNum(),
               FileManager.getPrevTeamNumber(getActivity()), comparison, pickNumber));
    }



    @Override
    public void autoPopulate() {
        if(entry.getPostMatch()!=null){
            ArrayList<CheckBox> robotCheckedComments = entry.getPostMatch().getRobotQuickComments();
            for(int i = 0; i < robotCheckedComments.size(); i++)
                if(robotCheckedComments.get(i).isChecked())
                    robotQuickComments.get(i).setChecked(true);
            robotComment.setText(entry.getPostMatch().getRobotComment());


            for(CheckBox cb : focusButtons)
                for(String item : entry.getPostMatch().getFocus().split("; "))
                    if(cb.getText().equals(item))
                        cb.setChecked(true);

            String[] comparisonValues = {">", "<", "="};
            for(int i = 0; i < comparisonValues.length; i++)
                if(entry.getPostMatch().getComparison().equals(comparisonValues[i]))
                    comparisonButtons[i].setChecked(true);

            try{
                pickNumberButtons[2-entry.getPostMatch().getPickNumber()].setChecked(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        getActivity().setTitle("Add Entry - Post Match");
        super.onResume();

    }
}
