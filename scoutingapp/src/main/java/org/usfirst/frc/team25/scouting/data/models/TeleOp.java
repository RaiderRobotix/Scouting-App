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

    private boolean topRow_leftComm_LeftCone,topRow_leftComm_MidCube, topRow_leftComm_RightCone,topRow_midComm_LeftCone,topRow_midComm_MidCube, topRow_midComm_RightCone,
            topRow_rightComm_LeftCone,topRow_rightComm_MidCube, topRow_rightComm_RightCone,midRow_leftComm_LeftCone,midRow_leftComm_MidCube, midRow_leftComm_RightCone,
            midRow_midComm_LeftCone,midRow_midComm_MidCube, midRow_midComm_RightCone, midRow_rightComm_LeftCone,midRow_rightComm_MidCube, midRow_rightComm_RightCone,
            bttmRow_leftComm_LeftCone,bttmRow_leftComm_MidCube, bttmRow_leftComm_RightCone,bttmRow_midComm_LeftCone,bttmRow_midComm_MidCube, bttmRow_midComm_RightCone,
            bttmRow_rightComm_LeftCone,bttmRow_rightComm_MidCube, bttmRow_rightComm_RightCone;

    private HashMap<CheckBox,Boolean> gridVals;
    //private ArrayList<Boolean> gridVals;

    public TeleOp(int coneTop, int cubeTop, int coneMid, int cubeMid, int coneBttm, int cubeBttm, int coneDropped,
                  int cubeDropped, boolean dockAttempt, String dockStatus, boolean robotCommitedFoul, String foulType
                  ,boolean topRow_leftComm_LeftCone, boolean topRow_leftComm_MidCube, boolean topRow_leftComm_RightCone
            ,boolean topRow_midComm_LeftCone, boolean topRow_midComm_MidCube, boolean topRow_midComm_RightCone
            ,boolean topRow_rightComm_LeftCone, boolean topRow_rightComm_MidCube, boolean topRow_rightComm_RightCone
                  ,boolean midRow_leftComm_LeftCone, boolean midRow_leftComm_MidCube, boolean midRow_leftComm_RightCone
            ,boolean midRow_midComm_LeftCone, boolean midRow_midComm_MidCube, boolean midRow_midComm_RightCone
            ,boolean midRow_rightComm_LeftCone, boolean midRow_rightComm_MidCube, boolean midRow_rightComm_RightCone
            ,boolean bttmRow_leftComm_LeftCone, boolean bttmRow_leftComm_MidCube, boolean bttmRow_leftComm_RightCone
            ,boolean bttmRow_midComm_LeftCone, boolean bttmRow_midComm_MidCube, boolean bttmRow_midComm_RightCone
            ,boolean bttmRow_rightComm_LeftCone, boolean bttmRow_rightComm_MidCube, boolean bttmRow_rightComm_RightCone

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
//        this.gridVals = gridVals;
        this.topRow_leftComm_LeftCone = topRow_leftComm_LeftCone;
        this.topRow_leftComm_MidCube = topRow_leftComm_MidCube;
        this.topRow_leftComm_RightCone = topRow_leftComm_RightCone;
        this.topRow_midComm_LeftCone = topRow_midComm_LeftCone;
        this.topRow_midComm_MidCube = topRow_midComm_MidCube;
        this.topRow_midComm_RightCone = topRow_midComm_RightCone;
        this.topRow_rightComm_LeftCone = topRow_rightComm_LeftCone;
        this.topRow_rightComm_MidCube = topRow_rightComm_MidCube;
        this.topRow_rightComm_RightCone = topRow_rightComm_RightCone;

        this.midRow_leftComm_LeftCone = midRow_leftComm_LeftCone;
        this.midRow_leftComm_MidCube = midRow_leftComm_MidCube;
        this.midRow_leftComm_RightCone = midRow_leftComm_RightCone;
        this.midRow_midComm_LeftCone = midRow_midComm_LeftCone;
        this.midRow_midComm_MidCube = midRow_midComm_MidCube;
        this.midRow_midComm_RightCone = midRow_midComm_RightCone;
        this.midRow_rightComm_LeftCone = midRow_rightComm_LeftCone;
        this.midRow_rightComm_MidCube = midRow_rightComm_MidCube;
        this.midRow_rightComm_RightCone = midRow_rightComm_RightCone;

        this.bttmRow_leftComm_LeftCone = bttmRow_leftComm_LeftCone;
        this.bttmRow_leftComm_MidCube = bttmRow_leftComm_MidCube;
        this.bttmRow_leftComm_RightCone = bttmRow_leftComm_RightCone;
        this.bttmRow_midComm_LeftCone = bttmRow_midComm_LeftCone;
        this.bttmRow_midComm_MidCube = bttmRow_midComm_MidCube;
        this.bttmRow_midComm_RightCone = bttmRow_midComm_RightCone;
        this.bttmRow_rightComm_LeftCone = bttmRow_rightComm_LeftCone;
        this.bttmRow_rightComm_MidCube = bttmRow_rightComm_MidCube;
        this.bttmRow_rightComm_RightCone = bttmRow_rightComm_RightCone;
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

    //public HashMap<CheckBox,Boolean> getGridVals() { return gridVals; }

    public boolean getBttmRow_leftComm_LeftCone() {
        return bttmRow_leftComm_LeftCone;
    }

    public boolean getBttmRow_leftComm_MidCube() {
        return bttmRow_leftComm_MidCube;
    }

    public boolean getBttmRow_leftComm_RightCone() {
        return bttmRow_leftComm_RightCone;
    }

    public boolean getBttmRow_midComm_LeftCone() {
        return bttmRow_midComm_LeftCone;
    }

    public boolean getBttmRow_midComm_MidCube() {
        return bttmRow_midComm_MidCube;
    }

    public boolean getBttmRow_midComm_RightCone() {
        return bttmRow_midComm_RightCone;
    }

    public boolean getBttmRow_rightComm_LeftCone() {
        return bttmRow_rightComm_LeftCone;
    }

    public boolean getBttmRow_rightComm_MidCube() {
        return bttmRow_rightComm_MidCube;
    }

    public boolean getBttmRow_rightComm_RightCone() {
        return bttmRow_rightComm_RightCone;
    }

    public boolean getMidRow_leftComm_LeftCone() {
        return midRow_leftComm_LeftCone;
    }

    public boolean getMidRow_leftComm_MidCube() {
        return midRow_leftComm_MidCube;
    }

    public boolean getMidRow_leftComm_RightCone() {
        return midRow_leftComm_RightCone;
    }

    public boolean getMidRow_midComm_LeftCone() {
        return midRow_midComm_LeftCone;
    }

    public boolean getMidRow_midComm_MidCube() {
        return midRow_midComm_MidCube;
    }

    public boolean getMidRow_midComm_RightCone() {
        return midRow_midComm_RightCone;
    }

    public boolean getMidRow_rightComm_LeftCone() {
        return midRow_rightComm_LeftCone;
    }

    public boolean getMidRow_rightComm_MidCube() {
        return midRow_rightComm_MidCube;
    }

    public boolean getMidRow_rightComm_RightCone() {
        return midRow_rightComm_RightCone;
    }

    public boolean getTopRow_leftComm_LeftCone() {
        return topRow_leftComm_LeftCone;
    }

    public boolean getTopRow_leftComm_MidCube() {
        return topRow_leftComm_MidCube;
    }

    public boolean getTopRow_leftComm_RightCone() {
        return topRow_leftComm_RightCone;
    }

    public boolean getTopRow_midComm_LeftCone() {
        return topRow_midComm_LeftCone;
    }

    public boolean getTopRow_midComm_MidCube() {
        return topRow_midComm_MidCube;
    }

    public boolean getTopRow_midComm_RightCone() {
        return topRow_midComm_RightCone;
    }

    public boolean getTopRow_rightComm_LeftCone() {
        return topRow_rightComm_LeftCone;
    }

    public boolean getTopRow_rightComm_MidCube() {
        return topRow_rightComm_MidCube;
    }

    public boolean getTopRow_rightComm_RightCone() {
        return topRow_rightComm_RightCone;
    }


}

