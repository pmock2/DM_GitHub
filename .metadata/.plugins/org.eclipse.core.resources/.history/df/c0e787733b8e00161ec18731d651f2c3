import java.util.Arrays;

public class Schedule {
	Week[] schedule;
	
	public Schedule(String[] teamNames) {
		generateSchedule(teamNames);
	}
	
	public String toString() {
		String s = "";
		
		for (int week=0; week < schedule.length; week++) {
			s += "Week "+(week+1)+":\n" + schedule[week].toString() + "\n\n";
		}
		
		return s;
	}
	
	/*
	 * Algorithm used is called the "Polygon method of round-robin scheduling"
	 * Explanation and Visual examples of the algorithm can be found here: 
	 * 					http://mathforum.org/library/drmath/view/54715.html
	 */
	private void generateSchedule(String[] teamNames) {
		//TODO support odd number of teams
		String pivot = teamNames[0];
		Week[] week = new Week[teamNames.length-1];
		teamNames = Arrays.copyOfRange(teamNames, 1, teamNames.length);
		
		for (int offset=0; offset < week.length; offset++) {
			Match[] weekMatches = new Match[(teamNames.length+1)/2];
			
			weekMatches[0] = new Match(pivot, teamNames[offset]);
			for (int i=1; i < weekMatches.length; i++) {
				int slot0 = (i+offset);
				int slot1 = slot0 + (teamNames.length-(2*i));
				weekMatches[i] = new Match(teamNames[slot0 % teamNames.length], teamNames[slot1 % teamNames.length]);
			}
			
			week[offset] = new Week(weekMatches);
		}
		
		this.schedule = week;
	}
}
