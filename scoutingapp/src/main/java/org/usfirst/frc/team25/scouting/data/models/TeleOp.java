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

    public TeleOp(int notesPickedGround, int notesPickedHps, int ampNotesScoredTelop, int speakerNotesScoredTeleop, int ampSpeakerNotesScored, int notesDroppedTeleop) {
        this.notesPickedGround = notesPickedGround;
        this.notesPickedHps = notesPickedHps;
        this.ampNotesScoredTelop = ampNotesScoredTelop;
        this.speakerNotesScoredTeleop = speakerNotesScoredTeleop;
        this.ampSpeakerNotesScored = ampSpeakerNotesScored;
        this.notesDroppedTeleop = notesDroppedTeleop;
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
}
