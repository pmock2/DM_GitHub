package teamHarambe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Calendar;
import java.security.*;

import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.*;

public class Server {	
	public static List<Team> teams = new LinkedList<>();
	public static List<Referee> referees = new LinkedList<>();
	public static Schedule schedule;
	
	private static String databasePath = getDatabasePath();

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		loadData();
		
		ServerSocket server = new ServerSocket(1234);
		System.out.println("Server ready to accept clients.");
		
		while (true) {
			Socket s = server.accept();
			Runnable connectionHandler = new ConnectionHandler(s);
			new Thread(connectionHandler).start();
		}
	}
	
	private static String getDatabasePath() {
		File resources = new File("resources");
		if (!resources.exists()) {
			resources.mkdir();
		}
		return resources.getPath()+"/Database.json";
	}
	
	private static void createDatabase() throws IOException {
		System.out.println("Creating database");
		Referee superReferee = new Referee(0, "DMSuperReferee@mail.com", true);
		String dbString = "";
		dbString += "{\n";
		dbString += "\t\"Schedule\" : null,\n";
		dbString += "\t\"Teams\" : {},\n";
		dbString += "\t\"Referees\" : {\n";
		dbString += "\t\t" + superReferee.getId() + " : " + superReferee.toJSON() + "\n";
		dbString += "\t}\n";
		dbString += "}";
		
		saveData(dbString);
	}
	
	private static void loadData() throws IOException, NoSuchAlgorithmException {
		File dbFile = new File(databasePath);
		if (!dbFile.exists()) {
			createDatabase();
		}
		
		JSONObject database = new JSONObject(readFile(databasePath, StandardCharsets.UTF_8));
		loadTeams(database.getJSONObject("Teams"));
		loadReferees(database.getJSONObject("Referees"));
		try {loadSchedule(database.getJSONObject("Schedule"));} catch(JSONException e) {}
	}
	
	private static void loadTeams(JSONObject teamList) {
		String[] teamIds = JSONObject.getNames(teamList);
		teamIds = (teamIds == null ? new String[0] : teamIds);
		for (int i=0; i < teamIds.length; i ++) {
			JSONObject teamData = teamList.getJSONObject(teamIds[i]);
			int teamId = Integer.parseInt(teamIds[i]);

			teams.add(new Team(
					teamId, teamData.getString("Name"), teamData.getDouble("Wins")
			));
		}
	}
	
	private static void loadReferees(JSONObject refereeList) {
		String[] refereeIds = JSONObject.getNames(refereeList);
		for (int i=0; i < refereeIds.length; i ++) {
			JSONObject refereeData = refereeList.getJSONObject(refereeIds[i]);
			int refereeId = Integer.parseInt(refereeIds[i]);
			String password = null;
			JSONObject loginCode = null;
			
			try {
				password = refereeData.getString("Password");
			} catch (JSONException e) {}
			try {
				loginCode = refereeData.getJSONObject("LoginCode");
			} catch (JSONException e) {}
			
			referees.add(new Referee (
				refereeId, refereeData.getString("Email"), password, loginCode, refereeData.getBoolean("IsSuper")
			));
		}
	}
	
	private static void loadSchedule(JSONObject matchList) {
		String[] matchIds = JSONObject.getNames(matchList);
		matchIds = (matchIds == null ? new String[0] : matchIds);
		List<Match> matches = new LinkedList<>();
		
		for (int i=0; i < matchIds.length; i++) {
			int matchId = Integer.parseInt(matchIds[i]);
			matches.add(new Match(matchId, matchList.getJSONObject(matchIds[i])));
		}
		
		schedule = new Schedule(matches);
	}
	
	public static void saveData() throws IOException {
		saveData(generateDatabaseJSON());
	}
	
	public static void saveData(String dbString) throws IOException {
		PrintWriter writer = new PrintWriter(databasePath, "UTF-8");
		writer.println(dbString);
		writer.close();
	}
	
	private static String generateDatabaseJSON() {
		String s = "{\n";
		
		s += "\t\"Schedule\" : " + (schedule == null ? "null" : schedule.toJSON()) + ",\n";
		
		s += "\t\"Teams\" : {\n";
		for (int i=0; i < teams.size(); i++) {
			s += "\t\t" + teams.get(i).getId() + " : " + teams.get(i).toJSON() + (i+1 == teams.size() ? "" : ",") + "\n";
		}
		s += "\t},\n";
		
		s += "\t\"Referees\" : {\n";
		for (int i=0; i < referees.size(); i++) {
			s += "\t\t" + referees.get(i).getId() + " : " + referees.get(i).toJSON() + (i+1 == referees.size() ? "" : ",") + "\n";
		}
		s += "\t}\n";
		
		s += "}";
		return s;
	}
	
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static JSONObject rankingsFromSchedule() {
		Team[] sortedTeams = new Team[teams.size()];
		
		for (int i=0; i < sortedTeams.length; i++) {
			int insertAt = 0;
			for (int j=0; j < i; j++) {
				if (sortedTeams[j].getWins() > teams.get(i).getWins()) {
					insertAt++;
				} else {
					break;
				}
			}
			
			//Shift array over
			for (int j=sortedTeams.length-1; j > insertAt; j--) {
				sortedTeams[j] = sortedTeams[j-1];
			}
			sortedTeams[insertAt] = teams.get(i);
		}
		
		JSONObject rankings = new JSONObject();
		
		int ranking = 1;
		double lastScore = sortedTeams[0].getWins();
		for (int i=0; i < sortedTeams.length; i++) {
			double thisScore = sortedTeams[i].getWins();
			if (thisScore != lastScore) {
				lastScore = thisScore;
				ranking = i+1;
			}
			
			JSONObject data = new JSONObject();
			data.put("Name", sortedTeams[i].getName());
			data.put("Wins", sortedTeams[i].getWins());
			data.put("Rank", ranking);
			rankings.put(""+i, data);
		}
		
		return rankings;
	}
}
