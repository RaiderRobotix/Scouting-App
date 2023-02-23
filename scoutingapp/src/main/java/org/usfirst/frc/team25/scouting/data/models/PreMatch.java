package org.usfirst.frc.team25.scouting.data.models;


/**
 * General information about a match and scout before it begins
 */
public class PreMatch {

    private String scoutName;
    private int matchNum;
    private String scoutPos;
    private int teamNum;
    private boolean robotNoShow;
    private String startingGamePiece;

    public String getScoutName() {
        return scoutName;
    }

    public String getScoutPos() {
        return scoutPos;
    }


    public int getMatchNum() {
        return matchNum;
    }

    public int getTeamNum() {
        return teamNum;
    }


    public boolean isRobotNoShow() {
        return robotNoShow;
    }

    public String getStartingGamePiece() {
        return startingGamePiece;
    }

    public PreMatch(String scoutName, String scoutPos, int matchNum,
                    int teamNum, boolean robotNoShow, String startingGamePiece) {
        this.scoutName = scoutName;
        this.scoutPos = scoutPos;
        this.matchNum = matchNum;
        this.teamNum = teamNum;
        this.robotNoShow = robotNoShow;
        this.startingGamePiece = startingGamePiece;
    }
}
