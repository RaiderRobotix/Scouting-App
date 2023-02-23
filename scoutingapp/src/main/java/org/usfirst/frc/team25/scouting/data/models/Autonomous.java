package org.usfirst.frc.team25.scouting.data.models;


/**
 * Object model for autonomous period of a match
 */
public class Autonomous {

    private boolean robotExitCommunity;

    private int coneTop;
    private int cubeTop;

    private int coneMid;
    private int cubeMid;

    private int coneBttm;
    private int cubeBttm;

    private int coneDropped;
    private int cubeDropped;

    private boolean dockAttempt;
    private String dockStatus;






    public Autonomous(int coneTop, int cubeTop, int coneMid,
                      int cubeMid, int coneBttm, int cubeBttm,
                      int coneDropped, int cubeDropped,
                      boolean robotExitCommunity, boolean dockAttempt, String dockStatus) {
        this.coneTop = coneTop;
        this.cubeTop = cubeTop;
        this.coneMid = coneMid;
        this.cubeMid = cubeMid;
        this.coneBttm = coneBttm;
        this.cubeBttm = cubeBttm;
        this.coneDropped = coneDropped;
        this.cubeDropped = cubeDropped;
        this.robotExitCommunity = robotExitCommunity;
        this.dockAttempt = dockAttempt;
        this.dockStatus = dockStatus;
    }

    public boolean isDockAttempt() {
        return dockAttempt;
    }

    public boolean isRobotExitCommunity() {
        return robotExitCommunity;
    }

    public int getConeBttm() {
        return coneBttm;
    }

    public int getConeMid() {
        return coneMid;
    }

    public int getConeTop() {
        return coneTop;
    }

    public int getConeDropped() {
        return coneDropped;
    }

    public int getCubeBttm() {
        return cubeBttm;
    }

    public int getCubeMid() {
        return cubeMid;
    }

    public int getCubeTop() {
        return cubeTop;
    }

    public int getCubeDropped() {
        return cubeDropped;
    }

    public String getDockStatus() {
        return dockStatus;
    }
}
