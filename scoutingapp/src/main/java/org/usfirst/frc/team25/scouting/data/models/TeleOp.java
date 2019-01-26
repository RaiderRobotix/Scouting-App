package org.usfirst.frc.team25.scouting.data.models;


/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {

    private final int CargoShipHatchesTeleop;
    private final int CargoShipCargoTeleop;
    private final int RocketCargoTeleop;
    private final int RocketHatchesTeleop;
    private final int HatchesDroppedTeleop;
    private final int climbsAssisted;
    private final int attemptHabClimbLevel;
    private final int successHabClimbLevel;
    private final boolean attemptHabClimb;
    private final boolean successHabClimb;
    private final boolean otherRobotClimb;
    private final String otherRobotClimbType;

    public TeleOp(int CargoShipHatchesTeleop, int CargoShipCargoTeleop, int RocketCargoTeleop,
                  int RocketHatchesTeleop,
                  int HatchesDroppedTeleop, int climbsAssisted, int attemptHabClimbLevel,
                  int successHabClimbLevel, boolean attemptHabClimb, boolean successHabClimb,
                  boolean otherRobotClimb, String otherRobotClimbType) {
        this.CargoShipHatchesTeleop = CargoShipHatchesTeleop;
        this.CargoShipCargoTeleop = CargoShipCargoTeleop;
        this.RocketCargoTeleop = RocketCargoTeleop;
        this.RocketHatchesTeleop = RocketHatchesTeleop;
        this.HatchesDroppedTeleop = HatchesDroppedTeleop;
        this.climbsAssisted = climbsAssisted;
        this.attemptHabClimbLevel = attemptHabClimbLevel;
        this.successHabClimbLevel = successHabClimbLevel;
        this.attemptHabClimb = attemptHabClimb;
        this.successHabClimb = successHabClimb;
        this.otherRobotClimb = otherRobotClimb;
        this.otherRobotClimbType = otherRobotClimbType;
    }

    public int getCargoShipHatchesTeleop() {
        return CargoShipHatchesTeleop;
    }

    public int getCargoShipCargoTeleop() {
        return CargoShipCargoTeleop;
    }

    public int getRocketCargoTeleop() {
        return RocketCargoTeleop;
    }

    public int getRocketHatchesTeleop() {
        return RocketHatchesTeleop;
    }

    public int getHatchesDroppedTeleop() {
        return HatchesDroppedTeleop;
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
