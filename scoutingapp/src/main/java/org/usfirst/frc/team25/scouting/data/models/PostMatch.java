package org.usfirst.frc.team25.scouting.data.models;

import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Qualitative reflection on the robot's performance after a match
 * Not to be used for end game actions
 */
public class PostMatch {


    private int teamOneCompare;
    private int teamTwoCompare;
    private int pickNumber;
    private String comparison;
    private HashMap<String, Boolean> robotQuickCommentSelections;
    private transient String[] robotQuickCommentValues;
    private String robotComment;
    private String focus;
    private transient ArrayList<CheckBox> robotQuickComments;

    public PostMatch(String robotComment, ArrayList<CheckBox> robotQuickComments,
                     String[] robotQuickCommentValues, String focus, int teamOneCompare,
                     int teamTwoCompare, String comparison, int pickNumber) {
        this.robotComment = robotComment;
        this.robotQuickComments = robotQuickComments;
        this.robotQuickCommentValues = robotQuickCommentValues;
        this.focus = focus;
        this.teamOneCompare = teamOneCompare;
        this.teamTwoCompare = teamTwoCompare;
        this.comparison = comparison;
        robotQuickCommentSelections = new HashMap<>();
        this.pickNumber = pickNumber;

    }

    public int getTeamOneCompare() {
        return teamOneCompare;
    }

    public int getTeamTwoCompare() {
        return teamTwoCompare;
    }

    public int getPickNumber() {
        return pickNumber;
    }

    public String getComparison() {
        return comparison;
    }

    public HashMap<String, Boolean> getRobotQuickCommentSelections() {
        return robotQuickCommentSelections;
    }

    public String[] getRobotQuickCommentValues() {
        return robotQuickCommentValues;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getRobotComment() {
        return robotComment;
    }

    private void setRobotComment(String robotComment) {
        this.robotComment = robotComment;
    }


    public ArrayList<CheckBox> getRobotQuickComments() {
        return robotQuickComments;
    }

    public void setRobotQuickComments(ArrayList<CheckBox> robotQuickComments) {
        this.robotQuickComments = robotQuickComments;
    }


    public void finalizeComment() {

        for (String value : robotQuickCommentValues) {
            for (CheckBox cb : robotQuickComments) {
                if (cb.getText().toString().equals(value)) {
                    robotQuickCommentSelections.put(value, cb.isChecked());
                }
            }
        }

        StringBuilder newRobotComment = new StringBuilder();

        for (int i = 0; i < robotComment.length(); i++) {
            if (robotComment.charAt(i) == ',' || robotComment.charAt(i) == '\n') //prevent csv
            // problems
            {
                newRobotComment.append(";");
            } else {
                newRobotComment.append(robotComment.charAt(i));
            }
        }

        setRobotComment(newRobotComment.toString());

    }
}



