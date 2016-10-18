import java.util.Arrays;
import java.util.Random;

public class Schedule {
	Week[] schedule;
	
	public Schedule(String[] teamNames) {
		generateSchedule(shuffleTeams(teamNames));
	}
	
	public String toString() {
		String s = "";
		
		for (int week=0; week < schedule.length; week++) {
			s += "Week "+(week+1)+":\n" + schedule[week].toString() + "\n\n";
		}
		
		return s;
	}
	
	private String[] shuffleTeams(String[] teamNames) {
		Random rand = new Random();
		
		for (int i=teamNames.length-1; i > 0; i--) {
			int index = rand.nextInt(i+1);
			String temp = teamNames[index];
			teamNames[index] = teamNames[i];
			teamNames[i] = temp;
		}
		
		return teamNames;
	}
	
	/*
	 * Algorithm used is called the "Polygon method of round-robin scheduling"
	 * Explanation and Visual examples of the algorithm can be found here: 
	 * 					http://mathforum.org/library/drmath/view/54715.html
	 * 
	 * Supports even or odd number of teams, though with odd #teams each team
	 * will have an idle/bye week
	 * 
	 * Minimum required #weeks = #teams if odd or #teams-1 if even
	 * #matches per week required for that amount of weeks = floor(#teams/2)
	 * 
	 * Probably room to improve method's differentiation between even/odd #teams  
	 */
	private void generateSchedule(String[] teamNames) {
		Week[] week; String pivot = null;
		boolean evenNumTeams = teamNames.length % 2 == 0;
		int matchesPerWeek = teamNames.length/2;
		
		if (evenNumTeams) {
			pivot = teamNames[0];
			teamNames = Arrays.copyOfRange(teamNames, 1, teamNames.length);
		}
		week = new Week[teamNames.length];
		
		for (int offset=0; offset < week.length; offset++) {
			Match[] weekMatches = new Match[matchesPerWeek];
			if (evenNumTeams) {
				weekMatches[0] = new Match(pivot, teamNames[offset]);
			}
			for (int i=1; i < weekMatches.length +  (evenNumTeams ? 0 : 1); i++) {
				int slot0 = (i+offset);
				int slot1 = slot0 + (teamNames.length-(2*i));
				weekMatches[evenNumTeams ? i : i-1] = new Match(teamNames[slot0 % teamNames.length], teamNames[slot1 % teamNames.length]);
			}
			
			week[offset] = new Week(weekMatches);
		}
		
		this.schedule = week;
	}
}
