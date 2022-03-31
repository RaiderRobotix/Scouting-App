package org.usfirst.frc.team25.scouting.data.models;


import org.usfirst.frc.team25.scouting.R;

/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {


    private int robotCargoScoredUpperHub;
    private int robotCargoScoredLowerHub;
    private int successRungClimbLevel;
    private int attemptRungClimbLevel;
    private int climbTime;

    private boolean attemptRungClimb;




    public TeleOp( int robotCargoScoredUpperHub , int robotCargoScoredLowerHub ,
                   int successRungClimbLevel ,
                  boolean attemptRungClimb , int attemptRungClimbLevel , int climbTime) {
       this.robotCargoScoredUpperHub = robotCargoScoredUpperHub;
       this.robotCargoScoredLowerHub = robotCargoScoredLowerHub;
       this.attemptRungClimbLevel = attemptRungClimbLevel;
       this.successRungClimbLevel = successRungClimbLevel;
       this.attemptRungClimb = attemptRungClimb;
       this.climbTime = climbTime;
    }



    public int getRobotCargoScoredUpperHub() {
        return robotCargoScoredUpperHub;
    }

    public int getRobotCargoScoredLowerHub() {
        return robotCargoScoredLowerHub;
    }

    public int getAttemptRungClimbLevel() {
        return attemptRungClimbLevel;
    }

    public int getSuccessRungClimbLevel() {
        return successRungClimbLevel;
    }

    public boolean isAttemptRungClimb() {
        return attemptRungClimb;
    }
}
