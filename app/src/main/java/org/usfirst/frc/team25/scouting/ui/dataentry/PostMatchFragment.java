package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.content.DialogInterface;
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



    ScoutEntry entry;
    MaterialEditText robotComment, pilotComment;
    RelativeLayout robotCommentView, pilotCommentView;
    ArrayList<CheckBox> robotQuickComments, pilotQuickComments;
    Button finish;
    CheckBox[] focusButtons = new CheckBox[5];

    final String[] ROBOT_COMMENT_VALUES = {
            "Cube intake/pickup",
            "Shoots cubes",
            "Slow climb",
            "Ramp bot",
            "Has rung on robot",
            "Iron cross robot",
            "Plays strategically",
            "More cubes uneeded",
            "Attains cubes slowly",
            "Played defense effectively",
            "Slowed by defense",
            "Lost comms.",
            "Efficient cycler",
            "Tipped over",
            "Caused (tech) fouls (explain below)",
            "Do not pick (explain)",
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

        robotComment = (MaterialEditText) view.findViewById(R.id.robotDriverComment);

        robotCommentView = (RelativeLayout) view.findViewById(R.id.robotDriverCommentView);

        finish = (Button) view.findViewById(R.id.post_finish);
        focusButtons[0] =  view.findViewById(R.id.own_switch_focus);
        focusButtons[1] = view.findViewById(R.id.opponent_switch_focus);
        focusButtons[2] = view.findViewById(R.id.scale_focus);
        focusButtons[3] = view.findViewById(R.id.vault_focus);
        focusButtons[4] = view.findViewById(R.id.defense_focus);

        robotQuickComments = new ArrayList<CheckBox>();
        pilotQuickComments = new ArrayList<CheckBox>();


        generateRobotQuickComments();

        autoPopulate();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean focusChecked = false;

                for(CheckBox cb : focusButtons)
                    if(cb.isChecked())
                        focusChecked = true;

                if(!focusChecked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Select the robot's focus for this match")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
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


            }
        });

        return view;
    }

    void generateRobotQuickComments(){

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
            leftComment.setPadding(3,3,3,5);

            robotQuickComments.add(leftComment);
            checkSet.addView(leftComment);

            if(checkSetValues.size()!=1){
                CheckBox rightComment = new CheckBox(getActivity());

                rightComment.setLayoutParams(params);
                rightComment.setText(checkSetValues.get(1));
                rightComment.setPadding(3,3,3,5);

                robotQuickComments.add(rightComment);
                checkSet.addView(rightComment);

            }

            int currentId = ViewGroup.generateViewId();

            checkSet.setId(currentId);


            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(16,16,16,16);


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
        String focus = "";
        for(CheckBox cb : focusButtons)
            if(cb.isChecked()){
                focus = (String) cb.getText();
                break;
            }

        entry.setPostMatch(new PostMatch(robotComment.getText().toString(), "", robotQuickComments,
                null, ROBOT_COMMENT_VALUES,  focus));
    }



    @Override
    public void autoPopulate() {
        if(entry.getPostMatch()!=null){
            ArrayList<CheckBox> robotCheckedComments = entry.getPostMatch().getRobotQuickComments();
            for(int i = 0; i < robotCheckedComments.size(); i++)
                if(robotCheckedComments.get(i).isChecked())
                    robotQuickComments.get(i).setChecked(true);
            robotComment.setText(entry.getPostMatch().getRobotComment());

            ArrayList<CheckBox> pilotCheckedComments = entry.getPostMatch().getPilotQuickComments();
            for(int i = 0; i < pilotCheckedComments.size(); i++)
                if(pilotCheckedComments.get(i).isChecked())
                    pilotQuickComments.get(i).setChecked(true);
            pilotComment.setText(entry.getPostMatch().getPilotComment());

            for(CheckBox cb : focusButtons)
                if(cb.getText().equals(entry.getPostMatch().getFocus()))
                    cb.setChecked(true);
        }
    }

    @Override
    public void onResume() {
        getActivity().setTitle("Add Entry - Post Match");
        super.onResume();

    }
}
