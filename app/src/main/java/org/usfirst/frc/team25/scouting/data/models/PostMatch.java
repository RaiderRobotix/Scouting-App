package org.usfirst.frc.team25.scouting.data.models;

import android.widget.CheckBox;


import java.util.ArrayList;
import java.util.HashMap;

/** Qualitative reflection on the robot's performance after a match
 *  Not to be used for end game actions
 */
public class PostMatch {


    public PostMatch(String robotComment, String pilotComment, ArrayList<CheckBox> robotQuickComments,
                     ArrayList<CheckBox> pilotQuickComments,
                     String[] robotQuickCommentValues, String[] pilotQuickCommentValues) {
        this.robotComment = robotComment;
        this.pilotComment = pilotComment;
        this.robotQuickComments = robotQuickComments;
        this.pilotQuickComments = pilotQuickComments;
        this.robotQuickCommentValues = robotQuickCommentValues;
        this.pilotQuickCommentValues = pilotQuickCommentValues;

        robotQuickCommentSelections = new HashMap<>();
        pilotQuickCommentSelections = new HashMap<>();



    }


    String robotComment, pilotComment;
    HashMap<String, Boolean> robotQuickCommentSelections, pilotQuickCommentSelections;


    public transient ArrayList<CheckBox> robotQuickComments, pilotQuickComments;
    transient String[] robotQuickCommentValues, pilotQuickCommentValues;

    public String getRobotComment() {
        return robotComment;
    }

    public void setRobotComment(String robotComment) {
        this.robotComment = robotComment;
    }

    public String getPilotComment() {
        return pilotComment;
    }

    public void setPilotComment(String pilotComment) {
        this.pilotComment = pilotComment;
    }

    public ArrayList<CheckBox> getRobotQuickComments() {
        return robotQuickComments;
    }

    public void setRobotQuickComments(ArrayList<CheckBox> robotQuickComments) {
        this.robotQuickComments = robotQuickComments;
    }

    public ArrayList<CheckBox> getPilotQuickComments() {
        return pilotQuickComments;
    }

    public void setPilotQuickComments(ArrayList<CheckBox> pilotQuickComments) {
        this.pilotQuickComments = pilotQuickComments;
    }



    public void finalizeComment(){

        for(String value : robotQuickCommentValues)
            for(CheckBox cb : robotQuickComments)
                if(cb.getText().toString().equals(value))
                    robotQuickCommentSelections.put(value, cb.isChecked());

        for(String value : pilotQuickCommentValues)
            for(CheckBox cb : pilotQuickComments)
                if(cb.getText().toString().equals(value))
                    pilotQuickCommentSelections.put(value, cb.isChecked());

        String newPilotComment = "";

        for(int i = 0; i < pilotComment.length(); i++) {
            if(pilotComment.charAt(i)!=',')
                newPilotComment += pilotComment.charAt(i);
            else newPilotComment+=';';
        }

        setPilotComment(newPilotComment);

        String newRobotComment = "";

        for(int i = 0; i < robotComment.length(); i++) {
            if(robotComment.charAt(i)==','||robotComment.charAt(i)=='\n') //prevent csv problems
                newRobotComment+=";";
            else newRobotComment += robotComment.charAt(i);
        }

        setRobotComment(newRobotComment);

    }



}



