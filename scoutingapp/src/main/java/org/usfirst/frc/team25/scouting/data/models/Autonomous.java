package org.usfirst.frc.team25.scouting.data.models;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Object model for autonomous period of a match
 */
@Data
@AllArgsConstructor
public class Autonomous {

    private int rocketCargo;
    private int rocketHatches;
    private int cargoShipCargo;
    private int cargoShipHatches;

    private boolean cargoDroppedRocket;
    private boolean hatchesDroppedRocket;
    private boolean cargoDroppedCargoShip;
    private boolean hatchesDroppedCargoShip;

    private int cargoDropped;
    private int hatchesDropped;

    private boolean frontCargoShipHatchCapable;
    private boolean sideCargoShipHatchCapable;

    private boolean crossHabLine;
    private boolean opponentCargoShipLineFoul;

}
