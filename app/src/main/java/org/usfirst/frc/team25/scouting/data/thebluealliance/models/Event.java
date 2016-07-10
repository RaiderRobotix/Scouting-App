package org.usfirst.frc.team25.scouting.data.thebluealliance.models;

public class Event {
	
	String key, name, short_name, event_code, start_date, end_date;
	int year;
	Team[] teams;
	Match[] matches;

	public Event(){

	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getShort_name() {
		return short_name;
	}

	public String getEvent_code() {
		return event_code;
	}

	public String getStart_date() {
		return start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public int getYear() {
		return year;
	}

	public Team[] getTeams() {
		return teams;
	}

	public Match[] getMatches() {
		return matches;
	}
}
