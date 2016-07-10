package org.usfirst.frc.team25.scouting.data.thebluealliance.models;


public class Match {

	String comp_level;
	int match_number;
	Alliances alliances;

	public String getCompLevel() {
		return comp_level;
	}

	public int getMatchNumber() {
		return match_number;
	}

	public Alliances getAlliances() {
		return alliances;
	}


	
	public static class Alliances{
		Alliance blue, red;

        public Alliance getBlueAlliance() {
            return blue;
        }

        public Alliance getRedAlliance() {
            return red;
        }

        public static class Alliance{
			String[] teams;
			int[] teamNumbers;


            public int[] getTeamNumbers() throws RuntimeException{
                if(teamNumbers==null)
                    throw new RuntimeException("Team number array is null. Did you remember to call refreshNumbers?");

                return teamNumbers;
            }

            public void setIntTeamNumbers(){
				teamNumbers = new int[teams.length];
				for(int i = 0; i < teams.length; i++){
					String truncated = "";
					for(int j = 3; j < teams[i].length(); j++)
						truncated+=teams[i].charAt(j);
					teamNumbers[i]=Integer.valueOf(truncated);
				}
					
			}
		}
	}

    /**
     * Initializes the teamNumbers integer array for both alliances
     */
    public void refreshNumbers(){
        this.getAlliances().getRedAlliance().setIntTeamNumbers();
        this.getAlliances().getRedAlliance().setIntTeamNumbers();
    }
	
	public Match(){
		
		
	}
	
	
}
