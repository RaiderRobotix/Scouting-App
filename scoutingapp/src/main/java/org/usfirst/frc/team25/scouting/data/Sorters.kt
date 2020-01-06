package org.usfirst.frc.team25.scouting.data

import com.thebluealliance.api.v3.models.Match
import com.thebluealliance.api.v3.models.Team
import java.util.*

/**
 * Collection of static methods to sort and filter ArrayLists of object models
 *
 * @author sng
 */
object Sorters {
	/**
	 * Method implementing a Comparator to sort Matches
	 *
	 * @param matches ArrayList of Match objects
	 * @return Same list, sorted by ascending match number
	 */
	fun sortByMatchNum(matches: ArrayList<Match>): ArrayList<Match> {
		Collections.sort(matches) { m1: Match, m2: Match -> m1.matchNumber - m2.matchNumber }
		return matches
	}

	/**
	 * Method implementing a Comparator to sort Teams
	 *
	 * @param events List of Team objects
	 * @return Same list, sorted by ascending team number
	 */
	fun sortByTeamNum(events: ArrayList<Team>): ArrayList<Team> {
		Collections.sort(events) { t1: Team, t2: Team -> t1.teamNumber - t2.teamNumber }
		return events
	}

	/**
	 * Removes all Matches from a list besides qualification matches.
	 * Qualification matches denoted by `comp_level` "qm"
	 *
	 * @param matches ArrayList of Match objects
	 * @return Modified ArrayList
	 */
	fun filterQualification(matches: ArrayList<Match>): ArrayList<Match> {
		var i = 0
		while (i < matches.size) {
			if (matches[i].compLevel != "qm") {
				matches.removeAt(i)
				i--
			}
			i++
		}
		return matches
	}
}