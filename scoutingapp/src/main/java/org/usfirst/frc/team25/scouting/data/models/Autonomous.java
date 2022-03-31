package org.usfirst.frc.team25.scouting.data.models;


/**
 * Object model for autonomous period of a match
 */
public class Autonomous {

    private int RobotCargoScoredUpperHub;
    private int RobotCargoScoredLowerHub;

    private int humanCargoScored;


    private boolean robotPassTarmac;
    private boolean robotCommitFoul;


    public Autonomous(  int RobotCargoScoredUpperHub,
                      int RobotCargoScoredLowerHub,int humanCargoScored,
                      boolean robotPassTarmac, boolean robotCommitFoul) {

        this.robotCommitFoul = robotCommitFoul;

        this.RobotCargoScoredUpperHub = RobotCargoScoredUpperHub;
        this.RobotCargoScoredLowerHub = RobotCargoScoredLowerHub;

        this.humanCargoScored = humanCargoScored;

        this.robotPassTarmac = robotPassTarmac;
    }

    public boolean isRobotCommitFoul() {
        return robotCommitFoul;
    }

    public int getRobotCargoScoredUpperHub() {
        return RobotCargoScoredUpperHub;
    }

    public int getRobotCargoScoredLowerHub() {
        return RobotCargoScoredLowerHub;
    }

    public int getHumanCargoScored() {
        return humanCargoScored;
    }

    public boolean isRobotPassTarmac() {
        return robotPassTarmac;
    }
}
