package org.usfirst.frc.team25.scouting.ui.dataentry;

import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;

/**
 * Interface that all fragments for data entry implement to ensure successful communication
 * between fragments
 */
interface EntryFragment {

    /**
     * A ScoutEntry container object to hold scouting info, passed around between fragments with
     * setter and getter methods
     * Entry should be a parameter in the constructor, when setEntry is called
     */
    ScoutEntry entry = new ScoutEntry();

    /* Called when a fragment transaction is started to pass data onto the next fragment
     */
    ScoutEntry getEntry();

    void setEntry(ScoutEntry entry);

    /**
     * Method that finds if the data for the fragment that is held in <code>entry</code> is
     * <code>null</code>.
     * If it isn't, the fields and views of the fragment are populated with the existing data.
     */
    void autoPopulate();

    /**
     * Takes the inputted values and creates a new object containing the data from a fragment and
     * sets it as a member variable of <code>entry</code>.
     * Called when a new fragment transaction begins in which the fragment is replaced.
     */
    void saveState();

}
