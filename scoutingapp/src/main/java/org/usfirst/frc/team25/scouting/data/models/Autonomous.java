package org.usfirst.frc.team25.scouting.data.models;


/**
 * Object model for autonomous period of a match
 */
public class Autonomous {

    private int ampAuto;
    private int foulAuto;
    private boolean crossComLine;
    private int zoneOne;
    private int zoneTwo;
    private int zoneThree;
    private int zoneFour;
    private int missedAuto;



    public Autonomous(int ampAuto, int foulAuto, boolean crossComLine, int zoneOne, int zoneTwo, int zoneThree, int zoneFour, int missedAuto) {
        this.ampAuto = ampAuto;
        this.foulAuto = foulAuto;
        this.crossComLine = crossComLine;
        this.zoneOne = zoneOne;
        this.zoneTwo = zoneTwo;
        this.zoneThree = zoneThree;
        this.zoneFour = zoneFour;
        this.missedAuto = missedAuto;
    }

    public int getAmpAuto() {return ampAuto;}

    public int getFoulAuto() {return foulAuto;}

    public boolean isCrossComLine() {
        return crossComLine;
    }

    public int getZoneOne() {return zoneOne;}

    public int getZoneTwo() {return zoneTwo;}

    public int getZoneThree() {return zoneThree;}

    public int getZoneFour() {return zoneFour;}

    public int getMissedAuto() {return missedAuto;}

}
