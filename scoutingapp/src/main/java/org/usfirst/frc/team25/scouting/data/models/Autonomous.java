package org.usfirst.frc.team25.scouting.data.models;


/**
 * Object model for autonomous period of a match
 */
public class Autonomous {

    private int processorAuto;
    private int majorFoulAuto;
    private int minorFoulAuto;
    private boolean crossComLine;
    private int levelOne;
    private int levelTwo;
    private int levelThree;
    private int levelFour;
    private int coral;
    private int madeAuto;
    private int missedAuto;



    public Autonomous(int processorAuto, int majorFoulAuto, int minoFoulAuto, boolean crossComLine, int levelOne, int levelTwo, int levelThree, int levelFour, int coral, int madeAuto, int missedAuto) {
        this.processorAuto = processorAuto;
        this.majorFoulAuto = majorFoulAuto;
        this.minorFoulAuto = minorFoulAuto;
        this.crossComLine = crossComLine;
        this.levelOne = levelOne;
        this.levelTwo = levelTwo;
        this.levelThree = levelThree;
        this.levelFour = levelFour;
        this.madeAuto = madeAuto;
        this.missedAuto = missedAuto;
    }

    public int getProcessorAuto() {return processorAuto;}

    public int getMajorFoulAuto() {return majorFoulAuto;}

    public int getMinorFoulAuto() {return minorFoulAuto;}

    public boolean isCrossComLine() {
        return crossComLine;
    }

    public int getLevelOne() {return levelOne;}

    public int getLevelTwo() {return levelTwo;}

    public int getLevelThree() {return levelThree;}

    public int getLevelFour() {return levelFour;}

    public int getCoral() {return coral;}

    public int getMadeAuto() {return madeAuto;}

    public int getMissedAuto() {return missedAuto;}

}
