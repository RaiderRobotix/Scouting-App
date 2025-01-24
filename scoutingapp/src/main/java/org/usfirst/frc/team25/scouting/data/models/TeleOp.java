package org.usfirst.frc.team25.scouting.data.models;


/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {

    private int netTeleop;
    private int minFoulTeleop;
    private int majFoulTeleop;
    private int levelOneTeleop;
    private int levelTwoTeleop;
    private int levelThreeTeleop;
    private int levelFourTeleop;
    private int levelFiveTeleop;
    private int missedTeleop;

    public TeleOp(int netTeleop, int minFoulTeleop, int majFoulTeleop, int levelOneTeleop, int levelTwoTeleop, int levelThreeTeleop, int levelFourTeleop, int levelFiveTeleop, int missedTeleop) {
        this.netTeleop = netTeleop;
        this.minFoulTeleop = minFoulTeleop;
        this.majFoulTeleop = majFoulTeleop;
        this.levelOneTeleop = levelOneTeleop;
        this.levelTwoTeleop = levelTwoTeleop;
        this.levelThreeTeleop = levelThreeTeleop;
        this.levelFourTeleop = levelFourTeleop;
        this.levelFiveTeleop = levelFiveTeleop;
        this.missedTeleop = missedTeleop;
    }

    public int getNetTeleop() {return netTeleop;}

    public int getMinFoulTeleop() {return minFoulTeleop;}

    public int getMajFoulTeleop() {return majFoulTeleop;}

    public int getLevelOneTeleop() {return levelOneTeleop;}

    public int getLevelTwoTeleop() {return levelTwoTeleop;}

    public int getLevelThreeTeleop() {return levelThreeTeleop;}

    public int getLevelFourTeleop() {return levelFourTeleop;}

    public int getLevelFiveTeleop() {return levelFiveTeleop;}

    public int getMissedTeleop() {return missedTeleop;}
}
