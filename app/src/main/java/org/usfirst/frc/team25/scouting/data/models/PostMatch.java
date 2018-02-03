package org.usfirst.frc.team25.scouting.data.models;

import android.widget.CheckBox;


import java.util.ArrayList;
import java.util.HashMap;

/** Qualitative reflection on the robot's performance after a match
 *  Not to be used for end game actions
 */
public class PostMatch {


    public PostMatch(String robotComment, ArrayList<CheckBox> robotQuickComments,
                     String[] robotQuickCommentValues, String focus, int teamOneCompare, int teamTwoCompare, String comparison) {
        this.robotComment = robotComment;
        this.pilotComment = pilotComment;
        this.robotQuickComments = robotQuickComments;
        this.robotQuickCommentValues = robotQuickCommentValues;
        this.focus = focus;
        this.teamOneCompare = teamOneCompare;
        this.teamTwoCompare = teamTwoCompare;
        this.comparison = comparison;
        robotQuickCommentSelections = new HashMap<>();;

    }

    private int teamOneCompare, teamTwoCompare;
    private String comparison;
    private String robotComment;
    private String pilotComment;

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    String focus;
    HashMap<String, Boolean> robotQuickCommentSelections;


    public transient ArrayList<CheckBox> robotQuickComments;
    transient String[] robotQuickCommentValues;

    public String getRobotComment() {
        return robotComment;
    }

    public void setRobotComment(String robotComment) {
        this.robotComment = robotComment;
    }


    public ArrayList<CheckBox> getRobotQuickComments() {
        return robotQuickComments;
    }

    public void setRobotQuickComments(ArrayList<CheckBox> robotQuickComments) {
        this.robotQuickComments = robotQuickComments;
    }


    public void finalizeComment(){

        for(String value : robotQuickCommentValues)
            for(CheckBox cb : robotQuickComments)
                if(cb.getText().toString().equals(value))
                    robotQuickCommentSelections.put(value, cb.isChecked());

        String newRobotComment = "";

        for(int i = 0; i < robotComment.length(); i++) {
            if(robotComment.charAt(i)==','||robotComment.charAt(i)=='\n') //prevent csv problems
                newRobotComment+=";";
            else newRobotComment += robotComment.charAt(i);
        }

        setRobotComment(newRobotComment);

    }



}



