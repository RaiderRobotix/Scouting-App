package org.usfirst.frc.team25.scouting.data.models;


import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {

    private int coneTopTele;
    private int cubeTopTele;

    private int coneMidTele;
    private int cubeMidTele;

    private int coneBttmTele;
    private int cubeBttmTele;

    private int coneDroppedTele;
    private int cubeDroppedTele;

    private boolean dockAttemptTele;
    private String dockStatusTele;

    private boolean robotCommitedFoulTele;
    private String foulTypeTele;

    //private HashMap<CheckBox,Boolean> gridVals;
    private ArrayList<Boolean> gridVals;

    public TeleOp(int coneTop, int cubeTop, int coneMid,
                  int cubeMid, int coneBttm, int cubeBttm,
                  int coneDropped, int cubeDropped,
                  boolean dockAttempt, String dockStatus,
                  boolean robotCommitedFoul, String foulType
                  //HashMap<CheckBox,Boolean> gridVals
                  //ArrayList<Boolean> gridVals
    ) {
        this.coneTopTele = coneTop;
        this.cubeTopTele = cubeTop;
        this.coneMidTele = coneMid;
        this.cubeMidTele = cubeMid;
        this.coneBttmTele = coneBttm;
        this.cubeBttmTele = cubeBttm;
        this.coneDroppedTele = coneDropped;
        this.cubeDroppedTele = cubeDropped;
        this.dockAttemptTele = dockAttempt;
        this.dockStatusTele = dockStatus;
        this.robotCommitedFoulTele = robotCommitedFoul;
        this.foulTypeTele = foulType;
        //this.gridVals = gridVals;
    }


    public boolean isDockAttempt() {
        return dockAttemptTele;
    }

    public int getConeBttm() {
        return coneBttmTele;
    }

    public int getConeMid() {
        return coneMidTele;
    }

    public int getConeTop() {
        return coneTopTele;
    }

    public int getConeDropped() {
        return coneDroppedTele;
    }

    public int getCubeBttm() {
        return cubeBttmTele;
    }

    public int getCubeMid() {
        return cubeMidTele;
    }

    public int getCubeTop() {
        return cubeTopTele;
    }

    public int getCubeDropped() {
        return cubeDroppedTele;
    }

    public String getDockStatus() {
        return dockStatusTele;
    }

    public boolean isRobotCommitedFoul() {
        return robotCommitedFoulTele;
    }

    public String getFoulType() {
        return foulTypeTele;
    }

//    public HashMap<CheckBox,Boolean> getGridVals() {
//    return gridVals; }


//    public ArrayList<Boolean> getGridVals() {
//        return gridVals;
//    }
}
