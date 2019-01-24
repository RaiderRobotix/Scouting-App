package org.usfirst.frc.team25.scouting.data.models;


/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {

    private final int cargoShipHatches;
    private final int cargoShipCargo;
    private final int rocketCargo;
    private final int rocketHatches;
    private final int hatchesDropped;
    private final int climbsAssisted;
    private final int attemptHabClimbLevel;
    private final int successHabClimbLevel;
    private final boolean attemptHabClimb;
    private final boolean successHabClimb;
    private final boolean otherRobotClimb;
    private final String otherRobotClimbType;

    public TeleOp(int cargoShipHatches, int cargoShipCargo, int rocketCargo, int rocketHatches,
                  int hatchesDropped, int climbsAssisted, int attemptHabClimbLevel,
                  int successHabClimbLevel, boolean attemptHabClimb, boolean successHabClimb,
                  boolean otherRobotClimb, String otherRobotClimbType) {
        this.cargoShipHatches = cargoShipHatches;
        this.cargoShipCargo = cargoShipCargo;
        this.rocketCargo = rocketCargo;
        this.rocketHatches = rocketHatches;
        this.hatchesDropped = hatchesDropped;
        this.climbsAssisted = climbsAssisted;
        this.attemptHabClimbLevel = attemptHabClimbLevel;
        this.successHabClimbLevel = successHabClimbLevel;
        this.attemptHabClimb = attemptHabClimb;
        this.successHabClimb = successHabClimb;
        this.otherRobotClimb = otherRobotClimb;
        this.otherRobotClimbType = otherRobotClimbType;
    }

    public int getCargoShipHatches() {
        return cargoShipHatches;
    }

    public int getCargoShipCargo() {
        return cargoShipCargo;
    }

    public int getRocketCargo() {
        return rocketCargo;
    }

    public int getRocketHatches() {
        return rocketHatches;
    }

    public int getHatchesDropped() {
        return hatchesDropped;
    }

    public int getClimbsAssisted() {
        return climbsAssisted;
    }

    public int getAttemptHabClimbLevel() {
        return attemptHabClimbLevel;
    }

    public int getSuccessHabClimbLevel() {
        return successHabClimbLevel;
    }

    public boolean isAttemptHabClimb() {
        return attemptHabClimb;
    }

    public boolean isSuccessHabClimb() {
        return successHabClimb;
    }

    public boolean isOtherRobotClimb() {
        return otherRobotClimb;
    }

    public String getOtherRobotClimbType() {
        return otherRobotClimbType;
    }
}
