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
    int team1Score = 0;
    int team2Score = 0;
    boolean isMorning = true;
    boolean scored = false;
    

    public Match(int id, Team team1, Team team2, Referee referee, Calendar date) {
    	this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.referee = referee;
        this.date = date;
    }
    
    public Match(int id, JSONObject matchData, Team team0, Team team1) {
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
		this.isMorning = matchData.getBoolean("IsMorning");
		this.team1 = team0;
		this.team2 = team1;
		this.referee = matchReferee;
		this.scored = matchData.getBoolean("Scored");
		this.team1Score = matchData.getInt("Score0");
		this.team2Score = matchData.getInt("Score1");
    }
    
    public Match(int matchId, Team team0, Team team1, Referee referee, Calendar date, boolean isMorning, int team0Score, int team1Score, boolean scored) {
    	this(matchId, team0, team1, referee, date);
    	this.isMorning = isMorning;
    	this.team1Score = team0Score;
    	this.team2Score = team1Score;
    	this.scored = scored;
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

    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public double getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
    }
    
    public boolean getScored() {
    	return scored;
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
    
    public Calendar getDate() {
    	return date;
    }
    
    public void setDate(Calendar newDate) {
    	date = newDate;
    }
    
    public boolean isScored() {
    	return scored;
    }
    
    public void setIsMorning(boolean isMorning) {
    	this.isMorning = isMorning;
    }
    
    public boolean getIsMorning() {
    	return this.isMorning;
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
    			 + "\"IsMorning\" : " + (isMorning == true ? "true" : "false") + ", "
    			 + "\"Scored\" : " + (scored ? "true" : "false") + ", "
    			 + "\"Score0\" : " + team1Score + ", \"Score1\" : " + team2Score + "}";
    	
    	return s;
    }
}
