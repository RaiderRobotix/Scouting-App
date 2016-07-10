package org.usfirst.frc.team25.scouting.data;

import android.widget.CheckBox;


import java.util.ArrayList;

/**
 * Created by sng on 6/30/2016.
 */
public class PostMatch {

    String comment;


    public transient ArrayList<CheckBox> quickComments;

    final transient  String[] quickCommentValues = {
            "did not do much",
            "accurate shooter",
            "lost communications",
            "flipped over",
            "helped teammates",
            "defense",
            "good human player",
            "good driver",
            "efficient",
            "inefficient",
            "good pick",
            "bad pick",
            "to be replayed",
            "INCORRECT DATA"
    };

    public PostMatch(String comment, ArrayList<CheckBox> quickComments) {
        this.comment = comment;
        this.quickComments = quickComments;
    }

    public void finalizeComment() throws RuntimeException{
        // CheckBoxes added in sequential order
        // Specific identifiers not necessary since we only want their state, which will not be saved

        if(quickComments.size()!=quickCommentValues.length)
            throw new RuntimeException("String values must be set for each Quick Comment in PostMatch");

        for(int i = 0; i < quickComments.size(); i++)
            if(quickComments.get(i).isChecked())
                comment+= "; " + quickCommentValues[i];
    }

    public String getComment() {
        return comment;
    }


    public ArrayList<CheckBox> getQuickComments() {
        return quickComments;
    }

    public PostMatch(){
        //Default empty constructor for Jackson JSON parsing
    }

}



