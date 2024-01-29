package org.usfirst.frc.team25.scouting.data.models;

import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Qualitative reflection on the robot's performance after a match
 * Not to be used for end game actions
 */
public class PostMatch {


    private int teamOneCompare;
    private int teamTwoCompare;
    private int pickNumber;
    private String comparison;
    private String robotComment;
    private boolean ampFocused;
    private boolean speakerFocused;
    private boolean defenseFocused;
    private boolean onstageFocused;

    private boolean missed;
    private boolean commsLost;
    private boolean tipped;
    private boolean fouls;
    private boolean inaccurateData;
    private boolean slowIntake;
    private boolean aim;
    private boolean coopertition;
    private boolean hanging;
    private boolean fastIntake;

    private transient ArrayList<CheckBox> robotQuickComments;

    public PostMatch(String robotComment, boolean missed, boolean commsLost, boolean tipped, boolean fouls, boolean inaccurateData, boolean slowIntake, boolean aim, boolean coopertition, boolean hanging, boolean fastIntake, boolean ampFocused, boolean speakerFocused, boolean defenseFocused, boolean onstageFocused, int teamOneCompare,
                     int teamTwoCompare, String comparison, int pickNumber) {
        this.robotComment = robotComment;
        this.missed = missed;
        this.commsLost = commsLost;
        this.tipped = tipped;
        this.fouls = fouls;
        this.inaccurateData = inaccurateData;
        this.slowIntake = slowIntake;
        this.aim = aim;
        this.coopertition = coopertition;
        this.hanging = hanging;
        this.fastIntake = fastIntake;
        this.ampFocused = ampFocused;
        this.speakerFocused = speakerFocused;
        this.defenseFocused = defenseFocused;
        this.onstageFocused = onstageFocused;
        this.teamOneCompare = teamOneCompare;
        this.teamTwoCompare = teamTwoCompare;
        this.comparison = comparison;
        this.pickNumber = pickNumber;

    }

    public String getRobotComment() {
        return robotComment;
    }

    public boolean isMissed() {return missed; }

    public boolean isCommsLost() {return commsLost; }

    public boolean isTipped() {return tipped; }

    public boolean isFouls() {return fouls; }

    public boolean isInaccurateData() {return inaccurateData; }

    public boolean isSlowIntake() {return slowIntake; }

    public boolean isAim() {return aim; }

    public boolean isCoopertition() {return coopertition; }

    public boolean isHanging() {return hanging; }

    public boolean isFastIntake() {return fastIntake; }

    public boolean isAmpFocused() {return ampFocused; }

    public boolean isSpeakerFocused() {return speakerFocused; }

    public boolean isDefenseFocused() {return defenseFocused; }

    public boolean isOnstageFocused() {return onstageFocused; }

    public int getTeamOneCompare() {
        return teamOneCompare;
    }

    public int getTeamTwoCompare() {
        return teamTwoCompare;
    }

    public String getComparison() {
        return comparison;
    }

    public int getPickNumber() {
        return pickNumber;
    }


}



