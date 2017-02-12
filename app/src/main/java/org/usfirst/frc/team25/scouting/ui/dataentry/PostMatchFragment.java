package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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

    final String[] ROBOT_COMMENT_VALUES = {
            "Accurate high shooter",
            "Fast shoot rate",
            "Low accuracy",
            "Collects fuel from ground",
            "Collects gears from ground",
            "Active gear mech. (no pilot interact.)",
            "Lost comms.",
            "Played defense",
            "Slowed by defense",
            "Caused foul (specify below)",
            "INCORRECT DATA (specify below)"
    };

    final String[] PILOT_COMMENT_VALUES = {
            "Dropped a gear from lift",
            "Quick in retrieving gears",
            "Starts rotors quickly",
            "Caused foul (specify below)"
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

        robotComment = (MaterialEditText) view.findViewById(R.id.robotComment);
        pilotComment = (MaterialEditText) view.findViewById(R.id.pilotComment);
        robotCommentView = (RelativeLayout) view.findViewById(R.id.robotCommentView);
        pilotCommentView = (RelativeLayout) view.findViewById(R.id.pilotCommentView);
        finish = (Button) view.findViewById(R.id.post_finish);

        robotQuickComments = new ArrayList<CheckBox>();
        pilotQuickComments = new ArrayList<CheckBox>();

        if(!entry.getPreMatch().isPilotPlaying()) {
            ((ViewGroup) pilotCommentView.getParent()).removeView(pilotCommentView);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) finish.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.robotCommentView);
            finish.setLayoutParams(params);
        }
        else generatePilotQuickComments();

        generateRobotQuickComments();

        autoPopulate();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveState();
                final PostMatch pm = entry.getPostMatch();
                pm.finalizeComment();
                entry.setPostMatch(pm);

                FileManager.saveData(entry, getActivity());


                Settings set = Settings.newInstance(getActivity());
                set.setMatchNum(set.getMatchNum()+1);
                getActivity().finish();
                Toast.makeText(getActivity().getBaseContext(), "Match data saved", Toast.LENGTH_LONG).show();

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
            leftComment.setPadding(5,5,5,7);

            robotQuickComments.add(leftComment);
            checkSet.addView(leftComment);

            if(checkSetValues.size()!=1){
                CheckBox rightComment = new CheckBox(getActivity());

                rightComment.setLayoutParams(params);
                rightComment.setText(checkSetValues.get(1));
                rightComment.setPadding(5,5,5,5);

                robotQuickComments.add(rightComment);
                checkSet.addView(rightComment);

            }

            int currentId = ViewGroup.generateViewId();

            checkSet.setId(currentId);


            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(16,16,16,16);


            if(i==0)
                rlp.addRule(RelativeLayout.BELOW, R.id.robotQuickCommentHint);
            else rlp.addRule(RelativeLayout.BELOW, prevId);

            prevId = currentId;

            robotCommentView.addView(checkSet, rlp);



        }

        RelativeLayout.LayoutParams robotCommentParams = (RelativeLayout.LayoutParams) robotComment.getLayoutParams();
        robotCommentParams.addRule(RelativeLayout.BELOW, prevId);

        robotComment.setLayoutParams(robotCommentParams);
    }

    void generatePilotQuickComments(){

        int prevId = -1;

        for(int i = 0; i < Math.ceil(PILOT_COMMENT_VALUES.length/2.0); i++){
            ArrayList<String> checkSetValues = new ArrayList<>();
            checkSetValues.add(PILOT_COMMENT_VALUES[i*2]);
            try{
                checkSetValues.add(PILOT_COMMENT_VALUES[i*2+1]);
            }catch(IndexOutOfBoundsException e){

            }

            LinearLayout checkSet = new LinearLayout(getActivity());
            CheckBox leftComment = new CheckBox(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            leftComment.setPadding(3,3,3,3);

            leftComment.setLayoutParams(params);
            leftComment.setText(checkSetValues.get(0));

            pilotQuickComments.add(leftComment);
            checkSet.addView(leftComment);

            if(checkSetValues.size()!=1){
                CheckBox rightComment = new CheckBox(getActivity());

                rightComment.setLayoutParams(params);
                rightComment.setText(checkSetValues.get(1));
                rightComment.setPadding(3,3,3,3);

                pilotQuickComments.add(rightComment);
                checkSet.addView(rightComment);

            }

            int currentId = ViewGroup.generateViewId();

            checkSet.setId(currentId);


            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlp.setMargins(16,16,16,16);


            if(i==0)
                rlp.addRule(RelativeLayout.BELOW, R.id.pilotQuickCommentHint);
            else rlp.addRule(RelativeLayout.BELOW, prevId);

            prevId = currentId;

            pilotCommentView.addView(checkSet, rlp);



        }

        RelativeLayout.LayoutParams pilotCommentParams = (RelativeLayout.LayoutParams) pilotComment.getLayoutParams();
        pilotCommentParams.addRule(RelativeLayout.BELOW, prevId);

        pilotComment.setLayoutParams(pilotCommentParams);
    }

    public void saveState(){
        entry.setPostMatch(new PostMatch(robotComment.getText().toString(), pilotComment.getText().toString(), robotQuickComments,
                pilotQuickComments, ROBOT_COMMENT_VALUES, PILOT_COMMENT_VALUES));
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
        }
    }

    @Override
    public void onResume() {
        getActivity().setTitle("Add Entry - Post Match");
        super.onResume();

    }
}
