package org.usfirst.frc.team25.scouting.data.models;


/**
 * Object model for autonomous period of a match
 */
public class Autonomous {

    private boolean crossHabLine;

    private int cargoShipHatches;
    private int rocketHatches;

    private int cargoShipCargo;
    private int rocketCargo;

    private boolean frontCargoShipHatchCapable;
    private boolean sideCargoShipHatchCapable;

    private int RobotCargoPickedup;
    private int RobotCargoScoredUpperHub;
    private int RobotCargoScoredLowerHub;
    private int robotCargoDropped;
    private int humanCargoScored;
    private int humanCargoMissed;

    private boolean hatchesDroppedCargoShip;
    private boolean hatchesDroppedRocket;
    private boolean cargoDroppedCargoShip;
    private boolean cargoDroppedRocket;

    private boolean robotPassTarmac;


    public Autonomous(int rocketCargo, int rocketHatches, int cargoShipHatches,
                      int cargoShipCargo, int RobotCargoPickedup, int RobotCargoScoredUpperHub,
                      int RobotCargoScoredLowerHub, int robotCargoDropped,int humanCargoScored, int humanCargoMissed,
                      boolean crossHabLine, boolean robotPassTarmac,
                      boolean sideCargoShipHatchCapable, boolean frontCargoShipHatchCapable,
                      boolean cargoDroppedCargoShip, boolean cargoDroppedRocket,
                      boolean hatchesDroppedRocket, boolean hatchesDroppedCargoShip) {
        this.rocketCargo = rocketCargo;
        this.rocketHatches = rocketHatches;
        this.cargoShipHatches = cargoShipHatches;
        this.cargoShipCargo = cargoShipCargo;
        this.RobotCargoPickedup = RobotCargoPickedup;
        this.RobotCargoScoredUpperHub = RobotCargoScoredUpperHub;
        this.RobotCargoScoredLowerHub = RobotCargoScoredLowerHub;
        this.robotCargoDropped = robotCargoDropped;
        this.humanCargoScored = humanCargoScored;
        this.humanCargoMissed = humanCargoMissed;
        this.crossHabLine = crossHabLine;
        this.robotPassTarmac = robotPassTarmac;
        this.sideCargoShipHatchCapable = sideCargoShipHatchCapable;
        this.frontCargoShipHatchCapable = frontCargoShipHatchCapable;
        this.cargoDroppedCargoShip = cargoDroppedCargoShip;
        this.cargoDroppedRocket = cargoDroppedRocket;
        this.hatchesDroppedRocket = hatchesDroppedRocket;
        this.hatchesDroppedCargoShip = hatchesDroppedCargoShip;
    }

    public int getRocketCargo() {
        return rocketCargo;
    }

    public int getRocketHatches() {
        return rocketHatches;
    }

    public int getCargoShipHatches() {
        return cargoShipHatches;
    }

    public int getCargoShipCargo() {
        return cargoShipCargo;
    }

    public int getRobotCargoPickedup() {
        return RobotCargoPickedup;
    }

    public int getRobotCargoScoredUpperHub() {
        return RobotCargoScoredUpperHub;
    }

    public int getRobotCargoScoredLowerHub() {
        return RobotCargoScoredLowerHub;
    }

    public int getRobotCargoDropped() {
        return robotCargoDropped;
    }

    public int getHumanCargoScored() {
        return humanCargoScored;
    }

    public int getHumanCargoMissed() {
        return humanCargoMissed;
    }

    public boolean isCrossHabLine() {
        return crossHabLine;
    }

    public boolean isRobotPassTarmac() {
        return robotPassTarmac;
    }

    public boolean isSideCargoShipHatchCapable() {
        return sideCargoShipHatchCapable;
    }

    public boolean isFrontCargoShipHatchCapable() {
        return frontCargoShipHatchCapable;
    }

    public boolean isCargoDroppedCargoShip() {
        return cargoDroppedCargoShip;
    }

    public boolean isCargoDroppedRocket() {
        return cargoDroppedRocket;
    }

    public boolean isHatchesDroppedRocket() {
        return hatchesDroppedRocket;
    }

    public boolean isHatchesDroppedCargoShip() {
        return hatchesDroppedCargoShip;
    }
}
