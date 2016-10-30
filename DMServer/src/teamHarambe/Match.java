/**
 * Created by Daylon on 9/29/2016.
 */
package teamHarambe;

import java.util.Calendar;

import org.json.JSONObject;

public class Match {
    Team team1;
    Team team2;
    Referee referee;
    Calendar date;
    int id;
    double team1Score = 0;
    double team2Score = 0;
   

    public Match(int id, Team team1, Team team2, Referee referee, Calendar date) {
    	this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.referee = referee;
        this.date = date;
    }
    
    public Match(int id, Team team1, Team team2, Referee referee, Calendar date, double score1, double score2) {
    	this(id, team1, team2, referee, date);
    	team1Score = score1;
    	team2Score = score2;
    }
    
    public Match(int id, JSONObject matchData) {
    	int matchRefereeId = matchData.getInt("Referee");
		Referee matchReferee = null;
		for (int j=0; j < Server.referees.size(); j++) {
			if (Server.referees.get(j).getId() == matchRefereeId) {
				matchReferee = Server.referees.get(j);
				break;
			}
		}
		
		JSONObject jsonDate = matchData.getJSONObject("Date");
		Calendar date = Calendar.getInstance();
		date.set(jsonDate.getInt("Year"), jsonDate.getInt("Month"), jsonDate.getInt("Day"));
		
		this.id = id;
		this.date = date;
		this.team1 = Server.teams.get(matchData.getInt("Team0"));
		this.team2 = Server.teams.get(matchData.getInt("Team1"));
		this.referee = matchReferee;
		this.team1Score = matchData.getDouble("Score0");
		this.team2Score = matchData.getDouble("Score1");
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public double getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(double team1Score) {
        this.team1Score = team1Score;
    }

    public double getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(double team2Score) {
        this.team2Score = team2Score;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }
    
    public int getId() {
    	return id;
    }
    
    public boolean isMatchOnDate(Calendar date) {
    	return this.date.get(this.date.YEAR) == date.get(date.YEAR) &&
    		   this.date.get(this.date.MONTH) == date.get(date.MONTH) &&
    		   this.date.get(this.date.DAY_OF_MONTH) == date.get(date.DAY_OF_MONTH);
    }
    
    private String dateToJSON() {
    	String s = "{"  
    			   + "\"Year\" : " + date.get(date.YEAR) + ", "
    			   + "\"Month\" : " + date.get(date.MONTH) + ", "
    			   + "\"Day\" : " + date.get(date.DAY_OF_MONTH) + ","
    			   + "}";

    	return s;
    }

    public String toString() {
        return team1.getName() + " - " + team2.getName() + "; Refereed by ID # " + getReferee().getId();
    }
    
    public String toJSON() {
    	String s = "{\"Team0\" : " + team1.getId() + ", \"Team1\" : " + team2.getId() + ", "
    			 + "\"Referee\" : " + getReferee().getId() + ", "
    			 + "\"Date\" : " + dateToJSON() + ", "
    			 + "\"Score0\" : " + team1Score + ", \"Score1\" : " + team2Score + "}";
    	
    	return s;
    }
}
