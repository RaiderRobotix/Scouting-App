package org.usfirst.frc.team25.scouting.data.models;

import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Endgame Tasks
 */
public class EndGame {

    private boolean park;
    private boolean deepBarge;
    private boolean shallowBarge;


    public EndGame(int hps, int trap, boolean park, boolean deepBarge, boolean shallowBarge) {
        this.park = park;
        this.deepBarge = deepBarge;
        this.shallowBarge = shallowBarge;

    }

    public boolean isPark() {return park; }

    public boolean isDeepBarge() {return deepBarge; }

    public boolean isShallowBarge() {return shallowBarge; }





}



