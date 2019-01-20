package org.usfirst.frc.team25.scouting.data.models;


/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {

    private transient final float cycleTime;
    private final int cargoShipHatches;
    private final int scaleCubes;
    private final int rocketCargo;
    private final int exchangeCubes;
    private final int cubesDropped;
    private final int climbsAssisted;
    private final boolean parked;
    private final boolean attemptRungClimb;
    private final boolean successfulRungClimb;
    private final boolean otherRobotClimb;
    private final String otherRobotClimbType;

    public TeleOp(float cycleTime, int cargoShipHatches, int scaleCubes, int rocketCargo,
                  int exchangeCubes, int cubesDropped, int climbsAssisted, boolean parked,
                  boolean attemptRungClimb, boolean successfulRungClimb, boolean otherRobotClimb,
                  String otherRobotClimbType) {
        this.cycleTime = cycleTime;
        this.cargoShipHatches = cargoShipHatches;
        this.scaleCubes = scaleCubes;
        this.rocketCargo = rocketCargo;
        this.exchangeCubes = exchangeCubes;
        this.cubesDropped = cubesDropped;
        this.climbsAssisted = climbsAssisted;
        this.parked = parked;
        this.attemptRungClimb = attemptRungClimb;
        this.successfulRungClimb = successfulRungClimb;
        this.otherRobotClimb = otherRobotClimb;
        this.otherRobotClimbType = otherRobotClimbType;
    }


    public float getCycleTime() {
        return cycleTime;
    }

    public int getCargoShipHatches() {
        return cargoShipHatches;
    }

    public int getScaleCubes() {
        return scaleCubes;
    }

    public int getRocketCargo() {
        return rocketCargo;
    }

    public int getExchangeCubes() {
        return exchangeCubes;
    }

    public int getCubesDropped() {
        return cubesDropped;
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
