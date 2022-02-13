package org.usfirst.frc.team25.scouting.data.models;


import org.usfirst.frc.team25.scouting.R;

/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {

    /*private int cargoShipHatches;
    private int rocketLevelOneHatches;
    private int rocketLevelTwoHatches;
    private int rocketLevelThreeHatches;
    private int cargoShipCargo;
    private int rocketLevelOneCargo;
    private int rocketLevelTwoCargo;
    private int rocketLevelThreeCargo;

    private int hatchesDropped;
    private int cargoDropped;

    private boolean attemptHabClimb;
    private int attemptHabClimbLevel;
    private boolean successHabClimb;
    private int successHabClimbLevel;
    private boolean climbAssistedByPartner;
    private int assistingClimbTeamNum;
    private int numPartnerClimbAssists;
    private int partnerClimbAssistStartLevel;
    private int partnerClimbAssistEndLevel;*/

    private int humanCargoScored;
    private int humanCargoMissed;
    private int robotCargoPickedUp;
    private int robotCargoScoredUpperHub;
    private int robotCargoScoredLowerHub;
    private int robotCargoDropped;
    private int successRungClimbLevel;
    private int attemptRungClimbLevel;
    private int climbTime;

    private boolean attemptRungClimb;
    private boolean successRungClimb;



    public TeleOp(int robotCargoPickedUp , int robotCargoScoredUpperHub , int robotCargoScoredLowerHub ,
                  int robotCargoDropped , int successRungClimbLevel ,
                  boolean attemptRungClimb , int attemptRungClimbLevel , int climbTime) {
       this.robotCargoPickedUp = robotCargoPickedUp;
       this.robotCargoScoredUpperHub = robotCargoScoredUpperHub;
       this.robotCargoScoredLowerHub = robotCargoScoredLowerHub;
       this.robotCargoDropped = robotCargoDropped;
       this.attemptRungClimbLevel = attemptRungClimbLevel;
       this.successRungClimbLevel = successRungClimbLevel;
       this.attemptRungClimb = attemptRungClimb;
       this.successRungClimb = successRungClimb;
       this.climbTime = climbTime;
    }

    public int getClimbTime(){return climbTime;}
    public int getRobotCargoPickedUp() {
        return robotCargoPickedUp;
    }

    public int getRobotCargoScoredUpperHub() {
        return robotCargoScoredUpperHub;
    }

    public int getRobotCargoScoredLowerHub() {
        return robotCargoScoredLowerHub;
    }

    public int getRobotCargoDropped() {
        return robotCargoDropped;
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

    public boolean isSuccessRungClimb() {
        return successRungClimb;
    }
}
