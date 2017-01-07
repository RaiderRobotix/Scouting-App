package org.usfirst.frc.team25.scouting.data.models;


import java.io.Serializable;

/** Container object for all parts of a match
 *  To be serialized into JSON
 */
public class ScoutEntry implements Serializable {

    private PreMatch preMatch;
    private Autonomous auto;
    private TeleOp teleOp;
    private PostMatch postMatch;

    //Actual member variables will be set using setters as data is filled in
    public ScoutEntry() {}

    public PreMatch getPreMatch() {
        return preMatch;
    }

    public void setPreMatch(PreMatch preMatch) {
        this.preMatch = preMatch;
    }

    public Autonomous getAuto() {
        return auto;
    }

    public void setAuto(Autonomous auto) {
        this.auto = auto;
    }

    public TeleOp getTeleOp() {
        return teleOp;
    }

    public void setTeleOp(TeleOp teleOp) {
        this.teleOp = teleOp;
    }

    public PostMatch getPostMatch() {
        return postMatch;
    }

    public void setPostMatch(PostMatch postMatch) {
        this.postMatch = postMatch;
    }


}
