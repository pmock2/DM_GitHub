public class Match {
	String team1;
	String team2;
	
	public Match(String team1, String team2) {
		this.team1 = team1;
		this.team2 = team2;
	}
	
	public String toString() {
		return team1 + " - " + team2;
	}
}
