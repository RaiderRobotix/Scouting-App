package org.usfirst.frc.team25.scouting.data.models;


/** Object model for autonomous period of a match
 *
 */
public class Autonomous {

    public Autonomous(boolean baselineCrossed, boolean useHoppers, int highGoals, int lowGoals, int rotorsStarted, int gearsDelivered, boolean shootsFromKey) {
        this.baselineCrossed = baselineCrossed;
        this.useHoppers = useHoppers;
        this.highGoals = highGoals;
        this.lowGoals = lowGoals;
        this.rotorsStarted = rotorsStarted;
        this.gearsDelivered = gearsDelivered;
        this.shootsFromKey = shootsFromKey;
    }

    public boolean isBaselineCrossed() {
        return baselineCrossed;
    }

    public void setBaselineCrossed(boolean baselineCrossed) {
        this.baselineCrossed = baselineCrossed;
    }

    public boolean isUseHoppers() {
        return useHoppers;
    }

    public void setUseHoppers(boolean useHoppers) {
        this.useHoppers = useHoppers;
    }

    public int getHighGoals() {
        return highGoals;
    }

    public void setHighGoals(int highGoals) {
        this.highGoals = highGoals;
    }

    public int getLowGoals() {
        return lowGoals;
    }

    public void setLowGoals(int lowGoals) {
        this.lowGoals = lowGoals;
    }

    public int getRotorsStarted() {
        return rotorsStarted;
    }

    public void setRotorsStarted(int rotorsStarted) {
        this.rotorsStarted = rotorsStarted;
    }

    public int getGearsDelivered() {
        return gearsDelivered;
    }

    public void setGearsDelivered(int gearsDelivered) {
        this.gearsDelivered = gearsDelivered;
    }

    boolean baselineCrossed;
    boolean useHoppers;

    public boolean isShootsFromKey() {
        return shootsFromKey;
    }

    public void setShootsFromKey(boolean shootsFromKey) {
        this.shootsFromKey = shootsFromKey;
    }

    boolean shootsFromKey;
    int highGoals, lowGoals, rotorsStarted, gearsDelivered;



}
