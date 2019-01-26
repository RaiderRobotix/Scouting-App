package org.usfirst.frc.team25.scouting.data.models;


/**
 * Object model for autonomous period of a match
 */
public class Autonomous {

    private final int cargoToRocket;
    private final int hatchPanelsToRocket;
    private final int hatchesToCargoShip;
    private final int cargoToCargoShip;
    private final int hatchesDropped;
    private final int cargoDropped;
    private final boolean reachHabLine;
    private final boolean opponentCargoShipLineFoul;
    private final boolean hatchesSideCargo;
    private final boolean hatchesFrontCargo;

    public int getCargoToRocket() {
        return cargoToRocket;
    }

    public int getHatchPanelsToRocket() {
        return hatchPanelsToRocket;
    }

    public int getHatchesToCargoShip() {
        return hatchesToCargoShip;
    }

    public int getCargoToCargoShip() {
        return cargoToCargoShip;
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

    public boolean isHatchesSideCargo() {
        return hatchesSideCargo;
    }

    public boolean isHatchesFrontCargo() {
        return hatchesFrontCargo;
    }

    public Autonomous(int cargoToRocket, int hatchPanelsToRocket, int hatchesToCargoShip,
                      int cargoToCargoShip, int hatchesDropped, int cargoDropped,
                      boolean reachHabLine, boolean opponentCargoShipLineFoul,
                      boolean hatchesSideCargo, boolean hatchesFrontCargo) {
        this.cargoToRocket = cargoToRocket;
        this.hatchPanelsToRocket = hatchPanelsToRocket;
        this.hatchesToCargoShip = hatchesToCargoShip;
        this.cargoToCargoShip = cargoToCargoShip;
        this.hatchesDropped = hatchesDropped;
        this.cargoDropped = cargoDropped;
        this.reachHabLine = reachHabLine;
        this.opponentCargoShipLineFoul = opponentCargoShipLineFoul;
        this.hatchesSideCargo = hatchesSideCargo;
        this.hatchesFrontCargo = hatchesFrontCargo;
    }
}
