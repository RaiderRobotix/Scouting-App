package org.usfirst.frc.team25.scouting.data.models;


import java.io.Serializable;

import lombok.Data;

/**
 * Container object for all parts of a match
 * To be serialized into JSON
 */
@Data
public class ScoutEntry implements Serializable {

    private PreMatch preMatch;
    private Autonomous autonomous;
    private TeleOp teleOp;
    private PostMatch postMatch;

}
