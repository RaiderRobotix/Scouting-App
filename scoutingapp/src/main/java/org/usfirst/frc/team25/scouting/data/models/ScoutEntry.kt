package org.usfirst.frc.team25.scouting.data.models

import java.io.Serializable

/**
 * Container object for all parts of a match
 * To be serialized into JSON
 */
data class ScoutEntry(var preMatch: PreMatch,
                      var autonomous: Autonomous,
                      var teleOp: TeleOp,
                      var postMatch: PostMatch) : Serializable