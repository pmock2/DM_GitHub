package teamHarambe;

import java.io.File;
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
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Calendar;
import java.security.*;

import org.json.JSONObject;

public class Server {
	private static Scanner console = new Scanner(System.in);
	private static String databasePath = "resources/Database.json";
	
	public static List<Team> teams = new LinkedList<>();
	public static List<Referee> referees = new LinkedList<>();
	public static Schedule schedule;
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		ServerSocket server = new ServerSocket(1234);
		loadData();
		generateRefereeList(10);
		
		System.out.println("Server ready to accept clients.");
		
		while (true) {
			Socket s = server.accept();
			Runnable connectionHandler = new ConnectionHandler(s);
			new Thread(connectionHandler).start();
		}
	}
	
	private static void loadData() throws IOException, NoSuchAlgorithmException {
		File dbFile = new File(databasePath);
		if (dbFile.exists()) {
			JSONObject database = new JSONObject(readFile(databasePath, StandardCharsets.UTF_8));
			loadTeams(database.getJSONObject("Teams"));
			loadReferees(database.getJSONObject("Referees"));
			loadSchedule(database.getJSONObject("Schedule"));
			System.out.println(schedule.toString());
		}
	}
	
	private static void loadTeams(JSONObject teamList) {
		String[] teamIds = JSONObject.getNames(teamList);
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
			
			referees.add(new Referee (
				refereeId, refereeData.getString("Email"), refereeData.getString("Password"), refereeData.getBoolean("IsSuper")
			));
		}
	}
	
	private static void loadSchedule(JSONObject weekList) {
		String[] weekNames = JSONObject.getNames(weekList);
		Week[] weekArray = new Week[weekNames.length];
		
		for (int i=0; i < weekNames.length; i++) {
			Match[] matches = Week.parseWeekMatches(weekList.getJSONObject(weekNames[i]));
			int weekNumber = Integer.parseInt(weekNames[i].replaceAll("\\D+", ""));
			weekArray[weekNumber] = new Week(matches);
		}
		
		schedule = new Schedule(weekArray);
	}
	
	public static void saveData() throws IOException {
		PrintWriter writer = new PrintWriter(databasePath, "UTF-8");
		String dbString = generateDatabaseJSON();
		writer.println(dbString);
		writer.close();
	}
	
	private static String generateDatabaseJSON() {
		String s = "{\n";
		
		s += "\t\"Schedule\" : " + schedule.toJSON() + ",\n";
		
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

	private static int promptInt(int minValue) {
		int input = 0;
		
		do {
			try {
				input =  console.nextInt();
				if (input < minValue) {
					System.out.print("Please input an integer >= " + minValue + ": ");
				}
			}
			catch (NumberFormatException e) {
				System.out.print("Please input an integer >= " + minValue + ": ");
			}
		} while (input < minValue);
		
		return input;
	}
	
	private static void generateRefereeList(int numReferees) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		Random r = new Random();
		
		for (int i=0; i < numReferees; i ++) {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update("password".getBytes(), 0, "password".length());
			String md5 = new BigInteger(1, md.digest()).toString(16); // Hash value
			referees.add(new Referee(r.nextInt(999999999) + 111111111, "email", md5, i==0));
		}
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
