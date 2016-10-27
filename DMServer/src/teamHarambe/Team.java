/**
 * Created by Daylon on 9/28/2016.
 */
package teamHarambe;

public class Team {
	int id;
    String name;
    double wins;

    public Team(int id, String name) {
    	this.id = id;
        this.name = name;
        this.wins = 0;
    }
    
    public Team(int id, String name, double wins) {
    	this(id, name);
    	this.wins = wins;
    }

    public String getName() {
        return name;
    }
    
    public int getId() {
    	return id;
    }
    
    public double getWins() {
    	return wins;
    }
    
    public String toJSON() {
    	String s = "{\"Name\" : \"" + name + "\", \"Wins\" : " + wins + "}";
    	
    	return s;
    }
}
