package org.usfirst.frc.team25.scouting.data.models;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Object model for autonomous period of a match
 */
@Data
@AllArgsConstructor
public class Autonomous {

    private int cellsScoredBottom;
    private int cellsScoredInner;
    private int cellsScoredOuter;
    private int cellPickupRpoint;
    private int cellPickupTrench;
    private int cellsDropped;

    private boolean crossInitLine;
    private boolean crossOpponentSector;

}
