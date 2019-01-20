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
    private final boolean parked;
    private final boolean attemptRungClimb;
    private final boolean successfulRungClimb;
    private final boolean otherRobotClimb;
    private final String otherRobotClimbType;


    public TeleOp(int cargoShipHatches, int rocketHatches, int rocketCargo,
                  int cargoShipCargo, int hatchesDropped, int climbsAssisted, boolean parked,
                  boolean attemptRungClimb, boolean successfulRungClimb, boolean otherRobotClimb,
                  String otherRobotClimbType) {

        this.cargoShipHatches = cargoShipHatches;
        this.cargoShipCargo = cargoShipCargo;
        this.rocketCargo = rocketCargo;
        this.rocketHatches = rocketHatches;
        this.hatchesDropped = hatchesDropped;
        this.climbsAssisted = climbsAssisted;
        this.parked = parked;
        this.attemptRungClimb = attemptRungClimb;
        this.successfulRungClimb = successfulRungClimb;
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

    public boolean isParked() {
        return parked;
    }

    public boolean isAttemptRungClimb() {
        return attemptRungClimb;
    }

    public boolean isSuccessfulRungClimb() {
        return successfulRungClimb;
    }

    public boolean isOtherRobotClimb() {
        return otherRobotClimb;
    }

    public String getOtherRobotClimbType() {
        return otherRobotClimbType;
    }


}
