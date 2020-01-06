package org.usfirst.frc.team25.scouting.ui.dataentry

import android.app.Fragment
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry

/**
 * Interface that all fragments for data entry implement to ensure successful communication
 * between fragments
 */
abstract class EntryFragment : Fragment() {
	/* Called when a fragment transaction is started to pass data onto the next fragment
     */
	/**
	 * A ScoutEntry container object to hold scouting info, passed around between fragments with
	 * setter and getter methods
	 * Entry should be a parameter in the constructor, when setEntry is called
	 */
	open var entry: ScoutEntry? = null

	/**
	 * Method that finds if the data for the fragment that is held in `entry` is
	 * `null`.
	 * If it isn't, the fields and views of the fragment are populated with the existing data.
	 */
	abstract fun autoPopulate()

	/**
	 * Takes the inputted values and creates a new object containing the data from a fragment and
	 * sets it as a member variable of `entry`.
	 * Called when a new fragment transaction begins in which the fragment is replaced.
	 */
	abstract fun saveState()
}