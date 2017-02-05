package org.usfirst.frc.team25.scouting.data.models;


/** General information about a match and scout before it begins
 *
 */
public class PreMatch {

    public PreMatch(String scoutName, String currentEvent, String scoutPos, int matchNum, int teamNum, boolean pilotPlaying) {
        this.scoutName = scoutName;
        this.currentEvent = currentEvent;
        this.scoutPos = scoutPos;
        this.matchNum = matchNum;
        this.teamNum = teamNum;
        this.pilotPlaying = pilotPlaying;
    }

    String scoutName, currentEvent, scoutPos;
    int matchNum, teamNum;
    boolean pilotPlaying;

    public boolean isPilotPlaying() {
        return pilotPlaying;
    }

    public void setPilotPlaying(boolean pilotPlaying) {
        this.pilotPlaying = pilotPlaying;
    }





    public String getScoutName() {
        return scoutName;
    }

    public String getCurrentEvent() {
        return currentEvent;
    }

    public String getScoutPos() { return scoutPos; }

    public int getMatchNum() {
        return matchNum;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public PreMatch(){
        //Default empty constructor for JSON parsing
    }
}
