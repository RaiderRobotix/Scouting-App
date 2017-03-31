package  org.usfirst.frc.team25.scouting.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.adithyasairam.tba4j.models.Match;
import com.adithyasairam.tba4j.models.Team;

/**
 * Collection of static methods to sort and filter ArrayLists of object models
 * @author sng
 *
 */
public class Sorters {

    /** Method implementing a Comparator to sort Matches
     * @param matches ArrayList of Match objects
     * @return Same list, sorted by ascending match number
     */
   public static ArrayList<Match> sortByMatchNum(ArrayList<Match> matches){

        Collections.sort(matches, new Comparator<Match>(){
            public int compare(Match m1, Match m2){
                return m1.match_number - m2.match_number;
            }
        });
        return matches;
    }

    /** Method implementing a Comparator to sort Teams
     * @param events List of Team objects
     * @return Same list, sorted by ascending team number
     */
   public  static ArrayList<Team> sortByTeamNum(ArrayList<Team> events){

        Collections.sort(events, new Comparator<Team>(){
            public int compare(Team t1, Team t2){
                return t1.team_number - t2.team_number;
            }
        });
        return events;
    }

    /** Removes all Matches from a list besides qualification matches.
     * Qualification matches denoted by <code>comp_level</code> "qm"
     * @param matches ArrayList of Match objects
     * @return Modified ArrayList
     */
   public static ArrayList<Match> filterQualification(ArrayList<Match> matches){
        for(int i = 0; i < matches.size(); i++){

            if(!matches.get(i).comp_level.equals("qm")){
                matches.remove(i);
                i--;
            }
        }
        return matches;
    }
}
