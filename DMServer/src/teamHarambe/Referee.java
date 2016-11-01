package teamHarambe;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.json.JSONObject;

/**
 * Created by Daylon on 10/7/2016.
 */

public class Referee {
    int id;
    String email;
    String password = null;
    boolean isSuperReferee;
    JSONObject loginCode = null;

    public Referee(int id, String email, boolean isSuperReferee) {
    	this.id = id;
        this.email = email;
        this.isSuperReferee = isSuperReferee;
        sendPasswordReset();
    }
    
    public Referee(int id, String email, String password, JSONObject loginCode, boolean isSuperReferee) {
    	this.id = id;
        this.email = email;
        this.isSuperReferee = isSuperReferee;
    	this.password = password;
    	this.loginCode = loginCode;
    }
    
    public void clearLoginCode() {
    	this.loginCode = null;
    	try {Server.saveData();} catch (Exception e){e.printStackTrace();}
    }
    
    public void setEmail(String newEmail) {
    	System.out.println("Email set to: "+newEmail);
    	this.email = newEmail;
    	try {Server.saveData();} catch (Exception e){e.printStackTrace();}
    }
    
    public void setPassword(String newPassword) {
    	System.out.println("Password set to :"+newPassword);
    	this.password = newPassword;
    	loginCode = null;
    	try {Server.saveData();} catch (Exception e){e.printStackTrace();}
    }

	public int getId() {
        return id;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public boolean verifyPassword(String password) {
    	return this.password != null && this.password.equals(password);
    }
    
    public boolean verifyLoginCode(String code) {
    	return (this.loginCode == null) ? false : this.loginCode.getString("Code").equals(code);
    }
    
    private String getPasswordResetTitle() {
    	String s = (password == null ? "DM Password Setup" : "DM Password Reset");
    	
    	return s;
    }
    
    private String getPasswordResetMessage(String code) {
    	String s = "Your login code is: " + code + "\n\nPlease log in with this code to "
    			+ (password == null ? "set" : "reset") + " your password."
    			+ (password == null ? "" : "The code will expire in 10 minutes.");
    	
    	return s;
    }
    
    public void sendPasswordReset() {
    	Random generator = new Random();
    	String code = generator.nextInt(1000000)+"";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		md.update(code.getBytes(), 0, code.length());
		String hashedCode = new BigInteger(1, md.digest()).toString(16);
		
    	JSONObject loginCode = new JSONObject();
    	loginCode.put("Code", hashedCode);
    	this.loginCode = loginCode;
    	
    	try {
    		EmailSender.sendEmail(getEmail(), getPasswordResetTitle(), getPasswordResetMessage(""+code));
    		Server.saveData();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public String getPasswordJSON() {
    	String s = "";
    	
    	s += (password == null ? "" : "\"");
    	s += password;
    	s += (password == null ? "" : "\"");
    	
    	return s;
    }
    
    public String getLoginCodeJSON() {
    	String s = "";
    	
    	s += (loginCode == null ? null : loginCode.toString());
    	
    	return s;
    }
    
    public String toJSON() {
    	String s = "{\"Email\" : \"" + email + "\", \"Password\" : " + getPasswordJSON() +", "
    			 + "\"LoginCode\" : " + getLoginCodeJSON() + ", "
    			 + "\"IsSuper\" : " + (isSuperReferee == true ? "true" : "false") + "}";
    	
    	return s;
    }
}
