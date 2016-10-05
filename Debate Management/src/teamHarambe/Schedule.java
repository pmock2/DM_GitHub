package teamHarambe;

import java.util.Calendar;

public class Schedule {
	private Calendar scheduleStart;
	private Calendar schedulEnd;
	private Team[] teamList;
	
	public Schedule(Team[] teams, Calendar scheduleStart, Calendar scheduleEnd) {
		this.teamList = teams;
		this.scheduleStart = scheduleStart;
		this.schedulEnd = scheduleEnd;
	}
	
	public String toString() {
		return "";
	}
	
	//Length in weeks
	public int getLength() {
		return 0;
	}
}
