package org.usfirst.frc.team25.scouting.data.models;


/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {

    private final int cargoShipHatches;
    private final int cargoShipCargo;

    private final int rocketLevelOneCargo;
    private final int rocketLevelOneHatches;
    private final int rocketLevelTwoCargo;
    private final int rocketLevelTwoHatches;
    private final int rocketLevelThreeCargo;
    private final int rocketLevelThreeHatches;

    private final int hatchesDropped;
    private final int cargoDropped;

    private final int climbsAssisted;
    private final int attemptHabClimbLevel;
    private final int successHabClimbLevel;
    private final boolean attemptHabClimb;
    private final boolean successHabClimb;
    private final boolean otherRobotClimb;
    private final String otherRobotClimbType;

}
