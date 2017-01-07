package org.usfirst.frc.team25.scouting.data.models;

import android.widget.CheckBox;


import java.util.ArrayList;

/** Qualitative reflection on the robot's performance after a match
 *  Not to be used for end game actions
 */
public class PostMatch {

    String comment;


    private transient ArrayList<CheckBox> quickComments;


    /** A list of short human-readable descriptions corresponding to
     *  CheckBox objects in the XML layout file of the post match fragment
     */
    //Transient means the data is not serialized
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

        //Appends the quick comments to the long form comment
        for(int i = 0; i < quickComments.size(); i++)
            if(quickComments.get(i).isChecked()){
            	if(!comment.equals(""))
            		comment+=";";
                comment+= quickCommentValues[i];
            }
    }

    public String getComment() {
        return comment;
    }

    //Used to reinitialize PostMatchFragment
    public ArrayList<CheckBox> getQuickComments() {
        return quickComments;
    }

    public PostMatch(){
        //Default empty constructor for JSON parsing
    }

}



