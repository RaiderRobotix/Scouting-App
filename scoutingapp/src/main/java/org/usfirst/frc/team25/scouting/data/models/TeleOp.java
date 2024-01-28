package org.usfirst.frc.team25.scouting.data.models;


/**
 * Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {

    private int notesPickedGround;
    private int notesPickedHps;
    private int ampNotesScoredTelop;
    private int speakerNotesScoredTeleop;
    private int ampSpeakerNotesScored;
    private int notesDroppedTeleop;

    private boolean park;
    private boolean onstage;
    private boolean spotlit;
    private boolean trap;
    private boolean harmony;
    private boolean ensemble;

    public TeleOp(int notesPickedGround, int notesPickedHps, int ampNotesScoredTelop, int speakerNotesScoredTeleop, int ampSpeakerNotesScored, int notesDroppedTeleop, boolean park, boolean onstage, boolean spotlit, boolean trap, boolean harmony, boolean ensemble) {
        this.notesPickedGround = notesPickedGround;
        this.notesPickedHps = notesPickedHps;
        this.ampNotesScoredTelop = ampNotesScoredTelop;
        this.speakerNotesScoredTeleop = speakerNotesScoredTeleop;
        this.ampSpeakerNotesScored = ampSpeakerNotesScored;
        this.notesDroppedTeleop = notesDroppedTeleop;
        this.park = park;
        this.onstage = onstage;
        this.spotlit = spotlit;
        this.trap = trap;
        this.harmony = harmony;
        this.ensemble = ensemble;
    }


    public int getNotesPickedGround() {
        return notesPickedGround;
    }

    public int getNotesPickedHps() {
        return notesPickedHps;
    }

    public int getAmpNotesScoredTelop() {
        return ampNotesScoredTelop;
    }

    public int getSpeakerNotesScoredTeleop() {return speakerNotesScoredTeleop;}

    public int getAmpSpeakerNotesScored() {return ampSpeakerNotesScored;}

    public int getNotesDroppedTeleop() {
        return notesDroppedTeleop;
    }

    public boolean isPark() {return park;}

    public boolean isOnstage() {return onstage;}

    public boolean isSpotlit() {return spotlit;}

    public boolean isTrap() {return trap;}

    public boolean isHarmony() {return harmony;}

    public boolean isEnsemble() {return ensemble;}
}
