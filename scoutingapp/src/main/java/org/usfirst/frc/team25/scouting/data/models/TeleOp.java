package org.usfirst.frc.team25.scouting.data.models;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
@Data
@AllArgsConstructor
public class TeleOp {

    private int cellsScoredBottom;
    private int cellsScoredInner;
    private int cellsScoredOuter;
    private int cellsDropped;
    private int assistedClimbs;

    private int assistingClimbTeamNum;

    private boolean rungLevel;
    private boolean attemptedClimb;
    private boolean successClimb;
    private boolean rotationControl;
    private boolean positionControl;
    private boolean rotationOverspun;
}
