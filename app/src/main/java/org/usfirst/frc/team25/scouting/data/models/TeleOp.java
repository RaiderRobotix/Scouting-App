package org.usfirst.frc.team25.scouting.data.models;


/** Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {


    public int getLowGoals() {
        return lowGoals;
    }

    public void setLowGoals(int lowGoals) {
        this.lowGoals = lowGoals;
    }

    public int getHighGoals() {
        return highGoals;
    }

    public void setHighGoals(int highGoals) {
        this.highGoals = highGoals;
    }

    public int getGearsDelivered() {
        return gearsDelivered;
    }

    public void setGearsDelivered(int gearsDelivered) {
        this.gearsDelivered = gearsDelivered;
    }

    public int getHopppersUsed() {
        return hopppersUsed;
    }

    public void setHopppersUsed(int hopppersUsed) {
        this.hopppersUsed = hopppersUsed;
    }

    public int getRotorsStarted() {
        return rotorsStarted;
    }

    public void setRotorsStarted(int rotorsStarted) {
        this.rotorsStarted = rotorsStarted;
    }

    public boolean isAttemptTakeoff() {
        return attemptTakeoff;
    }

    public void setAttemptTakeoff(boolean attemptTakeoff) {
        this.attemptTakeoff = attemptTakeoff;
    }

    public boolean isReadyTakeoff() {
        return readyTakeoff;
    }

    public void setReadyTakeoff(boolean readyTakeoff) {
        this.readyTakeoff = readyTakeoff;
    }

    public boolean isUseReturnLoading() {
        return useReturnLoading;
    }

    public void setUseReturnLoading(boolean useReturnLoading) {
        this.useReturnLoading = useReturnLoading;
    }

    public boolean isUseOverflowLoading() {
        return useOverflowLoading;
    }

    public void setUseOverflowLoading(boolean useOverflowLoading) {
        this.useOverflowLoading = useOverflowLoading;
    }

    public int getNumCycles() {
        return numCycles;
    }

    public void setNumCycles(int numCycles) {
        this.numCycles = numCycles;
    }

    int lowGoals;
    int highGoals;
    int gearsDelivered;

    public int getGearsDropped() {
        return gearsDropped;
    }

    public void setGearsDropped(int gearsDropped) {
        this.gearsDropped = gearsDropped;
    }

    public String getGearsDroppedLoc() {
        return gearsDroppedLoc;
    }

    public void setGearsDroppedLoc(String gearsDroppedLoc) {
        this.gearsDroppedLoc = gearsDroppedLoc;
    }

    int gearsDropped;
    String gearsDroppedLoc;
    int hopppersUsed;
    transient int rotorsStarted;
    int numCycles;
    transient boolean  useReturnLoading, useOverflowLoading;
    boolean attemptTakeoff, readyTakeoff;


    public TeleOp(int lowGoals, int highGoals, int gearsDelivered, int hopppersUsed, int rotorsStarted, boolean attemptTakeoff, boolean readyTakeoff, boolean useReturnLoading,
                  boolean useOverflowLoading, int numCycles, int gearsDropped, String gearsDroppedLoc) {
        this.lowGoals = lowGoals;
        this.highGoals = highGoals;
        this.gearsDelivered = gearsDelivered;
        this.hopppersUsed = hopppersUsed;
        this.rotorsStarted = rotorsStarted;
        this.attemptTakeoff = attemptTakeoff;
        this.readyTakeoff = readyTakeoff;
        this.useReturnLoading = useReturnLoading;
        this.useOverflowLoading = useOverflowLoading;
        this.numCycles = numCycles;
        this.gearsDropped = gearsDropped;
        this.gearsDroppedLoc = gearsDroppedLoc;
    }
}
