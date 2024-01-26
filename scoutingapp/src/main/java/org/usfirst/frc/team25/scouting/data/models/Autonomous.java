package org.usfirst.frc.team25.scouting.data.models;


/**
 * Object model for autonomous period of a match
 */
public class Autonomous {

    private boolean crossComLine;

    private int notesPickedAuto;
    private int ampScoredAuto;

    private int speakerScoredAuto;
    private int notesDroppedAuto;



    public Autonomous(boolean crossComLine, int notesPickedAuto, int ampScoredAuto, int speakerScoredAuto, int notesDroppedAuto) {
        this.crossComLine = crossComLine;
        this.notesPickedAuto = notesPickedAuto;
        this.ampScoredAuto = ampScoredAuto;
        this.speakerScoredAuto = speakerScoredAuto;
        this.notesDroppedAuto = notesDroppedAuto;
    }

    public int getNotesPickedAuto() {
        return notesPickedAuto;
    }

    public int getAmpScoredAuto() {
        return ampScoredAuto;
    }

    public int getSpeakerScoredAuto() {
        return speakerScoredAuto;
    }

    public int getNotesDroppedAuto() {
        return notesDroppedAuto;
    }

    public boolean isCrossComLine() {
        return crossComLine;
    }
}
