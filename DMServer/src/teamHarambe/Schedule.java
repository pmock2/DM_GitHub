package teamHarambe;

import java.util.*;

public class Schedule {
	Calendar startDate;
	List<Match> matches = new LinkedList<>();

	public Schedule(List<Team> teamlist, List<Referee> refereelist, Calendar startDate) {
		this.startDate = startDate;
		generateSchedule(shuffleTeams(teamlist), refereelist);
	}
	
	public Schedule(List<Match> matches) {
		this.matches = matches;
	}

	public String toString() {
		String s = "";

		for (int i=0; i < matches.size(); i++) {
			s += matches.get(i).toString() + "\n";
		}

		return s;
	}
	
	public String toJSON() {
		String s = "{\n";
		
		for (int i=0; i < matches.size(); i++) {
			//s += "\t\t\"Week" + week + "\" : " + schedule[week].toJSON() + (week+1 == schedule.length ? "" : ",") + "\n";
			s += "\t\t" + matches.get(i).getId() + " : " + matches.get(i).toJSON() + (i+1 == matches.size() ? "" : ",") + "\n";
		}
		
		s += "\t}";
		return s;
	}
	
	public List<Match> getMatches() {
		return matches;
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
	
	long msInWeek = 1000 * 60 * 60 * 24 * 7;
	private Calendar getDateFromWeek(int weekInt) {
		long week = (long)weekInt;
		long startMs = startDate.getTimeInMillis();
		long newMs = startMs + (msInWeek * week);
		Calendar newDate = Calendar.getInstance();
		newDate.setTimeInMillis(newMs);
		
		return newDate;
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
	private void generateSchedule(List<Team> teams, List<Referee> referees) {
		Random rand = new Random();
		Team pivot = null;
		boolean evenNumTeams = teams.size() % 2 == 0;
		int numWeeks = teams.size() - (evenNumTeams ? 1 : 0);
		int matchesPerWeek = teams.size()/2;
		Iterator iterator = referees.listIterator();
		Referee r = (Referee)iterator.next();


		if (evenNumTeams) {
			pivot = teams.get(0);
			teams = teams.subList(1, teams.size());
		}

		for (int offset=0; offset < numWeeks; offset++) {
			Calendar weekDate = getDateFromWeek(offset);

			if (evenNumTeams) {
				matches.add(new Match(matches.size(), pivot, teams.get(offset), r, weekDate));
				if (iterator.hasNext())
				{
					r = (Referee)iterator.next();
				}
				else
				{
					iterator = referees.listIterator();
					r = (Referee)iterator.next();
				}
			}
			for (int i=1; i < matchesPerWeek + (evenNumTeams ? 0 : 1); i++) {
				int slot0 = (i+offset);
				int slot1 = slot0 + (teams.size()-(2*i));
				matches.add(new Match(matches.size(), teams.get(slot0 % teams.size()), teams.get(slot1 % teams.size()), r, weekDate));
				if (iterator.hasNext())
				{
					r = (Referee)iterator.next();
				}
				else
				{
					iterator = referees.listIterator();
					r = (Referee)iterator.next();
				}
			}
		}
	}
}