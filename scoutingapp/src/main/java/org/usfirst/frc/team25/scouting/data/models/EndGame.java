package org.usfirst.frc.team25.scouting.data.models;

import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Endgame Tasks
 */
public class EndGame {


    private int hps;
    private int trap;
    private boolean climb;
    private boolean park;
    private boolean harmony;

    private transient ArrayList<CheckBox> robotQuickComments;

    public EndGame(int hps, int trap, boolean climb, boolean park, boolean harmony) {
        this.hps = hps;
        this.trap = trap;
        this.climb = climb;
        this.park = park;
        this.harmony = harmony;

    }

    public int getHps() {return hps; }

    public int getTrap() {return trap; }

    public boolean isClimb() {return climb; }

    public boolean isPark() {return park; }

    public boolean isHarmony() {return harmony; }





}



