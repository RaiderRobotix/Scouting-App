package org.usfirst.frc.team25.scouting.data.models

/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
data class TeleOp(val cargoShipHatches: Int,
                  val rocketLevelOneHatches: Int,
                  val rocketLevelTwoHatches: Int,
                  val rocketLevelThreeHatches: Int, val cargoShipCargo: Int, val rocketLevelOneCargo: Int,
                  val rocketLevelTwoCargo: Int, val rocketLevelThreeCargo: Int, val hatchesDropped: Int,
                  val cargoDropped: Int, val isAttemptHabClimb: Boolean, val attemptHabClimbLevel: Int,
                  val isSuccessHabClimb: Boolean, val successHabClimbLevel: Int,
                  val isClimbAssistedByPartner: Boolean, val assistingClimbTeamNum: Int,
                  val numPartnerClimbAssists: Int, val partnerClimbAssistEndLevel: Int,
                  val partnerClimbAssistStartLevel: Int)