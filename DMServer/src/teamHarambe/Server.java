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
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.security.*;

import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.*;

public class Server {	
	public static Map<Integer,Referee> referees = new HashMap<>();
	public static Map<Integer,Season> seasons = new HashMap<>();
	public static int activeSeason;
	
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
	
	private static void loadData() throws IOException, NoSuchAlgorithmException {
		File dbFile = new File(databasePath);
		if (!dbFile.exists()) {
			createDatabase();
		}
		
		JSONObject database = new JSONObject(readFile(databasePath, StandardCharsets.UTF_8));
		try {activeSeason = database.getInt("ActiveSeason");} catch (Exception e) {}
		loadReferees(database.getJSONObject("Referees"));
		loadSeasons(database.getJSONObject("Seasons"));
		
	}
	
	private static void createDatabase() throws IOException {
		System.out.println("Creating database");
		String dbString = "";
		dbString += "{\n";
		dbString += "\t\"ActiveSeason\" : null,\n";
		dbString += "\t\"Seasons\" : {},\n";
		dbString += "\t\"Referees\" : {\n";
		dbString += "\t\t0 : " + new Referee(0, "DMSuperReferee@mail.com", true).toJSON() + "\n";
		dbString += "\t}\n";
		dbString += "}";
		
		saveData(dbString);
	}
	
	public static void saveData() throws IOException {
		saveData(generateDatabaseJSON());
	}
	
	private static void saveData(String dbString) throws IOException {
		PrintWriter writer = new PrintWriter(databasePath, "UTF-8");
		writer.println(dbString);
		writer.close();
	}
	
	private static String generateDatabaseJSON() {
		String s = "{\n";
		int i;
	
		s += "\t\"ActiveSeason\" : " + (seasons.get(activeSeason) == null ? "null" : activeSeason) + ",\n";
		s += "\t\"Seasons\" : {\n";
		
		i=0;
		for (Map.Entry<Integer, Season> entry : seasons.entrySet()) {
			int id = entry.getKey();
			Season season = entry.getValue();
			s += "\t\t" + id + " : " + season.toJSON() + (i+1 == seasons.size() ? "" : ",") + "\n";
			i++;
		}
		
		s += "\t},\n";
		s += "\t\"Referees\" : {\n";
		
		i=0;
		for (Map.Entry<Integer, Referee> entry : referees.entrySet()) {
			int id = entry.getKey();
			Referee referee = entry.getValue();
			s += "\t\t" + id + " : " + referee.toJSON() + (i+1 == referees.size() ? "" : ",") + "\n";
			i++;
		}
		
		s += "\t}\n";
		s += "}";
		
		return s;
	}
	
	public static List<Referee> getNonSupers() {
		List<Referee> nonSupers = new LinkedList<>();
		for (Map.Entry<Integer, Referee> entry : referees.entrySet()) {
			Referee referee = entry.getValue();
			if (!referee.isSuperReferee) {
				nonSupers.add(referee);
			}
		}
		
		return nonSupers;
	}
	
	public static Season getActiveSeason() {
		return seasons.get(activeSeason);
	}
	
	private static void loadReferees(JSONObject refereeList) {
		String[] refereeIds = JSONObject.getNames(refereeList);
		for (int i=0; i < refereeIds.length; i ++) {
			JSONObject refereeData = refereeList.getJSONObject(refereeIds[i]);
			int refereeId = Integer.parseInt(refereeIds[i]);
			referees.put(refereeId, new Referee (refereeId, refereeData));
		}
	}
	
	private static void loadSeasons(JSONObject seasonList) {
		String[] seasonIds = JSONObject.getNames(seasonList);
		seasonIds = (seasonIds == null ? new String[0] : seasonIds);
		for (int i=0; i < seasonIds.length; i ++) {
			JSONObject seasonData = seasonList.getJSONObject(seasonIds[i]);
			int seasonId = Integer.parseInt(seasonIds[i]);
			seasons.put(seasonId, new Season(seasonId, seasonData));
		}
	}
	
	public static void addSeason(Season season) {
		seasons.put(season.getId(), season);
		activeSeason = season.getId();
	}
	
	private static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
