package org.usfirst.frc.team25.scouting.data.models;


/**
 * Object model for autonomous period of a match
 */
public class Autonomous {

    private int rocketCargo;
    private int rocketHatches;
    private int cargoShipHatches;
    private int cargoShipCargo;
    private int hatchesDropped;
    private int cargoDropped;
    private boolean reachHabLine;
    private boolean opponentCargoShipLineFoul;
    private boolean sideCargoShipHatchCapable;
    private boolean frontCargoShipHatchCapable;

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

    public int getHatchesDropped() {
        return hatchesDropped;
    }

    public int getCargoDropped() {
        return cargoDropped;
    }

    public boolean isReachHabLine() {
        return reachHabLine;
    }

    public boolean isOpponentCargoShipLineFoul() {
        return opponentCargoShipLineFoul;
    }

    public boolean isSideCargoShipHatchCapable() {
        return sideCargoShipHatchCapable;
    }

    public boolean isFrontCargoShipHatchCapable() {
        return frontCargoShipHatchCapable;
    }





    public Autonomous(int rocketCargo, int rocketHatches, int cargoShipHatches,
                      int cargoShipCargo, int hatchesDropped, int cargoDropped,
                      boolean reachHabLine, boolean opponentCargoShipLineFoul,
                      boolean sideCargoShipHatchCapable, boolean frontCargoShipHatchCapable) {
        this.rocketCargo = rocketCargo;
        this.rocketHatches = rocketHatches;
        this.cargoShipHatches = cargoShipHatches;
        this.cargoShipCargo = cargoShipCargo;
        this.hatchesDropped = hatchesDropped;
        this.cargoDropped = cargoDropped;
        this.reachHabLine = reachHabLine;
        this.opponentCargoShipLineFoul = opponentCargoShipLineFoul;
        this.sideCargoShipHatchCapable = sideCargoShipHatchCapable;
        this.frontCargoShipHatchCapable = frontCargoShipHatchCapable;
    }
}
