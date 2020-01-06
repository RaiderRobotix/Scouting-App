package org.usfirst.frc.team25.scouting.data.models

/**
 * Object model for autonomous period of a match
 */
data class Autonomous(val rocketCargo: Int, val rocketHatches: Int, val cargoShipHatches: Int,
                 val cargoShipCargo: Int, val hatchesDropped: Int, val cargoDropped: Int,
                 val crossHabLine: Boolean, val opponentCargoShipLineFoul: Boolean,
                 val sideCargoShipHatchCapable: Boolean, val frontCargoShipHatchCapable: Boolean,
                 val cargoDroppedCargoShip: Boolean, val cargoDroppedRocket: Boolean,
                 val hatchesDroppedRocket: Boolean, val hatchesDroppedCargoShip: Boolean)