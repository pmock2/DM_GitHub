public class Main {

	public static void main(String[] args) {
		String[] teamNames = {
			"Team1","Team2","Team3","Team4","Team5",
			"Team6","Team7","Team8","Team9","Team10"
		};
		Schedule schedule = new Schedule(teamNames);
		System.out.println("Schedule: ");
		System.out.println(schedule.toString());
	}

}