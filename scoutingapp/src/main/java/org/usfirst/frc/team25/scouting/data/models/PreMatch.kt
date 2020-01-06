package org.usfirst.frc.team25.scouting.data.models

/**
 * General information about a match and scout before it begins
 */
data class PreMatch(val scoutName: String, val scoutPos: String, val startingPos: String, val matchNum: Int,
               val teamNum: Int, val startingLevel: Int, val isRobotNoShow: Boolean, val startingGamePiece: String)