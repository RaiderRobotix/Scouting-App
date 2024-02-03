package org.usfirst.frc.team25.scouting.data.models;


/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {

    private int ampTeleop;
    private boolean foulTeleop;
    private int zoneOneTeleop;
    private int zoneTwoTeleop;
    private int zoneThreeTeleop;
    private int zoneFourTeleop;
    private int missedTeleop;

    public TeleOp(int ampTeleop, boolean foulTeleop, int zoneOneTeleop, int zoneTwoTeleop, int zoneThreeTeleop, int zoneFourTeleop, int missedTeleop) {
        this.ampTeleop = ampTeleop;
        this.foulTeleop = foulTeleop;
        this.zoneOneTeleop = zoneOneTeleop;
        this.zoneTwoTeleop = zoneTwoTeleop;
        this.zoneThreeTeleop = zoneThreeTeleop;
        this.zoneFourTeleop = zoneFourTeleop;
        this.missedTeleop = missedTeleop;
    }

    public int getAmpTeleop() {return  ampTeleop;}

    public boolean isFoulTeleop() {return foulTeleop;}

    public int getZoneOneTeleop() {return zoneOneTeleop;}

    public int getZoneTwoTeleop() {return zoneTwoTeleop;}

    public int getZoneThreeTeleop() {return zoneThreeTeleop;}

    public int getZoneFourTeleop() {return zoneFourTeleop;}

    public int getMissedTeleop() {return missedTeleop;}
}
