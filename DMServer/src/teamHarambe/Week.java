package teamHarambe;

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
    		s += "\t\"Match" + i + "\" : \"" + matches[i].toString() + "\"" + (i+1 == matches.length ? "" : ",") + "\n";
    	}
    	
    	s += "}";
    	return s;
    }
}