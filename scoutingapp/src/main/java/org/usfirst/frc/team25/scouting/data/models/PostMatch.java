package org.usfirst.frc.team25.scouting.data.models;

import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Qualitative reflection on the robot's performance after a match
 * Not to be used for end game actions
 */
@Data
@AllArgsConstructor
public class PostMatch {

    private final HashMap<String, Boolean> robotQuickCommentSelections = new HashMap<>();
    private String robotComment;
    private transient ArrayList<CheckBox> robotQuickComments;
    private transient String[] robotQuickCommentValues;
    private String focus;
    private int teamOneCompare;
    private int teamTwoCompare;
    private String comparison;
    private int pickNumber;

    public void finalizeComment() {

        for (String value : robotQuickCommentValues) {
            for (CheckBox cb : robotQuickComments) {
                if (value.equals(cb.getText().toString())) {
                    robotQuickCommentSelections.put(value, cb.isChecked());
                }
            }
        }

        setRobotComment(
                getRobotComment()
                        .replaceAll("[,\n]", ";")
        );

    }
}



