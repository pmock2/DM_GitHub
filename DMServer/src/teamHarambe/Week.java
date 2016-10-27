package teamHarambe;

import org.json.JSONObject;

public class Week {

    Match[] matches;


    public Week(Match[] matches) {
        this.matches = matches;
    }

    public String toString() {
        String s = "";

        for (int i=0; i < matches.length; i++) {
            s = s + matches[i].toString() + "\n";
        }

        return s;
    }
    
    public String toJSON() {
    	String s = "{\n";
    	
    	for (int i=0; i < matches.length; i++) {
    		s += "\t\"Match" + i + "\" : " + matches[i].toJSON() + (i+1 == matches.length ? "" : ",") + "\n";
    	}
    	
    	s += "}";
    	return s;
    }
    
    public static Match[] parseWeekMatches(JSONObject matchList) {
    	String[] matchNames = JSONObject.getNames(matchList);
    	Match[] matches = new Match[matchNames.length];
    	
    	for (int i=0; i < matchNames.length; i++) {
    		JSONObject matchData = matchList.getJSONObject(matchNames[i]);
    		
    		int matchRefereeId = matchData.getInt("Referee");
    		Referee matchReferee = null;
    		for (int j=0; j < Server.referees.size(); j++) {
    			if (Server.referees.get(j).getId() == matchRefereeId) {
    				matchReferee = Server.referees.get(j);
    				break;
    			}
    		}
    		
    		Match match = new Match(
    			Server.teams.get(matchData.getInt("Team0")),
    			Server.teams.get(matchData.getInt("Team1")),
    			matchReferee,
    			matchData.getDouble("Score0"),
    			matchData.getDouble("Score1")
    		);
    		matches[i] = match;
    	}
    	
    	return matches;
    }
}