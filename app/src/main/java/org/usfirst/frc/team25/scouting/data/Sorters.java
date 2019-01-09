package org.usfirst.frc.team25.scouting.data;


import com.thebluealliance.api.v3.models.Match;
import com.thebluealliance.api.v3.models.Team;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Collection of static methods to sort and filter ArrayLists of object models
 *
 * @author sng
 */
public class Sorters {

    /**
     * Method implementing a Comparator to sort Matches
     *
     * @param matches ArrayList of Match objects
     * @return Same list, sorted by ascending match number
     */
    public static ArrayList<Match> sortByMatchNum(ArrayList<Match> matches) {

        Collections.sort(matches, (m1, m2) -> m1.getMatchNumber() - m2.getMatchNumber());
        return matches;
    }

    /**
     * Method implementing a Comparator to sort Teams
     *
     * @param events List of Team objects
     * @return Same list, sorted by ascending team number
     */
    public static ArrayList<Team> sortByTeamNum(ArrayList<Team> events) {

        Collections.sort(events, (t1, t2) -> t1.getTeamNumber() - t2.getTeamNumber());
        return events;
    }

    /**
     * Removes all Matches from a list besides qualification matches.
     * Qualification matches denoted by <code>comp_level</code> "qm"
     *
     * @param matches ArrayList of Match objects
     * @return Modified ArrayList
     */
    public static ArrayList<Match> filterQualification(ArrayList<Match> matches) {
        for (int i = 0; i < matches.size(); i++) {

            if (!matches.get(i).getCompLevel().equals("qm")) {
                matches.remove(i);
                i--;
            }
        }
        return matches;
    }
}
