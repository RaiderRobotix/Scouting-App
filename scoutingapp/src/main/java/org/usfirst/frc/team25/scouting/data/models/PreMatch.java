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
    private boolean robotStartBall;
    private int startingLocation;
    private String startingPos;
    private String startingGamePiece;

    public String getScoutName() {
        return scoutName;
    }

    public String getScoutPos() {
        return scoutPos;
    }

    public String getStartingPos() {
        return startingPos;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public int getStartingLevel() {
        return startingLocation;
    }

    public boolean isRobotNoShow() {
        return robotNoShow;
    }

    public boolean isRobotStartBall(){return robotStartBall;}

    public String getStartingGamePiece() {
        return startingGamePiece;
    }

    public PreMatch(String scoutName, String scoutPos, String startingPos, int matchNum,
                    int teamNum, int startingLocation, boolean robotNoShow , boolean robotStartBall ) {
        this.scoutName = scoutName;
        this.scoutPos = scoutPos;
        this.startingPos = startingPos;
        this.matchNum = matchNum;
        this.teamNum = teamNum;
        this.startingLocation = startingLocation;
        this.robotNoShow = robotNoShow;
        this.robotStartBall = robotStartBall;
        //this.startingGamePiece = startingGamePiece;
    }
}
