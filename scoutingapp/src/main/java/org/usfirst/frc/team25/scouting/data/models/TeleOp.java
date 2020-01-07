package org.usfirst.frc.team25.scouting.data.models;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
@Data
@AllArgsConstructor
public class TeleOp {

    private int cargoShipHatches;
    private int rocketLevelOneHatches;
    private int rocketLevelTwoHatches;
    private int rocketLevelThreeHatches;
    private int cargoShipCargo;
    private int rocketLevelOneCargo;
    private int rocketLevelTwoCargo;
    private int rocketLevelThreeCargo;

    private int hatchesDropped;
    private int cargoDropped;

    private boolean attemptHabClimb;
    private int attemptHabClimbLevel;
    private boolean successHabClimb;
    private int successHabClimbLevel;
    private boolean climbAssistedByPartner;
    private int assistingClimbTeamNum;
    private int numPartnerClimbAssists;
    private int partnerClimbAssistStartLevel;
    private int partnerClimbAssistEndLevel;

}
