package org.usfirst.frc.team25.scouting.data.models;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * General information about a match and scout before it begins
 */
@Data
@AllArgsConstructor
public class PreMatch {

    private String scoutName;
    private String scoutPos;

    private String startingPos;
    private int startingLevel;

    private int matchNum;
    private int teamNum;

    private boolean robotNoShow;
    private String startingGamePiece;

}
