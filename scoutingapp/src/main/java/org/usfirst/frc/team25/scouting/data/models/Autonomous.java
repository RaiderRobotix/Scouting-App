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
    private boolean cargoDroppedCargoShip;
    private boolean cargoDroppedRocket;
    private boolean hatchesDroppedRocket;
    private boolean hatchesDroppedCargoShip;

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
                      boolean sideCargoShipHatchCapable, boolean frontCargoShipHatchCapable,
                      boolean cargoDroppedCargoShip, boolean cargoDroppedRocket,
                      boolean hatchesDroppedRocket, boolean hatchesDroppedCargoShip) {
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
        this.cargoDroppedCargoShip = cargoDroppedCargoShip;
        this.cargoDroppedRocket = cargoDroppedRocket;
        this.hatchesDroppedRocket = hatchesDroppedRocket;
        this.hatchesDroppedCargoShip = hatchesDroppedCargoShip;
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
