/**
 * Created by Daylon on 9/28/2016.
 */
package teamHarambe;

public class Team {
	int id;
    String name;

    public Team(int id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
    	return id;
    }
    
    public String toJSON() {
    	String s = "{\"Name\" : \"" + name + "\"}";
    	
    	return s;
    }
}
