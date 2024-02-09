package org.usfirst.frc.team25.scouting.data.models;

import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Qualitative reflection on the robot's performance after a match
 * Not to be used for end game actions
 */
public class PostMatch {


    private int allianceScore;
    private boolean winRanking;
    private boolean melody;
    private boolean ensemble;
    private int defensive;
    private int speed;
    private int manuver;
    private int hpEfficiency;
    private int ranking;
    private boolean lostComms;

    public PostMatch(int allianceScore, boolean winRanking, boolean melody, boolean ensemble, boolean lostComms, int defensive, int speed, int manuver, int hpEfficiency, int ranking) {
        this.allianceScore = allianceScore;
        this.winRanking = winRanking;
        this.melody = melody;
        this.ensemble = ensemble;
        this.lostComms = lostComms;
        this.defensive = defensive;
        this.speed = speed;
        this.manuver = manuver;
        this.hpEfficiency = hpEfficiency;
        this.ranking = ranking;
    }

    public int getAllianceScore() {
        return allianceScore;
    }
    public boolean isWinRanking() {return winRanking;}
    public boolean isMelody() {
        return melody;
    }
    public boolean isEnsemble() {
        return ensemble;
    }
    public boolean isLostComms() {return lostComms;}
    public int getDefensive() {
        return defensive;
    }
    public int getSpeed() {
        return speed;
    }
    public int getManuver() {
        return manuver;
    }
    public int getHpEfficiency() {
        return hpEfficiency;
    }
    public int getRanking() {return ranking; }

}



