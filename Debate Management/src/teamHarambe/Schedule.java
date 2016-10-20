package teamHarambe;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Schedule {
	Week[] schedule;

	public Schedule(List<Team> teamlist, List<Referee> refereelist) {

		generateSchedule(shuffleTeams(teamlist), refereelist);
	}

	public String toString() {
		String s = "";

		for (int week=0; week < schedule.length; week++) {
			s += "Week "+(week+1)+":\n" + schedule[week].toString() + "\n";
		}

		return s;
	}

	private List<Team> shuffleTeams(List<Team> list) {
		Random rand = new Random();

		for (int i=list.size()-1; i > 0; i--) {
			int index = rand.nextInt(i+1);
			Team temp = list.get(index);
			list.set(index, list.get(i));
			list.set(i, temp);
		}

		return list;
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
	private void generateSchedule(List<Team> list, List<Referee> Rlist) {
		Random rand = new Random();
		Week[] week; Team pivot = null;
		boolean evenNumTeams = list.size() % 2 == 0;
		int matchesPerWeek = list.size()/2;
		List<Referee> temp;
		temp = (List)((LinkedList)Rlist).clone();


		if (evenNumTeams) {
			pivot = list.get(0);
			list = list.subList(1, list.size());
		}
		week = new Week[list.size()];

		for (int offset=0; offset < week.length; offset++) {
			if (temp.size() > 0)
			{
				temp.clear();
			}
			temp = (List)((LinkedList)Rlist).clone();
			int tempInt;
			Match[] weekMatches = new Match[matchesPerWeek];
			if (evenNumTeams) {
				tempInt = rand.nextInt(temp.size()) + 0;
				weekMatches[0] = new Match(pivot, list.get(offset), 0, 0, temp.get(tempInt));
				temp.remove(tempInt);
			}
			for (int i=1; i < weekMatches.length +  (evenNumTeams ? 0 : 1); i++) {
				int slot0 = (i+offset);
				int slot1 = slot0 + (list.size()-(2*i));
				tempInt = rand.nextInt(temp.size()) + 0;
				weekMatches[evenNumTeams ? i : i-1] = new Match(list.get(slot0 % list.size()), list.get(slot1 % list.size()), 0, 0, temp.get(tempInt));
				temp.remove(tempInt);
			}

			week[offset] = new Week(weekMatches);
		}

		this.schedule = week;
	}
}