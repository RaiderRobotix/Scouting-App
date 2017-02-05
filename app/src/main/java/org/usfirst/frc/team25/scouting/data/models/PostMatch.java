package org.usfirst.frc.team25.scouting.data.models;

import android.widget.CheckBox;


import java.util.ArrayList;

/**
 * Created by sng on 6/30/2016.
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
    }

    String robotComment, pilotComment;


    public transient ArrayList<CheckBox> robotQuickComments, pilotQuickComments;

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



    transient String[] robotQuickCommentValues, pilotQuickCommentValues;





    public void finalizeComment(){

        for(int i = 0; i < robotQuickComments.size(); i++)
            if(robotQuickComments.get(i).isChecked()){
            	if(!robotComment.equals(""))
            		robotComment +=";";
                robotComment += robotQuickCommentValues[i];
            }

        for(int i = 0; i < pilotQuickComments.size(); i++)
            if(pilotQuickComments.get(i).isChecked()){
                if(!pilotComment.equals(""))
                    pilotComment +=";";
                pilotComment += pilotQuickCommentValues[i];
            }
    }



}



