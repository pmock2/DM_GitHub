package teamHarambe;

/**
 * Created by Daylon on 10/7/2016.
 */

public class Referee {
    int id;
    String email,password;
    boolean isSuperReferee;

    public Referee(int id, String email, String password, boolean isSuperReferee) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isSuperReferee = isSuperReferee;
    }

    public int getId() {
        return id;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public boolean verifyPassword(String password) {
    	return this.password.equals(password);
    }
    
    public String toJSON() {
    	String s = "{\"Email\" : \"" + email + "\", \"Password\" : \"" + password + "\", "
    			 + "\"IsSuper\" : " + (isSuperReferee == true ? "true" : "false") + "}";
    	
    	return s;
    }
}
